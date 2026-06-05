# Decisiones Técnicas y de Diseño - Catalogo Express

Este documento detalla las decisiones de arquitectura, estrategias de resiliencia, manejo de backend, normalización de datos y la hoja de ruta para mejoras futuras de la aplicación.

## 1. Arquitectura y Trade-offs por Tiempo
* **Arquitectura:** Se implementó una arquitectura limpia (**Clean Architecture**) combinada con **MVVM** y flujos de estado unidireccionales (**UI State Pattern**). La capa de datos está completamente desacoplada de la interfaz de usuario mediante repositorios inyectados por **Hilt**.
* **Trade-offs (Compromisos por tiempo):** Debido al límite de 4 horas, se optó por un flujo de navegación híbrido utilizando `Fragments` con `ComposeView` embebido en lugar de migrar el 100% a *Compose Navigation*. Esto permitió cumplir con el requisito de usar Fragments manteniendo vistas modernas y declarativas en Jetpack Compose.

## 2. Nube e Integración Backend (Inciso 4)

### A. Autenticación y Manejo de Tokens (JWT)
Para implementar seguridad mediante tokens de portador (JWT), se seguiría la siguiente estrategia:
1.  **Almacenamiento Seguro:** El token se guardaría localmente utilizando **EncryptedSharedPreferences** (de la librería *Jetpack Security*). Esta API cifra automáticamente las llaves y valores a nivel de hardware utilizando el *Android Keystore System*, mitigando vulnerabilidades de acceso en dispositivos rooteados.
2.  **Inyección Automática:** Se crearía un interceptor de OkHttp (`AuthInterceptor`) que recupere el token del almacenamiento seguro y lo adjunte automáticamente en el encabezado de cada petición HTTP: `Authorization: Bearer <TOKEN>`.
3.  **Renovación de Tokens:** Se configuraría un `Authenticator` de OkHttp para interceptar de manera transparente las respuestas `401 Unauthorized`, solicitar un nuevo token al servidor mediante un *Refresh Token* y reintentar la petición original sin interrumpir la experiencia del usuario.

### B. Estrategia de Errores y Resiliencia
* **Timeouts:** Se limitaron los tiempos de espera a 15 segundos (`connect`, `read`, `write`) en el `OkHttpClient` para evitar que la aplicación quede colgada indefinidamente en redes inestables.
* **Estados de Red:** Las desconexiones físicas o fallas de enrutamiento se controlan capturando excepciones `IOException` en la capa de datos (`RepositoryImpl`), devolviendo un resultado tipificado de fallo con un mensaje amigable al usuario ("Verifica tu red").
* **Errores del Backend:** Se evalúa explícitamente el método `response.isSuccessful`. Si el servidor responde con un código de error (ej. 4xx o 5xx), se captura el código de estado HTTP y se propaga como una excepción controlada, mapeándose en la UI a través de un `DialogError` personalizado.

### C. Estrategia de Release y Entrega a QA
1.  **Diferenciación de Ambientes:** Mediante `buildTypes` en Gradle, la variante `debug` habilita el interceptor de logs (`HttpLoggingInterceptor`) y apunta a un entorno de pruebas o *Staging*. La variante `release` deshabilita los logs por seguridad, aplica ofuscamiento de código (`minifyEnabled true`) con *R8/ProGuard*, y apunta a la `BASE_URL` de producción.
2.  **Entrega a QA:** El proceso de entrega se automatizaría mediante **Firebase App Distribution** o los canales internos de **Google Play Store (Internal Testing)**. Al realizar un *Push* o *Pull Request* a la rama de lanzamiento, un flujo de CI/CD (GitHub Actions) compilaría ambas APKs (`./gradlew assembleDebug` y `./gradlew assembleRelease`) y las subiría a la plataforma de distribución notificando automáticamente al equipo de control de calidad.

## 3. Reto de Criterio: Normalización de Datos y Score (Inciso 5)
Para el cálculo del Score: `score = (rating * ln(stock + 1)) / max(price, 1)`, se aplicó una estricta estrategia de sanitización y normalización de datos en una función de extensión (`Extensions.kt`) antes de que impacte a la lógica de negocio o la UI:
* **Valores Nulos / Vacíos:** Si campos críticos como `rating`, `stock` o `price` vienen como `null` desde el JSON, se transforman por defecto a `0.0` (o `0` para enteros). El título ausente se reemplaza por "Producto sin título".
* **Protección Matemática:** Para evitar divisiones por cero en la fórmula si el precio es `0` o nulo, la función `max(price, 1.0)` garantiza que el denominador mínimo sea siempre `1.0`. Para el logaritmo natural, se añade `1.0` al stock (`stock + 1.0`) asegurando que el argumento de `ln()` nunca sea menor a `1.0`, previniendo así resultados indeterminados o negativos.
* **Ordenamiento:** Tras la normalización, la lista de productos se ordena inmediatamente de forma descendente utilizando `.sortedByDescending { it.score }` en la implementación del repositorio, garantizando consistencia en la visualización de ambas pantallas.

## 4. Mejoras Futuras (Hoja de Ruta)
Dada la limitación de tiempo de la prueba técnica, se identificaron los siguientes puntos clave para robustecer la aplicación en una fase posterior:
* **Persistencia Local con Room (Estrategia Offline-First):** Implementar una base de datos local con **Room** para almacenar en caché los productos obtenidos de la API. Esto permitiría un inicio instantáneo de la app y soporte completo *offline*, sincronizando los datos en segundo plano mediante *WorkManager*.
* **Monitoreo Activo de Conectividad:** Reemplazar el manejo de errores reactivo (`IOException`) por un `ConnectivityManager` activo utilizando `Flow` para escuchar los cambios en el estado de la red en tiempo real y mostrar un banner persistente de "Sin conexión" en la interfaz.
* **Notificaciones Locales de Stock:** Implementar un servicio que alerte al usuario mediante **Notificaciones Locales** cuando un producto de interés tenga "¡Pocas unidades!" o se quede "Sin stock".
* **Migración Completa a Compose Navigation:** Eliminar el grafo XML y los `Fragments` para transicionar hacia una solución 100% declarativa utilizando la librería oficial de *Jetpack Compose Navigation* con rutas seguras basadas en tipos (*Type-Safe Navigation*).
* **Pruebas Unitarias y de UI:** Desarrollar pruebas unitarias con *JUnit* y *MockK* para asegurar la precisión matemática del cálculo del score en el repositorio, y pruebas de interfaz con *Espresso/Compose Rule* para los componentes críticos.