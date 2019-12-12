(def input (slurp "input.txt"))

(defn parse-moon [line]
  (let [groups (re-matches #"<x=(-?\d+), y=(-?\d+), z=(-?\d+)>" line)]
    {:pos (vec (map read-string (rest groups))) :vel [0 0 0]}))

(defn parse-moons [input]
  (map parse-moon (clojure.string/split-lines input)))

(defn apply-gravity [{p1 :pos v1 :vel} {p2 :pos}]
  (let [delta-vel (map compare p2 p1)]
    {:pos p1 :vel (vec (map + v1 delta-vel))}))

(defn apply-gravity-all [moon moons]
  (let [other-moons (remove #(= moon %) moons)]
    (reduce apply-gravity moon other-moons)))

(defn apply-velocity [moon]
  (update moon :pos #(map + % (:vel moon))))

(defn time-step [moons]
  (->> moons
       (map #(apply-gravity-all % moons))
       (map apply-velocity)
       (vec)))

(defn moon-axis [moons index]
  (vec (map vector
            (map #(nth (:pos %) index) moons)
            (map #(nth (:vel %) index) moons))))

(moon-axis (parse-moons input) 0)

(defn axis-repeats-at [initial-moons index]
  (loop [moons (time-step initial-moons)
         n 1]
    (if (= (moon-axis moons index) (moon-axis initial-moons index))
      n
      (recur (time-step moons) (inc n)))))

(defn gcd[a b]
  (if (zero? b)
    a
    (recur b, (mod a b))))

(defn lcm [a b]
  (/ (* a b) (gcd a b)))

(defn lcmv [& v] (reduce lcm v))

(let [repeats-x (axis-repeats-at (parse-moons input) 0)
      repeats-y (axis-repeats-at (parse-moons input) 1)
      repeats-z (axis-repeats-at (parse-moons input) 2)]
  (lcmv repeats-x repeats-y repeats-z))