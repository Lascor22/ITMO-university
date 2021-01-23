(defn abstractUnaryOperation [function operand]
  (fn [variables]
    (function (operand variables))))

(def negate (partial abstractUnaryOperation -))

(def square (partial abstractUnaryOperation #(* % %)))

(defn sqrt [x]
  (fn [variables]
    (Math/sqrt (Math/abs (double (x variables))))))


(defn variable [name]
  (fn [variables]
    (variables name)))


(defn constant [arg]
  (constantly arg))


(defn abstractBinaryOperation [function & operands]
  (fn [variables]
    (apply function (map #(% variables) operands))))

(def multiply (partial abstractBinaryOperation *))

(def subtract (partial abstractBinaryOperation -))

(def add (partial abstractBinaryOperation +))

(defn divide [first second]
  #(/ (first %) (double (second %))))

(def VARS
  {'x (variable "x")
   'y (variable "y")
   'z (variable "z")})


(def OPS
  {'+      add
   'negate negate
   '-      subtract
   '*      multiply
   '/      divide
   'square square
   'sqrt   sqrt
   })


(defn parse [expression]
  (cond
    (list? expression)
    (apply (OPS (first expression))
           (map parse (rest expression)))
    (number? expression) (constant expression)
    (contains? VARS expression) (VARS expression)
    (contains? OPS expression) (OPS expression)))


(defn parseFunction [expression]
  (parse (read-string expression)))