(def input (slurp "input.txt"))

(defn parse-map [input]
  (let [lines (clojure.string/split-lines input)
        width (count (first lines))
        height (count lines)
        asteroids (into #{} (for [x (range width)
                                  y (range height)
                                  :when (= (first "#") (nth (nth lines y) x))]
                              [x y]))]
    {:width width :height height :asteroids asteroids}))

(defn abs [n] (max n (- n)))

(defn distance [[x1 y1] [x2 y2]]
  (Math/sqrt (+ (* (- x1 x2) (- x1 x2)) (* (- y1 y2) (- y1 y2)))))

(defn point-in-between? [a b c]
  (> 0.0000001 (abs (- (+ (distance a c) (distance c b)) (distance a b)))))

(defn blocked? [map [x1 y1] [x2 y2]]
  (let [minx (min x1 x2)
        maxx (max x1 x2)
        miny (min y1 y2)
        maxy (max y1 y2)]

    (not-empty (for [x (range minx (inc maxx))
                     y (range miny (inc maxy))
                     :when (and (point-in-between? [x1 y1] [x2 y2] [x y])
                                (contains? (:asteroids map) [x y])
                                (not= [x y] [x1 y1])
                                (not= [x y] [x2 y2]))]
                 [x y]))))

(defn reachable [map point]
  (count (remove #(blocked? map point %) (disj (:asteroids map) point))))

(let [m (parse-map input)]
  (apply max-key second (map #(vector % (reachable m %)) (:asteroids m))))

