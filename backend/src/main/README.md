# Backend - Trinidad (jugadoresMySQL)

## Descripción general
Este módulo contiene el **backend** de la aplicación Trinidad, implementado con **Spring Boot (Kotlin)**.

Incluye:
- Interfaz web (Thymeleaf) para gestionar **jugadores**, **equipos** y **ligas**.
- API REST consumible desde Android para consultar/gestionar jugadores y otras relaciones.
- Persistencia con **MySQL** mediante **Spring Data JPA / Hibernate**.
- Importación de datos de ejemplo desde ficheros CSV.

## Requisitos
- **Java JDK 21**
- **Maven**
- **MySQL Server** (local o remoto)

Dependencias principales (ver `pom.xml`):
- `spring-boot-starter-webmvc`
- `spring-boot-starter-thymeleaf`
- `spring-boot-starter-data-jpa`
- `mysql-connector-j`

## Configuración
La configuración está en `src/main/resources/application.properties`:

- `spring.datasource.url=jdbc:mysql://localhost:3306/jugadores?...`
- `spring.datasource.username=equipo_user`
- `spring.datasource.password=equipo123`
- `spring.jpa.hibernate.ddl-auto=update`

Notas:
- Con `ddl-auto=update`, Hibernate **crea/actualiza automáticamente** tablas según las entidades JPA.
- En entornos reales se recomienda gestionar migraciones con herramientas tipo Flyway/Liquibase.

## Base de datos
Nombre de la BD (según `spring.datasource.url`): `jugadores`

### Tablas (entidades) y uso
A continuación se listan las tablas principales que genera/usa el backend (según las clases `@Entity`):

#### 1) `ligas`
Entidad: `Liga`
- **PK**: `id_liga` (AUTO)
- **Campos**:
  - `nombre_liga` (String)
  - `pais_liga` (String)
  - `foto` (String)
- **Uso**: Catálogo de ligas. Relación 1:N con `equipos`.

#### 2) `equipos`
Entidad: `Equipo`
- **PK**: `id_equipo` (AUTO)
- **Campos**:
  - `nombre_equipo` (String)
  - `pais_equipo` (String)
  - `foto` (String)
  - `id_liga` (FK -> `ligas.id_liga`)
- **Uso**: Equipos asociados a una liga.

#### 3) `jugadores`
Entidad: `Jugador`
- **PK**: `id_jugador` (AUTO)
- **Campos**:
  - `nombre_jugador` (String)
  - `id_equipo` (FK -> `equipos.id_equipo`)
  - `posicion` (String)
  - `edad` (int)
  - `dorsal` (int)
  - `foto` (String)
- **Uso**: Jugadores y sus datos.

#### 4) `jugadores_por_equipo`
Entidad: `JugadorPorEquipo`
- **PK**: `id_jugador_equipo` (AUTO)
- **Campos**:
  - `id_jugador` (FK -> `jugadores.id_jugador`)
  - `id_equipo` (FK -> `equipos.id_equipo`)
  - `dorsal` (int)
- **Uso**: Tabla de relación para gestionar la pertenencia jugador-equipo.

#### 5) `equipos_por_liga`
Entidad: `EquipoPorLiga`
- **PK**: `id_equipo_liga` (AUTO)
- **Campos**:
  - `id_equipo` (FK -> `equipos.id_equipo`)
  - `id_liga` (FK -> `ligas.id_liga`)
  - `temporada` (String)
  - `fecha_incorporacion` (Long)
  - `activo` (boolean)
- **Uso**: Relación equipo-liga por temporada.

#### 6) `formacion_legendaria`
Entidad: `Formacion`
- **PK**: `id_formacion` (AUTO)
- **Campos**:
  - `esquema` (String) ejemplo: `4-3-3`
  - `fecha_creacion` (Long)
- **Uso**: Guarda la última formación (equipo legendario) creada.

#### 7) `jugadores_en_posicion_legendaria`
Entidad: `JugadorEnPosicion`
- **PK**: `id_jugador_posicion` (AUTO)
- **Campos**:
  - `id_jugador` (FK -> `jugadores.id_jugador`)
  - `id_formacion` (FK -> `formacion_legendaria.id_formacion`)
  - `posicion` (String)
  - `posicion_x` (float)
  - `posicion_y` (float)
- **Uso**: Jugadores colocados en una formación con coordenadas (x,y).

### Cómo replicar la BD en otro servidor
Opción A (recomendado): replicar estructura y datos con `mysqldump`.

1) Exportar en el servidor origen:
```bash
mysqldump -u equipo_user -p jugadores > jugadores.sql
```

2) Copiar `jugadores.sql` al servidor destino.

3) Crear la BD e importar:
```bash
mysql -u equipo_user -p -e "CREATE DATABASE jugadores CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
mysql -u equipo_user -p jugadores < jugadores.sql
```

4) Ajustar `spring.datasource.url`, `username` y `password` en `application.properties` para apuntar al nuevo servidor.

Opción B: dejar que JPA genere tablas automáticamente.
- Crear manualmente una BD vacía `jugadores`.
- Arrancar el backend con `spring.jpa.hibernate.ddl-auto=update`.
- Importar datos con los endpoints de importación (ver sección "Opciones del programa").

## Instrucciones para ejecutar la aplicación
Desde la carpeta `backend`:

### 1) Arranque en modo desarrollo
```bash
mvn spring-boot:run
```

### 2) Compilar y ejecutar JAR
```bash
mvn clean package
java -jar target/jugadoresMySQL-0.0.1-SNAPSHOT.jar
```

Si tu MySQL no está en `localhost:3306` o cambian credenciales, modifica `src/main/resources/application.properties`.

## Opciones del programa y ejemplos de uso
El backend expone:

### A) Interfaz web (Thymeleaf)
- **Home**: `GET /`
- **Jugadores**:
  - `GET /jugadores`
  - `GET /jugadores/{id}`
  - `GET /jugadores/nuevo`
  - `GET /jugadores/editar/{id}`
  - `POST /jugadores/guardar`
  - `GET /jugadores/borrar/{id}`
  - Importación (CSV fijo):
    - `GET /jugadores/importar`
    - `POST /jugadores/importar`
- **Equipos** (similar): `GET /equipos`, `POST /equipos/guardar`, etc.
- **Ligas** (similar): `GET /ligas`, `POST /ligas/guardar`, etc.
- **Inicialización de datos**:
  - `GET /init-data`
  - `POST /init-data/importar` (importa datos si las tablas están vacías y crea relaciones)

Importación desde CSV fijo:
- Los servicios leen desde `backend/data/*.csv` (por ejemplo `data/jugadores_ejemplo.csv`).

### B) API REST
#### Jugadores (`/api/jugadores`)
- `GET /api/jugadores` (listar)
- `GET /api/jugadores/{id}` (detalle)
- `POST /api/jugadores` (crear)
- `PUT /api/jugadores/{id}` (actualizar)
- `DELETE /api/jugadores/{id}` (borrar)
- `GET /api/jugadores/equipo/{equipo}` (filtrar por equipo)
- `GET /api/jugadores/posicion/{posicion}` (filtrar por posición)
- `GET /api/jugadores/estadisticas` (estadísticas)
- `POST /api/jugadores/update-photos` (asigna foto por defecto)
- `POST /api/jugadores/importar` (subida de CSV como `multipart/form-data`)
  - Parámetro: `file`

Ejemplo de importación por API (Windows PowerShell):
```powershell
Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/jugadores/importar" -Form @{ file = Get-Item "C:\ruta\jugadores.csv" }
```

#### Equipo legendario (`/api/equipo-legendario`)
- `GET /api/equipo-legendario` (última formación)
- `POST /api/equipo-legendario` (guardar formación con jugadores y posiciones)
- `DELETE /api/equipo-legendario` (borrar última formación)
- `GET /api/equipo-legendario/exists` (comprobar si existe una formación)

#### Relaciones (`/api/relaciones`)
- `POST /api/relaciones/equipos-liga?idEquipo=...&idLiga=...&temporada=2024-2025`
- `POST /api/relaciones/jugadores-equipo?idJugador=...&idEquipo=...&dorsal=...`
- `GET /api/relaciones/equipos-por-liga/{idLiga}`
- `GET /api/relaciones/jugadores-por-equipo/{idEquipo}`

## Notas importantes
- **Credenciales**: `application.properties` contiene usuario/contraseña de MySQL. En producción no se recomienda commitearlas (usar variables de entorno/secret manager).
- **CORS**: la API de jugadores permite `@CrossOrigin(origins = ["*"])` para facilitar consumo desde Android. En producción conviene restringir orígenes.
- **Datos de ejemplo**:
  - Los CSV de ejemplo están en `backend/data/`.
  - Al arrancar, `JugadoresMySqlApplication` intenta importar jugadores si la tabla está vacía.
