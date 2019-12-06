(require '[clojure.string :as str])

(def input (slurp "input.txt"))

(defn load-orbits [input]
  (->> input
       (str/split-lines)
       (map #(reverse (str/split % #"\)")))
       (reduce (fn [orbits [from to]] (assoc orbits from to)) {})))

(defn count-orbits [orbits object]
  (loop [o object
         count 0]
    (if (= o "COM")
      count
      (recur (get orbits o) (inc count)))))

(let [orbits (load-orbits input)
      objects (keys orbits)
      counts (map #(count-orbits orbits %) objects)]
  (reduce + counts))