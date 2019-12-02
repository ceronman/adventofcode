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

(defn set-1202 [computer]
  (store-word (store-word computer 1 12) 2 2))

(->> (clojure.string/split input #",")
     (map read-string)
     (create-computer)
     (set-1202)
     (execute-program))

