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

- JDBC URL: `jdbc:h2:mem:testdb`
- User name: `sa`
- Password: leave empty

## Demo users

The database is populated at application startup using
`src/main/resources/data.sql`.

| Role | User name | Password |
| --- | --- | --- |
| Administrator | `admin1` | `4dm1n` |
| Player | `player1` through `player10` | `0wn3r` |

The default configuration uses an in-memory H2 database and
`spring.jpa.hibernate.ddl-auto=create-drop`. As a result, the data is recreated
whenever the backend restarts and is not persistent.

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
`application-mysql.properties` file provides an example configuration for a
local MySQL database named `petclinic`.

Once MySQL is configured, activate that profile with:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=mysql"
```

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
