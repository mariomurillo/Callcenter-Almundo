# Call Center Almundo 
[![CircleCI Status](https://circleci.com/gh/mariomurillo/Callcenter-Almundo.svg?style=svg)][circleci]
[![codecov](https://codecov.io/gh/mariomurillo/Callcenter-Almundo/branch/develop/graph/badge.svg)][codecov]

[circleci]: https://circleci.com/gh/mariomurillo/Callcenter-Almundo
[codecov]: https://codecov.io/gh/mariomurillo/Callcenter-Almundo

Proyecto sobre funcionamiento de call center en java para Almundo

**Autor** Mario Andres Murillo

**Tecnologias** Las tecnologias que se usaron para este proyecto son las siguientes:
* Java 8
* Spring Boot
* Spring WebFlux
* Maven
* CircleCI
* Codecov

El uso de **Codecov** es para conocer la cobertura de pruebas unitarias, este proyecto fue trabajado bajo el proceso de **TDD** (Test-driven development) por lo cual es valioso mostrar el porcentaje de cobertura directamente desde la documentacion gracias al distintivo:

![codecov](https://codecov.io/gh/mariomurillo/Callcenter-Almundo/branch/develop/graph/badge.svg)

o mas graficamente: 

![sunburst](https://codecov.io/gh/mariomurillo/Callcenter-Almundo/branch/develop/graphs/sunburst.svg)

El uso de **CircleCI** es para tener una integracion continua y asi poder estar pendiente por cada commit que se realiza, que todo compilara y se ejecutaran las pruebas implementadas de forma correcta, para de esta forma garantizar que por algun commit realizado no dañar las funcionalidades ya existentes, se puede conocer el estado de la ultima construccion gracias al indicativo:

![CircleCI Status](https://circleci.com/gh/mariomurillo/Callcenter-Almundo.svg?style=svg)

Para la solucion del problema planteado, se decidio construir tres microservicios rest, uno para el manejo de una cola de llamadas, otro para el manejo de una cola de prioridad para los empleados y por ultimo uno con el **Dispatcher**, que es el encargado del procesamiento de llamadas y asignacion a los empleados. 

Se uso el framework de **Reactor** que viene implicito dentro de **Spring WebFlux** para hacer uso de la programacion reactiva, de esta forma el maneja el patron **Publish-Subscriber** donde el servicio de llamadas es el encargado de publicar cada vez que llegue una llamada y el servicio donde se encuentra el dispatcher sera el subscriptor.

Se limita la cantidad de mensajes procesados concurrentemente gracias a la funcionalidad de Backpressure que maneja Webflux:

![backpressure](https://image.slidesharecdn.com/springone2016reactor3060mn-160909143255/95/reactor-30-a-reactive-foundation-for-java-8-and-spring-7-638.jpg?cb=1473431998)

Donde gracias al metodo Buffer de la clase Flux puedo colocar un tamaño maximo de mensajes a entregar al servicio subscriptor:

![buffer](https://raw.githubusercontent.com/reactor/reactor-core/v3.1.3.RELEASE/src/docs/marble/buffersize.png)

Cuando llega una o mas llamadas al subscriptor y no se encuentra ningun empleado disponible para procesarla, el servicio de Dispatcher se encargara de consumir el servicio de llamadas para volver a ingresarla a la cola, con una prioridad mas alta.

El servicio encargado de manejar las llamadas podra recibir mas llamadas de las que se limitan en el servicio de Dispatcher, ya que este simplemente las dejara en una cola de mensajes y como se explico con anterioridad solo seran procesadas cierta cantidad de llamadas concurrentemente en el servicio del Dispatcher, esta cantidad esta parametrizada en un archivo de configuracion de este mismo servicio.

En el modelo las llamadas tienen un tiempo de duracion y una prioridad que inicialmente sera la misma para todas, cuando el procesador que se encuentra en el servicio del Dispatcher asigna una llamada a un empleado, esto pone a dormir un hilo durante el tiempo de duracion que tiene la llamada y posteriormente el empleado que fue obtenido de la cola de prioridad vuelve a esta cola con el fin de volver a estar disponible para atender mas llamadas, todo esto consumiendo el servicio para manejar empleados. 

**Arquitectura del proyecto**

**Referencias: **
* https://projectreactor.io/docs
* https://docs.spring.io/spring/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html
* https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
* https://www.slideshare.net/StphaneMaldini/reactor-30-a-reactive-foundation-for-java-8-and-spring
* https://docs.codecov.io/docs/supported-languages
* https://circleci.com/docs/2.0/
