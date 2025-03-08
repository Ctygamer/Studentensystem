# Studentensystem

Studentensystem mit React und SpringBoot. Dieses Repository beinhaltet den Quellcode für ein umfassendes Studentensystem, das eine interaktive Chat-Funktionalität integriert. Das System ist als Microservice-Architektur konzipiert und besteht aus drei Hauptkomponenten: einem React-basierten Frontend, einem Spring-Boot-Backend für die Hauptlogik und einem separaten Spring-Boot-Service für die Chat-Funktionalität. Die Datenhaltung erfolgt über eine externe PostgreSQL-Datenbank, die ebenfalls auf Render gehostet wird.



## Externe Datenbank

Für die Speicherung von Daten wird eine externe Postgres Datenbank verwendet, die ebenfalls auf Render gehostet wird.
- **hostname:** dpg-cv1bpftds78s73dmomk0-a.frankfurt-postgres.render.com
- **database:** geraete
- **user:** m347
- **password:** TsFHAbD0H0O8NUbVqTik7q5CnQhVTCAe


## Projektarchitektur und Komponenten

* **Frontend (studentfrontend):**
    * Implementiert mit React, bietet eine dynamische und benutzerfreundliche Oberfläche.
    * Kommuniziert über REST-APIs mit dem Backend, um Daten abzurufen und zu aktualisieren.
    * Integriert eine Websocket-Verbindung für die Chat-Funktionalität.
* **Backend (studentsystem):**
    * Entwickelt mit Spring Boot, verwaltet die Kernlogik des Studentensystems.
    * Verarbeitet Anfragen vom Frontend und interagiert mit der Datenbank.
* **Chat (chat):**
    * Eigener Spring Boot Service der die Chatfunktionalität verwaltet.
    * Nutzt Websockets für die Echtzeit-Kommunikation.
* **Externe Datenbank (PostgreSQL):**
    * Eine auf Render gehostete PostgreSQL-Datenbank, die alle persistenten Daten speichert.
    * Die Datenbankinformationen sind wie folgt:
        * **Hostname:** dpg-cv1bpftds78s73dmomk0-a.frankfurt-postgres.render.com
        * **Datenbank:** geraete
        * **Benutzer:** m347
        * **Passwort:** TsFHAbD0H0O8NUbVqTik7q5CnQhVTCAe
    * **WICHTIG:** Die oben genannten Anmeldeinformationen sind nur für Entwicklungszwecke. In einer Produktionsumgebung sind diese durch sichere Umgebungsvariablen zu ersetzen.

## Lokale Entwicklung


Für die lokale Entwicklung sind folgende Schritte notwendig:

1.  Repository klonen:
    ```bash
    git clone <https://github.com/Ctygamer/Studentensystem.git>
    ```
2. **Projekt bauen**  
   Zuerst müssen Sie das Projekt bauen, um den `target`-Ordner zu erstellen und die JAR-Datei zu generieren:
   ```bash
   ./mvnw clean package -DskipTest
   ```
   Dieser Befehl reinigt vorherige Builds und erstellt eine neue JAR-Datei im `target`-Verzeichnis.

3. **JAR-Datei überprüfen**  
   Stellen Sie sicher, dass die Datei `Lernen-0.0.1-SNAPSHOT.jar` im `target`-Ordner vorhanden ist. Diese Datei wird im **Dockerfile** verwendet, um das Docker-Image zu erstellen.

4. **Docker-Container starten**  
   Starten Sie die Docker-Container mit **Docker Compose**:
   ```bash
   docker-compose up --build
   ```     

5.  Zum Projektverzeichnis Backend navigieren:
    ```bash
    cd studentsystem
    ```

6. Installieren der Abhängigkeiten:
    ```bash
    npm i
    ```

npm i anstelle von npm install verwendet.


7.  Zum Projektverzeichnis Frontend navigieren:
    ```bash
    cd studentfrontend
    ```

8. Installieren der Abhängigkeiten:
    ```bash
    npm i
    ```

9. Zum Projektverzeichnis Chat navigieren:
    ```bash
    cd chat
    ```

10. Installieren der Abhängigkeiten:
    ```bash
    npm i
    ```

10. Starte die Applikation:
    ```bash
    npm run
    ```


**Passe deine Befehle entsprechend deiner IDE an.


    * Dieser Befehl startet alle Komponenten (Frontend, Backend, Chat, Datenbank) in Docker-Containern.
    * Das Flag `--build` sorgt dafür, dass die Docker-Images bei Bedarf neu erstellt werden.
4.  Zugriff auf die Anwendung:
    * Frontend: `http://localhost:3000`
    * Backend-API: `http://localhost:8080/swagger-ui.html`
    * Chat-Service: `http://localhost:8081`


## Technologien

* **Backend:**
    * Spring Boot
    * JPA (Java Persistence API)
    * PostgreSQL
    * MapStruct
    * Lombok
    * Validation API
    * Docker
* **Frontend:**
    * React
    * Jest
    * Material UI
    * stompjs
    * sockjs-client
    * Docker
* **CI/CD:**
    * GitHub Actions
    * Docker
    * Render
* **Chat:**
    * Spring Websocket
    * STOMP
    * SockJS
    * Chat Service (Spring Boot)
* **Datenbank:**
    * PostgreSQL
    * JPA (Java Persistence API)
    * Flyway

## Zusätzliche Erläuterungen

* **Docker:**
    * Wird sowohl im Backend als auch im Frontend verwendet, um eine konsistente und reproduzierbare Umgebung für Entwicklung, Tests und Bereitstellung zu schaffen.
    * Ermöglicht die Containerisierung der Anwendungen, was die Bereitstellung auf Render vereinfacht.
* **Jest:**
    * Ein JavaScript-Testframework, das im Frontend für Unit-Tests verwendet wird.
    * Stellt sicher, dass die Frontend-Komponenten korrekt funktionieren.
* **GitHub Actions:**
    * Wird für die CI/CD-Pipeline verwendet, um automatisierte Tests und Bereitstellungen zu ermöglichen.
    * Automatisiert den Build- und Deployment-Prozess.
* **Render:**
    * Cloud Plattform wo die Docker Container gehostet werden.

## Backend (studentsystem)

Das Backend des Studentensystems ist mit Spring Boot entwickelt und implementiert eine RESTful API zur Verwaltung von Studenten und Kursen. Es nutzt JPA für die Datenpersistenz und MapStruct für das Mapping zwischen Entitäten und DTOs.

### Architektur und Komponenten

* **Controller:**
    * `CourseController.java`: Verwaltet Kurse, inklusive Hinzufügen, Löschen und Zuweisen von Studenten zu Kursen.
    * `StudentController.java`: Verwaltet Studenten, inklusive Hinzufügen, Löschen und Aktualisieren von Kurszuweisungen.
    * `HomeController.java`: Bietet einen einfachen Endpunkt für die Startseite der API.
* **DTOs (Data Transfer Objects):**
    * `CourseDto.java`: Datenübertragungsobjekt für Kurse.
    * `StudentDto.java`: Datenübertragungsobjekt für Studenten.
* **Entitäten:**
    * `Course.java`: JPA-Entität für Kurse.
    * `Student.java`: JPA-Entität für Studenten.
* **Exceptions:**
    * `GlobalExceptionHandler.java`: Globaler Exception-Handler zur Behandlung von RuntimeExceptions.
* **Mapper:**
    * `CourseMapper.java`: MapStruct-Mapper für Kurse.
    * `StudentMapper.java`: MapStruct-Mapper für Studenten.
* **Repository:**
    * `CourseRepository.java`: JPA-Repository für Kurse.
    * `StudentRepository.java`: JPA-Repository für Studenten.
* **Services:**
    * `CourseService.java`: Interface für den Kurs-Service.
    * `CourseServiceImpl.java`: Implementierung des Kurs-Service.
    * `StudentService.java`: Interface für den Studenten-Service.
    * `StudentServiceImpl.java`: Implementierung des Studenten-Service.
* **Konfiguration:**
    * `StudentsystemApplication.java`: Hauptklasse der Spring Boot-Anwendung.
    * `SwaggerConfig.java`: Konfiguration für CORS-Einstellungen.

### Funktionalitäten

* **Kursverwaltung:**
    * Hinzufügen, Abrufen und Löschen von Kursen.
    * Zuweisen von Studenten zu Kursen.
* **Studentenverwaltung:**
    * Hinzufügen, Abrufen und Löschen von Studenten.
    * Aktualisieren der Kurszuweisungen für Studenten.
* **Datenpersistenz:**
    * Verwendung von JPA zur Interaktion mit der PostgreSQL-Datenbank.
* **API-Endpunkte:**
    * `/course`: Endpunkte zur Kursverwaltung.
    * `/student`: Endpunkte zur Studentenverwaltung.
    * `/`: Endpunkt für die Startseite der API.
* **Validierung:**
    * Verwendung von `@Valid` und `BindingResult` zur Validierung von Anfragen.
* **Exception-Handling:**
    * Globaler Exception-Handler zur Behandlung von Fehlern.
* **CORS-Konfiguration:**
    * Konfiguration von Cross-Origin Resource Sharing (CORS) zur Ermöglichung von Anfragen vom Frontend.

## Frontend-Details (studentfrontend)

Das Frontend ist mit React aufgebaut und bietet eine Single-Page-Application (SPA) mit folgenden Komponenten:

* **App.css:**
    * Definiert das grundlegende Styling der Anwendung.
* **App.js:**
    * Hauptkomponente der Anwendung.
    * Nutzt `react-router-dom` für das Routing.
    * Definiert die Routen für Studenten, Kurse und den Chatroom.
* **index.js:**
    * Einstiegspunkt der React-Anwendung.
    * Rendert die `App`-Komponente in den `root`-Container.
* **Appbar.js:**
    * Navigationsleiste mit Links zu Studenten, Kursen und dem Chatroom.
    * Nutzt Material-UI für das Design.
* **ChatRoom.js:**
    * Implementiert die Chat-Funktionalität mit Websockets.
    * Ermöglicht öffentliche und private Nachrichten.
    * Nutzt `stompjs` und `sockjs-client` für die Websocket-Verbindung.
* **Course.js:**
    * Verwaltung von Kursen.
    * Ermöglicht das Hinzufügen und Löschen von Kursen.
    * Nutzt Material-UI für das Design.
* **Student.js:**
    * Verwaltung von Studenten.
    * Ermöglicht das Hinzufügen, Löschen und Zuweisen von Kursen zu Studenten.
    * Nutzt Material-UI für das Design.

## Chat-Funktionalität (chat Service)

Der Chat-Service nutzt Websockets für die Echtzeit-Kommunikation. Hier sind die Hauptkomponenten:

* **WebsocketConfig.java:**
    * Konfiguriert die Websocket-Verbindung und den Message Broker.
    * Ermöglicht Verbindungen über `/ws` mit SockJS-Fallback.
    * Definiert `/app` als Präfix für Anwendungsziele und `/chatroom`, `/user` als Message Broker Ziele.
* **ChatController.java:**
    * Verarbeitet öffentliche und private Nachrichten.
    * `/message` sendet Nachrichten an den öffentlichen Chatroom (`/chatroom/public`).
    * `/private` sendet private Nachrichten an bestimmte Benutzer (`/user/{username}/private`).
* **Message.java:**
    * Datenmodell für Chat-Nachrichten.
    * Enthält Sender, Empfänger, Nachricht, Datum und Status (JOIN, MESSAGE, LEAVE).
* **Status.java:**
    * Enum für den Status einer Nachricht.

# Teststrategie

Die Teststrategie dieses Projekts umfasst Unit-Tests und Integrationstests, um eine hohe Codequalität und Zuverlässigkeit sicherzustellen.

### Unit-Tests

* **Umfassende Unit-Tests**: Für Controller, Services und DTOs wurden gut strukturierte Unit-Tests mit Mockito implementiert.
    * Die Tests decken verschiedene Szenarien ab, einschließlich positiver und negativer Fälle (z. B. Kurs nicht gefunden).
    * Die Validierung von DTOs mit Bean Validation ist ebenfalls gut abgedeckt.
* **Klare Teststruktur**: Die Tests sind gut organisiert und leicht zu verstehen.
    * Die Verwendung von `@BeforeEach` und `@Test` trägt zur Klarheit bei.
    * Die Tests folgen den üblichen Konventionen.
* **Abdeckung wichtiger Funktionalitäten**: Die Tests decken die wichtigsten Funktionalitäten der Anwendung ab, einschließlich CRUD-Operationen für Kurse und Studenten.
    * Die Interaktion zwischen Kursen und Studenten wird ebenfalls getestet.
* **Verwendung von Mockito und Spring Test**: Die Verwendung von Mockito und Spring Test ermöglicht es, die Tests effektiv zu schreiben und zu warten.
    * Die Tests sind dadurch sehr performant.

### Integrationstests

* Integrationstests wurden implementiert, um die Interaktion zwischen verschiedenen Komponenten zu überprüfen.
* Diese Tests stellen sicher, dass die Datenzugriffsschicht (Repositories) korrekt funktioniert und dass die Abfragen die erwarteten Ergebnisse liefern.
* Die Integrationstests decken End-to-End-Szenarien ab, z.B. das Hinzufügen eines Studenten zu einem Kurs oder das Abrufen aller Kurse eines Studenten.
* Die Integrationstests werden über ein eigenes Maven Profil ausgeführt.

# CI/CD-Pipeline (Continuous Integration/Continuous Deployment)

## Überblick

Diese CI/CD-Pipeline automatisiert den Entwicklungs-, Test- und Deployment-Prozess für ein Projekt mit Frontend-, Backend- und Chat-Komponenten. Sie unterstützt verschiedene Entwicklungszweige (develop, staging, main, ci-cd) und ermöglicht so parallele Entwicklung und Testung in unterschiedlichen Umgebungen.

## Ziele des Workflows

* **Automatisierte Tests zur Qualitätssicherung:** Unit-Tests, Integrationstests und Linting werden automatisch durchgeführt, um Fehler frühzeitig zu erkennen und die Codequalität zu sichern.
* **Automatisierte Bereitstellung:** Neue Funktionen und Fehlerbehebungen werden schnell und zuverlässig in die jeweiligen Umgebungen (dev, staging, production) bereitgestellt.
* **Sicherstellung der Code-Qualität und Sicherheit:** Code-Qualitätsprüfungen und der Einsatz von Umgebungsvariablen für sensible Daten sind integriert.
* **Effizienz und Zuverlässigkeit:** Automatisierung reduziert manuelle Eingriffe und minimiert Fehler.
* **DevOps-Praktiken:** CI/CD-Prinzipien werden durch regelmäßige Codeintegration, automatisierte Tests und schnelle Bereitstellungen gefördert.
* **Testen der Pipeline:** Die Pipeline selbst wird in einem separaten Branch (`ci-cd`) getestet, um ihre Zuverlässigkeit zu gewährleisten.
* **Umgebungsspezifische Konfiguration:** Unterschiedliche `application-\*.properties`-Dateien und GitHub Actions Environments werden verwendet, um Konfigurationen für die verschiedenen Umgebungen zu verwalten.
* **Dynamische Konfigurationsauswahl:** Der Build-Prozess wählt basierend auf dem Branch dynamisch die richtige Konfigurationsdatei aus.

## Workflow-Details

Die Pipeline besteht aus zwei Hauptworkflows:

* **`ci-cd.yml` (im `ci-cd`-Branch):**
    * Testet die Pipeline selbst.
    * Führt Linting, Unit-Tests, Docker-Builds und Deployment auf Render durch.
* **`deploy.yml` (im `main`, `develop` und `staging`-Branch):**
    * Stellt die Anwendung in den jeweiligen Umgebungen (dev, staging, production) bereit.
    * Verwendet die in der Build-Phase ausgewählte Konfiguration.

Die Pipeline besteht aus den folgenden Jobs:

* **lint:** Führt Linting für das Frontend durch.
* **test:** Führt Unit-Tests für Frontend, Backend und Chat durch.
* **integration-test:** Führt Integrationstests für das Backend durch.
* **build:** Erstellt Docker-Images und wählt die Umgebungsspezifische Konfiguration aus.
* **deploy:** Stellt die Anwendung auf Render bereit.

### CI/CD-Workflow (für Entwickler)

1.  **Neuen Branch `ci-cd` erstellen:**
    ```bash
    git checkout -b ci-cd
    ```
2.  **Änderungen an der Pipeline vornehmen.**
3.  **Änderungen pushen:**
    ```bash
    git push origin ci-cd
    ```
4.  **GitHub Actions-Status überprüfen.**
5.  **Pull Request von `ci-cd` nach `main`, `develop` oder `staging` erstellen.**
6.  **Pull Request mergen.**

## Container Docker

Die Pipeline verwendet Docker für die Erstellung von Images, um eine konsistente Ausführungsumgebung zu gewährleisten. Die Images werden in der GitHub Container Registry (GHCR) gespeichert.

## Pipeline Environments

* **develop:** Entwicklungsumgebung für neue Funktionen und Fehlerbehebungen.
* **staging:** Testumgebung, die der Produktionsumgebung ähnelt.
* **production:** Produktionsumgebung für Endbenutzer.
* **ci-cd:** Umgebung zum Testen der Pipeline selbst.

## Verwendung

1.  Pushen Sie Änderungen in den entsprechenden Branch (develop, staging, main, ci-cd).
2.  Die Pipeline wird automatisch ausgelöst.
3.  Überwachen Sie die Pipeline-Ausführung in GitHub Actions.

## Konfiguration

* Konfigurieren Sie die `application-\*.properties`-Dateien im Verzeichnis `studentsystem/src/main/resources/` für jede Umgebung.
* Konfigurieren Sie die Render Service IDs und API Keys in GitHub Secrets.
* Konfigurieren Sie Umgebungsvariablen in GitHub Actions Environments.


### Deployment auf Render

Die Anwendung wird auf der Cloud-Plattform Render gehostet. Hierbei werden Docker-Container verwendet, um eine konsistente und reproduzierbare Umgebung zu schaffen.

* Die `docker-compose.yml`-Datei definiert die Struktur der Anwendung und die Abhängigkeiten zwischen den Komponenten.
* Render verwendet die zuvor gespeicherten Docker Images, um die Anwendung zu deployen.
* Die externe PostgreSQL-Datenbank wird als Service in Render eingebunden, und die Verbindungsdaten werden über Umgebungsvariablen an die Backend-Komponenten übergeben.

# Render Deployment 

Wir haben unsere Anwendung erfolgreich auf Render.com bereitgestellt, einer Cloud-Plattform-as-a-Service (PaaS), die es uns ermöglicht, Webanwendungen und APIs direkt aus unserem GitHub-Repository bereitzustellen.

## 1. Bereitstellungsvorgang

* Wir haben ein Render-Konto erstellt und unser GitHub-Repository verbunden.
* Für jeden unserer Services (Backend, Frontend, Chat) haben wir einen neuen Web Service in unserem Render-Dashboard erstellt.
* Wir haben die Build-Einstellungen konfiguriert, einschließlich der Build-Befehle und Start-Kommandos, die für unsere Anwendung erforderlich sind.
* Wir haben unsere Anwendung mit den entsprechenden Service-Namen, Regionen und Instance Types bereitgestellt.
* Zusätzlich haben wir die benötigten Umgebungsvariablen in den Render-Einstellungen für jeden Service konfiguriert, um die korrekte Funktion der Applikation sicherzustellen.

## 2. Anwendung online erreichbar

Unsere Anwendung ist nun unter den folgenden Links online erreichbar:

* **Backend:** `https://studentensystem-b5i7.onrender.com`
* **Frontend:** `https://studentensystem-frontend.onrender.com/`
* **Chat:** `https://studentensystem-frontend.onrender.com/chat`

## 3. Chat-Funktionalität testen

* Um die Chat-Funktionalität zu testen, öffnen wir die Frontend-Anwendung unter `https://studentensystem-frontend.onrender.com/`.
* Testen die Funktionalität und ob die Speicherung zur Datenbank funktioniert und auf den anderen Laptops angezeigt werden, wo auch im Team sind. Funktioniert soweit einwandfrei.
* Wir suchen den "Chat"-Button und klicken darauf.
* Dies sollte uns zur Chat-Funktionalität unserer Anwendung unter `https://studentensystem-frontend.onrender.com/chat` weiterleiten. Was es erfolgreich macht und auch die anderen Chat User anzeigt, Nachrichten versendet in Echtzeit keine verzögerungen. Das funktioniert auch einwandfrei.

## 4. Frontend Studentenkurse testen

*Die Anwendung ist wie erwartet am laufen und wird auch nochmals getestet, Funktionalitäten wurden alle nochmals getestet und werden auch bei allen Usern einwandfrei angezeigt und in der DB gespeichert.

## 5. Backen testen

* Das Backend wird geladen und ist mit der DB verbunden.

## 6. Datenbank testen

* Die Datenbank wird geladen und alle Funktionen die Implementiert wurden funktionieren einwandfrei.

## Zusätzliche Informationen

* **GitHub Actions (CI/CD):** Wir haben GitHub Actions verwendet, um den Deployment-Prozess zu automatisieren.
* **Render API:** Wir können die Render API verwenden, um Bereitstellungen programmatisch zu steuern.
* **Dokumentation:** Die offizielle Render-Dokumentation ist eine wertvolle Ressource für weitere Informationen.
* **Workflow:** Wir haben in CI-CD workflows.yaml ein entsprechend passendes deploy geschrieben das die Automatisierung bei änderungen durch den push Vorgang gemacht werden.


# Log-for-CourseController

## Überblick

Dieser Controller verwaltet Kurse und bietet Endpunkte für das Abrufen, Hinzufügen, Löschen und Zuweisen von Studenten zu Kursen.

## Endpunkte

* **GET /course:**
    * Gibt eine Liste aller Kurse zurück.
    * Logs:
        * "Rufe alle Kurse ab." (INFO)
        * "Anzahl der abgerufenen Kurse: {Anzahl}" (INFO)
* **POST /course:**
    * Fügt einen neuen Kurs hinzu.
    * Logs:
        * "Füge neuen Kurs hinzu: {courseDto}" (INFO)
        * "Kurs hinzugefügt: {savedCourse}" (INFO)
* **DELETE /course/{id}:**
    * Löscht einen Kurs anhand der ID.
    * Logs:
        * "Lösche Kurs mit ID: {id}" (INFO)
        * "Kurs mit ID: {id} gelöscht." (INFO)
* **PUT /course/{courseId}/add-student/{studentId}:**
    * Fügt einen Studenten zu einem Kurs hinzu.
    * Logs:
        * "Füge Studenten mit ID: {studentId} zu Kurs mit ID: {courseId} hinzu." (INFO)
        * "Student erfolgreich hinzugefügt: {result}" (INFO)
        * "Fehler beim Hinzufügen von Studenten: {errorMessage}" (ERROR)

## Logging

Der Controller verwendet SLF4j für die Protokollierung. Die folgenden Log-Meldungen werden generiert:

* **INFO:**
    * Informationen über den Ablauf der Endpunkte.
    * Erfolgsmeldungen.
* **ERROR:**
    * Fehler beim Hinzufügen von Studenten zu einem Kurs.

## Konfiguration


* SLF4j und eine entsprechende Logging-Implementierung.

# Log-for-StudentController

## Überblick

Dieser Controller verwaltet Studenten und bietet Endpunkte für das Hinzufügen, Abrufen, Löschen und Aktualisieren von Kursen für Studenten.

## Endpunkte

* **POST /student:**
    * Fügt einen neuen Studenten hinzu.
    * Validiert die Eingabedaten.
    * Logs:
        * `WARN: Validierungsfehler beim Hinzufügen eines Studenten: {errors}` (bei Validierungsfehlern)
        * `INFO: Student erfolgreich hinzugefügt: {savedStudent}` (bei erfolgreichem Hinzufügen)
        * `ERROR: Fehler beim Hinzufügen eines Studenten: {errorMessage}` (bei Fehlern)
* **GET /student:**
    * Gibt eine Liste aller Studenten zurück.
    * Logs:
        * `INFO: Anzahl der abgerufenen Studenten: {count}` (gibt die Anzahl der abgerufenen Studenten an)
* **DELETE /student/{id}:**
    * Löscht einen Studenten anhand der ID.
    * Logs:
        * `INFO: Student mit ID {id} erfolgreich gelöscht.` (bei erfolgreichem Löschen)
        * `ERROR: Fehler beim Löschen des Studenten mit ID {id}: {errorMessage}` (bei Fehlern)
* **PUT /student/{id}/update-courses:**
    * Aktualisiert die Kurse eines Studenten.
    * Logs:
        * `INFO: Kurse des Studenten mit ID {id} erfolgreich aktualisiert.` (bei erfolgreicher Aktualisierung)
        * `ERROR: Fehler beim Aktualisieren der Kurse des Studenten mit ID {id}: {errorMessage}` (bei Fehlern)

## Logging

Der Controller verwendet SLF4j für die Protokollierung. Die folgenden Log-Meldungen werden generiert:

* **WARN:**
    * Validierungsfehler beim Hinzufügen eines Studenten.
* **INFO:**
    * Erfolgreiche Operationen (Hinzufügen, Abrufen, Löschen, Aktualisieren).
    * Anzahl der abgerufenen Studenten.
* **ERROR:**
    * Fehler, die während der Ausführung der Endpunkte auftreten.

# Branching

* Wir haben verschiedene Branches hinzugefügt, welche auf die erledigten Änderungen bezogen sind, wie zb Readme, postgressDB, cd-ci.

# Docker 
Wir haben mit Docker gearbeitet, verschiedene Microservices angewendet und Dockerfiles implementiert.

## Docker Compose

Dieses Projekt verwendet Docker Compose, um das Studentenverwaltungssystem mit Datenbank, Backend, Frontend und Chat zu containerisieren.

## Voraussetzungen

* Docker und Docker Compose müssen installiert sein.

## Aufbau

Die Anwendung besteht aus folgenden Komponenten:

* **Datenbank (MySQL):** Speichert die Daten des Studentenverwaltungssystems.
* **Backend (Spring Boot):** Bietet die REST-API für die Verwaltung von Studenten und Kursen.
* **Frontend (React):** Die Benutzeroberfläche für das Studentenverwaltungssystem.
* **Chat (Node.js):** Ein Chat-Backend für die Kommunikation.
 
Im Abschnitt, lokale Entwicklung ist beschrieben wie man das build machen kann.

## Dockerfiles 

Das Dockerfile ist eine Textdatei, die Anweisungen zum Erstellen eines Docker-Images enthält. Es dient als Bauplan, der definiert, wie eine Anwendung und ihre Abhängigkeiten in einem Container verpackt werden sollen.

## Hauptbestandteile

* **Basis-Image (FROM):**
    * Jedes Dockerfile beginnt mit der Angabe eines Basis-Images.
    * Dies ist das Betriebssystem oder die Laufzeitumgebung, auf der die Anwendung laufen wird.
    * Das Basis-Image kann ein offizielles Image von Docker Hub oder ein benutzerdefiniertes Image sein.
* **Arbeitsverzeichnis (WORKDIR):**
    * Definiert das Arbeitsverzeichnis im Container, in dem die nachfolgenden Befehle ausgeführt werden.
* **Abhängigkeiten kopieren (COPY/ADD):**
    * Kopiert Dateien und Verzeichnisse vom Hostsystem in den Container.
    * Dies wird verwendet, um Anwendungsabhängigkeiten, Quellcode oder Konfigurationsdateien in den Container zu übertragen.
* **Befehle ausführen (RUN):**
    * Führt Befehle im Container aus, um Software zu installieren, Konfigurationen vorzunehmen oder die Anwendung zu erstellen.
    * Dies wird verwendet, um die Umgebung für die Anwendung einzurichten.
* **Port freigeben (EXPOSE):**
    * Gibt an, welche Ports die Anwendung im Container verwenden wird.
    * Dies dient zur Dokumentation und ermöglicht die Portweiterleitung beim Starten des Containers.
* **Startbefehl (CMD/ENTRYPOINT):**
    * Definiert den Befehl, der beim Starten des Containers ausgeführt wird.
    * Dies startet die Anwendung oder den Dienst, der im Container laufen soll.
  
Welche alle in der Applikation enthaten sind.
# Tasks
*Wir haben JIRA mit unserem Repository verbunden und die Tasks erstellt als Epics.


![grafik](https://github.com/user-attachments/assets/6374c2b4-528f-4583-8203-59ab2549ad40)


# Authentication 

* Noch nicht erfüllt


# Observe Tools Grafana

**
noch nicht erfüllt


# Kubernetes

**
noch nicht erfüllt 
