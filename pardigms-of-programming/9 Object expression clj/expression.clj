(use '[clojure.string :only (join)])

(definterface OpJ
  (EvalJ [args])

  (toStrJ [])

  (DiffJ [name]))


(deftype ConstJ [val]
  OpJ
  (EvalJ [this _] (.val this))

  (toStrJ [this] (format "%.1f" (double (.val this))))

  (DiffJ [_ _] (ConstJ. 0.0)))


(def ZERO (ConstJ. 0))

(def ONE (ConstJ. 1))

(def TWO (ConstJ. 2))

(def Constant #(ConstJ. %))


(deftype VarJ [val]
  OpJ
  (EvalJ [this args] (args (.val this)))
  (toStrJ [this] (.val this))
  (DiffJ [this name] (if (= name (.val this)) ONE ZERO)))

(def Variable #(VarJ. %))


(deftype AbstractOperation [operands symbol f der]
  OpJ
  (EvalJ [this args] (apply f (map #(.EvalJ % args) (.operands this))))

  (toStrJ [this] (str "(" (join " " (cons symbol (map #(.toStrJ %) (.operands this)))) ")"))

  (DiffJ [this name] (second (apply der (map list (.operands this) (map #(.DiffJ % name) (.operands this)))))))

(defn operation [symbol f der]
  #(AbstractOperation. %& symbol f der))

(def Add (operation '+ + (fn [[x x_der] [y y_der]]
                           [(Add x y) (Add x_der y_der)])))


(def Subtract (operation '- - (fn [[x x_der] [y y_der]]
                                [(Subtract x y) (Subtract x_der y_der)])))


(def Multiply (operation '* * (fn [[x x_der] [y y_der]]
                                [(Multiply x y)
                                 (Add
                                   (Multiply y x_der) (Multiply x y_der))])))


(def divideOperation #(reduce (fn [x y] (/ (double x) (double y))) %&))

(def Divide (operation '/ divideOperation (fn [[x x_der] [y y_der]]
                                            [(Divide x y)
                                             (Divide
                                               (Subtract
                                                 (Multiply y x_der) (Multiply x y_der))
                                               (Multiply y y))])))


(defn unaryOperation [symbol f der]
  #(AbstractOperation. [%] symbol f der))


(def Negate (unaryOperation 'negate - (fn [[x x_der]] [(Negate x)
                                                       (Negate x_der)])))


(def Square (unaryOperation 'square (fn [x] (* x x)) (fn [[x x_der]] [(Square x)
                                                                      (Multiply (Multiply TWO x) x_der)])))


(def Sqrt (unaryOperation 'sqrt (fn [x] (Math/sqrt (max x (- x)))) (fn [[x x_der]]
                                                                     [(Sqrt (Sqrt (Square x)))
                                                                      (Multiply (Divide (Sqrt (Sqrt (Square x)))
                                                                                        (Multiply TWO x)) x_der)])))


(def evaluate #(.EvalJ %1 %2))

(def toString #(.toStrJ %))

(def diff #(.DiffJ %1 %2))


(def operations
  {'+      Add
   '-      Subtract
   '*      Multiply
   '/      Divide
   'negate Negate
   'sqrt   Sqrt
   'square Square})


(defn parse [expr]
  (cond (list? expr) (apply (operations (first expr))
                            (map parse (rest expr)))
        (number? expr) (Constant expr)
        :else (Variable (str expr))))


(defn parseObject [expr]
  (parse (read-string expr)))