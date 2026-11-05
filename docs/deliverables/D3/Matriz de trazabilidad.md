# Matriz de trazabilidad

> Generada automáticamente al ejecutar `./mvnw verify` a partir de las anotaciones de los tests
> (`src/test/allure/traceability-plugin.mjs`). No la edites a mano: cambia las anotaciones
> (`@Epic`, `@Feature`, `@Story("HU-xx: Nombre")`, `@Description` y las de capa/tipo del
> paquete `testing`) y vuelve a generar el informe.

**46** pruebas: 46 pasan, 0 fallan.

## 1. Resumen por historia de usuario

| Historia de usuario | Estado | Pruebas | Controlador (aislada) | Controlador (sociable) | Modelo (aislada) | Seguridad (aislada) | Servicio (sociable) |
|---|---|---|---|---|---|---|---|
| HU-01: Iniciar sesión | ✅ Cubierta | 4 | 3 | 1 |  |  |  |
| HU-02: Registrar usuario | ✅ Cubierta | 6 | 2 | 2 |  |  | 2 |
| (sin historia de usuario) | — | 37 | 10 | 7 | 1 | 2 | 17 |

## 2. Detalle de las pruebas

| Épica | Feature | Historia de usuario | Prueba | Descripción | Tipo | Estado |
|---|---|---|---|---|---|---|
| Configuration | Packaged frontend (SPA) | (sin historia de usuario) | [SpaForwardingTests › shouldForwardDeepRoutesToIndex()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/configuration/SpaForwardingTests.java) |  | Controlador (sociable) | ✅ Pasa |
| Configuration | Packaged frontend (SPA) | (sin historia de usuario) | [SpaForwardingTests › shouldForwardNestedRoutesToIndex()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/configuration/SpaForwardingTests.java) |  | Controlador (sociable) | ✅ Pasa |
| Configuration | Packaged frontend (SPA) | (sin historia de usuario) | [SpaForwardingTests › shouldForwardSingleSegmentRoutesToIndex()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/configuration/SpaForwardingTests.java) |  | Controlador (sociable) | ✅ Pasa |
| Configuration | Packaged frontend (SPA) | (sin historia de usuario) | [SpaForwardingTests › shouldNotForwardNorExposeTheApi()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/configuration/SpaForwardingTests.java) |  | Controlador (sociable) | ✅ Pasa |
| Configuration | Packaged frontend (SPA) | (sin historia de usuario) | [SpaForwardingTests › shouldNotForwardStaticFiles()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/configuration/SpaForwardingTests.java) |  | Controlador (sociable) | ✅ Pasa |
| Configuration | Packaged frontend (SPA) | (sin historia de usuario) | [SpaForwardingTests › shouldNotForwardSwagger()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/configuration/SpaForwardingTests.java) |  | Controlador (sociable) | ✅ Pasa |
| Miscelaneous tets | Validation | (sin historia de usuario) | [ValidatorTests › shouldNotValidateWhenFirstNameEmpty()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/model/ValidatorTests.java) |  | Modelo (aislada) | ✅ Pasa |
| Users & Admin Module | Authentication | HU-01: Iniciar sesión | [AuthControllerTests › shouldAuthenticateUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/auth/AuthControllerTests.java) | Un usuario con credenciales válidas obtiene un token JWT. | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Authentication | HU-01: Iniciar sesión | [AuthControllerTests › shouldNotValidateToken()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/auth/AuthControllerTests.java) | El endpoint de validación rechaza un token no válido. | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Authentication | HU-01: Iniciar sesión | [AuthControllerTests › shouldValidateToken()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/auth/AuthControllerTests.java) | El endpoint de validación acepta un token válido. | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Authentication | HU-01: Iniciar sesión<br>HU-02: Registrar usuario | [SignupAndLoginTests › registeredUserCanLogIn()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/auth/SignupAndLoginTests.java) | Un usuario recién registrado puede iniciar sesión con su contraseña. | Controlador (sociable) | ✅ Pasa |
| Users & Admin Module | Authentication | HU-02: Registrar usuario | [AuthControllerTests › shouldNotRegisterUserWithExistingUsername()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/auth/AuthControllerTests.java) | No se puede registrar un usuario con un nombre ya existente. | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Authentication | HU-02: Registrar usuario | [AuthControllerTests › shouldRegisterUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/auth/AuthControllerTests.java) | Un usuario nuevo puede registrarse. | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Authentication | HU-02: Registrar usuario | [AuthServiceTests › shouldCreateAdminUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/auth/AuthServiceTests.java) | El registro crea un usuario con rol ADMIN. | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Authentication | HU-02: Registrar usuario | [AuthServiceTests › shouldCreatePlayerUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/auth/AuthServiceTests.java) | El registro crea un usuario con rol PLAYER. | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Authentication | HU-02: Registrar usuario | [SignupAndLoginTests › passwordIsEncodedOnlyOnceOnSignup()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/auth/SignupAndLoginTests.java) | Al registrarse, la contraseña se guarda codificada una sola vez. | Controlador (sociable) | ✅ Pasa |
| Users & Admin Module | Authentication | (sin historia de usuario) | [SignupAndLoginTests › savingAnExistingUserAgainKeepsItsPassword()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/auth/SignupAndLoginTests.java) |  | Controlador (sociable) | ✅ Pasa |
| Users & Admin Module | Authorization | (sin historia de usuario) | [AuthoritiesServiceTests › shouldFindAllAuthorities()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/AuthoritiesServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Authorization | (sin historia de usuario) | [AuthoritiesServiceTests › shouldFindAuthoritiesByAuthority()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/AuthoritiesServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Authorization | (sin historia de usuario) | [AuthoritiesServiceTests › shouldInsertAuthorities()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/AuthoritiesServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Authorization | (sin historia de usuario) | [AuthoritiesServiceTests › shouldNotFindAuthoritiesByIncorrectAuthority()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/AuthoritiesServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserControllerTests › shouldCreateUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserControllerTests.java) |  | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserControllerTests › shouldDeleteOtherUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserControllerTests.java) |  | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserControllerTests › shouldFindAll()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserControllerTests.java) |  | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserControllerTests › shouldFindAllAuths()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserControllerTests.java) |  | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserControllerTests › shouldFindAllWithAuthority()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserControllerTests.java) |  | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserControllerTests › shouldNotDeleteLoggedUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserControllerTests.java) |  | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserControllerTests › shouldReturnNotFoundUpdateUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserControllerTests.java) |  | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserControllerTests › shouldReturnNotFoundUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserControllerTests.java) |  | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserControllerTests › shouldReturnUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserControllerTests.java) |  | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserControllerTests › shouldUpdateUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserControllerTests.java) |  | Controlador (aislada) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldExistUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldFindAllUsers()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldFindCurrentUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldFindSingleUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldFindUsersByAuthority()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldFindUsersByUsername()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldInsertUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldNotExistUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldNotFindAuthenticated()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldNotFindCorrectCurrentUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldNotFindSingleUserWithBadID()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldNotFindUserByIncorrectUsername()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| Users & Admin Module | Users Management | (sin historia de usuario) | [UserServiceTests › shouldUpdateUser()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/user/UserServiceTests.java) |  | Servicio (sociable) | ✅ Pasa |
| — | — | (sin historia de usuario) | [JwtUtilsTests › shouldGenerateAndValidateToken()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/configuration/jwt/JwtUtilsTests.java) |  | Seguridad (aislada) | ✅ Pasa |
| — | — | (sin historia de usuario) | [JwtUtilsTests › shouldRejectMalformedToken()](../../../src/test/java/es/us/dp1/lx_xy_24_25/your_game_name/configuration/jwt/JwtUtilsTests.java) |  | Seguridad (aislada) | ✅ Pasa |
