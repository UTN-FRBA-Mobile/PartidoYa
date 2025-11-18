# 🏆 PartidoYa!
PartidoYa! es una aplicación móvil diseñada para ayudarte a **encontrar jugadores**, **organizar partidos de fútbol** y **participar en encuentros cerca de ti**.  
Aquí encontrarás una guía completa para aprender a utilizar la aplicación.

---

## 📱 Pantalla Principal
Al abrir la aplicación, llegarás a una pantalla que te solicita **iniciar sesión**.  
Puedes elegir entre:
- **Crear una cuenta**.
- **Iniciar sesión** con una cuenta existente.
- **Iniciar sesión** usando **Google**.

<p align="center">
  <img src="./docs/assets/images/MainScreen.png" alt="Pantalla principal" width="40%">
</p>

---

## 📝 Registro
Para crear una cuenta, debes:
1. Especificar tu **correo electrónico** y **contraseña** en un primer paso.
2. Luego, completar tu **información de jugador** (nombre, estilo de juego, posición favorita, etc.).
3. ¡Ahora puedes usar **PartidoYa!** para encontrar tu próximo partido!

<p align="center">
  <img src="./docs/assets/images/Inscription.png" alt="Pantalla de inscripción" width="40%">
  <img src="./docs/assets/images/ProfileCreation.png" alt="Pantalla de creación del perfil" width="40%">
</p>

---

## 🔐 Inicio de Sesión
Si ya tienes una cuenta, solo necesitas ingresar tu **correo electrónico** y **contraseña** para **acceder a la aplicación**.

<p align="center">
  <img src="docs/assets/images/Login.png" alt="Pantalla de inicio de sesión" width="40%">
</p>

---

## 🏠 Pantalla Principal (Usuario Conectado)
Una vez **conectado**, llegarás a la **pantalla principal**, donde encontrarás:
- Los botones **"Buscar Jugadores"** y **"Buscar Partidos"**.
- Una **barra de navegación** en la parte inferior que permite acceder a las diferentes secciones de la aplicación.

<p align="center">
  <img src="./docs/assets/images/LoggedMainScreen.png" alt="Pantalla principal (usuario conectado)" width="40%">
</p>

---

## 👤 Perfil del Usuario
El ícono ubicado a la **derecha** de la barra de navegación te lleva a tu **perfil de jugador**.  
Desde el perfil puedes **consultar y editar** tu información personal.

<p align="center">
  <img src="./docs/assets/images/ProfilePreview.png" alt="Pantalla del perfil personal" width="40%">
  <img src="./docs/assets/images/InfoModifications.png" alt="Pantalla de la modificación de los datos personales" width="40%">
</p>

---

## ➕ Crear un Partido
La **pantalla principal** (accesible desde el ícono central) ofrece varios botones, incluido **"Buscar Jugadores"**.  
Al presionar este botón, el usuario accede a la **página de creación de partido**.  
Debes indicar la **hora de inicio**, la **duración**, el **tipo del partido** y el **número de jugadores necesarios** (indicando eventualmente su posición).  
El partido se **publica**, y otros usuarios pueden **inscribirse** para participar.

<p align="center">
  <img src="./docs/assets/images/GameCreation.png" alt="Pantalla de creación de partido" width="40%">
</p>

---

## 🔎 Buscar un Partido
La **pantalla principal** también permite **buscar entre los partidos ya publicados** mediante el botón **"Buscar Partidos"**.  
Al pulsar este botón, se muestran los partidos existentes, y es posible **inscribirse en uno o varios** de ellos.  
Además, al hacer clic en el **nombre del cancha**, se abre una ventana mostrando la **ubicación exacta** y la opción de **abrir la ruta en Google Maps** para llegar fácilmente.

<p align="center">
  <img src="./docs/assets/images/SearchGames.png" alt="Pantalla de búsqueda de partido" width="40%">
  <img src="./docs/assets/images/Map.png" alt="Pantalla con el mapa de la cancha" width="40%">
</p>

---

## 📅 Mis Partidos
El ícono de la **izquierda** te lleva a la sección **"Mis Partidos"**, donde se registran todos los partidos en los que el usuario está inscrito.  
Los partidos se dividen en **2 secciones**:
1. Partidos **creados y publicados** (donde el usuario es **organizador**).
2. Partidos **a los que te has unido** como jugador.

Desde esta pantalla, también es posible **ver la ubicación del cancha en el mapa** o **cancelar tu participación** en un partido.

<p align="center">
  <img src="./docs/assets/images/PlayerGamesView.png" alt="Pantalla Mis Partidos (jugador)" width="40%">
  <img src="./docs/assets/images/OrganizerGamesView.png" alt="Pantalla Mis Partidos (organizador)" width="40%">
</p>

---
## Configuracion Oauth
Para configuar el servicio de google Oauth se debe crear un web client en google cloud
<img width="1919" height="944" alt="image" src="https://github.com/user-attachments/assets/e0999f7a-e3bb-465e-85a3-861ef2dd4833" />

En la carpeta strings reemplazar web_client_id por el id del cliente creado
<img width="913" height="22" alt="image" src="https://github.com/user-attachments/assets/ee32052e-e0c4-451c-b719-388fd365d78b" />

Crear un cliente de android
<img width="1917" height="944" alt="image" src="https://github.com/user-attachments/assets/031c6f27-71f2-4be0-bb54-1ff19b0fff92" />

Indicar nombre del paquete com.example.partidoya y certificado SHA-1

<img width="564" height="187" alt="image" src="https://github.com/user-attachments/assets/94bc34a8-263d-4cb3-ac84-dcb0bab80b32" />

El certificado SHA-1 se puede obtener con el siguiente comando

keytool -list -v \
  -keystore ~/.android/debug.keystore \
  -alias androiddebugkey \
  -storepass android \
  -keypass android
  
<img width="1042" height="308" alt="image" src="https://github.com/user-attachments/assets/3300deb6-2740-4424-95f4-ddca86c271aa" />


En caso de que no exista generar con el comando a continuacion

keytool -genkeypair -v \
  -keystore ~/.android/debug.keystore \
  -storepass android \
  -alias androiddebugkey \
  -keypass android \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -dname "CN=Android Debug,O=Android,C=US"
  
---
## Puesta en marcha del backend
Se necesita java con maven
Base de datos postgress

En el archivo application.yml indicar:
Url de la base de datos con el sigueinte formatto jdbc:{URL}?sslmode=require
Usuario y contrasenia de la base
Secreto del JWT

<img width="524" height="528" alt="image" src="https://github.com/user-attachments/assets/0cd2b675-5054-4cbd-aaf2-21171c6fa18d" />

Compilar con el comando

mvn clean install

Levantar el backend con el comando

java -jar target/api-0.0.1-SNAPSHOT.jar

---

## 📬 Autores
- **Ciro Fernandez** ([@Ziro41](https://github.com/Ziro41))
- **Gerardo Muñoz** ([@GerrMunoz](https://github.com/GerrMunoz))
- **Ramiro Navarro** ([@rami-nava](https://github.com/rami-nava))
- **Théo Parezys** ([@MirageOff](https://github.com/MirageOff))


## Link al backend
https://github.com/rami-nava/partidoYa-backend 
