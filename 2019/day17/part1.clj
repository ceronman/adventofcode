(require '[clojure.string :as str])

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

(def direction {\^ [-1 0]
                \v [1 0]
                \> [0 1]
                \< [0 -1]})

(defn parse-image [output]
  (vec (map vec (clojure.string/split-lines output))))

(defn find-robot [image]
  (first 
   (for [y (range (count image))
         x (range (count (first image)))
         :when (contains? direction (get-in image [y x]))]
     [[y x] (direction (get-in image [y x]))])))

(defn rotate [[y x] dir]
  (if (pos? dir) [(- x) y] [x (- y)]))

(defn move-robot [image pos dir]
  (loop [dirs [dir (rotate dir 1) (rotate dir -1)]]
    (when-let [dir (first dirs)]
      (let [next-pos (map + pos dir)
            next-cell (get-in image next-pos)]
        (if (= next-cell \#)
          [next-pos dir]
          (recur (rest dirs)))))))

(let [computer (create-computer (slurp "input.txt"))
      camera-output (apply str (map char (:output (run computer []))))
      image (parse-image camera-output)]
  (println camera-output)
  (loop [[pos dir] (find-robot image)
         visited {}]
    (if (nil? pos)
      (->> visited
           (filter #(= 2 (second %)))
           (map #(apply * (first %)))
           (reduce +))
      (recur (move-robot image pos dir) (update visited pos (fnil inc 0))))))