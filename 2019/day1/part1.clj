(def input
  (clojure.string/split-lines (slurp "input.txt")))

(defn fuel
  [mass]
  (- (quot mass 3) 2))

(->> input
     (map read-string)
     (map fuel)
     (reduce +))

