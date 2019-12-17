(def input (slurp "input.txt"))

(defn pattern [pos]
  (let [base [0 1 0 -1]
        repeated (mapcat #(repeat (inc pos) %) base)]
    (rest (cycle repeated))))

(defn digits [value]
  (map #(Character/digit % 10) (str value)))

(defn last-digit [value]
  (mod (if (neg? value) (- value) value) 10))

(defn calculate-pos [signal pos]
  (->> (map * signal (pattern pos))
       (reduce +)
       (last-digit)))

(defn fft [signal max-phase]
  (loop [phase 0
         signal signal]
    (if (= phase max-phase)
      (apply str signal)
      (recur (inc phase)
             (map #(calculate-pos signal %) (range (count signal)))))))

(subs (fft (digits input) 100) 0 8)

