^{:nextjournal.clerk/visibility {:code :hide}}
(ns parametric
  ; #:nextjournal.clerk{:toc true}
  (:refer-clojure
    :exclude [* + - / = abs compare denominator divide
              infinite? numerator partial ref zero?])
  (:require [emmy.calculus.manifold :as cm]
            [emmy.clerk :as ec]
            [emmy.env :refer :all]
            [emmy.leva :as leva]
            [emmy.mathbox.plot :as plot]
            [emmy.viewer :as ev]
            [nextjournal.clerk :as clerk]))

{:nextjournal.clerk/width :wide}

^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(ec/install!)

;; ## Fun with Manifolds

(defn surface
  [{:keys [f] :as opts}]
  (let [f (if (cm/coordinate-system? f)
            (fn [[x y]]
              (cm/manifold-point-representation
                (cm/coords->point f [x y])))
            f)]
    (ev/fragment
      (plot/parametric-surface
        (assoc opts :f f))
      plot/scene)))


(defn toroidal->rect [R r]
  (fn [[theta phi]]
    (*
      (rotate-z-matrix phi)
      (up (+ R (* r (cos theta)))
          0
          (* r (sin theta))))))

;; Single torus:

(surface
  {:f (toroidal->rect 2 0.5)
   :u [0 (*  Math/PI)]
   :v [0 (*  Math/PI)]})

;; Parametrized torus:

(plot/scene
  (ev/with-let [!opts {:R Math/PI :r Math/PI}]
               [:<>
                (leva/controls
                  {:folder {:name "Torus"}
                   :schema
                   {:R {:min 0.5 :max 2 :step 0.01}
                    :r {:min 0.5 :max 2 :step 0.01}}
                   :atom !opts})
                (surface
                  {:color "LimeGreen"
                   :f (ev/with-params {:atom !opts :params [:R :r]}
                                      toroidal->rect)
                   :u [0 (* 2 Math/PI)]
                   :v [0 (* 2 Math/PI)]})]))

;; Torus with a parametric curve overlaid, both interactive:

(ev/with-let [!opts {:scale 1 :R 2 :r 0.5}]
             (plot/scene
               (leva/controls
                 {:atom !opts
                  :folder {:name "Torus and Spring"}
                  :schema
                  {:scale {:min 0.5 :max 2 :step 0.01}
                   :R     {:min 0.5 :max 2 :step 0.01}
                   :r     {:min 0.5 :max 2 :step 0.01}}})
               (plot/parametric-curve
                 {:f (ev/with-params {:atom !opts :params [:scale :R]}

                                     (fn [scale R]
                                       (up (* R cos) (* R sin) (* scale 0.3 identity))))
                  :t [-10 10]})
               (surface
                 {:f (ev/with-params {:atom !opts :params [:R :r]}
                                     toroidal->rect)
                  :u [0 (* 2 pi)]
                  :v [0 (* 2 Math/PI)]})))

;; A scmutils coordinate system object!!

(surface
  {:f S2-spherical
   :u [0 Math/PI]
   :v [0 (* 2 Math/PI)]})

;; parametrized:

(ev/with-let
  [!opts {:opacity 0.8
          :theta (* 2 Math/PI)
          :color "#3090FF"
          :phi Math/PI}]
  (plot/scene
    (leva/controls
      {:folder {:name "S2-spherical"}
       :schema
       {:opacity {:min 0 :max 1 :step 0.01}
        :theta   {:min 0 :max (* 2 Math/PI) :step 0.01}
        :phi     {:min 0 :max Math/PI :step 0.01}}
       :atom !opts})
    (surface
      {:f S2-spherical
       :u [0 (ev/get !opts :phi)]
       :v [0 (ev/get !opts :theta)]
       :opacity (ev/get !opts :opacity)
       :color (ev/get !opts :color)})))