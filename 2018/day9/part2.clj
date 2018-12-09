(def nplayers 478)
(def last-marble (* 100 71240))

(defn create-circle
  []
  (let [head (transient {:value 0})]
    (assoc! head :next head)
    (assoc! head :prev head)
    head))

(defn move
  [current steps direction]
  (loop [node current 
         step 0]
    (if (= step steps)
      node
      (recur (direction node) (inc step)))))

(defn insert
  [current value]
  (let [next1 (move current 1 :next)
        next2 (move current 2 :next)
        new-node (transient {:value value})]
    (assoc! next1 :next new-node)
    (assoc! next2 :prev new-node)
    (assoc! new-node :prev next1)
    (assoc! new-node :next next2)
    new-node))

(defn remove
  [current]
  (let [prev (:prev current)
        next (:next current)]
    (assoc! prev :next next)
    (assoc! next :prev prev)
    next))

(defn max-score
  []
  (loop [circle (create-circle) 
         current-player -1
         scores (vec (repeat nplayers 0))
         marbles (drop 1 (range))]
    (let [marble (first marbles)]
      (if (zero? (mod marble 23))
        (let [to-remove (move circle 7 :prev)]
          (recur (remove to-remove)
                 (mod (inc current-player) nplayers)
                 (update scores
                         current-player #(+ % marble (:value to-remove)))
                 (rest marbles)))
        (if (<= marble last-marble)
          (recur (insert circle marble)
                 (mod (inc current-player) nplayers)
                 scores
                 (rest marbles))
          (apply max scores))))))