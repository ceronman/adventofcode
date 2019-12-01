(def input
  (clojure.string/split-lines (slurp "input.txt")))

(defn fuel
  [mass]
  (- (quot mass 3) 2))

(defn total-fuel
  [mass]
  (loop [mass mass
         total-fuel 0]
    (let [fuel (fuel mass)]
      (if (> fuel 0)
        (recur fuel (+ total-fuel fuel))
        total-fuel))))

(->> input
     (map read-string)
     (map total-fuel)
     (reduce +))