# DP1 frontend

React frontend built with Vite and tested with Vitest.

## Requirements

- Node.js 24
- npm 11 or 12

The expected Node version is also recorded in `.nvmrc` and `.node-version`.

Backend-only Maven commands do not run npm. Add `-Pfrontend` to a Maven command
when it must install, test, build, and package this frontend. From the repository
root, for example:

```shell
./mvnw clean verify -Pfrontend
```

On Windows, run `.\mvnw.cmd clean verify -Pfrontend` from the repository root.

## Commands

```shell
npm ci
npm run dev
```

The development server runs at <http://localhost:5173> and proxies API and
OpenAPI requests to the Spring Boot application at <http://localhost:8080>.

Other useful commands:

- `npm test`: run the test suite once.
- `npm run test:watch`: run tests in watch mode.
- `npm run coverage`: generate the test coverage report.
- `npm run build`: create the production bundle in `dist/`.
- `npm run preview`: preview the production bundle locally.
