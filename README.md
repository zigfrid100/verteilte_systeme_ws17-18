# Aufgabe 2
_Die zweite Aufgabe besteht darin, in den Kühlschrank einen Webserver zu integrieren. 
Der Webserver soll mit einem beliebigen Browser (Chrome, Firefox, Internet Explorer, Safari, etc.) 
angesprochen werden können und jeweils eine einfache HTML Seite mit einer Übersicht über 
die im Kühlschrank befindliche Menge von jeweils 5 Artikeln an den Browser schicken_

* Dieser Webserver soll mit TCP Sockets realisiert werden - und nicht etwa mit fertigen Webserver- Klassen aus Bibliotheken. *
* Die Sensoren aus Aufgabe 1 müssen weiter laufen. Das heißt, dass die "Zentrale" gleichzeitig 
mit den Sensoren als auch mit Browser (HTTP Klienten) in Kontakt bleiben soll. *

----------------------------------------------------------------------------------

## For Compile and Start: 

* You need is Maven and Java 8 JDK install.
* Then clone Project on brach step1.

```
$ git clone -b step2 https://github.com/zigfrid100/verteilte_systeme_ws17-18.git
```
* Don't forget change IP-Address by Client 

## Compile
```
$ mvn clean package 
```
in the Project directory
## Usage
```
$ cd "Project-Directory"/target/classes 
$ java de/hda/fbi/ds/ks/Main
```
* Choice in Menu Server(1) or Client(2) or Exit(3).
* The server runs forever. You may terminate the server by pressing Ctrl + x. 
