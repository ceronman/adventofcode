; (def input ["position=< 9,  1> velocity=< 0,  2>"
;             "position=< 7,  0> velocity=<-1,  0>"
;             "position=< 3, -2> velocity=<-1,  1>"
;             "position=< 6, 10> velocity=<-2, -1>"
;             "position=< 2, -4> velocity=< 2,  2>"
;             "position=<-6, 10> velocity=< 2, -2>"
;             "position=< 1,  8> velocity=< 1, -1>"
;             "position=< 1,  7> velocity=< 1,  0>"
;             "position=<-3, 11> velocity=< 1, -2>"
;             "position=< 7,  6> velocity=<-1, -1>"
;             "position=<-2,  3> velocity=< 1,  0>"
;             "position=<-4,  3> velocity=< 2,  0>"
;             "position=<10, -3> velocity=<-1,  1>"
;             "position=< 5, 11> velocity=< 1, -2>"
;             "position=< 4,  7> velocity=< 0, -1>"
;             "position=< 8, -2> velocity=< 0,  1>"
;             "position=<15,  0> velocity=<-2,  0>"
;             "position=< 1,  6> velocity=< 1,  0>"
;             "position=< 8,  9> velocity=< 0, -1>"
;             "position=< 3,  3> velocity=<-1,  1>"
;             "position=< 0,  5> velocity=< 0, -1>"
;             "position=<-2,  2> velocity=< 2,  0>"
;             "position=< 5, -2> velocity=< 1,  2>"
;             "position=< 1,  4> velocity=< 2,  1>"
;             "position=<-2,  7> velocity=< 2, -2>"
;             "position=< 3,  6> velocity=<-1, -1>"
;             "position=< 5,  0> velocity=< 1,  0>"
;             "position=<-6,  0> velocity=< 2,  0>"
;             "position=< 5,  9> velocity=< 1, -2>"
;             "position=<14,  7> velocity=<-2,  0>"
;             "position=<-3,  6> velocity=< 2, -1>"])

(def input
  (clojure.string/split-lines (slurp "input.txt")))

(defn parse-line
  [line]
  (let [match (re-matches 
               #"position=<\s*(-?\d+),\s*(-?\d+)> velocity=<\s*(-?\d+),\s*(-?\d+)>" 
               line)
        [x y vx vy] (map read-string (rest match))]
    {:pos [x y] :vel [vx vy]}))

(defn bounds
  [positions]
  (let [left (apply min (map first positions))
        right (apply max (map first positions))
        top (apply min (map second positions))
        bottom (apply max (map second positions))]
       [[left top] [right bottom]]))

(defn print-msg
  [lights]
  (let [positions (set (map :pos lights))
        [[left top] [right bottom]] (bounds positions)]
    (doseq [y (range top (inc bottom))]
      (println (clojure.string/join 
                (map (fn [x] (if (positions [x y]) "#" ".")) 
                     (range left (inc right))))))))

(defn move
  [lights]
  (map (fn [{pos :pos vel :vel}]
         {:pos (vec (map + pos vel))
          :vel vel})
       lights))

(defn max-distance
  [lights]
  (let [[[left top] [right bottom]] (bounds (map :pos lights))]
       (max (- right left) (- bottom top))))

(defn play
  []
  (loop [continue? (read-line)
         lights (map parse-line input)]
    (if (empty? continue?)
      (if (> (max-distance lights) 200)
        (recur "" (move lights))
        (do
          (print-msg lights)
          (recur (read-line) (move lights))))
      nil)))
