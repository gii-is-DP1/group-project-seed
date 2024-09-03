# Plan de Pruebas

## 1. Introducción

Este documento describe el plan de pruebas para el proyecto **[Nombre del Proyecto]** desarrollado en el marco de la asignatura **Diseño y Pruebas 1** por el grupo **LX-XY**. El objetivo del plan de pruebas es garantizar que el software desarrollado cumple con los requisitos especificados en las historias de usuario y que se han realizado las pruebas necesarias para validar su funcionamiento.

## 2. Alcance

El alcance de este plan de pruebas incluye:

- Pruebas unitarias.
  - Pruebas unitarias de backend incluyendo pruebas servicios o repositorios
  - Pruebas unitarias de frontend: pruebas de las funciones javascript creadas en frontend.
  - Pruebas unitarias de interfaz de usuario. Usan la interfaz de  usuario de nuestros componentes frontend.
- Pruebas de integración.  En nuestro caso principalmente son pruebas de controladores.

## 3. Estrategia de Pruebas

### 3.1 Tipos de Pruebas

#### 3.1.1 Pruebas Unitarias
Las pruebas unitarias se realizarán para verificar el correcto funcionamiento de los componentes individuales del software. Se utilizarán herramientas de automatización de pruebas como **JUnit** en background y .

#### 3.1.2 Pruebas de Integración
Las pruebas de integración se enfocarán en evaluar la interacción entre los distintos módulos o componentes del sistema, nosotros las realizaremos a nivel de API, probando nuestros controladores Spring.

## 4. Herramientas y Entorno de Pruebas

### 4.1 Herramientas
- **Maven**: Gestión de dependencias y ejecución de las pruebas.
- **JUnit**: Framework de pruebas unitarias.
- **Jacoco**: Generación de informes de cobertura de código.
- **Jest**: Framework para pruebas unitarias en javascript.
- **React-test**: Liberaría para la creación de pruebas unitarias de componentes React.

### 4.2 Entorno de Pruebas
Las pruebas se ejecutarán en el entorno de desarrollo y, eventualmente, en el entorno de pruebas del servidor de integración continua.

## 5. Planificación de Pruebas

### 5.1 Cobertura de Pruebas

El informe de cobertura de pruebas se puede consultar [aquí](
https://html-preview.github.io/?url=https://raw.githubusercontent.com/gii-is-DP1/group-project-seed/main/target/site/jacoco/index.html).

### 5.2 Matriz de Trazabilidad

| Historia de Usuario | Prueba | Descripción | Estado |Tipo |
|---------------------|--------|-------------|--------|--------|
| HU-01: Iniciar sesión | [UTB-1:TestLogin](./src/test/java/com/proyecto/tests/TestLogin.java) | Verifica que un usuario puede iniciar sesión con credenciales válidas. | Implementada | Unitaria en backend |
| HU-02: Registrar usuario | [UTB-2:TestRegister](./src/test/java/com/proyecto/tests/TestRegister.java) | Verifica que un nuevo usuario puede registrarse en el sistema. | Implementada |Unitaria en backend |
| HU-03: Realizar pedido | [UTB-3:TestOrder](./src/test/java/com/proyecto/tests/TestOrder.java) | Asegura que un usuario puede realizar un pedido correctamente. | Implementada | Unitaria en backend |
| HU-04: Cancelar pedido | [UTB-4:TestCancelOrder](./src/test/java/com/proyecto/tests/TestCancelOrder.java) | Verifica que un usuario puede cancelar un pedido antes de ser procesado. | Implementada |Unitaria en backend |

### 5.3 Matriz de Trazabilidad entre Pruebas e Historias de Usuario

| Prueba             | HU-01 | HU-02 | HU-03 | HU-04 |
|--------------------|-------|-------|-------|-------|
| UTB-1:TestLogin          |   X   |       |       |       |
| UTB-2:TestRegister       |       |   X   |       |       |
| UTB-3:TestOrder          |       |       |   X   |       |
| UTB-4:TestCancelOrder    |       |       |       |   X   |

## 6. Criterios de Aceptación

- Todas las pruebas unitarias deben pasar con éxito antes de la entrega final del proyecto.
- La cobertura de código debe ser al menos del 70%.
- No debe haber fallos críticos en las pruebas de integración y en la funcionalidad.

## 7. Conclusión

Este plan de pruebas establece la estructura y los criterios para asegurar la calidad del software desarrollado. Es responsabilidad del equipo de desarrollo y pruebas seguir este plan para garantizar la entrega de un producto funcional y libre de errores.
