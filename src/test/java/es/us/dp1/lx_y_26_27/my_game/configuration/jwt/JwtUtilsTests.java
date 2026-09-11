package es.us.dp1.lx_y_26_27.my_game.configuration.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import es.us.dp1.lx_y_26_27.my_game.configuration.jwt.JwtUtils;
import es.us.dp1.lx_y_26_27.my_game.user.Authorities;

class JwtUtilsTests {

	private static final String TEST_SECRET =
			"ZHAxLWdhbWUtZGV2ZWxvcG1lbnQta2V5LTIwMjYtMjAyNy1jaGFuZ2UtdGhpcy1pbi1wcm9kdWN0aW9uISEhISEh";

	private JwtUtils jwtUtils;

	@BeforeEach
	void setUp() {
		jwtUtils = new JwtUtils();
		ReflectionTestUtils.setField(jwtUtils, "jwtSecret", TEST_SECRET);
		ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 60_000);
	}

	@Test
	void shouldGenerateAndValidateToken() {
		Authorities authority = new Authorities();
		authority.setAuthority("ADMIN");

		String token = jwtUtils.generateTokenFromUsername("admin", authority);

		assertTrue(jwtUtils.validateJwtToken(token));
		assertEquals("admin", jwtUtils.getUserNameFromJwtToken(token));
	}

	@Test
	void shouldRejectMalformedToken() {
		assertFalse(jwtUtils.validateJwtToken("not-a-jwt"));
	}
}
