; (def input ["Step C must be finished before step A can begin."
;             "Step C must be finished before step F can begin."
;             "Step A must be finished before step B can begin."
;             "Step A must be finished before step D can begin."
;             "Step B must be finished before step E can begin."
;             "Step D must be finished before step E can begin."
;             "Step F must be finished before step E can begin."])

(def input
  (clojure.string/split-lines (slurp "input.txt")))

(defn parse-line
  [line]
  (rest (re-matches #"Step (\w) must be finished before step (\w) can begin." line)))

(defn task-time
  [str]
  (+ 61 (- (int (first str)) (int \A))))

(defn do-work
  [remaining pending]
  (->> remaining
       (map (fn [[k v]] [k (if (pending k) (dec v) v)]))
       (remove (fn [[k v]] (zero? v)))
       (into {})))

(loop [t 0
       dependencies (map parse-line input)
       remaining (into {} (map (juxt identity task-time) (flatten dependencies)))
       pending []]
  (if (empty? remaining)
    t
    (let [unavailable (into #{} (map second dependencies))
          pending (take 5 (set (concat pending (sort (remove unavailable (keys remaining))))))
          completed (into #{} (filter #(= (remaining %) 1) pending))]
      (recur (inc t)
             (remove #(completed (first %)) dependencies)
             (do-work remaining (set pending))
             (remove #(completed %) pending)))))