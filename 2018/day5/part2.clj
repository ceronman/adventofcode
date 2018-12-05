(use '[clojure.string :only [lower-case]])

; (def input "dabAcCaCBAcCcaDA")

(def input (slurp "input.txt"))

(defn cancel-out?
  [c1 c2]
  (and c1 c2 (not= c1 c2) (= (lower-case c1) (lower-case c2))))

(defn polymer-length
  [string]
  (count (reduce
          (fn [result c]
            (if (cancel-out? (last result) c)
              (subs result 0 (dec (count result)))
              (str result c)))
          ""
          string)))

(defn remove-unit
  [string unit]
  (filter #(not= unit (lower-case %)) string ))

(let [units (set (map lower-case input))]
  (->> units
       (map #(polymer-length (remove-unit input %)))
       (apply min)))