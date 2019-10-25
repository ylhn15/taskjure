(ns taskure.core
  (:gen-class)
  (:require [cheshire.core :refer :all])
  )

(defn showMenu []
  (println "(A)dd to add a task
           (L)ist to list all task
           (D)elete to delete a task
           (S)search to search a task
           (Q)uit to quit the program"))

(defn get-input [inputMessage]
  (println inputMessage)  
  (read-line)
  )

(defn now [] (new java.util.Date))

(defn init-tasks []
  (spit "tasks.json" (generate-string (assoc {} :tasks ()))
        :append true)
)

(defn id-of-last-task []
  (or (get (last (get (parse-string (slurp "tasks.json") true) :tasks)) :id) 0)
  )

(defn add-new-task-to-list []
  (conj (get (parse-string (slurp "tasks.json") true)
          :tasks) (assoc {} :id (inc (id-of-last-task)) 
          :title (get-input "Title of the task")
          :description (get-input "Description of the task")
          :date (now)))
  )

(defn write-tasks-to-file []
(spit "tasks.json" (generate-string (assoc {} :tasks (add-new-task-to-list))))
    )

(defn list-tasks []
   (run! println (get (parse-string (slurp "tasks.json") true) :tasks))
  )

(defn delete-task [id]
  (println "TODO delete " id)) 

(defn search-task [id]
  (println "TODO search " id)) 

(defn -main
  [& args]

  (if-not (.exists (clojure.java.io/file "tasks.json")) (init-tasks))

  (while true
    (showMenu)
    (case (clojure.string/lower-case(read-line))
      "a" (write-tasks-to-file) 
      "add" (write-tasks-to-file) 
      "l" (list-tasks) 
      "list" (list-tasks) 
      "d" (delete-task (read-line)) 
      "delete" (delete-task (read-line)) 
      "s" (search-task (read-line)) 
      "search" (search-task (read-line)) 
      "q" (System/exit 0) 
      "quit" (System/exit 0) 
      "default"))
  )


