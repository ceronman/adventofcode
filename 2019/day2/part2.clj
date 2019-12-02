(def input
  (slurp "input.txt"))

; (def input "1,1,1,4,99,5,6,0,99")

(defn create-computer [memory]
  {:pc 0 :memory (vec memory)})

(defn read-word [computer address]
  (get-in computer [:memory address]))

(defn store-word [computer address value]
  (assoc-in computer [:memory address] value))

(defn read-word-relative [computer offset]
  (read-word computer (+ (:pc computer) offset)))

(defn binary-op [computer op addr1 addr2 addr3]
  (store-word computer addr3 (op (read-word computer addr1) (read-word computer addr2))))

(defn next-instruction [computer]
  (update computer :pc (partial + 4)))

(defn execute-program [computer-x]
  (loop [computer computer-x]
    (let [instruction (read-word-relative computer 0)
          addr1 (read-word-relative computer 1)
          addr2 (read-word-relative computer 2)
          addr3 (read-word-relative computer 3)]
      (case instruction
        1 (recur (next-instruction (binary-op computer + addr1 addr2 addr3)))
        2 (recur (next-instruction (binary-op computer * addr1 addr2 addr3)))
        computer))))

(defn execute-with-input [computer noun verb]
  (let [initial-program (store-word (store-word computer 1 noun) 2 verb)
        result (execute-program initial-program)]
    [(read-word result 0) noun verb]))

(def program (->> (clojure.string/split input #",")
                  (map read-string)
                  (create-computer)))

(first
 (filter (fn [result] (= (first result) 19690720))
         (for [noun (range 100)
               verb (range 100)]
           (execute-with-input program noun verb))))

