; (def freq-changes ["+7" "+7" "-2" "-7" "-4"])

(def freq-changes
  (clojure.string/split-lines (slurp "input1.txt")))

(let [changes (cycle (map #(Integer/parseInt %) freq-changes))
      starting 0]
  (loop [prev starting
         values changes
         seen #{starting}]
    (let [current (+ prev (first values))]
      (if (contains? seen current)
        current
        (recur current (rest values) (conj seen current))))))