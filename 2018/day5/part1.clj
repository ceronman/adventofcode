(use '[clojure.string :only [lower-case]])

; (def input "dabAcCaCBAcCcaDA")

(def input (slurp "input.txt"))

(defn cancel-out?
  [c1 c2]
  (and c1 c2 (not= c1 c2) (= (lower-case c1) (lower-case c2))))

(count (reduce
        (fn [result c]
          (if (cancel-out? (last result) c)
            (subs result 0 (dec (count result)))
            (str result c)))
        ""
        input))