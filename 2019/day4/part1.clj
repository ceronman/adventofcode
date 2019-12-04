(defn possible-password? [value]
  (and
   (apply <= (map #(Character/digit % 10) (str value)))
   (some? (re-find #"(\d)\1" (str value)))))

(->> (range 273025 (inc 767253))
    (filter possible-password?)
    (count))