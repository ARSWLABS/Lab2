# PUNTO2
# Snake Game - Proyecto Arreglado

Este proyecto es un juego de la serpiente implementado en Java con hilos para gestionar múltiples serpientes de forma autónoma. El juego incluye un tablero de juego compartido entre las serpientes, con acceso concurrente y una interfaz gráfica. La lógica fue ajustada y corregida para mejorar el rendimiento y solucionar errores relacionados con condiciones de carrera y manejo de hilos.

## Características

- **Múltiples Serpientes**: El juego maneja N serpientes autónomas que compiten en un mismo tablero.
- **Interfaz Gráfica**: El juego tiene una interfaz simple basada en `javax.swing` para mostrar el estado del juego.
- **Control de Juego**: El juego incluye botones para iniciar, pausar y reanudar el juego.
- **Estadísticas de Juego**: Al pausar el juego, se muestra la serpiente viva más larga y la peor serpiente (la que primero muere).

  
Todo esto esta explicado mas en detalle en el RESPUESTAS.txt que encuentras en 
```bash
     Lab2\Punto2\RESPUESTAS.txt
     // antes de la ruta coloca algun editor como nano o vi para poder leer su contenido, o abrelo en un block de notas si usas windows
   ```
## Tecnologías Usadas

- **Java 8+**: Lenguaje de programación utilizado para desarrollar el juego.
- **javax.swing**: Biblioteca utilizada para la interfaz gráfica de usuario (GUI).
- **Hilos en Java**: Para gestionar las serpientes de forma autónoma.

## Instalación

1. Clona este repositorio:
   ```bash
   git clone https://github.com/tu-usuario/snake-game.git
   ```

2. Navega al directorio del proyecto:
   ```bash
   cd Punto2
   ```

3. Compila el proyecto:
   ```bash
   mvn clean package
   ```

4. Ejecuta el juego:
   ```bash
   mvn exec:java
   ```
## Arquitectura
Gracias por la aclaración. Vamos a corregir y ajustar la descripción de la arquitectura para reflejar la estructura correcta del proyecto. Aquí tienes la versión actualizada y lista para agregar a tu archivo `README.md`:

## 🐍 **Snake Game** - Arquitectura

### 🌟 Descripción General

La **arquitectura** de este juego de la serpiente está diseñada para ser modular, eficiente y escalable. Los componentes clave incluyen la gestión de las serpientes, el tablero de juego, y la lógica que permite la interacción y el movimiento de las serpientes en tiempo real.

---

### 📂 **Estructura del Proyecto**

```plaintext
├───src
│   ├───main
│   │   ├───java
│   │   │   ├───enums                # Enumeraciones para configuraciones y direcciones del juego
│   │   │   └───snakepackage          # Lógica principal del juego (tablero, serpientes, celdas, etc.)
│   │   └───resources
│   │       └───img                   # Recursos gráficos (sprites, fondos)
│   └───test
│       └───java
│           └───edu
│               └───eci
│                   └───arsw          # Pruebas unitarias que aseguran el correcto funcionamiento del juego
```

---

### 🧩 **Componentes Clave**

#### 1. **`enums/`**
Contiene las enumeraciones que definen configuraciones importantes del juego, como:

- **`Directions`**: Define las direcciones en las que las serpientes pueden moverse (arriba, abajo, izquierda, derecha).
- **`GridSize`**: Especifica el tamaño de la cuadrícula del tablero donde las serpientes se mueven.

#### 2. **`snakepackage/`**
Aquí reside la lógica principal del juego, que se encarga de:

- **`Board`**: Gestiona el tablero de juego, incluyendo la creación de celdas y el control de las posiciones de las serpientes.
- **`Cell`**: Representa una celda del tablero de juego. Cada celda puede estar ocupada por una parte de una serpiente.
- **`Snake`**: Controla la lógica de las serpientes, como el movimiento, la dirección y la actualización de su cuerpo.
- **`SnakeApp`**: Es la clase que inicia y gestiona el juego. Contiene la interfaz gráfica y controla el inicio, pausa y reanudación del juego.

#### 3. **`resources/img/`**
Contiene los recursos gráficos (sprites) utilizados para representar elementos visuales del juego, como las serpientes, el fondo del tablero, y otros.

#### 4. **`test/`**
Ubicada en la carpeta `test/java`, contiene las pruebas unitarias que verifican que las funcionalidades del juego funcionen correctamente. Estas pruebas aseguran que el movimiento de las serpientes, la detección de colisiones, y la actualización del estado del juego se realicen de acuerdo con las reglas definidas.

---

### ⚙️ **Flujo de Trabajo**

- **Hilos**: Cada serpiente se ejecuta en un hilo independiente para permitir el movimiento autónomo y simultáneo en el juego.
- **Sincronización**: Para evitar condiciones de carrera, se implementan mecanismos de sincronización en áreas críticas donde se comparten recursos.
- **Interfaz de Usuario**: La interfaz permite al usuario interactuar con el juego, pausando, reanudando e iniciando el juego mediante botones.

---

### 🧪 **Pruebas**

El proyecto incluye pruebas unitarias que validan componentes clave del juego. Estas pruebas son fundamentales para garantizar que las funcionalidades principales, como el movimiento de las serpientes y la detección de colisiones, se ejecuten correctamente en todas las condiciones.

---

### 🚀 **Conclusión**

La arquitectura del proyecto es modular y está orientada a la separación de responsabilidades. Esto facilita la extensión del juego en el futuro y asegura un código mantenible y fácil de entender. Si tienes alguna pregunta o deseas más información sobre algún componente, no dudes en preguntar. 😊

---

¡Disfruta del juego! 🎮

## Problemas Resueltos

- **Condiciones de carrera**: Se corrigieron problemas de acceso concurrente a las celdas del tablero y las listas de las serpientes, utilizando sincronización.
- **Uso inadecuado de colecciones**: Se corrigió el uso de `LinkedList` sin sincronización en las serpientes.
- **Esperas activas**: Se optimizó el bucle principal, eliminando la espera activa innecesaria.

## Iniciar/Pausar/Reactivate el Juego

- **Iniciar**: Inicia el juego con las serpientes moviéndose por el tablero.
- **Pausar**: Pausa el juego, deteniendo el movimiento de las serpientes. Se muestran las estadísticas de la serpiente más larga y la peor serpiente.
- **Reanudar**: Reactiva el juego desde donde se pausó.
## Vista del juego en ejecución
![image](https://github.com/user-attachments/assets/0bc10f83-ebff-46ec-acc2-e7df14fc3b6f)

ademas tambien en consola vamos obteniendo la informacion
![image](https://github.com/user-attachments/assets/2090f77f-84ec-4d6b-8705-8fd767ac953c)


## Contribuciones

Este proyecto fue modificado y arreglado para solucionar errores relacionados con la concurrencia y mejorar la funcionalidad general del juego. Se añaden nuevas características como el control de iniciar/pausar/reactivar el juego.

¡Disfruta del juego! 🎮
## Licencia

Este proyecto está bajo la Licencia MIT - consulta el archivo [LICENSE](LICENSE) para más detalles.

Nota: Este README explica que el proyecto fue modificado y no creado desde cero, además detalla las características y los arreglos que realizaste en él. ¡Espero que te sirva!

Repositorio del que se tomó:

```bash
https://github.com/ARSW-ECI-archive/snake-race-thread-concurrency.git
```
