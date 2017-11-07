# Übung: Virtualisierung mit Vagrant und Docker

## Vorbereitung für die Übung nächste Woche. Bitte _Jetzt_ testen!
* Prüfen sie, ob VirtualBox und Vagrant installiert sind. Installieren Sie beides für den Fall, dass sich auf ihrem Notebook die Software nicht befindet. Schauen sie sich mit dem Kommando `vagrant help` alle verfügbaren Kommandos von Vagrant an.
* Initialisieren sie mit Vagrant eine Box auf Basis der Vorlage *hashicorp/boot2docker* (`vagrant init`)
* Starten sie die Vagrant Box (`vagrant up`). Im Hintergrund lädt Vagrant dabei das virtuelle Image aus dem Internet. Da dies lange dauert, können sie in der Zwischenzeit schon mit den anderen Übungen starten.
* Überprüfen sie den Status der Vagrant Boxen (`vagrant status`), fahren sie die Box herunter (`vagrant halt`) und überprüfen sie abermals den Status.

*Hinweis:* Sollte Vargant nicht funktionieren oder der Download der Virtuellen Maschine zu lange dauern, dann können Sie die folgende Übung ab Schritt 3 auch hier durchführen: https://www.katacoda.com/courses/docker/playground

## Aufgabe 1: Aufruf von REST-Services

(Vorbereitung)
* Laden sie sich die Vorlagen der beiden Services 'AusleiheVerwaltung' und 'BenutzerVerwaltung' von der GitHub Vorlesungsseite.
* Bauen sie beide Services mit 'mvn clean install'. Beide Services sind Spring-Boot Container. Starten sie diese und schauen sie sich die Swagger-UI an (erreuchbar unter localhost:8082 bzw. localhost:8084).
* Machen sie sich mit der Implementierung der beiden Services vertraut.  

(1) Integrieren sie den Feign-Client der 'BenutzerVerwaltung' in die 'AusleiheVerwaltung'. Fügen sie dazu die folgende Dependency zu der pom.xml der 'AusleiheVerwaltung-app' hinzu:
```xml
    <dependency>
        <groupId>de.qaware.edu.cc.benutzer</groupId>
        <artifactId>BenutzerVerwaltung-client</artifactId>
        <version>0.1.0-SNAPSHOT</version>
    </dependency>
```

(2) Aktivieren sie den Feign-Client in der BenutzerVerwaltung, indem sie die folgende Annotation zur Klasse Application.java hinzufügen:
```java
@EnableFeignClients(basePackageClasses = {
        BenutzerServiceClient.class})
```

(3) Lassen sie sich den erzeugten Feign-Client in die Klasse AppService.java injecten:
```java
    @Autowired
    private BenutzerServiceClient benutzerServiceClient;
```

(4) Geben sie in der application.yml Konfigurationsdatei die url der BenutzerVerwaltung an:
```javascript
services:
    BenutzerVerwaltung: "localhost:8084"
```

(5) Benutzen sie den Feign-Client, um in der Methode AppService.getAusleiheDataContent() die Benutzerdaten des Benutzers abzurufen. Fügen sie den Namen des Benutzers zu der ausgegebenen Antwort hinzu.

(6) Testen sie den Feign-Client, indem sie beide Services starten und einen Aufruf über die Swagger-UI absetzen.

## Aufgabe 2: Implementierung eines REST-Services

(1) Übernehmen (kopieren o.ä.) sie den DTO Book.java aus dem Bookshelf-Service aus Übung 1/2 in das BenutzerVerwaltung Projekt

(2) Erstellen sie ein Interface 'BookshelfClient'. Fügen sie dem Interface die Methode
```java
    Book byIsbn( String isbn); 
```
hinzu. 

(3) Fügen sie nun die Annotationen, die Feign benütigt zu der Klasse hinzu. Sie können sich dabei an den Klassen BenutzerServiceClient und BenutzerService in der Benutzerverwaltung orientieren.
Tip: Pfad-Variablen kann man über die Annotation '@PathVariable("nameOfPlaceholderInPath")' definieren. 

(4) Gehen sie wie in Schritten 2-5 der vorherigen Aufgabe vor, um den implementierten ookshelf-Client zu benutzen

(5) Testen sie den neuen Bookshelf-Client wie in der vorherigen Aufgabe.

## Aufgabe 3 (Kür): ELK-Stack installieren

(1)
Logstash, Elastic, und Kibana installieren. (apt get install …) 

(2)
Logstash Konfigurieren
```
input {file { path => "/dtag/log/service.log"}}

filter {
grok {match=>{"message"=>%{TIMESTAMP_ISO8601:timestamp}\s+%{LOGLEVEL:leve}\s+..."}}}

output {  elasticsearch {}}
```

(3)
Kibana Visualisierungen und Dashboards bauen
