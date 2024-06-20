(ns visual-debug
  (:refer-clojure
    :exclude [+ - * / zero? compare divide numerator denominator
              infinite? abs ref partial =])
  (:require [emmy.clerk :as ec]
            [emmy.env :as e :refer :all]
            [emmy.calculus.manifold :as cm]
            [emmy.mafs :as mafs]
            [emmy.leva :as leva]
            [emmy.mathbox.plot :as plot]
            [emmy.viewer :as ev]
            [nextjournal.clerk :as clerk]))

{:nextjournal.clerk/width :wide}

^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(ec/install!)

(defn fparam [t]
  [(* 6 (cos t))
   (* 3 (sin t))])

(defn dist-from [f [x y]]
  (fn [t] (abs (- [x y] (f t)))))

(defn constrain [f [x y]]
  (emmy.env/brent-min (dist-from f [x y]) 0 (* 2 pi)
                      {:absolute-threshold 1.5e-8
                       :relative-threshold 1.0e-5}))

(defn debug [f]
  (ev/with-let [!point [1 2]]
               (let [path (ev/with-params
                            {:atom !point :params [0 1]}
                            (fn [x y]
                              (up identity (dist-from f [x y]))))]
                 (mafs/mafs {:zoom {:min 0.1 :max 2}
                             :view-box {:x [-7 7] :y [-4 7]}}
                            (mafs/cartesian)
                            (mafs/movable-point {:atom !point})
                            `(let [~'result (constrain ~f @~!point)
                                   ~'pt (~f (:result ~'result))]
                               [:<>
                                ~(mafs/text
                                   "Minimum"
                                   {:x `(:result ~'result)
                                    :y `(:value ~'result)
                                    :attach "e"
                                    :attach-distance 10})
                                ~(mafs/point
                                   {:x `(:result ~'result)
                                    :y `(:value ~'result)})
                                ~(mafs/text
                                   "Point"
                                   {:x `(first ~'pt)
                                    :y `(second ~'pt)
                                    :color :yellow
                                    :attach-distance 30
                                    :attach "n"})
                                ~(mafs/point
                                   {:x `(first ~'pt)
                                    :y `(second ~'pt)
                                    :color :yellow})])
                            (mafs/parametric {:xy path :t [0 (* 2 pi)]})
                            (mafs/parametric
                              {:color :green
                               :xy f
                               :t [0 (* 2 pi)]})
                            ))))

(debug fparam)

(debug (fn [t]
         [(* 3 (cos (* 3 t)))
          (* 3 (sin t))]))