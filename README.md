# JCCact3_t4

CRUD de Libros y Autores hecho con Spring Boot, Spring Data JPA, Thymeleaf y MySQL.
Un autor puede tener muchos libros y cada libro pertenece a un solo autor.

Proyecto en el VPS: http://20.119.81.138:8085

## Estructura

```
com.example.demoCrud
├── models/       Autor, Libro
├── repository/   AutorRepository, LibroRepository
├── service/      AutorService, LibroService
├── controllers/  controladores REST y controladores de vistas
├── dto/          objetos de entrada y salida
└── exception/    manejo de errores
```

## Relación entre entidades

- Libro tiene @ManyToOne hacia Autor, con @JoinColumn(name = "autor_id"), que crea la llave foránea.
- Autor tiene @OneToMany(mappedBy = "autor") hacia sus libros.
- No se puede eliminar un autor que tenga libros.

## Configuración

La conexión a la base de datos se define con variables de entorno, así no se suben contraseñas al repositorio.

| Variable    | Ejemplo |
|-------------|---------|
| SERVER_PORT | 8085    |
| DB_USER     | act3    |
| DB_PASS     | contraseña de la base de datos |

Hibernate crea las tablas automáticamente al iniciar la aplicación.

## Endpoints

| Método | Ruta               | Acción           |
|--------|--------------------|------------------|
| POST   | /api/libros        | Crear libro      |
| GET    | /api/libros        | Listar libros    |
| GET    | /api/libros/{id}   | Ver un libro     |
| PUT    | /api/libros/{id}   | Actualizar libro |
| DELETE | /api/libros/{id}   | Eliminar libro   |
| POST   | /api/autores       | Crear autor      |
| GET    | /api/autores       | Listar autores   |
| GET    | /api/autores/{id}  | Ver un autor     |
| PUT    | /api/autores/{id}  | Actualizar autor |
| DELETE | /api/autores/{id}  | Eliminar autor   |

Vistas web: /libros y /autores.

Ejemplo para crear un libro:

```json
{ "titulo": "Santa Biblia", "anioPublicacion": 100, "autorId": 1 }
```

Respuesta:

```json
{ "id": 1, "titulo": "Santa Biblia", "anioPublicacion": 100, "autorId": 1, "autorNombre": "Varios" }
```

La colección de Postman está en postman/JCCact3_t4.postman_collection.json.

## Despliegue en el VPS

1. Compilar el proyecto:

```
mvn clean package -DskipTests
```

2. Subir el archivo al VPS:

```
scp target/demoCrud-0.0.1-SNAPSHOT.jar azureuser@20.119.81.138:~/act3/
```

3. Crear la base de datos y el usuario en MySQL:

```
CREATE DATABASE act3_db;
CREATE USER 'act3'@'localhost' IDENTIFIED BY 'TU_PASSWORD';
GRANT ALL PRIVILEGES ON act3_db.* TO 'act3'@'localhost';
```

4. Ejecutar en el puerto 8085:

```
SERVER_PORT=8085 DB_USER=act3 DB_PASS=TU_PASSWORD java -jar ~/act3/demoCrud-0.0.1-SNAPSHOT.jar
```

5. Abrir el puerto 8085 en el firewall del VPS y en las reglas de red de Azure.

Para que siga corriendo al cerrar la sesión o reiniciar el VPS se usa el servicio systemd incluido en deploy/act3.service.

## Capturas de pantalla

Tablas y llave foránea en MySQL:

![Tablas](docs/capturas/01-tabla-autores-mysql.png)
![Llave foránea](docs/capturas/02-tabla-libros-fk.png)

Crear:

![Crear](docs/capturas/03-crear.png)

Leer:

![Leer](docs/capturas/04-leer-lista.png)

Actualizar:

![Actualizar](docs/capturas/05-actualizar.png)

Eliminar:

![Eliminar](docs/capturas/06-eliminar.png)

Relación (se ve el nombre del autor):

![Relación](docs/capturas/07-postman-get-libros.png)

Proyecto corriendo en el VPS:

![VPS](docs/capturas/08-vps-corriendo.png)