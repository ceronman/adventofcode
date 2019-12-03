; (def input "R8,U5,L5,D3\nU7,R6,D4,L4")
; (def input "R75,D30,R83,U83,L12,D49,R71,U7,L72\nU62,R66,U55,R34,D71,R55,D58,R83")
(def input (slurp "input.txt"))

(defn parse-move [move]
  [(subs move 0 1) (read-string (subs move 1))])

(defn parse-wire [wire-string]
  (->> (clojure.string/split wire-string #",")
       (map parse-move)))

(defn parse-input [input]
  (->> (clojure.string/split-lines input)
       (map parse-wire)))

(defn trace [[cursor visited] steps index direction]
  (->> (range steps)
       (map (fn [step] (assoc cursor index (direction (get cursor index) (inc step)))))
       (into visited)
       (conj [(assoc cursor index (direction (get cursor index) steps))])))

(defn trace-move [grid [direction steps]]
  (case direction
    "R" (trace grid steps 0 +)
    "L" (trace grid steps 0 -)
    "U" (trace grid steps 1 +)
    "D" (trace grid steps 1 -)))

(defn trace-moves [grid moves]
  (reduce (fn [grid move] (trace-move grid move))
          grid
          moves))

(defn manhattan-distance [pos]
  (->> pos
       (map #(Math/abs %))
       (apply +)))

(let [grid [[0 0] #{}]
      [moves1 moves2] (parse-input input)
      [_ visited1] (trace-moves grid moves1)
      [_ visited2] (trace-moves grid moves2)]
  (->> (clojure.set/intersection visited1 visited2)
       (map manhattan-distance)
       (apply min)))
