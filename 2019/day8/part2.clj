(def input (map #(Character/digit % 10) (slurp "input.txt")))
; (def input (map #(Character/digit % 10) "0222112222120000"))

(def width 25)
(def height 6)
(def pixel->char {0 " " 1 "#" 2 " "})

(defn blend-colors [colors]
  (reduce #(if (= %1 2) %2 %1) 2 colors))

(let [size (* width height)
      layers (partition size input)
      pixels (partition (count layers) (apply interleave layers))
      pixels (map blend-colors pixels)
      pixels (map pixel->char pixels)
      lines (partition width pixels)
      lines (map clojure.string/join lines)]
  (map println lines))