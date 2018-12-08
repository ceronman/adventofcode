; (def input "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")
(def input (slurp "input.txt"))

(defn node-value
  [node]
  (let [[n-children n-metadata] (take 2 node)]
    (if (zero? n-children)
      [(+ 2 n-metadata) (reduce + (take n-metadata (drop 2 node)))]
      (loop [i 0
             total 2
             all-metadata []]
        (if (< i n-children)
          (let [[size metadata] (node-value (drop total node))]
            (recur (inc i) (+ total size) (conj all-metadata metadata)))
          (let [indexes (map dec (take n-metadata (drop total node)))]
            [(+ total n-metadata)
             (reduce + (map #(get all-metadata % 0) indexes))]))))))


(second (node-value (map read-string (clojure.string/split input #" "))))