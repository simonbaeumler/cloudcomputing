# Übung: Provisionierung mit DockerFile

## Vorbereitung
1. Prüfen sie, ob VirtualBox und Vagrant installiert sind. Installieren Sie beides für den Fall, dass sich auf ihrem Notebook die Software nicht befindet.
* Installieren die das Vagrant scp-Plugin mit dem Befehl 'vagrant plugin install vagrant-scp'.
* Starten sie die Vagrant Box (`vagrant up`).
* Verbinden sie sich mit `vagrant ssh` per Kommandozeile in die Vagrant Box (Passwort: *tcuser*).

## Ziel
![Zielbild](ziel.png)

## Aufgabe 1 - Deployment eines Applikations Cluster mit Docker

### Images erstellen
Zunächst erstellen wir Images für den Webserver (*NGINX*) und den Load-Balancer (*HAproxy*). Die jeweiligen Dockerfiles sind auf github im Lösungsordner der Übung verfügbar. Dem Kommando `docker build` kann direkt die github-URL auf die Datei mitgegeben werden (URL wird ermittelt, indem man zur jeweiligen Datei navigiert und bei der Datei-Anzeige den Button *Raw* drückt).
* Greifen sie zunächst per Browser auf die beiden Dockerfile zu und analysieren sie den Provisionierungsablauf und die hinterlegten Konfigurationsdateien.
* Erstellen sie ein Image mit dem Namen *cloudcomputing/nginx-node* aus dem NGNIX-Dockerfile.
* Erstellen sie ein Image mit dem Namen *cloudcomputing/haproxy-node* aus dem HAproxy-Dockerfile.
* Vergewissern sie sich über das entsprechende Docker Kommando, dass beide Images lokal vorliegen.

### NGINX-Cluster
Nun erstellen wir ein Cluster aus drei NGINX Webservern, die aus dem Host-System heraus unter den Ports 81, 82 und 83 erreichbar sind.
* Starten sie drei Container aus dem NGINX-Image. Der Container soll dabei im Hintergrund laufen (Parameter `-d`). Geben sie per `--name` Parameter den Containern die Namen nginx1, nginx2 und nginx3. Aufrufbeispiel:
`docker run -d -p 81:80 --name nginx1 cloudcomputing/nginx-node`
* Prüfen sie, ob die drei Container laufen.
* Prüfen sie, ob die drei in den Containern enthaltenen NGINX-Server eine http-Antwort geben.

### HAproxy
Nun starten sie einen HAProxy Container, der mit den drei NGINX Containern verbunden ist.
* Starten sie einen Container aus dem HAproxy Image und bilden sie dessen Port 80 auf den Host-Port 80 ab. Der Container soll dabei ebenfalls im Hintergrund laufen. Bauen sie dabei auch über entsprechende Kommandozeilen-Parameter eine Verbindung zwischen dem HAproxy Container und den NGINX Containern auf: `--link nginx1:nginx1`.
* Beantworten sie die folgende Frage: Wie bekommt der HAproxy mit, auf welchen IP-Adressen und unter welchen Ports er die NGINX-Server findet?

### Testlauf
* Überprüfen sie die HAproxy Statistiken aus einem Browser heraus: http://localhost:8080/haproxy?stats (Zugang mit *admin/admin*). Greifen sie dazu parallel mehrfach auf die Loadbalancer-URL zu: http://localhost:8080. Welche Informationen können sie der Statistik entnehmen?
* Recherchieren sie, unter welchem Port die Docker Remote API (REST) zur Verfügung steht und lassen sie sich über diese die aktuell laufenden Container ausgeben.

## Aufgabe 2 - Erstellen eines Dockerfiles

### Erstellen des Dockerfiles
* Erstellen auf dem Host-Computer (d.h. ausserhalb der Vagrant Virtuellen-Maschine) ein Verzeichnis <name> und kopieren sie die jar-Datei aus Übung 1 dorthin.
* Erstellen sie im gleichen Verzeichnis eine Datei 'Dockerfile'
* Bearbeiten sie das Dockerfile mit einem Texteditor, so dass der Bookshelf Service aus Übung 1 gestartet wird. Beachten sie dazu die folgenden Punkte:
** Installieren sie dazu das Paket apk add openjdk8-jre
** Kopieren sie die .jar Datei
** Starten sie den Java-Service mit 'java -jar /opt/app/ccexercise-0.0.1-SNAPSHOT.jar'
** Geben sie den Port 8080 nach aussen frei
** Hinweis: Sie können die in Übung 1 benutzen Dockerfiles als Vorlage benutzen.

### Bauen
* Kopieren sie die beiden benötigten Dateien in die boot2docker-VM:
    * Dockerfile: 'vagrant scp <name>/Dockerfile :\~/Dockerfile' 
    * jar-Datei: 'vagrant scp <name>/<Dateiname-jar-Datei> :\~/<Dateiname-jar-Datei>' 
    * Hinweis: Wenn sie eine Datei in der VM aktisieren möchten, müssen sie das Kommando erneut ausführen.
* Loggen sie sich in die boot2-docker-VM mit 'vagrant ssh' ein.
* Bauen sie mit dem erstellten Dockerfile ein Image ('docker build -t cloudcomputing/bookshelf-node:v1')

### Testlauf
* Starten sie das Image mit 'docker run -d -p 8080:8080 --name bookshelf1 cc/bookshelf-node:v1'
* Überprüfen sie mit curl, ob sie innerhalb der boot2docker-Umgebung den Service erreichen können.



