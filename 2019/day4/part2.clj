(defn two-repeated [value]
  (->> (re-seq #"(\d)\1+" (str value))
       (map first)
       (some #(= 2 (count %)))
       (some?)))

(defn possible-password? [value]
  (and
   (apply <= (map #(Character/digit % 10) (str value)))
   (two-repeated value)))

(->> (range 273025 (inc 767253))
     (filter possible-password?)
     (count))