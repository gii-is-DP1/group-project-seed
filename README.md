# DP1 group project seed

Starter project for the Design and Testing 1 course in the Software Engineering
degree at the University of Seville. It includes a Spring Boot REST API, JWT
authentication, and a React web application.

This project was originally based on
[Spring Petclinic](https://github.com/spring-projects/spring-petclinic), but its
structure and functionality have been simplified and adapted for a SPA
architecture.

## Technologies and versions

| Component | Version | Notes |
| --- | --- | --- |
| Java | 21 | Target language and bytecode version. |
| JDK | 21 or 25 | Both are supported; Maven always compiles with `--release 21`. |
| Maven | 3.9.16 | Provided through Maven Wrapper; no separate Maven installation is required. |
| Spring Boot | 3.5.16 | Backend and REST API. |
| Node.js | 24.x | The Maven `frontend` profile automatically uses Node 24.16.0. |
| npm | 11 or 12 | The Maven `frontend` profile automatically uses npm 12.0.2. |
| React | 18.3.1 | User interface. |
| Vite | 8.2.2 | Frontend development server and production build. |
| Vitest | 5.0.0 | Frontend tests. |

Using JDK 25 does not change the project's target Java version. The
`maven.compiler.release=21` setting prevents the accidental use of classes or
methods introduced after Java 21, so the generated code remains compatible with
team members using JDK 21.

## Requirements

The recommended local development environment includes:

- Git.
- Docker with Docker Compose v2, only when using MySQL; start Docker Desktop
  (or the Docker Engine) before running Compose commands.
- JDK 21 or JDK 25. JDK 21 is recommended as the shared team configuration.
- Node.js 24 with npm 11 or 12 to run the frontend directly.
- An IDE with Maven, Java, and React support, such as IntelliJ IDEA, Eclipse/STS,
  or Visual Studio Code.

The optional Maven `frontend` profile downloads its own Node and npm versions
into `target/`. Therefore, only Git and a compatible JDK are required for normal
backend Maven commands. Your globally installed Node version is used when
working directly inside `frontend/`.

Check the environment before starting:

```console
java --version
node --version
npm --version
```

To check which JDK Maven is actually using:

```bash
./mvnw --version
```

On Windows PowerShell:

```powershell
.\mvnw.cmd --version
```

The output must report Java 21 or Java 25. If it reports another version, review
`JAVA_HOME` and the JDK configured in your IDE.

## Getting and verifying the project

On Linux, macOS, or Git Bash:

```bash
git clone https://github.com/gii-is-DP1/group-project-seed.git
cd group-project-seed
./mvnw clean verify -Pfrontend
```

On Windows PowerShell:

```powershell
git clone https://github.com/gii-is-DP1/group-project-seed.git
Set-Location group-project-seed
.\mvnw.cmd clean verify -Pfrontend
```

`verify -Pfrontend` performs the most complete project check:

1. Validates that Maven and the JDK are compatible.
2. Compiles the backend for Java 21.
3. Downloads the Node and npm versions pinned in `pom.xml`.
4. Installs the exact dependencies from `frontend/package-lock.json` using
   `npm ci`.
5. Runs both the Java and frontend test suites.
6. Generates the React production bundle.
7. Copies the frontend into the Spring Boot JAR.
8. Generates the test coverage and status reports.

The first run can take longer because it downloads Maven, the Java dependencies,
Node, and the npm dependencies.

The frontend integration is deliberately opt-in. Maven commands without
`-Pfrontend` operate only on the backend and never run npm. This keeps everyday
commands such as `spring-boot:run`, `test`, and `install` fast. Use the profile
when the command must also test, build, or package the frontend:

```bash
# Backend only
./mvnw clean install

# Complete backend and frontend build
./mvnw clean install -Pfrontend
```

## Local development

During development, it is more convenient to run the backend and frontend
separately. This allows Vite to update the browser quickly when the React source
code changes.

### 1. Start the backend

From the repository root on Linux, macOS, or Git Bash:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The backend is available at <http://localhost:8080>. This command does not run
`npm ci`; the frontend is managed separately during development.

By default, the backend uses persistent H2. On a fresh checkout the database
starts empty: follow [Demo users](#demo-users) to load the example accounts once.
For MySQL, follow [MySQL with Docker](#mysql-with-docker) instead.

### 2. Start the frontend

Open another terminal and run:

```bash
cd frontend
npm ci
npm run dev
```

The application is available at <http://localhost:5173>. Vite proxies `/api`
and `/v3/api-docs` requests to the backend running on port 8080.

`npm ci` uses the lockfile, removes incomplete installations, and reproduces the
same dependency versions for every team member. Run it after cloning the
repository or whenever `package-lock.json` changes. After that, the frontend can
be started directly with `npm run dev`.

## Useful URLs

| Resource | URL |
| --- | --- |
| Frontend development server | <http://localhost:5173> |
| Packaged application | <http://localhost:8080> |
| Swagger UI | <http://localhost:8080/swagger-ui/index.html> |
| OpenAPI specification | <http://localhost:8080/v3/api-docs> |
| H2 console | <http://localhost:8080/h2-console> |

The default H2 console settings are:

- JDBC URL: `jdbc:h2:file:./data/testdb`
- User name: `sa`
- Password: leave empty

The H2 console is disabled when the `mysql` profile is active.

## Demo users

The optional `init-data` profile loads `src/main/resources/data.sql`. Use it
once on an empty database, then stop the backend and start without this profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=init-data
```

On PowerShell: `.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=init-data"`.
Do not enable it again on an initialized database: the inserts are not idempotent.

| Role | User name | Password |
| --- | --- | --- |
| Administrator | `admin1` | `4dm1n` |
| Player | `player1` through `player10` | `0wn3r` |

The default configuration stores H2 in `data/testdb.mv.db` and uses
`spring.jpa.hibernate.ddl-auto=update`, preserving data across restarts.
Without `init-data`, a new database starts empty. Subsequent starts do not reload
demo users or overwrite changes. Tests use a separate in-memory H2 database.

## Building and running the complete JAR

To generate a single application containing both the backend and frontend:

```bash
./mvnw clean package -Pfrontend
java -jar target/lx-xy-2425-game-name-0.1.0.BUILD-SNAPSHOT.jar
```

On Windows PowerShell:

```powershell
.\mvnw.cmd clean package -Pfrontend
java -jar target/lx-xy-2425-game-name-0.1.0.BUILD-SNAPSHOT.jar
```

Vite first generates `frontend/dist/`. Maven copies its contents into
`target/classes/static/` before creating the JAR. The Vite development server
must not be started in this mode: the complete application is served from
<http://localhost:8080>.

Use `clean verify -Pfrontend` instead of `clean package -Pfrontend` to run every
check and generate all reports before packaging.

## Testing and quality

### Complete project

From the repository root:

```bash
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

This command runs the backend JUnit tests only and does not invoke npm.

The normal test suite uses `src/test/resources/application-default.properties`
to select H2 in memory, recreate its schema, and load demo data. Run tests without
the `mysql` or `init-data` profiles; Docker is not required. The persistence test
uses its own temporary H2 file and checks that inserts, edits, and deletions
survive closing and reopening the application. Neither test setup uses `data/`.

To run the backend and frontend tests together:

```bash
./mvnw test -Pfrontend
```

On Windows:

```powershell
.\mvnw.cmd test -Pfrontend
```

To run the complete lifecycle, create the JAR, and generate the reports:

```bash
./mvnw clean verify -Pfrontend
```

The main results are written to:

- `target/surefire-reports/`: Java test results.
- `target/site/jacoco/index.html`: backend coverage report.
- `target/site/test-status/index.html`: Allure report.
- `docs/deliverables/D3/coverage/`: copy of the coverage report used in the
  project deliverables.

### Frontend only

Run the following commands from `frontend/`:

| Command | Purpose |
| --- | --- |
| `npm test` | Runs the complete test suite once. |
| `npm run test:watch` | Keeps Vitest open and reruns affected tests. |
| `npm run coverage` | Runs the tests and writes coverage to `frontend/coverage/`. |
| `npm run build` | Creates the production bundle in `frontend/dist/`. |
| `npm run preview` | Serves the latest production bundle locally. |
| `npm run dev` | Starts Vite with automatic reloading. |

`npm start` is an alias for `npm run dev`, but the latter is recommended because
it makes clear that the development server is being started.

## Database and configuration

Shared configuration is stored in
`src/main/resources/application.properties`. The default profile uses H2. The
`application-mysql.properties` file configures MySQL on `localhost:3306`, with
database, user, and password `petclinic`.

| Configuration | Database | Schema and initial data |
| --- | --- | --- |
| No explicit profile | H2 file at `data/testdb.mv.db` | Updates the schema; preserves existing data. |
| `mysql` | MySQL database `petclinic` | Updates the schema; preserves existing data. |
| `init-data` | H2, or MySQL when combined with `mysql` | Executes `data.sql`; use once on an empty database. |

Both database configurations use `spring.jpa.hibernate.ddl-auto=update` instead
of `create-drop`, so closing the backend does not delete the schema. By default,
`spring.sql.init.mode=never` prevents demo inserts from running on every start.
The `init-data` profile enables them after Hibernate has prepared the schema.

### Persistent H2

Always start the backend from the repository root: `./data/testdb` is relative
to the working directory. The `data/` directory is ignored by Git and survives
`mvnw clean`. Close the backend before copying the files for a backup. To reset
H2 intentionally, stop the backend and delete `data/testdb.*`, then optionally
start once with `init-data`. Only one backend process should open this file.

For normal starts after initialization:

```bash
./mvnw spring-boot:run
```

On PowerShell: `.\mvnw.cmd spring-boot:run`.

### MySQL with Docker

With Docker running, build `docker/mysql/Dockerfile` (based on the official
[MySQL 8.4 image](https://hub.docker.com/_/mysql)) and start the database:

```bash
docker compose up -d --build --wait
```

Run all Compose commands from the repository root. Compose starts only MySQL;
the backend runs separately on the host. The first start downloads the image and
creates the `petclinic` database and application user. The MySQL JDBC driver is
already included in `pom.xml`. Wait for the health check to pass before starting
the backend. Inspect the service with:

```bash
docker compose ps
docker compose logs --tail=100 mysql
```

Compose binds port 3306 to localhost and stores data in the `mysql-data` named
volume. `docker compose down` stops/removes the container but retains the data;
`docker compose down -v` deliberately deletes the database volume. Environment
variables for database initialization only take effect when the volume is empty.

Activate the MySQL profile with:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=mysql"
```

To populate an empty MySQL database with demo users, use this command for the
first backend start instead:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql,init-data
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=mysql,init-data"
```

After startup completes, stop the backend with Ctrl+C and restart with only
`mysql`. If the demo users have already been loaded, skip this initialization
step. The script creates one administrator and ten players, listed under
[Demo users](#demo-users). Repeating initialization can cause duplicate-key
errors; it is not a reset command.

For subsequent development sessions, start Docker, run
`docker compose up -d --wait`, and start the backend with the `mysql` profile.
Stop the backend before stopping MySQL with `docker compose down`. The next
start reuses the volume, including changes made through the application.

To deliberately start over, stop the backend, run `docker compose down -v`,
and repeat the first-start instructions. This deletes all data in the Compose
database volume, not just the example accounts.

H2 and MySQL are independent databases; switching profiles does not migrate
existing data. The H2 file and MySQL volume also survive Maven `clean`.

The packaged JAR accepts the same Spring profiles:

```bash
java -jar target/lx-xy-2425-game-name-0.1.0.BUILD-SNAPSHOT.jar --spring.profiles.active=mysql
```

`MYSQL_USER` and `MYSQL_PASSWORD` override credentials in both Compose and the
backend when exported in the shell. `MYSQL_URL` overrides the backend JDBC URL.
The defaults are for local development. Hibernate `update` keeps the development
schema between starts; controlled production schema changes require migrations.
SQL initialization follows [Spring Boot's initialization settings](https://docs.spring.io/spring-boot/how-to/data-initialization.html).

The Docker setup has been checked by starting the backend with `mysql,init-data`,
restarting with `mysql`, and recreating the MySQL container while retaining its
volume. All 11 demo users and the table checksums were preserved. This was a
manual integration check; the regular Maven tests do not start MySQL.

The JWT key included in `application.properties` is intended exclusively for
development. A real deployment must replace it with a secure Base64-encoded
secret, for example through the `DP1_GAME_APP_JWTSECRET` environment variable,
without committing the real value to Git.

## Repository structure

```text
.
├── frontend/                 React application, Vite configuration, and tests
├── src/main/java/            Java backend source code
├── src/main/resources/       Configuration, initial data, and resources
├── src/test/java/            Backend tests
├── docs/deliverables/        Deliverable documentation and reports
├── .mvn/ and mvnw*           Maven Wrapper
├── pom.xml                   Dependencies and build lifecycle
└── README.md                 This guide
```

The backend entry point is
`src/main/java/es/us/dp1/lx_xy_24_25/your_game_name/GameApplication.java`. The
frontend entry point is `frontend/src/index.jsx`.

## IDE configuration

- Import the repository root as a Maven project using `pom.xml`.
- Select JDK 21 or 25 as the Maven and project JDK.
- Keep the source language level set to Java 21.
- Enable annotation processing if requested by the IDE; the project uses Lombok
  and MapStruct.
- Configure `frontend/` as the JavaScript project root and select Node 24.
- Do not mark `target/`, `frontend/node_modules/`, `frontend/dist/`, or
  `frontend/coverage/` as source directories.

## Troubleshooting

### Docker or MySQL is not available

If Compose cannot connect to the Docker daemon, start Docker Desktop or the
Docker Engine and retry. If MySQL does not become healthy, inspect
`docker compose logs --tail=100 mysql`. Port 3306 must be available on the host.
The backend must use the `mysql` profile to connect to this container.

Changing `MYSQL_USER` or `MYSQL_PASSWORD` does not change accounts in an existing
volume. Use credentials matching that database, or update its accounts before
changing the backend configuration.

### Duplicate keys during startup

If the database was already initialized, remove `init-data` from the active
profiles and restart. Keep `mysql` if using MySQL. Normal startup preserves
existing accounts and does not execute `data.sql`.

### H2 data appears to be missing or the database is locked

Check that the backend was started from the repository root and that the JDBC
URL is `jdbc:h2:file:./data/testdb`. Starting from a different directory selects
a different file. If the file is locked, stop the other backend process using it
before restarting.

### Maven is using the wrong JDK

Check `./mvnw --version` or `.\mvnw.cmd --version`, not just `java --version`.
Maven Enforcer rejects versions older than 21 and versions 26 or newer.

In PowerShell, a JDK can be selected temporarily with:

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
.\mvnw.cmd --version
```

Adjust the path to match an existing JDK installation on your computer.

### Node or npm reports the wrong version

Check the versions from `frontend/`:

```console
node --version
npm --version
```

The repository includes `.nvmrc` and `.node-version` with the recommended Node
version. When using a compatible version manager, run `nvm use` from
`frontend/`.

### Frontend dependencies do not match

Do not edit `package-lock.json` manually. Run:

```bash
cd frontend
npm ci
```

When intentionally adding or updating a dependency, use
`npm install <package>` and commit `package.json` and `package-lock.json`
together.

### A port is already in use

The backend requires port 8080 and Vite requires port 5173. Stop the process
using the required port before starting the project. If the backend port is
changed, the proxy in `frontend/vite.config.js` must also be updated.

### The frontend cannot access the API

Check that the backend responds at <http://localhost:8080/v3/api-docs> and that
the frontend was started with Vite on port 5173. The proxy applies only to the
Vite development server; the packaged JAR serves the frontend and API from the
same origin.

## Additional documentation

- Frontend-specific configuration is summarized in
  [`frontend/README.md`](frontend/README.md).
- The complete API can be explored through Swagger UI while the backend is
  running.
- The introductory Spring Petclinic presentation is available on
  [Speaker Deck](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application).
