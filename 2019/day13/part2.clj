(defn create-computer [program]
  (let [instructions (map read-string (clojure.string/split program #","))]
    {:pc 0
     :relative-base 0
     :memory (into {} (map-indexed vector (map bigint instructions)))
     :output []
     :input []
     :halted false}))

(defn read-word [computer address]
  (get-in computer [:memory address] 0))

(defn store-word [computer address value]
  (assoc-in computer [:memory address] (bigint value)))

(defn read-word-relative [computer offset]
  (read-word computer (+ (:pc computer) offset)))

(defn parse-opcode [computer]
  (let [opcode (read-word-relative computer 0)
        instruction (mod opcode 100)
        modes (quot opcode 100)]
    {:instruction instruction
     :modes modes}))

(defn parameter-mode [modes pos]
  (int (mod (quot modes (Math/pow 10 (dec pos))) 10)))

(defn parameter-address [computer position]
  (case (parameter-mode (:modes (parse-opcode computer)) position)
    0 (read-word-relative computer position)
    2 (+ (:relative-base computer) (read-word-relative computer position))
    (throw (Exception. "Unknown parameter address"))))

(defn parameter-value [computer position]
  (case (parameter-mode (:modes (parse-opcode computer)) position)
    0 (read-word computer (parameter-address computer position))
    1 (read-word-relative computer position)
    2 (read-word computer (parameter-address computer position))
    (throw (Exception. "Unknown parameter mode"))))

(defn advance [computer size]
  (update computer :pc (partial + size)))

(defn binary-op [computer f]
  (let [value1 (parameter-value computer 1)
        value2 (parameter-value computer 2)
        result (f value1 value2)
        dest (parameter-address computer 3)]
    (-> computer
        (store-word dest result)
        (advance 4))))

(defn input-op [computer]
  (let [input-value (first (:input computer))]
    (-> (update computer :input rest)
        (store-word (parameter-address computer 1) (bigint input-value))
        (advance 2))))

(defn output-op [computer]
  (-> computer
      (update :output conj (parameter-value computer 1))
      (advance 2)))

(defn adjust-relative-base-op [computer]
  (-> computer
      (update :relative-base + (parameter-value computer 1))
      (advance 2)))

(defn jump-op [computer pred]
  (if (pred (parameter-value computer 1))
    (assoc computer :pc (parameter-value computer 2))
    (advance computer 3)))

(defn run [computer input]
  (loop [computer (assoc computer :input (map bigint input) :output [])]
    (let [opcode (parse-opcode computer)]
      (case (:instruction opcode)
        1 (recur (binary-op computer +))
        2 (recur (binary-op computer *))
        3 (if (empty? (:input computer))
            computer ; yield to scheduler
            (recur (input-op computer)))
        4 (recur (output-op computer))
        5 (recur (jump-op computer (complement zero?)))
        6 (recur (jump-op computer zero?))
        7 (recur (binary-op computer #(if (< %1 %2) (bigint 1) (bigint 0))))
        8 (recur (binary-op computer #(if (= %1 %2) (bigint 1) (bigint 0))))
        9 (recur (adjust-relative-base-op computer))
        99 (assoc computer :halted true)
        (throw (Exception. "Unknown instruction"))))))

(defn read-screen [computer]
  (into {} (map (fn [[x y i]] [[x y] i]) (partition 3 (:output computer)))))

(defn render-tile [tile]
  (case tile
    0 " "
    1 "#"
    2 "*"
    3 "-"
    4 "o"
    "?"))

(defn render-frame [screen]
  (let [score (first (filter #(= (first (first %)) -1) screen))
        tiles (dissoc screen (first score))
        min-x (apply min (map first (keys tiles)))
        max-x (apply max (map first (keys tiles)))
        min-y (apply min (map second (keys tiles)))
        max-y (apply max (map second (keys tiles)))
        range-x (range min-x (inc max-x))
        range-y (range min-y (inc max-y))
        lines (map (fn [y]
                     (apply str (map (fn [x] (render-tile (tiles [x y]))) range-x)))
                   range-y)]
    (str (clojure.string/join "\n" lines)
         "\n\nscore = " (second score))))

(defn read-joystick []
  (case (read-line)
    "a" [-1]
    "d" [1]
    [0]))

(defn run-game [input]
  (loop [computer (-> (create-computer (slurp "input.txt"))
                      (store-word 0 2)
                      (run input))
         screen (read-screen computer)]
    (println (render-frame screen))
    (if (:halted computer)
      (first (filter #(= (first (first %)) -1) screen))
      (let [[ball] (first (first (filter #(= 4 (second %)) screen)))
            [paddle] (first (first (filter #(= 3 (second %)) screen)))
            move [(compare ball paddle)]; (read-joystick)
            next (run computer move)]
        (recur next
               (merge screen (read-screen next)))))))