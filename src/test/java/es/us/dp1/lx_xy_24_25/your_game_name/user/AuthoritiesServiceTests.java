package es.us.dp1.lx_xy_24_25.your_game_name.user;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.user.AuthoritiesService;

//@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@SpringBootTest
@AutoConfigureTestDatabase
class AuthoritiesServiceTests {

//	@Autowired
//	private UserService userService;

	@Autowired
	private AuthoritiesService authService;

	@Test
	void shouldFindAllAuthorities() {
		List<Authorities> auths = (List<Authorities>) this.authService.findAll();
		assertEquals(2, auths.size());
	}

	@Test
	void shouldFindAuthoritiesByAuthority() {
		Authorities auth = this.authService.findByAuthority("ADMIN");
		assertEquals("ADMIN", auth.getAuthority());
	}

	@Test
	void shouldNotFindAuthoritiesByIncorrectAuthority() {
		assertThrows(ResourceNotFoundException.class, () -> this.authService.findByAuthority("authnotexists"));
	}

	@Test
	@Transactional
	void shouldInsertAuthorities() {
		int count = ((Collection<Authorities>) this.authService.findAll()).size();

		Authorities auth = new Authorities();
		auth.setAuthority("CLIENT");

		this.authService.saveAuthorities(auth);
		assertNotEquals(0, auth.getId().longValue());
		assertNotNull(auth.getId());

		int finalCount = ((Collection<Authorities>) this.authService.findAll()).size();
		assertEquals(count + 1, finalCount);
	}

//	@Test
//	@Transactional
//	void shouldAddAuthoritiesToUser() {
//		User user = userService.findUser("owner1");
//		assertEquals("OWNER" ,user.getAuthority().getAuthority());
//		
//		this.authService.saveAuthorities("owner1", "TEST");
//		assertEquals("TEST" ,user.getAuthority().getAuthority());
//
//	}

}
