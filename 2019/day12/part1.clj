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

(defn abs [n] (max n (- n)))

(defn total-energy [moon]
  (* (reduce + (map abs (:pos moon)))
     (reduce + (map abs (:vel moon)))))

(->> (parse-moons input)
     (iterate time-step)
     (drop 1000)
     (first)
     (map total-energy)
     (apply +))