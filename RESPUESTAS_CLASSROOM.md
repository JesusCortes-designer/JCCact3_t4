# Respuestas para Classroom — Actividad 3

> Léelas, ajústalas a tus palabras y pega el texto final en Classroom.

**a) ¿Qué dos entidades elegiste y qué tipo de relación implementaste?**

Elegí **Autor** y **Libro**. Implementé una relación **uno a muchos** entre ellas, vista desde
el otro lado como **muchos a uno**: un autor puede tener muchos libros, pero cada libro pertenece
a un solo autor. En `Libro` usé `@ManyToOne` con `@JoinColumn(name = "autor_id")`, que crea la
llave foránea en la tabla `libros`, y en `Autor` usé `@OneToMany(mappedBy = "autor")` como lado inverso.

**b) ¿Qué es un Repository en Spring Data JPA y qué ventaja tiene sobre escribir las consultas SQL?**

Un Repository es una interfaz (por ejemplo `LibroRepository extends JpaRepository<Libro, Long>`)
que Spring Data JPA implementa automáticamente en tiempo de ejecución. Con solo declararla ya
tengo métodos como `save`, `findAll`, `findById` y `delete`, sin escribir código ni SQL. También puedo
declarar consultas con solo nombrar el método, como `existsByAutorId`. La ventaja es que reduce
mucho código repetitivo y errores, evita escribir SQL a mano, es independiente del motor de base de
datos (cambiar de MySQL a otro requiere pocos cambios) y mapea los resultados directo a objetos Java.

**c) ¿Por qué es buena práctica separar la lógica en una capa Service?**

Porque cada capa tiene una sola responsabilidad: el Controller solo recibe la petición y devuelve la
respuesta, y el Service contiene las reglas de negocio (validar datos, comprobar que el autor existe,
impedir borrar un autor con libros) y maneja las transacciones. Así la lógica se reutiliza (la usan
tanto el controlador REST como el de vistas Thymeleaf sin duplicarla), el código es más fácil de
probar y mantener, y si cambia la forma de exponer los datos no hay que reescribir las reglas.

**d) Enlaces**

- Repositorio de GitHub: `https://github.com/TU_USUARIO/JCCact3_t4`
- Proyecto en el VPS: `http://IP_DE_TU_VPS:8083`
- Colección de Postman/Bruno: archivo `postman/JCCact3_t4.postman_collection.json` del repositorio
  (también puedes adjuntarlo en Classroom).
