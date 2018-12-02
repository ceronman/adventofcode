; (def input ["+7" "+7" "-2" "-7" "-4"])

(def input
  (clojure.string/split-lines (slurp "input1.txt")))

(loop [prev 0
       values (cycle (map #(Integer/parseInt %) input))
       seen #{0}]
  (let [current (+ prev (first values))]
    (if (contains? seen current)
      current
      (recur current (rest values) (conj seen current)))))