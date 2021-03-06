# Übung: Cloud-native Anwendung mit Spring-Boot, Consul und Fabio

In dieser Übung soll der Microservice aus Übung 1 um die notwendige Cloud-Infrastruktur
ergänzt werden. Dies ist Consul (https://www.consul.io) für Configuration & Coordination sowie Service Discovery
und Fabio (https://github.com/eBay/fabio) als Edge Server.

## Ziel
Ziel ist es den Bookshelf Microservice aus Übung 1 in die Consul- und Fabio-Infrastruktur zu integrieren.

## Aufgabenblock 1: Microservice Stack lokal ausführen

### Vorbereitung
 * Laden sie die Vorlage zur Übung von github herunter. Machen sie sich mit dem Code wieder vertraut und starten sie den Service aus der Entwicklungsumgebung heraus, rufen sie die Endpunkte im Browser auf und stoppen sie den Service wieder. Zwitscher läuft nun nicht auf einem fix vorgegebenen Port sondern sucht sich selbst einen freien Port. Wie wurde dies erreicht?
 * Laden sie Consul herunter (https://www.consul.io/downloads.html) und entpacken die ZIP-Datei im Wurzelverzeichnis der Übung.
 * Laden sie das letzte stabile Release von Fabio herunter (Linux und Mac OS X Version: https://github.com/eBay/fabio/releases - Datei im Anschluss in `fabio` umbenennen. Legen sie die Fabio executable ebenfalls im Wurzelverzeichnis deer Übung ab. Bei Linux und Mac OS X muss die Datei eventuell noch als ausführbar markiert werden: `chmod +x fabio`.
 * Legen sie im Wurzelverzeichnis der Übung eine Konfigurationsdatei für Fabio mit dem Dateinamen `fabio.properties` an und befüllen sie diese mit dem Default-Inhalt
 (https://raw.githubusercontent.com/eBay/fabio/master/fabio.properties).

### Consul starten (Configuration & Coordination)
 1. Consul starten: `consul agent -dev -ui -bind=127.0.0.1`. Consul kann später per `Ctrl + C` gestoppt werden.
 2. Die Ausgabe von Consul analysieren. Hier kann man sehen, wie das Raft-Protokoll den Leader wählt und anschließend den Zustand synchronisiert.
 3. Die Consul Web-UI aufrufen: http://localhost:8500/ui. Consul muss hier selbst als Service erfolgreich registriert sein.
 4. Über die REST API von Consul prüfen, ob der Service "consul" läuft: http://localhost:8500/v1/catalog/services.

### Spring Cloud Microservice anpassen

Ziel dieser Aufgabe ist es, die Microservice aus Übung 1 so zu erweitern, dass sich dieser

* beim Start bei der Consul Service Discovery anmeldet,
* beim Start seine Konfigurationswerte bei Consul abholt,
* die Service-Schnittstellen (nicht die Admin Schnittstellen) über Traefik aufgerufen werden können

(Hinweis: Falls der Microservice aus Übung 1 bei ihnen nicht oder nicht mehr läuft, benutzen sie bitte die Lösung aus Übung 1)

Die folgenden Dependencies müssen der `pom.xml` hinzugefügt werden:

```xml
<!-- required for Consol discovery and configuration -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-config</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
```

Danach muss unter `src/main/resources` die Datei `bootstrap.properties` angelegt werden:

```
spring.application.name=book-service

# specify Consul host and port
# we use the CONSUL_HOST and CONSUL_PORT env variables
# later set in docker compose as well as Kubernetes
spring.cloud.consul.host=${consul.host:127.0.0.1}
spring.cloud.consul.port=${consul.port:8500}

spring.cloud.consul.config.enabled=true
spring.cloud.consul.config.prefix=configuration
spring.cloud.consul.config.default-context=application

# do not fail at startup if Consul is not there
spring.cloud.consul.config.fail-fast=false

# store properties as blob in property syntax
# e.g. configuration/book-service/data
spring.cloud.consul.config.format=properties
spring.cloud.consul.config.data-key=data
```

In der `application.properties` müssen zudem folgende Properties angelegt werden, um die Service-Registrierung
in Consul und die Tags für Traefik korrekt zu konfigurieren:

```
# assign a unique instance ID
spring.cloud.consul.discovery.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}

# required by Docker compose and Consul to run the health check
# register IP address and heartbeats
spring.cloud.consul.discovery.prefer-ip-address=true
spring.cloud.consul.discovery.heartbeat.enabled=true

spring.cloud.consul.discovery.tags=traefik.enable=true,traefik.frontend.rule=PathPrefixStrip:/book-service,traefik.tags=api,traefik.frontend.entrypoint=http
```

### Den Microservice bei Consul registrieren (Service Discovery)
 1. Den im vorherigen Schritt angepassten Microservice starten
 2. In Consul prüfen, ob der Service registriert ist. Per UI (http://localhost:8500/ui) oder per REST API (http://localhost:8500/v1/catalog/service/book-service).
 3. Den Service direkt aufrufen: http://localhost:8080/api/books/0345391802
 4. In Consul den Health-Status des Service prüfen. Sollte grün sein.
 5. 3 weitere Service-Instanzen starten und in Consul nachvollziehen, dass sie dort registriert und gesund sind. Achtung: Diese müssen über den Konfigurationswert 'server.port' einen anderen Port zugewiesen bekommen.

### Fabio konfigurieren und starten (Edge Server)
 1. In der Konfigurationsdatei die Proxy-Adresse und die lokale IP auf `localhost` bzw. `127.0.0.1` setzen
 sowie die LB-Strategie auf Round Robin umsetzen.
 2. Fabio per `fabio -cfg fabio.properties` starten. Fabio setzt dabei einen lokal laufenden Consul-Agent auf dem Port 8500 voraus.
 3. In Consul prüfen, ob fabio als Service registriert und gesund ist.
 4. Auf die Fabio Web-UI zugreifen: http://localhost:9998 und die dort definierten Routen analysieren.
 Was bedeutet "Weight" bei einer Route?
 5. Den Bookshelf Service über fabio aufrufen: http://localhost:9999/api/books/. In den Logs der Service-Instanzen
   die Verteilung der Requests nachvollziehen. Fabio auf random-Verteilung umstellen und die Request-Verteilung dann
   nachvollziehen.

Hinweis: Alternativ zu Fabio kann Traefik verwendet werden. Dieser funktioniert auch unter Windows: https://traefik.io/
Als Konfigurationsdatei kann die datei traefik.toml aus der Lösung verwendet werden. Dieser wird mit 
```
./traefik_windows-amd64.exe --configFile=traefik.toml
```
gestartet. Auf http://localhost:8090/dashboard/ kann das Traefik Dashboard eingesehen werden. Der Bookshelf Service kann über Traefik durch die 
URL http://localhost/book-service/api/books/ angesprochen werden. 
   
## Aufgabenblock 2 (Kür): Microservice Stack in auf ein Kubernetes Cluster deployen

### Vorbereitung
 1. Laden sie minikube herunter (https://github.com/kubernetes/minikube/releases) und legen sie die Datei im Wurzelverzeichnis der Übung ab. Über minikube kann ein lokales Kubernetes Cluster erzeugt und verwaltet werden. Nennen sie die heruntergeladene Datei in _minikube_ / _minikube.exe_ um.
 * Laden sie kubectl herunter und legen sie die Datei im Wurzelverzeichnis ab. Mit kubectl kann ein Kubernetes Cluster gesteuert werden. Die Dokumentation zu kubectl finden sie hier: http://kubernetes.io/docs/user-guide/kubectl-overview. Die ausführbare kubectl Datei ist unter den folgenden URLs zugreifbar:
   * Win64: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/windows/amd64/kubectl.exe
   * Win32: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/windows/386/kubectl.exe
   * macOS64: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/darwin/amd64/kubectl
   * macOS32: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/darwin/386/kubectl
   * Linux64: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/linux/amd64/kubectl
   * Linux32: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/linux/386/kubectl
 * Starten sie ein lokales Kubernetes Cluster, lassen sie sich die dabei den Status und die verfügbaren Knoten ausgeben und öffnen sie das Kubernetes Web-Dashboard:
   * `minikube start`
   * `minikube status`
   * `kubectl get nodes`
   * `minikube dashboard`
 * Installieren sie das Docker Kommandozeilen-Werkzeug. Laden sie dieses entsprechend herunter (https://docs.docker.com/engine/installation/binaries) und legen sie die ausführbare _docker_ Datei ins Wurzelverzeichnis.
 * Führen sie die Kommandos aus, die der Befehl `minikube docker-env` ausgibt. Dies verbindet den Docker Client mit dem Docker Daemon innerhalb von minikube.
 * Prüfen sie mit `docker ps`, ob die Verbindung zum Docker Daemon klappt. Hier sollten ein paar Docker Container aufgelistet werden, die innerhalb von minikube laufen.

### Consul deployen
 1. Öffnen sie die Service- und RC-Deskriptoren für Consul und analysieren sie diese
 * Deployen sie zunächst den den Service und dann den RC und prüfen sie im Anschluss im Dashboard, ob beide erfolgreich laufen
   * `kubectl create -f ./src/infrastructure/k8s/consul-svc.yaml`
   * `kubectl create -f ./src/infrastructure/k8s/consul-rc.yaml`
 * Greifen sie auf die Consul Web-UI zu. Dazu müssen sie zunächst per `minikube ip` die IP des Kubernetes Cluster ermitteln. Der Zugriff auf die Consul Web-UI erfolgt dann über die URL: http://MINIKUBE-IP:30850/ui
 
### Microservice mit Consul verbinden 
 1. Modifizieren sie den Microservice so, dass er sich mit dem Consul Service innerhalb von Kubernetes verbindet. Wie ein Service-Endpunkt innerhalb von Kubernetes ermittelt werden kann ist hier beschrieben: http://kubernetes.io/docs/user-guide/services.
 * Sie müssen dabei auch die IP des Microservice Pods ermitteln. Welche Möglichkeiten es hierfür gibt, sind z.B. hier beschrieben:
    * http://stackoverflow.com/questions/30746888/how-to-know-a-pods-own-ip-address-from-a-container-in-the-pod
    * http://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
 * Erstellen sie ein Dockerfile für den Microservice und bauen sie mit dem Docker Kommandozeilenwerkzeug ein Image 
 * Erstellen sie einen Kubernetes Service- und RC-Deskriptor für den Microservice und deployen sie beides in den Kubernetes Cluster
 * Prüfen sie im Anschluss per Dashboard und Consul UI, ob der Microservice läuft und bei Consul registriert ist
 
### Fabio vor Consul schalten
  1. Erstellen sie einen Service- und RC-Deskriptor für fabio. 
    * Ein Docker Image für fabio ist hier zu finden: https://hub.docker.com/r/magiconair/fabio. 
    * Wie die Verbindung zwischen fabio und Consul per Kommandozeilen-Parameter aufgebaut werden kann ist hier zu finden: https://github.com/eBay/fabio/wiki/Configuration
  * Deployen sie den fabio Service und RC und prüfen sie, ob sie die fabio UI und den fabio Endpunkt erreichen können.
