(ns hospital.aula1
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model])
  (:require [hospital.logic :as h.logic]))

(defn simula-um-dia []
  ;root binding
  (def hospital (h.model/novo-hospital))
  (pprint (h.logic/chega-em hospital :espera "111"))
  )

(defn simula-um-dia []
  ;root binding
  (def hospital (h.model/novo-hospital))
  (def hospital (h.logic/chega-em hospital :espera "111"))
  (def hospital (h.logic/chega-em hospital :espera "222"))
  (def hospital (h.logic/chega-em hospital :espera "333"))
  (def hospital (h.logic/chega-em hospital :espera "444"))
  ; NÃ0 É nunca recomendavel alterar a variável global

  (def hospital (h.logic/chega-em hospital :laboratorio1 "555"))
  (def hospital (h.logic/chega-em hospital :laboratorio3 "666"))
  (pprint hospital)

  (def hospital (pprint (h.logic/atende hospital :laboratorio1)))
  (def hospital (pprint (h.logic/atende hospital :espera)))
  )

(defn chega-em-malvado [pessoa]
  (def hospital (h.logic/chega-em-pausado hospital :espera pessoa))
  (println "apos inserir" pessoa))



;Thread. -> Cria uma função que roda assincrona
;.start- inicia essa função
; (Thread/sleep 4000) - temporizador para execucao

(defn simula-um-dia-em-paralelo
  []
  (def hospital (h.model/novo-hospital))
  (.start (Thread. (fn [] (chega-em-malvado "111"))))
  (.start (Thread. (fn [] (chega-em-malvado "222"))))
  (.start (Thread. (fn [] (chega-em-malvado "333"))))
  (.start (Thread. (fn [] (chega-em-malvado "444"))))
  (.start (Thread. (fn [] (chega-em-malvado "555"))))
  (.start (Thread. (fn [] (chega-em-malvado "666"))))

  (.start (Thread. (fn [] (Thread/sleep 4000)
                     (pprint hospital)))))
(simula-um-dia-em-paralelo)
