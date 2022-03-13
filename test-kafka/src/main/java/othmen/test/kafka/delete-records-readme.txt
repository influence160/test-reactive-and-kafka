
creer le fichier delete-records-conf.json avec ce contenu
{"partitions": [{"topic": "jointure-table1", "partition": 0, "offset": -1},{"topic": "jointure-table2", "partition": 0, "offset": -1} ], "version": 1 }


pui faire 
> kafka-delete-records --bootstrap-server localhost:9092 --offset-json-file delete-records-conf.json