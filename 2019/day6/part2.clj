(require '[clojure.string :as str])

(def input (slurp "input.txt"))

(defn add-orbit [orbits [from to]]
  (-> orbits
      (update from (fnil conj #{}) to)
      (update to (fnil conj #{}) from)))

(defn load-orbits [input]
  (->> input
       (str/split-lines)
       (map #(reverse (str/split % #"\)")))
       (reduce add-orbit {})))

(defn shortest-path [orbits start end]
  (loop [queue [[0 start #{}]]]
    (let [[steps object visited] (first queue)]
      (if (= object end)
        steps
        (recur (into 
                (rest queue)
                (->> (get orbits object)
                     (filter (complement visited))
                     (map #(vector (inc steps) % (conj visited object))))))))))

(let [orbits (load-orbits input)
      start (first (get orbits "YOU"))
      end (first (get orbits "SAN"))]
  (shortest-path orbits start end))