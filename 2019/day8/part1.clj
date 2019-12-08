(def input (map #(Character/digit % 10) (slurp "input.txt")))

(let [layers (partition (* 25 6) input)
      fewest-zero (apply min-key #(count (filter zero? %)) layers)]
  (* (count (filter #(= % 1) fewest-zero))
     (count (filter #(= % 2) fewest-zero))))