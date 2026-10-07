package es.us.dp1.lx_xy_26_27.your_game_name.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * When the frontend is packaged inside the JAR (mvnw package -Pfrontend), React Router
 * routes such as /login or /users/5 do not exist as files. This controller forwards
 * them to index.html so the SPA can handle them (for example after reloading the page).
 *
 * Only paths whose last segment has no dot are forwarded, so static files (/assets/*.js,
 * /favicon.ico...) are still served normally. Paths that belong to the backend (API,
 * actuator, Swagger, H2 console, errors) are never forwarded. Routes up to four levels
 * deep are supported (PathPatternParser does not allow "**" in the middle of a pattern).
 */
@Controller
public class SpaForwardingController {

    // First segment: anything except the prefixes used by the backend
    private static final String FIRST =
            "/{first:(?!(?:api|actuator|v3|swagger-ui|swagger-resources|h2-console|error)$)[^.]+}";
    // Last segment: no dot, so it is not a static file
    private static final String LAST = "/{last:[^.]+}";

    @GetMapping({
        FIRST,
        FIRST + LAST,
        FIRST + "/{middle1}" + LAST,
        FIRST + "/{middle1}/{middle2}" + LAST
    })
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}
