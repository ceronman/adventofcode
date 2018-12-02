; (def input ["abcde"
;             "fghij"
;             "klmno"
;             "pqrst"
;             "fguij"
;             "axcye"
;             "wvxyz"])

(def input
  (clojure.string/split-lines (slurp "input1.txt")))

(defn common-chars
  [s1 s2]
  (->>
   (map vector (seq s1) (seq s2))
   (filter (fn [[c1 c2]] (= c1 c2)))
   (map first)
   (apply str)))

(loop [words input]
  (let [current-word (first words)
        expected-lenght (- (count current-word) 1)
        common-chars-list (map #(common-chars current-word %) (rest words))
        match (first (filter (fn [common] (= (count common) expected-lenght)) 
                             common-chars-list))]
    (if match
      match
      (recur (rest words)))))
