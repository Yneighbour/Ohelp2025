## Copilot Instructions (Ohelp2025)

### 🏗️ Architecture Overview
**Monorepo:** Spring Boot backend (`o-b/`) + Vue 3 frontend (`o-f/`)  
**Domain:** Smart elderly care service platform (智慧养老服务平台)  
**Tech Stack:**
- Backend: Spring Boot 3.5.9 (Java 17), MyBatis (annotation-based), MySQL 8
- Frontend: Vue 3 (composition API + JS), Vite, Vue Router 4, Axios
- Design: Purple theme (#7C3AED), CSS variables, responsive mobile-first

### 🚀 Critical Workflows

#### Backend (`o-b/`)
```powershell
cd o-b
./mvnw.cmd -U -DskipTests spring-boot:run  # Windows, Java 17 required
```
- Runs on `http://localhost:8080` with context-path `/api`
- DB init (first-time): `mysql -u root -p ohelp < database_schema.sql` then `database_init_data.sql`
- Config: `src/main/resources/application.properties` (MySQL connection, port, MyBatis settings)

#### Frontend (`o-f/`)
```powershell
cd o-f
npm install
npm run dev  # http://localhost:5173
```
- Vite proxy: `/api` → `http://localhost:8080` (see `vite.config.js`)
- Legacy static files (`index.html`, `scripts/`) are **reference only** — Vue 3 app is the runtime

### ⚠️ API Contract (Non-negotiable)
**Backend envelope format:**
```java
Map<String, Object> response = new HashMap<>();
response.put("code", 200);  // 200/201 = success, others = failure
response.put("message", "...");
response.put("data", ...);  // Optional payload
return ResponseEntity.ok(response);
```

**Frontend unwrapping** (`o-f/src/api/http.js`):
- `requestData()` throws Error if `code !== 200 && code !== 201`
- `requestRaw()` returns full envelope (use for login due to demo-mode quirks)

**Demo-mode quirk:** Login failures return HTTP 200 + `code=200` + `data=null` (not HTTP 401) — see `AuthController.java`

### 🔐 Auth Mechanism
- **No Spring Security** — custom UUID token stored in `auth` table
- Login: `POST /auth/login` → `{ data: { token, username, userId, role } }`
- Frontend: token saved to `localStorage['token']`, sent as `Authorization` header
- Token validation: `GET /auth/validate/{token}` → `{ data: { valid: true/false } }`
- Route guard: `o-f/src/router/index.js` checks `meta.requiresAuth`

### 📂 Backend Package Structure
```
o-b/src/main/java/com/soft/ob/
├── <module>/         # e.g., auth, user, elder, activity, health, emergency
│   ├── controller/   # @RestController + @RequestMapping
│   ├── service/      # Business logic + @Service
│   ├── mapper/       # MyBatis @Mapper (annotation-based: @Select, @Insert, @Update, @Delete)
│   └── entity/       # POJOs with Lombok (@Data, @NoArgsConstructor, @AllArgsConstructor)
└── OBApplication.java
```

**Mapper pattern** (MyBatis annotation style, not XML):
```java
@Mapper
public interface AuthMapper {
    @Select("SELECT * FROM auth WHERE username = #{username}")
    Auth selectByUsername(String username);
    
    @Insert("INSERT INTO auth (...) VALUES (...)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Auth auth);
}
```

### 🎨 Frontend File Organization
```
o-f/src/
├── api/              # Domain-specific API clients (auth.js, users.js, health.js, etc.)
│   └── http.js       # Shared Axios instance + envelope unwrapping
├── router/           # Vue Router config (index.js defines all routes)
├── views/            # Page components (*View.vue)
├── components/       # Reusable UI (BottomNav.vue, admin/*.vue)
├── styles/           # Global CSS (variables.css defines --primary-color, etc.)
└── services/         # Helper modules (session.js for token validation)
```

**Adding new API:**
1. Create `o-f/src/api/<domain>.js` — import `requestData` from `./http`
2. Match backend controller's URL pattern (omit `/api` prefix, proxy handles it)
3. Use `requestRaw()` for login-like endpoints with demo-mode envelope quirks

### 🎯 Design System Conventions
- **Purple theme**: `--primary-color: #7C3AED`, gradients `135deg #7C3AED → #A78BFA`
- **CSS variables**: All colors/spacing in `o-f/styles/variables.css` — never hardcode values
- **Touch targets**: `--min-touch-target: 44px` (accessibility)
- **Responsive**: Mobile-first (breakpoints in `common.css` at 768px, 1024px)
- **UI patterns**: Check `o-f/styles/common.css` and `variables.css` for reusable classes

### 🛠️ Development Patterns
**Backend Controller Template:**
```java
@RestController
@RequestMapping("/module")
@CrossOrigin(origins = "*")
public class ModuleController {
    @Autowired
    private ModuleService service;
    
    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Module m) {
        Module created = service.create(m);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 201);
        res.put("message", "Success");
        res.put("data", created);
        return ResponseEntity.status(201).body(res);
    }
}
```

**Frontend API Client Template:**
```javascript
import { requestData } from './http';

export async function getItems() {
  return await requestData({
    method: 'GET',
    url: '/module/',
  });
}
```

### 📋 Key Files Reference
- Backend API docs: `o-b/COMPLETE_API_DOCUMENTATION.md`, `o-b/INTERFACE_SUMMARY.md`
- Database schema: `o-b/database_schema.sql` (DDL), `o-b/database_init_data.sql` (sample data)
- Frontend routing: `o-f/src/router/index.js` (all routes with `meta.requiresAuth` guards)
- Axios config: `o-f/src/api/http.js` (request/response interceptors)
- Auth service: `o-b/src/main/java/com/soft/ob/auth/` (custom token management)
  - Top bar: gradient white bg, purple accents, sticky position
  - Bottom nav: 4-item navigation, purple indicator on active
  - Cards: `border-radius: 16px`, shadow with purple tint `rgba(124, 58, 237, 0.08)`

### 📚 Documentation & References
- **API docs:** `o-b/COMPLETE_API_DOCUMENTATION.md`, `o-b/INTERFACE_SUMMARY.md`
- **Architecture:** `o-b/PROJECT_STRUCTURE.md`, `o-b/PHASE2_COMPLETION_REPORT.md`
- **Features:** `o-f/功能说明.md` (UI enhancements, page-by-page breakdowns)
- **DB schema:** `o-b/database_schema.sql` (13 tables: user, auth, elderly, health, activity, etc.)

### 🔍 When Debugging
- Backend errors: check terminal for SQL logs (`spring.jpa.show-sql=true`)
- CORS issues: `@CrossOrigin(origins = "*")` already on all controllers
- API 404s: verify `server.servlet.context-path=/api` in `application.properties`
- Frontend API failures: inspect browser Network tab for envelope `{ code, message }`
- Token issues: check `localStorage['token']` in DevTools → Application tab
