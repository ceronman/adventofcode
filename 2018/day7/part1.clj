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

(loop [dependencies (map parse-line input)
       remaining (into #{} (flatten dependencies))
       result ""]
  (if (empty? remaining)
    result
    (let [unavailable (into #{} (map second dependencies))
          next (first (sort (remove unavailable remaining)))]
      (recur (remove #(= next (first %)) dependencies) 
             (disj remaining next) 
             (str result next)))))