(def input 8561)

(defn power
  [serial x y]
  (let [rack-id (+ x 10)
        x (* (+ (* rack-id y) serial) rack-id)
        hundreds (mod (quot x 100) 10)]
    (- hundreds 5)))

(defn make-grid
  [serial]
  (into {} (for [x (range 1 301) y (range 1 301)] [[x y] (power serial x y)])))

(defn square-power
  [grid left top]
  (let [cells (for [dx (range 3) dy (range 3)] [(+ left dx) (+ top dy)])]
    (reduce + (map #(get grid %) cells))))

(let [grid (make-grid input)
      corners (for [x (range 1 298) y (range 1 298)] [x y])
      powers (map (fn [[x y]] [[x y] (square-power grid x y)]) corners)]
  (apply max-key second powers))