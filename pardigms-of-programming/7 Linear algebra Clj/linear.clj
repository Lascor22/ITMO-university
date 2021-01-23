(defn abstractShapelessOperation [f & args]
  (if (number? (first args))
    (apply f args)
    (apply mapv (partial abstractShapelessOperation f) args)))

(defn s+ [& args] (apply (partial abstractShapelessOperation +) args))

(defn s- [& args] (apply (partial abstractShapelessOperation -) args))

(defn s* [& args] (apply (partial abstractShapelessOperation *) args))


(defn vectorBinaryOperation [v u f] (mapv f v u))

(defn v+ [v u] (vectorBinaryOperation v u +))

(defn v- [v u] (vectorBinaryOperation v u -))

(defn v* [v u] (vectorBinaryOperation v u *))

(defn v*s [v s] (mapv (partial * s) v))

(defn scalar [v u] (reduce + (v* v u)))

(defn det2 [a b c d] (- (* a d) (* b c)))

(defn vect [v u]
  (vector
    (det2 (v 1) (v 2) (u 1) (u 2))
    (- (det2 (v 0) (v 2) (u 0) (u 2)))
    (det2 (v 0) (v 1) (u 0) (u 1))))


(defn matrixBinaryOperation [m1 m2 f] (mapv f m1 m2))

(defn m+ [m1 m2] (matrixBinaryOperation m1 m2 v+))

(defn m- [m1 m2] (matrixBinaryOperation m1 m2 v-))

(defn m* [m1 m2] (matrixBinaryOperation m1 m2 v*))

(defn m*s [m s] (mapv #(v*s % s) m))

(defn m*v [m v] (mapv (partial scalar v) m))

(defn transpose [m] (apply mapv vector m))

(defn m*m [m1 m2]
  (def m2t (transpose m2))
  (transpose (mapv (partial m*v m1) m2t)))