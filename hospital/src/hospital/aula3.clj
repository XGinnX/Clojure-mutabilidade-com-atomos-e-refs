(ns hospital.aula3
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model])
  (:require [hospital.logic :as h.logic]))

;Variavel global - Qualquer thread que solicitar acesso ao namespace pode usar as variaveis definidas
;ex (def nome "JEAN") - Pode ser reutilizados por outras threads

(defn testa-atomao []
(let [hospital-sales (atom { :espera h.model/fila_vazia})]
  (println hospital-sales)
  ; Para referenciar o que esta dentro do atomo podemos realizar de duas maneiras.
  ;Usando a função deref
  (pprint (deref hospital-sales))
  ; Ou de maneira mais simplificada, usando o @
  (pprint @hospital-sales)

  ; assoc não altera o conteúdo dentor do atomo
  (pprint (assoc @hospital-sales :laboratorio1 h.model/fila_vazia))
  (pprint @hospital-sales)
  ; SWAP! - Ele faz com que sejam realizadas alteracões dentro do atom
  (swap! hospital-sales assoc :laboratorio1 h.model/fila_vazia)
  (pprint @hospital-sales)

  ;update tradicional imutavel, com dereferencia que não gera efeito nenhum
  (update @hospital-sales :laboratorio1 conj "111")
  ;SWAP - Altera
  (swap! hospital-sales update :laboratorio1 conj "111")

  ))

;(testa-atomao)

(defn chega-em-malvado [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado-logando :espera pessoa)
  (println "apos inserir" pessoa))



;Thread. -> Cria uma função que roda assincrona
;.start- inicia essa função
; (Thread/sleep 4000) - temporizador para execucao

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
              (.start (Thread. (fn [] (chega-em-malvado hospital "111"))))
              (.start (Thread. (fn [] (chega-em-malvado hospital "222"))))
              (.start (Thread. (fn [] (chega-em-malvado hospital "333"))))
              (.start (Thread. (fn [] (chega-em-malvado hospital "444"))))
              (.start (Thread. (fn [] (chega-em-malvado hospital "555"))))
              (.start (Thread. (fn [] (chega-em-malvado hospital "666"))))
              (.start (Thread. (fn [] (Thread/sleep 4000)
                                (pprint hospital))))))
(simula-um-dia-em-paralelo)
