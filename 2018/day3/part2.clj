; (def input ["#1 @ 1,3: 4x4"
;             "#2 @ 3,1: 4x4"
;             "#3 @ 5,5: 2x2"])

(def input
  (clojure.string/split-lines (slurp "input.txt")))

(defn parse-square
  [line]
  (->> (re-matches #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" line)
       (rest)
       (map read-string)
       (interleave [:id :left :top :width :height])
       (apply assoc {})))

(defn points-used
  [{left :left top :top width :width height :height}]
  (for [x (range left (+ left width))
        y (range top (+ top height))]
    [x y]))

(let [claims (map parse-square input)
      points-by-claim (map (juxt :id points-used) claims)
      freqs (frequencies (apply concat (map second points-by-claim)))]
  (->> points-by-claim
       (filter (fn [[id points]] (every? #(= 1 (get freqs %)) points)))
       (first)
       (first)))
