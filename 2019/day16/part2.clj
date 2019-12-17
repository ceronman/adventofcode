(def input (slurp "input.txt"))
; (def input "03081770884921959731165446850517")

(def offset (Integer/parseInt (subs input 0 7)))

(defn digits [value]
  (map #(Character/digit % 10) (str value)))

(def signal (drop offset (apply concat (repeat 10000 (digits input)))))

(let [signal (transient (vec signal))]
  (loop [signal signal
         phase 0]
    (if (= 100 phase)
      (apply str (take 8 (persistent! signal)))
      (recur (loop [i (dec (count signal))
                    signal signal]
               (if (= 0 i)
                 signal
                 (recur (dec i)
                        (assoc! signal 
                                (dec i) 
                                (mod (+ (get signal i) 
                                        (get signal (dec i))) 
                                     10)))))
             (inc phase)))))