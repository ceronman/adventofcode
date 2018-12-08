; (def input "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")
(def input (slurp "input.txt"))

(defn node-size-metadata
  [node]
  (let [[n-children n-metadata] (take 2 node)]
    (if (zero? n-children)
      [(+ 2 n-metadata) (take n-metadata (drop 2 node))]
      (loop [i 0
             total 2
             all-metadata []]
        (if (< i n-children)
          (let [[size metadata] (node-size-metadata (drop total node))]
            (recur (inc i) (+ total size) (concat all-metadata metadata)))
          [(+ total n-metadata) (concat all-metadata (take n-metadata (drop total node)))])))))


(reduce + (second (node-size-metadata (map read-string (clojure.string/split input #" ")))))