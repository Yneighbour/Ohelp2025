## Copilot instructions (Ohelp2025)

### Big picture
- Monorepo: Spring Boot backend in `o-b/`, Vue 3 + Vite (JS) frontend in `o-f/`.
- Backend context-path is `/api` (see `o-b/src/main/resources/application.properties`).
- Frontend dev uses Vite proxy `/api -> http://localhost:8080` (see `o-f/vite.config.js`); API baseURL defaults to `/api` (see `o-f/src/api/http.js`).
- Generated output: `o-b/target/`, `o-f/dist/` (do not edit).

### Local workflows (Windows-friendly)
- Backend: `cd o-b` then `./mvnw.cmd -U -DskipTests spring-boot:run` (Java 17, Spring Boot parent `3.5.9`).
- Frontend: `cd o-f` then `npm install` + `npm run dev` (Vite default `http://localhost:5173/`).

### API envelope (critical)
- Backend controllers return a JSON envelope: `{ code, message, data? }` (Map-based).
- Frontend unwraps it in `o-f/src/api/http.js` and treats `code !== 200 && code !== 201` as an error.
- Example controller style: `o-b/src/main/java/com/soft/ob/user/controller/UserController.java`.

### Auth & “token” reality
- Login is custom (no Spring Security): `POST /auth/login` issues UUID token (`o-b/src/main/java/com/soft/ob/auth/service/AuthService.java`).
- Demo-mode behavior: `/auth/login` failure may still return HTTP 200 + `code=200` + `data=null` (see `o-b/src/main/java/com/soft/ob/auth/controller/AuthController.java`).
- Frontend stores `localStorage['token']` and sends it as `Authorization` header (see `o-f/src/api/http.js`).
- Token validation exists: `GET /auth/validate/{token}`; frontend does a soft-check in `o-f/src/services/session.js`.

### Backend conventions (`o-b/`)
- Package layout: `com.soft.ob.<module>.(controller|service|mapper|entity)` (see `o-b/README.md`).
- Data access is primarily MyBatis *annotation* mappers (`@Select/@Insert/...`) under `*.mapper` (e.g. `o-b/src/main/java/com/soft/ob/auth/mapper/AuthMapper.java`).
- DB: default is MySQL `ohelp`; schema/seed scripts are `o-b/database_schema.sql` and `o-b/database_init_data.sql`.

### Frontend conventions (`o-f/`)
- Entrypoints: `o-f/index.html`, `o-f/src/main.js`, `o-f/src/App.vue`, `o-f/src/router/index.js`.
- Always use the shared Axios client: `o-f/src/api/http.js` (keeps headers + envelope handling consistent).
- Add new backend integrations as `o-f/src/api/<domain>.js` (see `o-f/src/api/auth.js`, `o-f/src/api/users.js`).

### Where to look first
- Backend API docs: `o-b/COMPLETE_API_DOCUMENTATION.md`, `o-b/INTERFACE_SUMMARY.md`.
- Backend config/entry: `o-b/src/main/resources/application.properties`, `o-b/src/main/java/com/soft/ob/OBApplication.java`.
- Frontend entry/routing: `o-f/src/main.js`, `o-f/src/router/index.js`.
