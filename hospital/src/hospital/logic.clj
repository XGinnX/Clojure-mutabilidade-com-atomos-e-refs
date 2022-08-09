(ns hospital.logic)

(defn cabe-na-fila?
  [hospital departamento]
  (-> hospital
      (get,,, departamento)
      count,,,
      (< ,,, 5))

  ;fila (get hospital departamento)
  ; tamanho-atual (count fila)
  ; cabe? (< tamanho-atual 5)
  )

(defn chega-em
  [hospital departamento pessoa]
    (if (cabe-na-fila? hospital departamento)
  (update hospital departamento conj pessoa)
  ; THROW é uma função que serve para validar os erros
  (throw (ex-info "FiLA JÁ ESTÁ CHEIA " {:tentando-adicicionar pessoa}))))

(defn chega-em-pausado
  [hospital departamento pessoa]
  (Thread/sleep (* (rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (do
        (update hospital departamento conj pessoa))
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn chega-em-pausado-logando
  [hospital departamento pessoa]
  (println "Tentei Inserir a pessoa" pessoa)
  (Thread/sleep (* (rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (do
      (update hospital departamento conj pessoa)
      (println "Dei Update" pessoa))
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))


(defn atende
  [hospital departamento]
  (update hospital departamento pop))

(defn proxima
  [hospital departamento]
  (-> hospital
      departamento
      peek))

(defn transfere
  [hospital de para]
  (let [pessoa (peek (proxima hospital de))]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))

(defn atende-completo
  [hospital departamento]
  {
  :paciente (update hospital departamento peek)
  :fila (update hospital departamento pop)
   })

(defn atende-completo
  [hospital departamento]
  {:paciente (update hospital departamento peek)
   :hospital (update hospital departamento pop)})

;JUXT permite executar duas funcções
;juxt aplica as funções isoladamente ao mesmo valor.
(defn atende-completo-que-chama-ambos
  [hospital departamento]
  (let [fila (get hospital departamento)
        peek-pop (juxt peek pop)
        [pessoa fila-atualizada] (peek-pop fila)]
    {:paciente pessoa
     :hospital fila-atualizada}))