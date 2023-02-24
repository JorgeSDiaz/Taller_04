# TALLER 4: ARQUITECTURAS DE SERVIDORES DE APLICACIONES, META PROTOCOLOS DE OBJETOS, PATRÓN IOC, REFLEXIÓN
Se construye un servidor Web (tipo Apache) en Java. El servidor es capaz de entregar páginas html e imágenes tipo PNG. Igualmente, el servidor provee un framework IoC para la construcción de aplicaciones web a partir de POJOS. Usando el servidor se puede construir una aplicación Web.

---
### Prerrequisitos
Para elaborar este proyecto requerimos de las siguientes tecnologías:

 - **[Maven](https://openwebinars.net/blog/que-es-apache-maven/)**: Apache Maven es una herramienta que estandariza la configuración de un proyecto en todo su ciclo de vida.
 - **[Git](https://learn.microsoft.com/es-es/devops/develop/git/what-is-git)**: Es un sistema de control de versiones distribuido (VCS).

---
###  Instalación
Primero clonamos el repositorio

     git clone https://github.com/jorge-stack/Taller_04.git
    
Se accede al repositorio que acabamos de clonar

	 cd Taller_04

Hacemos la construcción del proyecto

	 mvn clean package install
---
### Corriendo
Ahora corremos el servidor

**Windows**

	  java -cp .\target\classes\ org.myorg.App

**Linux/MacOs**

	 java -cp .\target\classes\ org.myorg.App

### Run
Para arrancar el servidor debemos de ejecutar el método estático "run()"
![Run]()
**Help**
![Help]()

### @RequestMapping
Definimos los métodos que definirán el servicio web, esto es posible poniendo dentro del repositorio root (org/myorg/App.java) los métodos acompañados del notation **"@RequestMapping"**, que tiene parámetros que nos permiten definir diferentes tipos de request, como request orientada a responder con un objeto, request que responder en formato plano y como request orientada a recibir un parámetro de la función por el path.

	 https://localhost:32000/pathDefined
	
**path**
Dentro de la anotación debemos definir este parámetro para poder tener una ruta de acceso desde el navegador, al método que estamos definiendo

![Path](https://i.imgur.com/0naodNu.png)
![hello](https://i.imgur.com/zplbsdz.png)
![bye](https://i.imgur.com/cQN27o1.png)
**file**
Podemos además definir este otro parámetro para indicar a la aplicación web que la respuesta a la consulta al path que definamos, sera un **File**
**{param}**
Podemos definir dentro del path para indicar que en aquella posición recibiremos el parámetro que definamos en la función.
![File Param](https://i.imgur.com/jLn00c4.png)
![png](https://i.imgur.com/n5HGlJG.png)
![html](https://i.imgur.com/CNhj9yu.png)
	
---
## Construido con

* [Maven](https://maven.apache.org/): Apache Maven es una herramienta que estandariza la configuración de un proyecto en todo su ciclo de vida.
* [Git](https://rometools.github.io/rome/):  Es un sistema de control de versiones distribuido (VCS).
* [IntelliJ](https://www.jetbrains.com/idea/): Es un entorno de desarrollo integrado para el desarrollo de programas informáticos.
* [Java 19](https://www.java.com/es/): Lenguaje de programación de propósito general, es decir, que sirve para muchas cosas, para web, servidores, aplicaciones móviles, entre otros. Java también es un lenguaje orientado a objetos, y con un fuerte tipado de variables.
* [Html](https://developer.mozilla.org/es/docs/Learn/Getting_started_with_the_web/HTML_basics): Es el código que se utiliza para estructurar y desplegar una página web y sus contenidos.
* [JavaScript](https://developer.mozilla.org/es/docs/Learn/JavaScript/First_steps/What_is_JavaScript): JavaScript es un lenguaje de programación o de secuencias de comandos que te permite implementar funciones complejas en páginas web
* [CSS](https://developer.mozilla.org/es/docs/Learn/CSS/First_steps/What_is_CSS):Las hojas de estilo en cascada permiten crear páginas web atractivas.

## Autor
* **[Jorge David Saenz Diaz](https://co.linkedin.com/in/jorgedsaenzd/en)**  - [Jorge-Stack](https://github.com/jorge-stack?tab=repositories)

## Licencia
**©** Jorge David Saenz Diaz, Estudiante de Ingeniería de Sistemas de la Escuela Colombiana de Ingeniería Julio Garavito.
