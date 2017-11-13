# Aufgabe 1
_Im ersten Schritt soll eine Anwendung entwickelt werden, die einen Kühlschrank mit Internet Anbindung simuliert. 
Der Kühlschrank arbeitet als Server und soll mit Hilfe von Sockets von einem einfachen Client angesprochen werden 
und für insgesamt 5 Artikel den aktuellen „Füllstand“ angeben. Jeder Kühlschrank hat zu jedem Zeitpunkt 
die vorhandene Menge aller Artikel. Die Menge jedes Artikels im Kühlschrank soll sich nach einem zu bestimmenden Zeitintervall reduzieren,
so dass irgendwann der Kühlschrank leer ist. Das heißt, es müssen Sensoren simuliert werden, die den Füllstand "messen" 
und eine "Zentrale", die alle Messungen sammelt und mit dem Besitzer des Kühlschranks kommuniziert._

*In dieser Aufgabe (und in Aufgabe 2) soll alle Kommunikation mittel Sockets erfolgen. Java oder C++ sind erlaubte Programmiersprachen.*
*Die Sensoren verwenden .DP, um deren Messungen an die Zentrale zu schicken. Die Zentrale soll sowohl 
die aktuell vorhandene Menge eines einzelnen Artikels, wie auch die Historie des Füllstandes ausgeben.*

----------------------------------------------------------------------------------

## For Compile and Start: 

```
$ git clone -b step1 https://github.com/zigfrid100/verteilte_systeme_ws17-18.git
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
