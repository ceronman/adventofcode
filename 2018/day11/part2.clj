(defn make-grid
  [serial]
  (into {} (for [x (range 1 301) y (range 1 301)] [[x y] (power serial x y)])))

(def grid (make-grid 8561))

(defn power
  [serial x y]
  (let [rack-id (+ x 10)
        x (* (+ (* rack-id y) serial) rack-id)
        hundreds (mod (quot x 100) 10)]
    (- hundreds 5)))

(defn edge
  [x y size]
  (concat (for [delta (range size)]
            [(+ x (dec size)) (+ y delta)])
          (for [delta (range (dec size))]
            [(+ x delta) (+ y (dec size))])))



(def square-power
  (memoize (fn
             [left top size]
             (if (= size 1)
               (grid [left top])
               (let [cells (edge left top size)]
                 (+ (square-power left top (dec size))
                    (reduce + (map #(get grid %) cells))))))))

(let [; grid (make-grid input)
      corners (for [x (range 1 301) y (range 1 301) s (range 1 301)] [x y s])
      corners (remove (fn [[x y s]] (or (> (+ x s) 300) (> (+ y s) 300))) corners)
      powers (map (fn [[x y s]] [[x y s] (square-power x y s)]) corners)]
  (apply max-key second powers))