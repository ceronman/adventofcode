(defn create-computer [program input]
  (let [memory (map read-string (clojure.string/split program #","))]
    {:pc 0 
     :memory (vec memory) 
     :output [] 
     :input input
     :halted false}))

(defn read-word [computer address]
  (get-in computer [:memory address]))

(defn store-word [computer address value]
  (assoc-in computer [:memory address] value))

(defn read-word-relative [computer offset]
  (read-word computer (+ (:pc computer) offset)))

(defn parse-opcode [computer]
  (let [opcode (read-word-relative computer 0)
        instruction (mod opcode 100)
        modes (quot opcode 100)]
    {:instruction instruction
     :modes modes}))

(defn immediate-mode? [modes pos]
  (= 1.0 (mod (quot modes (Math/pow 10 (dec pos))) 10)))

(defn parmeter-value [computer position]
  (if (immediate-mode? (:modes (parse-opcode computer)) position)
    (read-word-relative computer position)
    (read-word computer (read-word-relative computer position))))

(defn advance [computer size]
  (update computer :pc (partial + size)))

(defn binary-op [computer f]
  (let [value1 (parmeter-value computer 1)
        value2 (parmeter-value computer 2)
        result (f value1 value2)
        dest (read-word-relative computer 3)]
    (-> computer
        (store-word dest result)
        (advance 4))))

(defn input-op [computer]
  (let [input-value (first (:input computer))]
    (-> (update computer :input rest)
        (store-word (read-word-relative computer 1) input-value)
        (advance 2))))

(defn output-op [computer]
  (-> computer
      (update :output conj (parmeter-value computer 1))
      (advance 2)))

(defn jump-op [computer pred]
  (if (pred (parmeter-value computer 1))
    (assoc computer :pc (parmeter-value computer 2))
    (advance computer 3)))

(defn run [computer]
  (loop [computer computer]
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
        7 (recur (binary-op computer #(if (< %1 %2) 1 0)))
        8 (recur (binary-op computer #(if (= %1 %2) 1 0)))
        (assoc computer :halted true)))))

(defn add-input [computer input]
  (update computer :input conj input))

(defn scheduler-run [computers]
  (loop [index 0
         computers computers]
    (let [current-computer (run (computers index))
          next-index (mod (inc index) (count computers))
          next-computer (add-input (computers next-index) (last (:output current-computer)))]
      (if (every? :halted computers)
        (last computers)
        (recur next-index
               (-> computers
                   (assoc index current-computer)
                   (assoc next-index next-computer)))))))

(defn permutations [colls]
  (if (= 1 (count colls))
    (list colls)
    (for [head colls
          tail (permutations (disj (set colls) head))]
      (cons head tail))))

(defn create-computers [program sequence]
  (into [] (map #(create-computer program [%]) sequence)))


(def input (slurp "input.txt"))
; (def input "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5")

(defn run-sequence [program sequence]
 (let [computers (create-computers program sequence)
       computers (update computers 0 add-input 0)]
   (last (:output (scheduler-run computers)))))

(->> (permutations (range 5 10))
     (map #(run-sequence input %))
     (apply max))
