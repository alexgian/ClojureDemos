^{:nextjournal.clerk/visibility {:code :hide}}
(ns core)
(require '[emmy.env :as env])
(env/bootstrap-repl!)

(require '[emmy.clerk :as ec])
(ec/serve! {:browse true})

;; test all is OK

  (let [aLagrangian (emmy.mechanics.lagrange/L-harmonic 'm 'k)
        someFunction (literal-function 'x)
        eqnOfMotion ((Lagrange-equations aLagrangian) someFunction)]
    (-> (eqnOfMotion 't)
        simplify
        ->infix
        ))
