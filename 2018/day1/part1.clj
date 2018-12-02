; (def input ["+1" "-2" "+3", "+1"])

(def input
  (clojure.string/split-lines (slurp "input1.txt")))

(reduce + (map #(Integer/parseInt %) input))