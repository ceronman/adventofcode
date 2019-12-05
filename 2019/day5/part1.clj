(def input (slurp "input.txt"))

; (def input "3,0,4,0,99")

(defn create-computer [memory]
  {:pc 0 :memory (vec memory) :output []})

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
  (-> computer
      (store-word (read-word-relative computer 1) 1)
      (advance 2)))

(defn output-op [computer]
  (-> computer
      (update :output conj (parmeter-value computer 1))
      (advance 2)))


(loop [computer (->> (clojure.string/split input #",")
                     (map read-string)
                     (create-computer))]
  (let [opcode (parse-opcode computer)]
    (case (:instruction opcode)
      1 (recur (binary-op computer +))
      2 (recur (binary-op computer *))
      3 (recur (input-op computer))
      4 (recur (output-op computer))
      (:output computer))))
