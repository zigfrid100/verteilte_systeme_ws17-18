# Aufgabe 2
_In der dritten Aufgabe soll eine RPC Anbindung des Kühlschranks an ein Gesch ft mittels Apache Thrift 
implementiert werden. Wenn die Menge eines der 5 Artikel im Kühlschrank unter einen zuvor festgelegten 
Schwellwert sinkt, soll der Kühlschrank über die Thrift Schnittstelle selbst ndig beim Gesch ft den Artikel 
nachbestellen. Über die Schnittstelle soll es msglich sein, Artikel nachzubestellen und zu beliebigen 
Zeitpunkten Rechnungen über die bisher get tigten Bestellungen anzufordern. Artikel haben neben einem Namen 
auch Preise für eine bestimmte Menge, z.B. €/100g. Der Webserver im Kühlschrank soll dabei so erweitert werden, 
dass eine Nachbestellung auch manuell über einen Webbrowser erfolgen kann, wenn ausreichend Platz vorhanden ist._

* Hinweis: Wie erfahren die Sensoren, dass ein Artikel geliefert wurde? Das soll simuliert werden -- genau wie das erfolgen sollte, ist ein "Design-Decision", die Sie treffen sollen. *
* Für diese Aufgabe muss nur ein Gesch ft simuliert werden, aber dieses Gesch ft soll mehr als ein Kühlschrank bedienen. *

----------------------------------------------------------------------------------
######
## For Compile and Start: 

* You need is Maven and Java 8 JDK install.
* Then clone Project on brach step1.

```
$ git clone -b step2 https://github.com/zigfrid100/verteilte_systeme_ws17-18.git
```
* Don't forget change IP-Address by Client 
######
## Compile
```
$ mvn clean package 
```
in the Project directory
######
## Usage
```
$ cd "Project-Directory"/target/classes 
$ java de/hda/fbi/ds/ks/Main
```
* Choice in Menu Server(1) or Client(2) or Exit(3).
* The server runs forever. You may terminate the server by pressing Ctrl + x. 
* Webserver support Google Chrome and Safari.
######
## Testing capacity of Server: 

```
$ cd "Project-Directory"/src/main/bash
$ ./start-udp-socket-server.sh  
$ ./start-udp-socket-client-sensor.sh "value of clients"
```
* Don't forget chmod u+x "name of bash file"