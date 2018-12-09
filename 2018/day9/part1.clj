(def nplayers 478)
(def last-marble 71240)

(defn insert-at
  [array pos item]
  (vec (concat (subvec array 0 pos) [item] (subvec array pos))))

(defn remove-at
  [array pos]
  (vec (concat (subvec array 0 pos) (subvec array (inc pos)))))

(defn max-score
  []
  (loop [circle [0]
         current-marble 0
         current-player -1
         scores (vec (repeat nplayers 0))
         marbles (drop 1 (range))]
    (let [marble (first marbles)
          circle-size (count circle)
          pos (inc (mod (inc current-marble) circle-size))]
      (if (zero? (mod marble 23))
        (let [removed-pos (mod (- current-marble 7) circle-size)]
          (recur (remove-at circle removed-pos)
                 (if (>= removed-pos (dec circle-size)) 0 removed-pos)
                 (mod (inc current-player) nplayers)
                 (update scores
                         current-player #(+ % marble (get circle removed-pos)))
                 (rest marbles)))
        (if (<= marble last-marble)
          (recur (insert-at circle pos marble)
                 pos
                 (mod (inc current-player) nplayers)
                 scores
                 (rest marbles))
          (apply max scores))))))