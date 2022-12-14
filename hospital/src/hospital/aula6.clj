(ns hospital.aula6
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]))

(defn cabe-na-fila? [fila]
  (-> fila
      count
      (< 5)))

(defn chega-em
  [fila pessoa]
  (if (cabe-na-fila? fila)
    (conj fila pessoa)
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

;Troca a referencia via ref-set que dereferencia o valor setado
(defn chega-em! [hospital pessoa]
  (let [fila (get hospital :espera)]
    (ref-set fila (chega-em @fila pessoa))))

;Troca de referencia via Alter
(defn chega-em! [hospital pessoa]
  (let [fila (get hospital :espera)]
    (alter fila chega-em pessoa)))
;
(defn simula-um-dia []
  (let [hospital {
                  :espera (ref h.model/fila_vazia)
                  :laboratorio1 (ref h.model/fila_vazia)
                  :laboratorio2 (ref h.model/fila_vazia)
                  :laboratorio3 (ref h.model/fila_vazia)}]

    ;Permite a utilizacção variass vezes
    (dosync
      (chega-em! hospital "Jean"))
    (pprint hospital)))

;O uso de átomo é mais simples mas refs facilitam a coordenação de trabalho entre dois valores mutáveis, trazendo todos os problemas de coordenação junto.
;(simula-um-dia)

(defn async-chega-em! [hospital pessoa]
  (future (Thread/sleep (rand 5000))
  (dosync
    (println "Tentando o codigo sincronizado" pessoa)
    (chega-em! hospital pessoa))))


(defn async-chega-em! [hospital pessoa]
  (future
    (Thread/sleep (rand 5000))
    (dosync
      (println "Tentando o codigo sincronizado" pessoa)
      (chega-em! hospital pessoa))))

(defn simula-um-dia-async []
  (let [hospital {
                  :espera (ref h.model/fila_vazia)
                  :laboratorio1 (ref h.model/fila_vazia)
                  :laboratorio2 (ref h.model/fila_vazia)
                  :laboratorio3 (ref h.model/fila_vazia)}
        futures (mapv #(async-chega-em! hospital %) (range 10))]
    (future
      (dotimes [n 4]
        (Thread/sleep 2000)
        (pprint hospital)
        (pprint futures)))
    ))

(simula-um-dia-async)