#  Prueba Técnica – Consulta de precios

## 🏗️  Arquitectura

La aplicación se ha realizado con las tecnologías Java, SpringBoot y Maven. Como indicaba la prueba hemos utilizado una base datos en memoria h2 que cargamos con los datos indicados en el enunciado.

La aplicación sigue una arquitectura hexagonal(puertos y adaptadores). Se ha optado por esta arquitectura debido a su facilidad de mantenimiento y escalabilidad así como su separación clara de responsabilidades entre negocio y framework. El código se organiza en tres grandes módulos:
 + Domain: Contiene el núcleo del negocio y todas las reglas funcionales. Está completamente aislada de frameworks y detalles de infraestructura.
 + Application: Contiene la implementación de los casos de uso y la lógica de la aplicación. Dependiente del dominio pero está desacoplada de detalles técnicos concretos.
 + Infrastructure: Contiene todas las implementaciones técnicas y las integraciones externas. En nuestro caso albergará los detalles técnicos de controladores y persistencia.

La aplicación sigue un enfoque API First utilizando OpenAPI como framework. Esto implica ventajas de escalibilidad y mantenimiento, validación automática de parámetros y request, documentación clara y accesible de las APIs expuestas y un contrato único y de fácil integración para consumidores. Así mismo tras arrancar la aplicación podemos consultar la documentación de swagger en la ruta http://localhost:8080/swagger-ui/index.html.

---

## 🧪 Testing

El proyecto incluye diferentes niveles de testing para cubrir tanto la lógica de negocio como el comportamiento real de la API.

### Tests unitarios

Los tests unitarios están centrados en validar la lógica interna de la aplicación de forma aislada. Para ello hemos usado Junit 5, Mockito y AssertJ.

Se han implementado tests para:

+ Casos de uso y servicios de aplicación
+ Excepciones de dominio
+ Adaptadores
+ Mappers
+ Controllers a nivel unitario

### Tests E2E

Se han implementado test que permiten probar la integración de todas las capas en end-to-end a través de karate. Para ello se ha creado una feature "prices.feature" donde se comprueban las cinco casuísticas que se han indicado en esta prueba.

### Ejecución de test

A pesar de que lo mas habitual sería que los test E2E se ejecutaran en un test container de docker,siguiendo lo declarado en el enunciado, estos test utilizaran la base datos en memoria h2. Estos test se podrán ejecutar lanzando todos los test de la aplicación(incluyendo los unitarios) o explícitamente usando el maven wrapper.

+ Ejecutar los test E2E -> mvnw -Dtest=PricesKarateE2ETest test
+ Ejecutar todos los test -> mvnw test
+ El reporte de los test E2E se genera en la ruta: target\karate-reports\karate-summary

---

## 📘 Detalles de implementación

Como dependencias y framework he intentado incluir lo mínimo para un buen funcionamiento y comodidad/claridad del código. A mayores de las herramientas comentadas he añadido lombok por su mejora en la claridad y legibilidad del código y mapstructs por su facilidad a la hora de poder implementar mappers de manera sencilla. El resto de dependencias son los distintos starters de springboot, herramientas de testing (Junit, Asserts, Mockito, Karate...), y openapi.

Se han generado además, complementado la estrategia API FIRST excepciones custom en este caso para la posible casuística de que no se encuentre un precio disponible dados una fecha de aplicación, un identificador de producto y un identificador de cadena. Esta excepción y el resto que pudieran surgir de futuros casos de uso se gestionan a través de un handler común que lo mapea a los distintos errores.

Siguiendo la lógica ligera y efectiva de la prueba se han obviado algunas prácticas comunes que se aplicarían a un entorno de producción:
+ Aplicar el patrón CQRS.
+ Incluir un gestor de base de datos (PostgreSQL, Mysql, MongoDB...) que a su vez se gestionaría mediante una herramienta de versionado/migraciones como Liquibase o Flyway.
+ Una vez tengamos un gestor de base de datos se amplificaría la utilidad de docker permitiendonos levantar distintos test containers o la base de datos que se utilizarían tanto para levantar la aplicación contra una base de datos en docker, para los test E2E de karate o para posibles futuros test de integración. Como se ha recomendado utilizar docker, pero a la vez usar una base datos en memoria h2, únicamente se ha configurado docker para levantar la aplicación.

## 🚀 Arrancar la aplicación

Para arrancar la aplicación podemos hacerlo únicamente arrancadola con maven y spring boot.

+ mvnw spring-boot:run

En caso de que queramos arrancarla con docker, lo haríamos mediante el siguiente comando:

+ docker compose up --build

---