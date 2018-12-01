; (def freq-changes ["+1" "-2" "+3", "+1"])

(def freq-changes
  (clojure.string/split-lines (slurp "input1.txt")))

(reduce + (map #(Integer/parseInt %) freq-changes))