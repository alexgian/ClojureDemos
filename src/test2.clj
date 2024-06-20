(ns test2
(:refer-clojure
  :exclude [* + - / = abs compare denominator divide
            infinite? numerator partial ref zero?])
(:require
  [emmy.leva :as leva]
)
)
(comment
(:require
  [emmy.calculus.manifold :as cm]
  [emmy.env :refer :all]
  [emmy.leva :as leva]
  [emmy.mathbox.plot :as plot]
  [emmy.viewer :as ev]
  [nextjournal.clerk :as clerk]))