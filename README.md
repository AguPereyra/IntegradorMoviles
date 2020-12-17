# IntegradorMoviles
Integrative work for the subject Tecnologías Móviles (Mobile Technologies).

## Description
The goal is to develop an individual application on the Android platform that allows the user to visualize their cattle and its data.

## To run
You can open the project directly on Android Studio. In order to be able to build the app, the following software is needed:

* Kotlin
* JVM
* Gradle
* Android 6.0 or higher

### Apk file
The Apk file can be found at *githubStuff/app-VacasLocas-TP_Integrador_PEREYRA.apk*

## Login data
You can go to the register screen and create a brand new user. It will be registered and you will be able to reuse it for next logins. Users are saved in plain text under a local SQLite, which you can access connecting your device to your computer and running (under a Linux shell):  
`adb forward tcp:8080 tcp:8080`  
Then going to your browser to the URL localhost:8080.

## App working
<img src="githubStuff/media/appSample.gif" width=25% height=25% />

## App requirements

### Non functional
1. The application must be usable.
2. The name of the package must be: com.iua.<student's name and surname>
3. The application must provide support for internationalization (String Resources)
4. The application must maintain the state of the windows through rotations.
(Activities).
5. Operations performed against the server should not prevent the user from using the application. (AsyncTask)
6. Design material may be used: https://material.io/
7. Some activity must incorporate fragments.

### Second delivery requirements
1. Networking, use of SQLite, menus, dialogs.
2. Delivery date: 12/11/2020
3. Delivery method: Upload repository to GitHub.
4. Add a gif of the application and the compiled application (.apk)


### Extra
We were asked to add an extra part for the final presentation. In my case, I choose to incorporate the ML Kit library and use it *Face Detection* functionality to simulate the recognition of cattle through their faces.

## Workgroup
* Members: Pereyra Agustín Ezequiel.

* Teacher: Fanin Nicolás.

* Subject: Tecnologías Móviles (Mobile Technologies).

* Year: 2020.

* University: CRUC-IUA-UNDEF.
