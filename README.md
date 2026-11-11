# JCCact3_t4 — CRUD Spring Boot + JPA + MySQL

CRUD de **Libros** y **Autores** con relación **Muchos a Uno** (`Libro` → `Autor`),
hecho con Spring Boot 4.1.2, Spring Data JPA, Thymeleaf y MySQL. Incluye vistas
propias (Thymeleaf) y API REST (para Postman/Bruno), todo en el mismo proyecto.

## Estructura

```
com.example.demoCrud
├── models/       Autor, Libro (entidades JPA)
├── repository/   AutorRepository, LibroRepository (JpaRepository)
├── service/      AutorService, LibroService (lógica de negocio)
├── controllers/  *RestController (JSON) y *ViewController (Thymeleaf)
├── dto/          Request/Response (el Libro devuelve autorNombre)
└── exception/    ApiException + manejador
```

## Relación entre entidades

- `Libro` tiene `@ManyToOne` hacia `Autor` con `@JoinColumn(name = "autor_id")` → llave foránea.
- `Autor` tiene `@OneToMany(mappedBy = "autor")` hacia sus libros.
- Regla de negocio: no se puede eliminar un autor que tenga libros (responde 409).

## Ejecutar en local

1. Crear la base de datos vacía: `CREATE DATABASE act3_db;`
2. Ajustar la contraseña en `application.properties` (`DB_PASS`) o definir las variables de entorno.
3. `mvn spring-boot:run` (o ejecutar `DemoCrudApplication` desde el IDE).
4. Abrir <http://localhost:8080> (vistas) o usar `http://localhost:8080/api/libros` (REST).

Hibernate crea las tablas automáticamente (`ddl-auto=update`).

## Variables de entorno (para el VPS)

| Variable      | Ejemplo                                             |
|---------------|-----------------------------------------------------|
| `SERVER_PORT` | `8083`                                              |
| `DB_URL`      | `jdbc:mysql://localhost:3306/act3_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` |
| `DB_USER`     | `act3`                                              |
| `DB_PASS`     | *(tu contraseña, nunca en el repositorio)*          |

## Endpoints REST

| Método | Ruta               | Acción              |
|--------|--------------------|---------------------|
| POST   | `/api/libros`      | Crear libro         |
| GET    | `/api/libros`      | Listar libros       |
| GET    | `/api/libros/{id}` | Obtener libro       |
| PUT    | `/api/libros/{id}` | Actualizar libro    |
| DELETE | `/api/libros/{id}` | Eliminar libro      |
| POST/GET/PUT/DELETE | `/api/autores[/{id}]` | CRUD de autores |

Ejemplo de creación:

```json
POST /api/libros
{ "titulo": "Cien años de soledad", "anioPublicacion": 1967, "autorId": 1 }
```

Respuesta (se ve el nombre del autor, no solo el ID):

```json
{ "id": 1, "titulo": "Cien años de soledad", "anioPublicacion": 1967, "autorId": 1, "autorNombre": "Gabriel García Márquez" }
```

La colección de Postman está en `postman/JCCact3_t4.postman_collection.json`
(variable `baseUrl`: cambia `localhost:8080` por la IP:puerto del VPS).

## Despliegue en el VPS

```bash
# En tu PC
mvn clean package -DskipTests
scp target/demoCrud-0.0.1-SNAPSHOT.jar usuario@IP_VPS:~/act3/

# En el VPS (base de datos y usuario propios)
sudo mysql -e "CREATE DATABASE act3_db; CREATE USER 'act3'@'localhost' IDENTIFIED BY 'TU_PASSWORD'; GRANT ALL PRIVILEGES ON act3_db.* TO 'act3'@'localhost';"

# Ejecutar en un puerto nuevo (distinto al de las actividades 1 y 2)
SERVER_PORT=8083 DB_USER=act3 DB_PASS=TU_PASSWORD java -jar ~/act3/demoCrud-0.0.1-SNAPSHOT.jar
```

Para dejarlo corriendo de forma permanente se incluye `deploy/act3.service` (systemd).
Si el VPS tiene firewall, abre el puerto: `sudo ufw allow 8083/tcp`.

## Capturas de pantalla

> Reemplaza cada línea con tu captura (guárdalas en `docs/capturas/`).

### Entidades y llave foránea en MySQL
![Tablas](docs/capturas/01-tabla-autores-mysql.png)
![FK](docs/capturas/02-tabla-libros-fk.png)

### CRUD
| Operación | Captura |
|-----------|---------|
| Crear | ![crear](docs/capturas/03-crear.png) |
| Leer | ![leer](docs/capturas/04-leer-lista.png) |
| Actualizar | ![actualizar](docs/capturas/05-actualizar.png) |
| Eliminar | ![eliminar](docs/capturas/06-eliminar.png) |

### Relación reflejada (nombre del autor, no solo el ID)
![relacion](docs/capturas/07-postman-get-libros.png)

### Corriendo en el VPS
![vps](docs/capturas/08-vps-corriendo.png)
