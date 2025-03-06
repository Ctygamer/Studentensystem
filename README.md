# Studentensystem mit Chat-Funktionalität

Dieses Repository beinhaltet den Quellcode für ein umfassendes Studentensystem, das eine interaktive Chat-Funktionalität integriert. Das System ist als Microservice-Architektur konzipiert und besteht aus drei Hauptkomponenten: einem React-basierten Frontend, einem Spring-Boot-Backend für die Hauptlogik und einem separaten Spring-Boot-Service für die Chat-Funktionalität. Die Datenhaltung erfolgt über eine externe PostgreSQL-Datenbank, die ebenfalls auf Render gehostet wird.

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
    cd <studentsystem>
    ```
    
6. Installieren der Abhängigkeiten:
    ```bash
    npm i
    ```
7.  Zum Projektverzeichnis Frontend navigieren:
    ```bash
    cd <studentfrontend>
    ```

8. Installieren der Abhängigkeiten:
    ```bash
    npm i
    ```
   
9. Zum Projektverzeichnis Chat navigieren:
    ```bash
    cd <chat>
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

## CI/CD-Pipeline (Continuous Integration/Continuous Deployment)

Um eine effiziente und automatisierte Bereitstellung zu gewährleisten, wurde eine CI/CD-Pipeline mit GitHub Actions eingerichtet. Diese Pipeline ist in zwei separate Workflows unterteilt:

**Überlegungen und Ziele des Workflows:**

* **Automatisierung von Tests zur Qualitätssicherung:**
    * Ein wesentlicher Aspekt des Workflows ist die Automatisierung von Unit-Tests und anderen Testformen. Dies ermöglicht eine frühzeitige Erkennung von Fehlern, was die Entwicklungszyklen beschleunigt und die Qualität der Software verbessert.
* **Automatisierte Bereitstellung für schnelle Zyklen:**
    * Die automatisierte Bereitstellung ermöglicht es, neue Funktionen und Fehlerbehebungen schnell und zuverlässig in die Produktionsumgebung zu bringen. Dies unterstützt agile Entwicklungspraktiken und ermöglicht es, schnell auf Benutzerfeedback zu reagieren.
* **Sicherstellung der Code-Qualität und Sicherheit:**
    * Die Pipeline beinhaltet Schritte zur Überprüfung der Code-Qualität, um sicherzustellen, dass der Code den Standards entspricht. Zudem werden bewährte Sicherheitspraktiken, wie die Verwendung von Umgebungsvariablen für sensible Daten, integriert.
* **Effizienz und Zuverlässigkeit durch Automatisierung:**
    * Durch die Automatisierung von Build-, Test- und Bereitstellungsprozessen werden manuelle Eingriffe reduziert und das Risiko menschlicher Fehler minimiert. Dies führt zu effizienteren Entwicklungszyklen und zuverlässigen Bereitstellungen.
* **Förderung von DevOps-Praktiken:**
    * Die Pipeline unterstützt die Prinzipien von Continuous Integration (CI) und Continuous Deployment (CD), indem sie regelmäßige Codeintegration, automatisierte Tests und schnelle Bereitstellungen ermöglicht.
* **Testen der Pipeline selbst:**
    * Um eine hohe zuverlässigkeit zu gewährleisten, wird die Pipeline selber in einem separaten Branch getestet.


* **`ci-cd.yml` (im `ci-cd`-Branch):**
    * Dieser Workflow dient der Entwicklung und dem Testen der Pipeline selbst.
    * Er wird bei jedem Push in den `ci-cd`-Branch ausgelöst.
    * Die Pipeline führt folgende Schritte aus:
        * Linting des Frontend-Codes zur Sicherstellung der Codequalität.
        * Ausführung von Unit-Tests für alle Komponenten (Frontend, Backend, Chat).
        * Erstellung von Docker-Images für jede Komponente.
        * Push der Docker-Images in die GitHub Container Registry (GHCR).
        * Deployment der Docker Images auf Render.
    * Somit werden alle Schritte, die für ein erfolgreiches Deployment notwendig sind, getestet, bevor diese in der Produktion ausgeführt werden.
* **`deploy.yml` (im `main`-Branch):**
    * Dieser Workflow ist für die Produktionsbereitstellung zuständig.
    * Er wird bei jedem Push in den `main`-Branch ausgelöst.
    * Dieser Workflow führt ein Deployment der zuvor erstellten Docker Images auf Render aus.

## CI/CD-Workflow (für Entwickler)

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
5.  **Pull Request von `ci-cd` nach `main` erstellen.**
6.  **Pull Request mergen.**
## Deployment auf Render

Die Anwendung wird auf der Cloud-Plattform Render gehostet. Hierbei werden Docker-Container verwendet, um eine konsistente und reproduzierbare Umgebung zu schaffen.

* Die `docker-compose.yml`-Datei definiert die Struktur der Anwendung und die Abhängigkeiten zwischen den Komponenten.
* Render verwendet die zuvor gespeicherten Docker Images, um die Anwendung zu deployen.
* Die externe PostgreSQL-Datenbank wird als Service in Render eingebunden, und die Verbindungsdaten werden über Umgebungsvariablen an die Backend-Komponenten übergeben.

## Render Deployment (Vorgang)

1.  GitHub-Repository mit Render verbinden.
2.  Neuen Web Service erstellen.
3.  Umgebungsvariablen konfigurieren.
* Die Applikation ist nun auch online erreichbar unter folgenden links: 
4. Backend: https://studentensystem-b5i7.onrender.com
5. Frontend: https://studentensystem-frontend.onrender.com/
6. Chat: 
