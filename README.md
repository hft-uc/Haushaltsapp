# Haushaltsapp
Diese README ist unsere Projektdokumentation.


1. Einleitung
2. Planung
    1. Organisation
    2. x

3.  Werkzeuge für die Entwicklung
    1. Versionsverwaltung
    2. x

4. Architektur

5. Firebase zeugs bla bla
   1. Sicherheit
6. Kotlin vs Java
7. Projektjournal
8. Fazit




### 3.1 Versionsverwaltung
Für die Versionsverwaltung benutzen wir Git. Wir haben uns darauf geeinigt nur auf dem Master branch zu arbeiten da unsere Aufgabenbereiche Modular aufgeteilt sind. Als Plattform für unser Remote Repository benutzen wir Github da es kostenlos ist. Die Handhabung ist recht leicht und es gibt uns auch die Möglichkeit Workflows zu benutzen. Ein Workflow ist ein automatisierter Prozess der zum Beispiel überprüft ob unsere App kompiliert.   

### 4 Architektur
#### 4.1 Packete nach Feature nicht Schichten
Es gibt 2 Hauptgruppen bei der Aufteilung eines Projektes. Die eine ist die Aufteilung der Packete nach Schichten, also jeweils ein Packet für alle `Activitiy`,`Fragment`, `ViewModel`, `Model` usw. Dagegen gibt es die Aufteilung nach Features. Also ein Packet pro Feature, wo jeweils alle zum Feature relevanten Klassen enthalten sind und somit `Activitiy`,`Fragment`, `ViewModel` und `Model` alle in einem Packet zusammen vermischt sein können.

Wir haben uns für die Aufteilung nach Feature entschieden. Es gruppiert Klassen, die eng mit einander arbeiten, zusammen und verringert so den "mentalen weg", den ein Entwickler durch das Dateisystem laufen muss, um zu den dazugehörigen Klassen zu gelangen.

Da wir auch seperate jeweils an einen Feature arbeiten, hat jeder sein eigenes Packet und kann somit niemand anderen in quere kommen.

#### 4.2 Model View ViewModel (MVVM)
Ein sehr verbreitetes Architekturmodell in der nativen Android Entwicklung ist das von MVC abgewandelte MVVM Modell. Es schreibt eine Aufteilung der Verantwortlichkeiten vor und sorgt damit für eine ingesamt bessere Codebasis. Die Verantwortlichkeiten sind wie folgt aufgeteilt:

1. __Model__: Es beinhaltet die Daten, deren Format und die Zugriffslogik.
2. __View__: Es beinhaltet die graphische Oberfläche. Hier wird das Layout beschrieben, sowie das Verhalten bei einer Userinteraktion.
3. __ViewModel__: Es dient als eine Abstraktionsschicht zwischen Model und View. Es stellt alle business Funktionalität zur Verfügung, die die View benötigt. Zudem kann es den Zustand der View abspeichern.

Bei uns in der App ist:
1. das __Model__ die Integration mit Firebase, welche durch die Repository Klassen realisiert sind.
2. die __View__ die Beschreibung der Layouts durch die XML Dateien und das Interaktionsverhalten, welche durch die Fragment und Activity Klassen realisiert sind.
3. das __ViewModel__ die Abstraktionschicht zwischen Model und View und stellt alle nötigen business Funktionalitäten zur Verfügung. Dabei wird das `reactive pattern` verwendet, welches durch `LiveData` realisiert wird. Funktionen haben als Rückgabetyp `LiveData`, an welchen man einen Listener hinzufügen kann. Bei einem Event, also Daten wurden erfolgreich geladen, bzw haben sich verändert, kann die View reagieren und sich anpassen. Hierfür gibt es von Firebase auch Bibliotheken, die die Arbeit mit `RecyclerView`, also Listen, abnimmt. Es dient zusätzlich als Zustandspeicher, womit man über Fragmente hinweg Daten austauschen kann

Die Reaktive Programmierung ist hierbei sehr wichtig, weil man ansonsten, durch das Laden der Daten, den Hauptthread blockiert, welcher auch der UI Thread genannt wird. Somit würde die komplette UI einfrieren bis die Daten geladen worden sind. Im schlimmsten Fall würde Android auch die App beenden.

Das abspeichern des Zustandes in ViewModel ist wichtig, das man zb durch das drehen des Handys um 90°, das Fragement zerstört und neu erstellt wird. Somit geht jeglicher Zustand verloren, welcher nur im Fragment vorhanden ist.

Durch diese Aufteilung der Verantwortlichkeiten, kann man Code teilen zwischen Komponenten. Zudem haben erfahrene Entwicker einen leichteren Einstieg in neue MVVM Projekte, da sie wissen, wo sie nach bestimmten Verantwortlichkeiten suchen müssen. 

Ein weitere Vorteil, welcher hier aber nicht umgesetzt wurde, ist dass man die einzelnen Schichten einfacher testen kann. Indem man jeweils für die tiefer liegende Schicht ein Mock erstellt und diese per `Dependecy Injection` in die Klassen injektiert, kann man jede Schicht in isolation testen.

### 5 Firebase
#### 5.1 Sicherheit
Firebase schützt in der Datenbank abgelegte Daten nicht. Zumindest nicht von alleine bla bla bla


Default Regeln

    rules_version = '2';
    service cloud.firestore {
    match /databases/{database}/documents {
     match /{document=**} {
         allow read, write: if 
      	    request.time < timestamp.date(2021, 7, 1);
        }
       }
      }

Die Default Regeln sind nur für die Entwicklung gedacht da jedes Dokument gelesen und geändert werden kann. Die Datenbank ist nicht geschützt.


Bsp

    match /Budget/{itemId}{

      allow read: if isOwner(resource.data) &&
        isSignedIn();

      allow create: if isValidBudget(request.resource.data) &&
        isOwner(request.resource.data) &&
        checkKeys();

      allow update: if isValidBudget(request.resource.data) &&
        isOwner(request.resource.data) &&
        isOwner(resource.data) &&
        checkKeys();

      allow delete: if isOwner(resource.data);

      // FUNCTIONS
      function isSignedIn() {
        return request.auth != null;
      }
  
      function isOwner(budget) {
        return request.auth.uid == budget.;
      }
  
      function isValidBudget(budget) {
        return (
          // budget.id
          budget.id is string &&
          budget.id != '' 
        );
      }

      function checkKeys() {
        let requiredFields = ['id'];
        return request.resource.data.keys().hasAll(requiredFields)
      }
    }





  
#### 9.99 Wie man dieses Projekt baut
1. Log in [Firebase Konsole](https://console.firebase.google.com/)
2. Neues Projekt anlegen
3. Neue Datenabnk anlegen (Bild folgt)
4. Authentifizierung hinzufügen (Bild folgt)
5.Firebase mit dem Android App Projekt hinzufügen (Bild folgt)
6.----
7. google-services.json in app/ folder einfügen
   
   
   
   

