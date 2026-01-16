## Copilot Instructions (Ohelp2025)

### Monorepo shape
- Two apps: Spring Boot backend in `o-b/` + Vue 3 (Vite) frontend in `o-f/` for a smart elderly care platform.
- Backend stack: Spring Boot 3.5.9, Java 17, MyBatis (annotation mappers), MySQL 8; context path `/api` on port 8080 per [o-b/src/main/resources/application.properties](o-b/src/main/resources/application.properties#L1-L22).
- Frontend stack: Vue 3 (composition API), Vue Router 4, Axios, Vite dev server on 5173 with proxy `/api -> http://localhost:8080` ([o-f/vite.config.js](o-f/vite.config.js#L1-L12)). Legacy `index.html` + `scripts/` are reference-only.

### Build & run (happy path)
- Backend: from `o-b/`, `./mvnw.cmd -U -DskipTests spring-boot:run` (Java 17). DB: create `ohelp`, import `database_schema.sql` then `database_init_data.sql`.
- Frontend: from `o-f/`, `npm install` then `npm run dev`. Prod build: `npm run build` (expects API at `/api`).
- If API 404s, confirm `/api` context-path and proxy target; envelopes still return HTTP 200 on failures (see below).

### API envelope (must follow)
- Controllers return `{ code, message, data? }` maps; `200/201` mean success, everything else treated as failure.
- Frontend `requestData` in [o-f/src/api/http.js](o-f/src/api/http.js#L1-L24) throws when `code` not `200/201`; use `requestRaw` for login-like quirks.
- Demo quirk: login failures may be HTTP 200 with `code=200` but `data=null` (see AuthController), so frontend checks token existence, not HTTP status.

### Auth flow
- No Spring Security; custom UUID token stored in `auth` table. Login `POST /auth/login` → `data: { token, username, userId, role }`.
- Token sent via `Authorization` header (set in Axios interceptor). Soft-validate via `GET /auth/validate/{token}`; frontend guard in [o-f/src/router/index.js](o-f/src/router/index.js#L1-L106) redirects to `/login` if missing/invalid.
- Admin routes require `localStorage.role === 'admin'`; otherwise redirected to `/health`.

### Backend code pattern
- Package per domain: `controller` → `service` → `mapper` → `entity` under `com.soft.ob.<module>`.
- MyBatis is annotation-first (`@Select/@Insert/@Update/@Delete`); `mybatis.mapper-locations` exists but XML mappers are not used.
- Controllers are `@RestController` with `@RequestMapping` under `/api/<module>`; build responses with `Map<String,Object>` and set `code/message/data` consistently.

### Frontend conventions
- Routes defined in [o-f/src/router/index.js](o-f/src/router/index.js#L1-L106); `meta.requiresAuth` gated by `validateExistingToken` (services/session). `meta.showBottomNav` drives bottom nav rendering.
- API clients live in `o-f/src/api/*.js`; omit `/api` prefix (proxy adds it). Favor `requestData` unless you need the raw envelope.
- Styles centralized in `o-f/styles/variables.css` + `common.css`; keep purple theme `--primary-color: #7C3AED` and shared spacing tokens.

### Data & docs
- Schema and seed: [o-b/database_schema.sql](o-b/database_schema.sql), [o-b/database_init_data.sql](o-b/database_init_data.sql).
- Feature/API references: [o-b/COMPLETE_API_DOCUMENTATION.md](o-b/COMPLETE_API_DOCUMENTATION.md), [o-b/INTERFACE_SUMMARY.md](o-b/INTERFACE_SUMMARY.md), frontend overview in [o-f/README.md](o-f/README.md).

### Debugging tips
- SQL visibility on by default (`spring.jpa.show-sql=true`); adjust DB creds in `application.properties` if startup fails.
- 401s are unlikely; check envelope `code/message` instead. Token problems? clear `localStorage['token']` and re-login.
- If frontend fetches fail in dev, ensure Vite proxy is running and backend context path `/api` matches requests.
