; (def input ["[1518-11-01 00:00] Guard #10 begins shift"
;             "[1518-11-01 00:05] falls asleep"
;             "[1518-11-01 00:25] wakes up"
;             "[1518-11-01 00:30] falls asleep"
;             "[1518-11-01 00:55] wakes up"
;             "[1518-11-01 23:58] Guard #99 begins shift"
;             "[1518-11-02 00:40] falls asleep"
;             "[1518-11-02 00:50] wakes up"
;             "[1518-11-03 00:05] Guard #10 begins shift"
;             "[1518-11-03 00:24] falls asleep"
;             "[1518-11-03 00:29] wakes up"
;             "[1518-11-04 00:02] Guard #99 begins shift"
;             "[1518-11-04 00:36] falls asleep"
;             "[1518-11-04 00:46] wakes up"
;             "[1518-11-05 00:03] Guard #99 begins shift"
;             "[1518-11-05 00:45] falls asleep"
;             "[1518-11-05 00:55] wakes up"])

(def input
  (clojure.string/split-lines (slurp "input.txt")))

(defn parse-line
  [line]
  (let [match (re-matches #"^\[(\d+)-(\d+)-(\d+) (\d+):(\d+)\] (.*)$" line)
        datetime (->> match (drop 1) (take 5) (map #(Integer/parseInt %)) (vec))
        message (last match)]
    {:datetime datetime :message message}))

(defn parse-guard-id
  [entry]
  (let [match (re-matches #"Guard #(\d+) begins shift" (:message entry))]
       (if match
         (read-string (second match))
         nil)))

(defn sleep-periods
  [entries]
  (loop [entries entries
         current-sleep nil
         result []]
    (if (empty? entries)
      result
      (let [entry (first entries)
            guard-id (parse-guard-id entry)
            falls-asleep (= "falls asleep" (:message entry))
            wakes-up (= "wakes up" (:message entry))
            [year month day hour minute] (:datetime entry)]
        (cond
          guard-id (recur (rest entries)
                          {:id guard-id :date [month day]}
                          result)
          falls-asleep (recur (rest entries)
                              (assoc current-sleep :start minute)
                              result)
          wakes-up (recur (rest entries)
                          {:id (:id current-sleep) :date [month day]}
                          (conj result (assoc current-sleep :end minute))))))))

(defn minutes-sleeping
  [periods]
  (->> periods
       (map #(- (:end %) (:start %)))
       (reduce +)))

(defn most-asleep
  [periods]
  (->> periods
       (map #(range (:start %) (:end %)))
       (apply concat)
       (frequencies)
       (apply max-key second)
       (first)))

(let [entries (->> input (map parse-line) (sort-by :datetime))
      periods (->> entries (sleep-periods) (group-by :id))
      sleep-times (map (juxt first (comp minutes-sleeping second)) periods)
      top-sleeper (first (apply max-key second sleep-times))
      top-minute (most-asleep (get periods top-sleeper))]
  (* top-minute top-sleeper))