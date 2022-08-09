(ns hospital.colecoes
  (:use [clojure pprint]))

; Vetor

(defn testa-vetor
  []
  (let [espera [111 222]]
  (println [espera])
  (println (conj espera 333))
  (println (conj espera 444))
  (println (conj espera 555))
  (println (pop espera))))
; Esperado [111]
(testa-vetor)

; Lista coligada
(defn testa-lista []
  (let [espera '(111 222 333)]
    (println (conj espera 444))
    (println (conj espera 555))
    (println (conj espera 666))
    (println (pop espera))))
; Esperado [222 333]
(testa-lista)

;POP - para ser usado precisa ser em uma pilha
; 1. Em um vetor trabalha de maneira LIFO (Last in, Firs Out)
; 2. Em uma lista coligada trabalha de maneiro FIFO (First in, first out)

; Conjunto
(defn testa-conjunto []
  (let [espera #{111 222 333}]
    (println (conj espera 222))  ;Não permite adicionar um valor repetido ao conjunto
    (println (conj espera 444))
    (println (conj espera 555))
    (println (conj espera 666))
    ;(println (pop espera)) não rola pop em set
    ))
(testa-conjunto)

(defn testa-fila []
  (let [espera (conj clojure.lang.PersistentQueue/EMPTY "111" "222")]
   (println "FILA")
   (println (seq espera))
   (println (seq (conj espera "333")))
   (println (seq (pop espera )))
   (println (seq (conj espera "111")))
   (println (peek espera))
   (pprint espera)))  ;Imprime de forma bonitinha
(testa-fila)

;clojure.lang.PersistentQueue/EMPTY -> Definindo uma fila vazia