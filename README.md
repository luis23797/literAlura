# Proyecto LiterAlura
![Static Badge](https://img.shields.io/badge/Estado%20del%20proyecto-Terminado-green)
- [Descripcion del proyecto](#descripcion-del-proyecto)
- [Estado del proyecto](#estado-del-proyecto)
- [Caracteristicas de la aplicacion](#caracteristicas-de-la-aplicacion)
- [Instalacion](#Instalacion)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
# Descripcion del proyecto
Este es un proyecto que consiste en la codificacion de una aplicacion de escritorio Java que implemente la API Gutendex para la persistencia de datos consultados

# Estado del proyecto
:heavy_check_mark: Proyecto terminado :heavy_check_mark:

# Caracteristicas de la aplicacion
LiterAlura proporciona las siguientes opciones:
* `Buscar un libro`
* `Listar Libros registrados` 
* `Listar Autores registrados`
* `Listar Autores vivos en un determinado año`
* `Listar libros por idioma`
* `Top 10 libros mas descargados`
* `Estadisticos`
* `Buscar Autor por nombre`

Mediante esta aplicacion es posible realizar consultas a la API Gutendex para obtener informacion sobre los diferentes libros existentes en esta biblioteca y almacenar cada una de esas consultas asi como buscar informacion sobre los datos registrados en los libros registrados.

# Instalacion 
* Clonar el repositorio usando:
```
git clone https://github.com/luis23797/literAlura.git
```
* Crear una base de datos local y configurar los parametros de acceso a la base de datos en las propiedades de la aplicacion
```
// application.properties -> Archivo a editar

spring.datasource.url=jdbc:postgresql://${Tu_HOST}/literAlura
spring.datasource.username=${Usuario_De_BD}
spring.datasource.password=${Contraseña_De_UBD}
```
# Tecnologias utilizadas
- Java
- Jackson DataBind
- API Gutendex
- JPA
- Postgres Sql