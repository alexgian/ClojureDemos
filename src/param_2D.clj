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
;; on the _x_ and _y_ axes,  these are some of the simpler forms, determined
;; by the coefficients preceding the _sin_ and _cos_ functions in the equation
;; below.
;;
;; Move the sliders to see the shape change.  Note that the _xa_ and _ya_
;; parameters only affect scaling.  The ratio of the _xb_ to the _yb_ value
;; determines the shape of the looping.  For simple ratios a simple pattern
;; is produced.  If the values are the same, then it's an ellipse, of course.
^{:nextjournal.clerk/visibility {:code :show :result :hide}}
(defn param_eqn_1 [xa xb ya yb]
        (fn [t]
          [(* xa (sin (* xb t)))
           (* ya (cos (* yb t)))]))

;;
;;
^{:nextjournal.clerk/visibility {:code :hide :result :show}}
(ev/with-let
  [!coeffs {:xa 5 :ya 3 :xb (* pi 7/6) :yb pi}]
  (let [stepsize (/ pi 30)]
    (mafs/mafs
      (emmy.leva/controls
        {:folder {:name "Lissajous controls"}
         :schema
         {:xa {:min 0.5 :max 5 :step 0.05}
          :ya {:min 0.5 :max 3 :step 0.05}
          :xb {:min 0 :max (* 2 pi) :step stepsize}
          :yb {:min 0 :max (* 2 pi) :step stepsize}
          }
         :atom !coeffs})
      (mafs/cartesian)
      ; the fragment below is right, but would still like to have it
      ;  called from outside this scope, as (param_eqn_1 xa xb ya yb)
      (mafs/parametric {:xy (ev/with-params
                              {:atom !coeffs :params [:xa :xb :ya :yb]}
                              param_eqn_1)
                        :t     [0 (* 12 pi)]
                        :color "#43CC50EB"
                        }))))

;; ToDo
;; * trefoils
;; * epicycles

^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(comment
  `(fn [t]
     [(* ~x_a (sin (* ~x_b t)))
      (* ~y_a (cos (* ~y_b t)))])
  )