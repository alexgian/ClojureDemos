^{:nextjournal.clerk/visibility {:code :hide}}
(ns param-2D
  (:refer-clojure
    :exclude [+ - * / zero? compare divide numerator denominator
              infinite? abs ref partial =])
  (:require
    [emmy.clerk :as ec]
    [emmy.env :as e :refer :all]
    [emmy.generic :as g :exclude gcd]
    [emmy.mafs :as mafs]
    [emmy.mathbox :as mathbox]
    [emmy.mathbox.plot :as plot]
    [emmy.leva]
    [emmy.viewer :as ev]
    [nextjournal.clerk :as clerk]))

^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(ec/install!)

;;## Parametric equations with _mafs_
;;##### Coupled harmonic oscillator (Lissajous figure)
;; Commonly created on oscilloscopes by supplying two different signals
;; on the _x_ and _y_ axes.  This is one of the simpler forms, determined
;; by the coefficients preceding the _sin_ and _cos_ functions, below.
;;
;; This is the equation we _should_ be using.
^{:nextjournal.clerk/visibility {:code :show :result :hide}}
(defn param_eqn_1 [xa ya xb yb]
        (fn [t]
          [(* xa (sin (* ya t)))
           (* ya (cos (* yb t)))]))

;;
;;
^{:nextjournal.clerk/visibility {:code :hide :result :show}}
(ev/with-let
  [!coeffs {:xa 3 :ya 2 :xb 2 :yb 5}]

    (mafs/mafs
      (mafs/cartesian)
      (let [x_a (ev/get !coeffs :xa)
            y_a (ev/get !coeffs :ya)
            x_b (ev/get !coeffs :xb)
            y_b (ev/get !coeffs :yb)
            ]
      ; the fragment below is right, but would still like
      ; to have it defined outside this scope (param_eqn_1 a b c d)
      [:<>
       (emmy.leva/controls
                     {:folder {:name "Lissajous controls"}
                      :schema
                      {:xa {:min 0.5 :max 5 :step 0.05}
                       :xb {:min 0.5 :max 5 :step 0.05}
                       :ya {:min 0.5 :max 5 :step 0.05}
                       :yb {:min 0.5 :max 5 :step 0.05}
                       }
                      :atom !coeffs})
       (mafs/parametric {:xy `(fn [t]
                                [(* ~(ev/get !coeffs :xa) (sin (* ~(ev/get !coeffs :ya) t)))
                                 (* ~x_b (cos (* ~y_b t)))])
                         :t [0 (* 10 pi)]
                         :color :green})]
      )
    ))

;; epicycles