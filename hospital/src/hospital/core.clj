(ns hospital.core
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]))

(let [hospital-de-cristo (h.model/novo-hospital)]
  (pprint hospital-de-cristo))