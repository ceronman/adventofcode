; (def input ["1, 1"
;             "1, 6"
;             "8, 3"
;             "3, 4"
;             "5, 5"
;             "8, 9"])

(def input
  (clojure.string/split-lines (slurp "input.txt")))

(defn parse-input
  [line]
  (->> (re-matches #"(\d+), (\d+)" line)
       (rest)
       (map read-string)))

(defn find-bounds
  [coords]
  (let [left (apply min (map first coords))
        top (apply min (map second coords))
        right (apply max (map first coords))
        bottom (apply max (map second coords))]
       [[left top] [right bottom]]))

(defn expand-points
  [[left top] [right bottom]]
  (for [x (range left (inc right))
        y (range top (inc bottom))]
       [x y]))

(defn distance
  [[x1 y1] [x2 y2]]
  (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2))))

(defn point-distances
  [point coords]
  (map #(distance point %) coords))

(defn closest-coord
  [distances]
  (let [min-distance (apply min distances)
        closest (filter #(= min-distance %) distances)]
       (when (= (count closest) 1)
         (first (keep-indexed #(when (= min-distance %2) %1) distances)))))

(defn in-edge?
  [[x y] [[left top] [right bottom]]]
  (or (#{left right} x)
      (#{top bottom} y)))

(defn coords-infinite-area
  [all-points closest-coords bounds]
  (->> (map vector all-points closest-coords)
       (map (fn [[point coord]] (when (in-edge? point bounds) coord)))
       (remove nil?)
       (into #{})))
      
(let [coords (vec (map parse-input input))
      bounds (find-bounds coords)
      all-points (apply expand-points bounds)
      points-distances (map #(point-distances % coords) all-points)
      closest-coords (map closest-coord points-distances)
      infinite (coords-infinite-area all-points closest-coords bounds)
      finite (remove nil? (remove infinite closest-coords))
      areas (frequencies finite)]
  (apply max (vals areas)))

