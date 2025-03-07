# Studentensystem
Studentensystem mit React und SpringBoot

## Lokale Verwendung mit Docker
Um das Projekt lokal auf dem Computer auszuführen, wird **Docker** benötigt. Nachdem alles konfiguriert wurde und das Repository erfolgreich per git clone heruntergeladen wurde, können nun folgende Schritte durchgeführt werden, um das Projekt auszuführen!

### Generieren der JAR's
Bevor die Services in Docker verwendet werden können, müssen zuerst sämtliche JAR Datei generiert werden. Um diese zu generieren muss im Ordner **chat** und **studentsystem** das Terminal (nicht Powershell!) gestartet und folgenden Befehl eingegeben werden:
```java
mvnw clean install -DskipTests
```
### Installieren der Dependencies für Frontend
Das Frontend wurde mit ReactJS realisiert und benötigt daher andere Befehle. Das Frontend benötigt keine direkte generierte Datei für das Docker Compose, jedoch ist es sinnvoll auch diese korrekt zu installieren. Um sämtliche benötigten Pakete zu herunterladen für das Projekt kann einfach im Terminal im Ordner **studentfrontend** mit **Node Js** folgenden Befehl eingegeben werden:
```
npm install
```

### Bauen des Image für Docker
Da nun alles bereit ist kann im Terminal nachdem die Docker Engine am laufen ist folgenden Befehl eingeben, um einen eigenen Image zu bauen anhand der definierten Services in der .YAML File:
```
docker-compose build
```
Nachdem dem Erfoglreichen Bauen kann nun das Image im eigenen Container gestartet werden mit folgendem Befehl im Terminal:
```
docker-compose up -d
```
Das **-d** steht für "detached" und heisst, dass der Prozess des laufenden Containers im Terminal im hintergrund läuft und so noch weitere Befehle in der Zeile eingegeben werden können!

### Aufrufen des Frontends
Jetzt kann nach dem Erfolgreichem Starten des Containers folgende URL aufrufen, um das ReactJS Frontend zu starten:
```
http://localhost:3000
````

## Externe Datenbank
Für die Speicherung von Daten wird eine externe Postgres Datenbank verwendet, die ebenfalls auf Render gehostet wird.
- **hostname:** dpg-cv1bpftds78s73dmomk0-a.frankfurt-postgres.render.com
- **database:** geraete
- **user:** m347
- **password:** TsFHAbD0H0O8NUbVqTik7q5CnQhVTCAe

Externe Ganze URL: postgresql://m347:TsFHAbD0H0O8NUbVqTik7q5CnQhVTCAe@dpg-cv1bpftds78s73dmomk0-a.frankfurt-postgres.render.com/geraete
