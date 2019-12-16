(require '[clojure.string :as str])

(def input (slurp "input.txt"))

(defn parse-ingredient [text]
  (let [[number name] (str/split text #" ")]
    [(read-string number) name]))

(defn parse-reacton [reaction]
  (let [[input output] (str/split reaction #" => ")
        input (map parse-ingredient (str/split input #", "))
        output (parse-ingredient output)]
    [output (vec input)]))

(defn parse-reactions [input]
  (let [lines (str/split-lines input)]
    (into {} (map parse-reacton lines))))

(defn find-required [reactions [target-n target-chem]]
  (let [[req-n req-chem] (first (filter #(= target-chem (second %)) (keys reactions)))
        multiplier (int (Math/ceil (/ target-n req-n)))
        remaining (- (* req-n multiplier) target-n)
        required (map #(update % 0 * multiplier) (reactions [req-n req-chem]))]
    [required {target-chem remaining}]))

(defn run []
  (loop [reactions (parse-reactions input)
         ore 0
         remaining {}
         required (reactions [1 "FUEL"])]
    (if (empty? required)
      ore
      (let [[req-n req-chem] (first required)
            remaining-chem (get remaining req-chem 0)]
        (cond
          (= req-chem "ORE")
          (recur reactions (+ ore req-n) remaining (rest required))
          
          (>= remaining-chem req-n)
          (recur reactions
                 ore
                 (update remaining req-chem - req-n)
                 (rest required))
          
          :else
          (let [[new-required new-remaining] (find-required reactions [(- req-n remaining-chem) req-chem])]
            (recur reactions
                   ore
                   (merge-with + (assoc remaining req-chem 0) new-remaining)
                   (concat new-required (rest required)))))))))