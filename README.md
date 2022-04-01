# MARVEL

# Estructura del proyecto

# Variables privadas

Se debe añadir en local.properties los siguientes datos

- ts="####"
- apikey="API_KEY"
- hash="HASH"

# Comunicación entre capas

## Activity - ViewModel

Desde la Activity se comunican los eventos al Viewmodel.
Desde la Activity se observan, LiveData, los eventos del ViewModel para comunicar al usuario las respuestas.

Todas las decisiones se toman en el ViewModel. 
Por ejemplo, Si el usuario quiere reintentar cargar la lista de personajes en home, la Activity comunica el evento pero no sabe qué acciones desencadena.

### ObservableLiveData

Es una implementación propia basada en MutableLiveData cuyo comportamiento es similar a un Observable de Rx.
Al contrario que MutableLiveData, los nuevos observadores no reciben el último valor emitido.

Ejemplo de uso. En LiveData utilizados para comunicar errores a la vista. Si la pantalla se recrea por un cambio de configuración, un error pasado no sería emitido erróneamente.

## ViewModel Repository

### Caso pantalla Home-Lista de personajes

Se implementa mediante corrutinas. Aseguran la ejecución asíncorna fuera del hilo principal.

### Caso pantalla Detalle de personaje

Se implementa mediante Rx. Basado en el patrón Observer y permite realizar operaciones en hilos secundarios.

### Corrutinas versus Rx.

Aparentemente se puede conseguir el mismo resultado desde el punto de vista del usuario con ambas implementaciones.

Considero que Rx debe usarse para problemas que realmente encajen con el patron Observer. Como por ejemplo, una vista que observe el estado autenticación del usuario.
Y en caso de que se notifique un cambio de estado se puedan ejecutar las respuestas pertinentes como repintado de pantallas.

Por otro lado una corrutina es mejor opción para ejecutar lógicas de negocio en las que una acción desecadena una ejecución y espera un resultado.

En este proyecto se implementan ambas formas con fines demostrativos.

# Control de errorres

## Corrutinas

La corrutina se lanza dentro un bloque try-catch. En caso de error se captura la excepción.

## Rx callback de error

En el callback de error de la subscripción se implementa la respuesta en función del tipo de error.
Adicionalmente se puede hacer uso de onErrorReturn para devolver un objeto de respaldo o relanzar un error.

# Flavors

He creado tres variantes de compilación

- dev: con valores para desarrollo
- pro: con valores para producción
- mock: con valores para desarrollo y simulaciones de respuesta de servidor desde mock locales.

En función de flavors también se podrian configurar diferentes source sets para personalizar el producto.

## Flavor mock

Hay tres versiones de la clase MockInterceptor. Solo la que está en el source set mock realmente es un interceptor de retrofit que simula la respuesta de servidor del detalle de un personaje.
Los datos simulados se obtienen de un json local.

# Timber + Crashlytics

En la clase TimberCrashlytics se implementa el registro de errores y la clase CrashReportingTree que lo personaliza.

En caso ser una ejecución del flavor release y registrar un mensaje de error, este queda grabado en Crashlytics como error non-fatal.
En caso de debug se escribe el mensaje en el registro.

Esta implementación aporta dos soluciones:

- Controlar cuando es escriben los errores, para que no sean escritos en release.
- **Registrar en Crashlytics todos los errores controlados. Aporta información que de otro modo estaría oculta al desarrollador y da información estadística de errores que en principio no serían relevantes y que lo serían si su volumen fuera alto.**

# Nommenclatura de recursos

Basado en https://jeroenmols.com/img/blog/resourcenaming/resourcenaming_cheatsheet.pdf

- LAYOUTS <WHAT>_<WHERE>.XML
<WHAT>: activity, fragment, view, item o layout.

- STRINGS <WHERE>_<DESCRIPTION>
DRAWABLES <WHERE>_<DESCRIPTION>_<SIZE>

- IDS <WHAT>_<WHERE>_<DESCRIPTION>
<WHAT>: Android/Custom view class.

- DIMENSIONS <WHAT>_<WHERE>_<DESCRIPTION>_<SIZE>
<WHAT>: width, height, size, margin, padding, elevation, keyline, textsize.

# KtLint

Es un linter y formateador de código.
Está configurado en gradle para que se ejecute siempre antes de cada ejecución.

# Mejoras a implementar

- Proguard
- SafetyNet
- Funcionamiento off line con respaldo de base de datos encritpada. ROOM + SqlCypher
- SSL Pinning
- Dokka: generador de documentación
- Crear theme para modo claro y oscuro
- Property test based https://github.com/pholser/junit-quickcheck
- Screen shoot test https://facebook.github.io/screenshot-tests-for-android/
- SavedStateViewModel: para almacenar el estado de la pantalla (evitar relanzar peticiones al servidor en recreaciones de la pantalla) 
