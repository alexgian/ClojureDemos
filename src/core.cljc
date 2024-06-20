(ns core)
(require '[emmy.env :as env])
(env/bootstrap-repl!)

(require '[emmy.clerk :as ec])
(ec/serve! {:browse true})

;; test all is OK
(simplify
  (((Lagrange-equations
      (emmy.mechanics.lagrange/L-harmonic 'm 'k))
    (literal-function 'x)) 't))
