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
   
   
   
   

