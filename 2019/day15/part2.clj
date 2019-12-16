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

(defn calculate-position [steps]
  (loop [[x y] [0 0]
         steps steps]
    (if (empty? steps)
      [x y]
      (recur (case (first steps)
               1 [x (dec y)]
               2 [x (inc y)]
               3 [(dec x) y]
               4 [(inc x) y])
             (rest steps)))))

(def computer (create-computer (slurp "input.txt")))

(def tank-pos [18 -18])

(def space-map 
  (loop [queue (map #(vector %) [1 2 3 4])
         space-map {}
         visited #{}]
    (let [steps (first queue)
          result (-> (run computer steps) :output last)
          pos (calculate-position steps)]
      (cond
        (empty? queue) space-map
        (contains? visited pos) (recur (rest queue) space-map visited)
        (= result 0) (recur (rest queue) (assoc space-map pos 1) (conj visited pos))
        :else (recur (concat (rest queue)
                             (map #(conj steps %) [1 2 3 4]))
                     (assoc space-map pos 0)
                     (conj visited pos))))))

(def space-count (count (filter #(= (space-map %) 0) (keys space-map))))

(defn expand [[x y]]
  [[(inc x) y] [(dec x) y] [x (inc y)] [x (dec y)]])

(loop [oxygen #{tank-pos}
       minutes 0]
  (if (= (count oxygen) space-count)
    minutes
    (recur (->> (mapcat expand oxygen)
                (filter #(= (space-map %) 0))
                (into oxygen))
           (inc minutes))))

