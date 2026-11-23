# java-gui-swing

Ejemplo sencillo de una interfaz gráfica (GUI) con **Java Swing**.

## Descripción

`AutoresApp.java` es una ventana que permite gestionar una lista de autores en memoria:

- Agregar, modificar y eliminar autores.
- Ver los datos en una tabla.
- Seleccionar una fila para cargarla en el formulario.
- Limpiar los campos.
- Mostrar mensajes de validación y estado.

> Los datos no se guardan en disco; se pierden al cerrar la aplicación.

## Requisitos

- JDK 8 o superior.

## Cómo ejecutar

Desde la carpeta del proyecto:

```bash
javac AutoresApp.java
java AutoresApp
```

También puedes abrir el archivo directamente en tu IDE (IntelliJ IDEA, Eclipse, VS Code, etc.) y ejecutar el `main`.

## Estructura

```text
.
├── AutoresApp.java   # Código de la interfaz y la lógica
└── README.md
```
