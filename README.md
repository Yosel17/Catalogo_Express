# Catalogo Express 🚀

Catalogo Express es una aplicación nativa para Android construida con Kotlin que permite listar y explorar productos de un catálogo digital consumido desde una API REST pública. El proyecto fue desarrollado como parte de una evaluación técnica, aplicando principios sólidos de arquitectura moderna, inyección de dependencias y flujos reactivos de interfaz de usuario.

---

## 📸 Capturas de Pantalla

| Pantalla de Catálogo (Listado) | Pantalla de Detalle del Producto |
| :---: | :---: |
| <img width="384" height="858" alt="image" src="https://github.com/user-attachments/assets/62267bce-7cea-4eb9-89e0-6a879dbaa2fb" width="300" alt="Lista de Productos" /> | <img width="386" height="857" alt="image" src="https://github.com/user-attachments/assets/6a998824-85e2-4666-8015-a706f86919e6" width="300" alt="Detalle de Producto" />
|

---

## 🛠️ Versiones y Tecnologías Usadas (Version Catalog)

El proyecto gestiona sus dependencias de forma centralizada utilizando **Gradle Version Catalog (`libs.versions.toml`)**. Las versiones clave implementadas son:

### Versiones de Entorno de Construcción
* **Android Gradle Plugin (AGP):** `8.13.2`
* **Kotlin (K2 Compiler ready):** `2.2.21`
* **Kotlin Symbol Processing (KSP):** `2.2.20-2.0.3`

### Librerías de Componentes de Arquitectura y UI
* **Jetpack Compose BOM:** `2025.12.00` (Material 3 habilitado)
* **Jetpack Navigation (Safe Args Kotlin):** `2.9.6`
* **Dagger Hilt (Inyección de Dependencias):** `2.57.2`
* **Retrofit:** `2.11.0` (Conector HTTP para consumo de API JSON)
* **OkHttp:** `4.12.0` (Cliente HTTP con soporte de Timeouts y Logging)
* **Coil Compose:** `2.7.0` (Carga asíncrona y eficiente de imágenes en red)
* **AndroidX Fragment KTX / AppCompat:** `1.8.5` / `1.7.0` (Estructura base de navegación)

---

## 🏗️ Decisiones Técnicas y Arquitectura

La aplicación fue diseñada siguiendo las pautas oficiales de Google para el desarrollo de aplicaciones robustas y escalables:

1.  **Clean Architecture + MVVM:** Separación estricta de responsabilidades en tres capas diferenciadas:
    * **Data:** Implementación de repositorios (`ProductListRepositoryImpl`), llamadas de red con Retrofit, interceptores de OkHttp y mapeadores de transferencia de datos (DTO a Modelo de Dominio).
    * **Domain:** Definición de modelos de negocio limpios y contratos de repositorios libres de dependencias del framework de Android.
    * **UI (MVI / Unidirectional Data Flow):** Control de estados reactivos utilizando `StateFlow` dentro de un `ViewModel`. La interfaz reacciona inmediatamente ante mutaciones del estado (`Loading`, `Success`, `Error`).
2.  **Navegación Híbrida (Fragments + Jetpack Compose):** Se implementó una arquitectura de navegación basada en `Fragments` (`NavGraph` XML y Safe Args) que encapsulan contenedores nativos `ComposeView`. Esta solución permite el cumplimiento estricto del ciclo de vida tradicional (`Lifecycle`) exigido por la prueba, integrando la potencia declarativa de las pantallas estructuradas en Jetpack Compose.
3.  **Algoritmo de Criterio (Cálculo del Score):** Se implementó una función de extensión centralizada en la capa de datos para calcular el puntaje dinámico de los ítems mediante la fórmula matemática requerida. Se añadieron protecciones explícitas para normalizar valores nulos, cadenas vacías y prevenir indeterminaciones matemáticas (como la división por cero si el precio es cero o logaritmos negativos basados en el stock).

---

## 💻 Pasos de Compilación y Ejecución

El repositorio está completamente configurado y listo para clonar, compilar y ejecutar desde la consola de comandos sin requerir software intermedio adicional.

### Prerrequisitos
* Tener instalado el JDK 17 o superior.
* Variables de entorno de Java configuradas (`JAVA_HOME`).

### 1. Clonar el repositorio
```bash
git clone [https://github.com/TU_USUARIO/catalogo-express.git](https://github.com/TU_USUARIO/catalogo-express.git)
cd catalogo-express
