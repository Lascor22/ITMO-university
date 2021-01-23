(load-file "object.clj")
(load-file "combinators.clj")

(def *allChars (mapv char (range 0 128)))

(def *operations (+char "+-*/&|^"))

(def *space (+char (apply str (filter #(Character/isWhitespace (char %)) *allChars))))

(def *letter (+char (apply str (filter #(Character/isLetter (char %)) *allChars))))

(def *digit (+char (apply str (filter #(Character/isDigit (char %)) *allChars))))

(def *whiteSpace (+ignore (+star *space)))

(def *number (+str (+seq (+opt (+char "-+")) (+str (+plus *digit)) (+char ".") (+str (+plus *digit)))))

(def *constant (+map (comp Constant read-string) *number))

(def *operationsOrVariable (+map (comp #(OPERATIONS % (Variable (str %))) symbol) (+str (+plus (+or *letter *operations)))))

(declare *value)

(defn *seq [begin p end] (+seqn 1 (+char begin) (+plus (+seqn 0 *whiteSpace p)) *whiteSpace (+char end)))

(def *list (+map (fn [list] (apply (last list) (butlast list))) (*seq "(" (delay *value) ")")))

(def *value (+or *constant *operationsOrVariable *list))

(def parseObjectSuffix (+parser (+seqn 0 *whiteSpace *value *whiteSpace)))