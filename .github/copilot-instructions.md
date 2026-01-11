# Copilot instructions (Ohelp2025)

## Big picture
- Monorepo: Spring Boot backend in `o-b/`, Vue 3 + Vite frontend in `o-f/`.
- Backend base path is `/api` via `server.servlet.context-path=/api` in `o-b/src/main/resources/application.properties`.
- Frontend calls backend directly (no Vite proxy): `baseURL: 'http://localhost:8080/api'` in `o-f/src/shared/http.js`.
- Generated output: `o-b/target/`, `o-f/dist/` (do not edit).

## Local workflows (Windows-friendly)
- Backend: `cd o-b` then `./mvnw.cmd -U -DskipTests spring-boot:run` (Java 17, Spring Boot parent `3.5.9`).
- Frontend: `cd o-f` then `npm install` + `npm run dev` (Vite default `http://localhost:5173/`).

## API envelope (critical)
- All backend controllers should return a JSON envelope: `{ code, message, data? }`.
- Frontend enforces it in `o-f/src/shared/http.js` and treats `code !== 200 && code !== 201` as an error.
- Example controller style (Map-based): `o-b/src/main/java/com/soft/ob/user/controller/UserController.java`.

## Auth & “token” reality
- Login is custom (no Spring Security in code): `POST /auth/login` returns an `Auth` record with a UUID token (`o-b/src/main/java/com/soft/ob/auth/service/AuthService.java`).
- Frontend stores `localStorage['token']` and sends it as `Authorization` header, but backend does not globally enforce it (no `@RequestHeader("Authorization")` usage found). Token validation exists as `GET /auth/validate/{token}`.
- Router guard: checks token for logged-in, and uses `localStorage['role']` only for redirect/soft navigation limits (`o-f/src/router/index.js`).

## Backend conventions (`o-b/`)
- Package layout: `com.soft.ob.<module>.(controller|service|mapper|entity)` (see `o-b/README.md`).
- Data access is primarily MyBatis *annotation* mappers (`@Select/@Insert/...`) under `*.mapper` (e.g. `o-b/src/main/java/com/soft/ob/auth/mapper/AuthMapper.java`).
- DB: default is MySQL `ohelp`; schema/seed scripts are `o-b/database_schema.sql` and `o-b/database_init_data.sql`.

## Frontend conventions (`o-f/`)
- Modules are 1:1 with backend domains under `o-f/src/modules/<module>/`; each module owns `<module>.api.js` and optional `<module>.types.js`.
- Always use the shared Axios client: `o-f/src/shared/http.js` (keeps headers + envelope handling consistent).

## Where to look first
- Backend API docs: `o-b/COMPLETE_API_DOCUMENTATION.md`, `o-b/INTERFACE_SUMMARY.md`.
- Backend config/entry: `o-b/src/main/resources/application.properties`, `o-b/src/main/java/com/soft/ob/OBApplication.java`.
- Frontend entry/routing: `o-f/src/main.js`, `o-f/src/router/index.js`.
