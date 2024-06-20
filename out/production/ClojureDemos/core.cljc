(ns core)
(require '[emmy.env :as env])
(env/bootstrap-repl!)

(require '[emmy.clerk :as ec])
(ec/serve! {:browse true})

;; test all is OK
(->
  (let [Lagrangian (emmy.mechanics.lagrange/L-harmonic 'm 'k)]
    (((Lagrange-equations Lagrangian) (literal-function 'x)) 't))
  simplify
  )
