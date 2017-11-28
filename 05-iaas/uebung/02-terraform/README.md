# Übung: Infrastructure Provisionierung mit Terraform

## Vorbereitung

* Sie erhalten die Zugriffsdaten beim Übungsbetreuer. Empfehlung: setzen sie die Credentials
als Umgebungsvariablen in der Shell.

```bash
$ export AWS_ACCESS_KEY_ID="<your-accesskey>"
$ export AWS_SECRET_ACCESS_KEY="<your-secretkey>"
$ export AWS_DEFAULT_REGION="eu-central-1"
```

* Installieren sie Terraform (https://www.terraform.io/downloads.html) auf ihrem Rechner.

[NOTE]
Wenn sie Windows verwenden, wird wahrscheinlich der SSH Key in Aufgabe 3 nicht funktionieren. Daher wird empfohlen, die Aufgabe unter Linux (ggf. in einer VM) auszuführen. Alternativ können sie Aufgabe 3 auch mit einem anderen Studierenden bearbeiten.

## Aufgabe 1: Getting Started with Terraform

Lesen und folgen sie der Anleitung vom Getting Started Guide von der offiziellen Terraform Seite.

- https://www.terraform.io/intro/index.html
- https://www.terraform.io/intro/getting-started/install.html

Starten sie eine EC2 Instanz in der `eu-central-1` AWS Region und verwenden sie ein Ubuntu 16.04 Image ("ami-97e953f8"). Siehe auch https://cloud-images.ubuntu.com/locator/ec2/

```bash
$ terraform init
$ terraform plan
$ terraform apply
$ terraform show
$ terraform plan -destroy
$ terraform destroy
```

## Aufgabe 2: Amazons AWS REST API

Setzen sie die Umgebungsvariable TF_LOG=true. Dies aktiviert die Debugausgabe von terraform. Wiederholen sie die Befehle aus Aufgabe 1. Schauen sie sich dabei in der Konsolenausgabe die Blöcke 

```bash
---[ REQUEST POST-SIGN ]-----------------------------
```

und 

```bash
---[ RESPONSE ]--------------------------------------
```

an. Dies sind die Request/Responses von Terraform an die AWS Rest Schnittstelle. Versuchen sie die ausgeführten Schritte nachzuvollziehen und vergleichen sie sie mit der AWS Doku.

- https://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_Operations.html

[NOTE]
====
Sie deaktivieren die Debugausgabe, indem sie die Variable TF_LOG wieder auf einen leeren Wert setzen. 
====

## Aufgabe 3: Advanced Terraform

Mit Terraform lassen sich auch sehr aufwändige Infrastrukturen aufbauen. Studieren sie das Beispiel
im `adavanced/` Ordner. Versuchen sie mithilfe der Terraform Dokumentation herauszufinden, was das Terraform-Skript macht:

- https://www.terraform.io/docs/providers/aws/index.html

Planen sie die Ausführung. Dazu benötigen Sie einen SSH Key-Paar, das Sie mit folgendem Kommando erstellen können: 
`cd .ssh/ && ssh-keygen -t rsa -b 2048 -f terraform`
