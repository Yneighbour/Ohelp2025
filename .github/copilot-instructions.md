# Copilot instructions (Ohelp2025)

## Big picture
- Monorepo with **Spring Boot backend** in `o-b/` and **Vue 3 + Vite frontend** in `o-f/`.
- Backend serves APIs under `server.servlet.context-path=/api` (see `o-b/src/main/resources/application.properties`). Frontend calls the backend directly via Axios baseURL in `o-f/src/shared/http.js`.
- `11111111111111111/` is a **static UI demo/reference** (no backend integration).
- Generated/build output folders: `o-b/target/` and `o-f/dist/` (avoid editing manually).

## How to run (local)
- Backend (Java 17, Maven wrapper available):
  - `cd o-b` then `mvn -U -DskipTests spring-boot:run`
  - Default: `http://localhost:8080/api`
- Frontend (Node 16+):
  - `cd o-f` then `npm install` and `npm run dev` (Vite default: `http://localhost:5173/`)

## API contract (critical)
- Frontend expects backend responses shaped like: `{ code: number, message: string, data?: any }`.
  - Enforced in `o-f/src/shared/http.js` (treats `code !== 200 && code !== 201` as error).
  - When changing backend endpoints, keep this envelope consistent across controllers.
- Auth token handling:
  - Frontend stores token in `localStorage['token']` and sends it as `Authorization` header (see `o-f/src/shared/http.js`).
  - Route guard only checks “logged in” (token present) and uses `localStorage['role']` only for login redirect (see `o-f/src/router/index.js` + `o-f/src/router/roleMap.js`).

## Backend conventions (`o-b/`)
- Package layout is module-based under `com.soft.ob.<module>` with `controller/ service/ mapper/ entity` (see `o-b/README.md`).
- Controllers are simple `@RestController` + `@RequestMapping` and often build `Map<String,Object>` responses (example: `o-b/src/main/java/com/soft/ob/user/controller/UserController.java`).
- Data access is primarily **MyBatis annotation mappers** (example: `o-b/src/main/java/com/soft/ob/user/mapper/UserMapper.java`).
- Database scripts live in `o-b/database_schema.sql` and `o-b/database_init_data.sql`; default DB points at MySQL `ohelp` (see `application.properties`).

## Frontend conventions (`o-f/`)
- Feature modules live in `o-f/src/modules/<module>/` and map 1:1 to backend controllers.
  - Each module keeps its own API file like `o-f/src/modules/auth/auth.api.js` and types like `*.types.js`.
- Use the shared HTTP client only: `import http from '../../shared/http.js'`.
- Backend base URL is currently **hardcoded** in `o-f/src/shared/http.js` (no Vite proxy configured in `o-f/vite.config.js`; `.env` variables mentioned in docs are not wired into code yet).
- Prefer keeping cross-module reuse in `o-f/src/shared/` and UI reuse in `o-f/src/components/`.

## Where to look first
- API docs: `o-b/COMPLETE_API_DOCUMENTATION.md` and `o-b/INTERFACE_SUMMARY.md`
- Frontend routing/entry: `o-f/src/main.js`, `o-f/src/router/index.js`
- Backend entry/config: `o-b/src/main/java/com/soft/ob/OBApplication.java`, `o-b/src/main/resources/application.properties`
