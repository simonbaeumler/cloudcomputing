# Übung: Cluster Scheduling mit Amazon ECS

## Vorbereitung

* Legen sie sich auf Docker Hub einen privaten Account an.
* Installieren sie die AWS CLI auf ihrem System. https://docs.aws.amazon.com/cli/latest/userguide/installing.html
* Sie erhalten die AWS Zugriffsdaten beim Übungsbetreuer.
* Konfigurieren sie die AWS CLI: `aws configure`

Sollte die Konfiguration mit `aws configure` nicht funktionieren,
können die credentials auch durch die Umgebungsvariablen 
`AWS_SECRET_ACCESS_KEY` und `AWS_ACCESS_KEY_ID` gesetzt werden.

## Ziel

Wir wollen im Rahmen dieser Übung eine Beispiel Anwendung (bookstore) auf Amazon EC2 Container Service (ECS) bereitstellen.
Es sollen automatisiert mehrere Instanzen gescheduled werden, um die Verfügbarkeit zu erhöhen.
Alle Instanzen sollen über die gleiche URL im Internet erreichbar sein.

Wir verwenden dafür eine leicht modifizierte Version der Book Service Anwendung aus der Vorlesung und Übung __"Kommunikation"__.

## Aufgaben

### Docker

#### Dockerfile erzeugen

Schreiben sie für den Bookstore Microservice in diesem Verzeichnis ein `Dockerfile`.
Verwenden Sie das gegebene Dockerfile als Ausgangspunkt.

#### Docker Image lokal bauen

Bauen und testen sie das Image lokal. Verwenden sie hierfür die Kommandos aus der Übung __"Virtualisierung"__:

```bash
$ docker build -t book-service:latest .
$ docker run -it -p 8080:8080 book-service:latest
```

### Amazon EC2 Container Service (ECS)

Melden sie sich mit den AWS Zugriffsdaten über den Browser an der 
[ECS Web Konsole](https://eu-central-1.console.aws.amazon.com/ecs/home?region=eu-central-1#/taskDefinitions)
an.
Nutzen Sie den Ihnen zugewiesenen Service Account mit dem angegebenen IAM user, um sich einzuloggen.
Stellen Sie sicher, dass Sie sich in der AWS Region `Frankfurt (eu-central-1)` befinden.

_***Hinweis:***_ Bitte verwenden Sie in allen zu benennenden Resourcen immer Ihren Usernamen als prefix,
damit eine Zuordnung zu Ihren Komponenten möglich ist!

### Docker Registry konfigurieren

Erstellen Sie eine Docker Registry, in die Sie die docker container des bookstore hochladen werden.
Am besten bauen sie den AWS Account-Namen ein, z.B: `cc-user-03/bookstore`.

* Repository Name: `cc-user-03/bookstore`
* Repository URI: 123456789.dkr.ecr.eu-central-1.amazonaws.com/cc-user-03/bookstore

### Docker Image pushen

```bash
# Generieren Sie das docker login Kommando für Ihren account
aws ecr get-login --no-include-email --region eu-central-1

# kopieren sie den Output und führen diesen in der Shell aus (evtl. sudo)
# zBsp. docker login -u AWS -p eyJwYXl....R9 https://761646160558.dkr.ecr.eu-central-1.amazonaws.com

# Taggen Sie das zuvor gebaute Docker image mit dem neuen registry namen
docker tag book-service:latest 1234456789.dkr.ecr.eu-central-1.amazonaws.com/cc-user-03/bookstore:latest

# Pushen Sie das umgetaggte image in das erstellte repositort
docker push 1234456789.dkr.ecr.eu-central-1.amazonaws.com/cc-user-03/bookstore:latest
```


### Cluster erzeugen
Nachdem das Image hochgeladen wurde, erzeugen Sie ein ECS **Linux** Cluster.
Das ECS Cluster soll aus 2 nodes vom Typ "t2.small" bestehen. 
Nutzen Sie die default VPC ID und security group.

### Load Balancer erzeugen
Um Resourcen aus der AWS Cloud über das Internet zu erreichen,
benötigen wir einen Elastic Load Balancer (ELB).
Navigieren Sie dazu in die EC2 Konsole,
in den [Load Balancer Bereich](https://eu-central-1.console.aws.amazon.com/ec2/v2/home?region=eu-central-1#LoadBalancers).
Erstellen Sie nun einen HTTP Load Balancer mit Port 80.
Aktivieren Sie alle drei eu-central Availability Zones.
Achten Sie darauf, dass VPC ID und security group mit der des Load Balancers übereinstimmen.

Der Load Balancer soll eine neue Target Group erstellen (Protocol HTTP &Target Type  instance).
Targets müssen nicht registriert werden.

### Task Definition erstellen

Wenn Cluster und ELB eingerichtet sind, gehen sie nun zum nächsten Schritt und erzeugen die Task-Definition.
Diese beschreibt im wesentlichen den container aus der Docker registry, den Sie ausführen möchten.
Kehren Sie dazu in die 
[ECS Web Konsole](https://eu-central-1.console.aws.amazon.com/ecs/home?region=eu-central-1#/taskDefinitions)
zurück.

Vergeben sie sprechende Namen für `Task definition name` und `Container name`. Als `Memory Limits (MiB)*` setzen sie `500`.
Bei den Port `Port mappings` setzen sie den Container-Port `8080`.

### Service konfigurieren

Konfigurieren sie einen Service für die soeben erstellte Task Definition.
Setzen sie einen `Service name` und starten sie `2` Tasks.
Konfigurieren Sie den zuvor eingerichteten Load Balancer als Application Load Balancer für diesen Service.
Achten Sie darauf, den container aus dem Task zum Load Balancer hinzuzufügen.

Nach dem Erstellen des Services wird dieser die für die tasks benötigten container starten und auf den 
cluster nodes verteilen.

Identifizieren Sie, welche aus der Vorlesung bekannten Placement Algorithmen Sie verwenden können.

## Review

Sofern alles korrekt verschaltet ist, können Sie nun über den DNS Namen des load balancers
die book-service Instanzen erreichen.

- Verifizieren Sie zunächst die Erreichbarkeit des Services.

Der `/api/books` Endpunkt gibt eine Bücherliste zurück.
Die Bücher beinhalten eine storeId, welche pro Instanz vergeben wird.
- Verifizieren Sie, dass Sie durch mehrmaliges Neuladen der Seite beide 
Instanzen des Book Service erreichen können.

- Erhöhen Sie den Replikationsfaktor des Service.
- Finden Sie die Engpassbeschreibung, sobald keine Replikas mehr gescheduled werden können.
- Fügen Sie dem Cluster weitere Nodes hinzu, um den Engpass zu beseitigen.

