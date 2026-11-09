# Plan de Pruebas

**Asignatura:** Diseño y Pruebas (Grado en Ingeniería del Software, Universidad de Sevilla)  
**Curso académico:** <!-- p.ej., 2025/2026 -->  
**Grupo/Equipo:** <!-- p.ej., L4-03 Equipo 33 -->  
**Nombre del proyecto:** <!-- p. ej. Petris -->  
**Repositorio:** <!-- URL del repo -->  
**Integrantes (máx. 6):** <!-- Nombre Apellidos (US-Id / correo @us.es) -->


## 1. Introducción

Este documento describe el plan de pruebas para el proyecto **[Nombre del Proyecto]** desarrollado en el marco de la asignatura **Diseño y Pruebas 1** por el grupo **LX-XY**. El objetivo del plan de pruebas es garantizar que el software desarrollado cumple con los requisitos especificados en las historias de usuario y que se han realizado las pruebas necesarias para validar su funcionamiento.

## 2. Alcance

El alcance de este plan de pruebas incluye:

- Pruebas unitarias.
  - Pruebas unitarias de backend incluyendo pruebas servicios o repositorios
  - Pruebas unitarias de frontend: pruebas de las funciones javascript creadas en frontend.
  - Pruebas unitarias de interfaz de usuario. Usan la interfaz de  usuario de nuestros componentes frontend.
- Pruebas de integración.  En nuestro caso principalmente son pruebas de controladores que también se ejecutarán mediante JUnit.

## 3. Estrategia de Pruebas

### 3.1 Tipos de Pruebas

#### 3.1.1 Pruebas Unitarias
Las pruebas unitarias se realizarán para verificar el correcto funcionamiento de los componentes individuales del software. Se utilizarán herramientas de automatización de pruebas como **JUnit** en backend y jest en frontend.

#### 3.1.2 Pruebas de Integración
Las pruebas de integración se enfocarán en evaluar la interacción entre los distintos módulos o componentes del sistema, nosotros las realizaremos a nivel de API, probando nuestros controladores Spring.

## 4. Herramientas y Entorno de Pruebas

### 4.1 Herramientas
- **Maven**: Gestión de dependencias y ejecución de las pruebas.
- **JUnit**: Framework de pruebas unitarias.
- **Jacoco**: Generación de informes de cobertura de código. Si se ejecuta el comando de maven install, se copiará el informe de cobertura a la subcarpeta del repositorio /docs/deliverables/D3/coverage (puede visualizarse pulsando en el fichero index.html de dicho directorio).
- **Allure**: Generación de informes de estado de las últimas ejecuciones de las pruebas. Genera dos informes: uno agrupado por módulo/épica, feature e historia de usuario, y otro que además agrupa por capa (controlador, servicio...) y tipo de prueba (aislada o sociable). También genera la [matriz de trazabilidad](Matriz%20de%20trazabilidad.md) a partir de las anotaciones de los tests. Si se ejecuta el comando de maven install, se copiarán los informes a la subcarpeta del repositorio /docs/deliverables/D3/status (pueden visualizarse pulsando en el fichero index.html de dicho directorio).
- **Jest**: Framework para pruebas unitarias en javascript.
- **React-test**: Librería para la creación de pruebas unitarias de componentes React.

### 4.2 Entorno de Pruebas
Las pruebas se ejecutarán en el entorno de desarrollo y, eventualmente, en el entorno de pruebas del servidor de integración continua.

## 5. Planificación de Pruebas
### 5.1 Estado y trazabilidad de Pruebas por Módulo y Épica

El informe de estado de las pruebas se encuentra [aquí](
https://gii-is-dp1.github.io/group-project-seed/deliverables/D3/status/). Incluye dos vistas:

- [Pruebas por épica / feature / historia de usuario](
https://gii-is-dp1.github.io/group-project-seed/deliverables/D3/status/awesome/).
- [Pruebas por épica / feature, capa y tipo (aislada o sociable)](
https://gii-is-dp1.github.io/group-project-seed/deliverables/D3/status/layers/).

### 5.2 Matriz de trazabilidad

La [matriz de trazabilidad](Matriz%20de%20trazabilidad.md) entre historias de usuario y pruebas se genera automáticamente al ejecutar `./mvnw verify` (o `install`); no se debe editar a mano. Para cada historia de usuario indica cuántas pruebas la cubren y de qué tipo, avisa de las historias del [documento de requisitos](../D1/Análisis%20de%20requisitos%20del%20sistema.md) que no tienen pruebas, y detalla cada prueba con su descripción, tipo y estado.

La información se toma de las anotaciones de cada clase o método de prueba:

| Anotación | Uso |
|-----------|-----|
| `@Epic("...")`, `@Feature("...")` | Módulo/épica y funcionalidad a la que pertenece la prueba. |
| `@Story("HU-01: Iniciar sesión")` | Historia de usuario que verifica. Puede repetirse si verifica varias. El identificador `HU-xx` debe coincidir con el del documento de requisitos. El prefijo `HU` se puede cambiar en `allurerc.mjs` (`STORY_PREFIX`). |
| `@Description("...")` | Descripción de lo que verifica la prueba. |
| `@ControllerTest`, `@ServiceTest`, `@RepositoryTest`, `@ModelTest`, `@Layer("...")` | Capa que se prueba. |
| `@IsolatedTest` / `@SociableTest` | Prueba aislada (los colaboradores se sustituyen por mocks) o sociable (usa los colaboradores reales, como la base de datos). |

Las anotaciones de capa y tipo están en el paquete de pruebas `es.us.dp1.lx_xy_26_27.your_game_name.testing`. Por ejemplo:

```java
@Epic("Users & Admin Module")
@Feature("Authentication")
@ControllerTest
@IsolatedTest
@WebMvcTest(AuthController.class)
class AuthControllerTests {

    @Test
    @Story("HU-01: Iniciar sesión")
    @Description("Un usuario con credenciales válidas obtiene un token JWT.")
    void shouldAuthenticateUser() { ... }
}
```

### 5.3 Cobertura de Pruebas

El informe de cobertura de pruebas se puede consultar [aquí](
https://gii-is-dp1.github.io/group-project-seed/deliverables/D3/coverage/).

*Nota importante para el alumno*: A la hora de entregar el proyecto, debes modificar las url para que estén asociadas al respositorio concreto de tu proyecto. Date cuenta de que ahora mismo apuntan al repositorio _gii-is-DP1/group-project-seed_.

## 6. Criterios de Aceptación

- Todas las pruebas unitarias deben pasar con éxito antes de la entrega final del proyecto.
- La cobertura de código debe ser al menos del 70%.
- No debe haber fallos críticos en las pruebas de integración y en la funcionalidad.

## 7. Conclusión

Este plan de pruebas establece la estructura y los criterios para asegurar la calidad del software desarrollado. Es responsabilidad del equipo de desarrollo y pruebas seguir este plan para garantizar la entrega de un producto funcional y libre de errores.
