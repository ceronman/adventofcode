; (def input ["abcdef"
;             "bababc"
;             "abbcde"
;             "abcccd"
;             "aabcdd"
;             "abcdee"
;             "ababab"])

(def input
  (clojure.string/split-lines (slurp "input1.txt")))

(defn count-repeated
  [lines expected-count]
  (->> input
       (map frequencies)
       (map vals)
       (map set)
       (filter (fn [repetitions] (contains? repetitions expected-count)))
       (count)))

(* (count-repeated input 2) (count-repeated input 3))