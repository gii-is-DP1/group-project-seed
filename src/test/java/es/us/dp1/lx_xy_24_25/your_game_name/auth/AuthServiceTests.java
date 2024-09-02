package es.us.dp1.lx_xy_24_25.your_game_name.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_24_25.your_game_name.auth.AuthService;
import es.us.dp1.lx_xy_24_25.your_game_name.auth.payload.request.SignupRequest;
import es.us.dp1.lx_xy_24_25.your_game_name.user.AuthoritiesService;
import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import es.us.dp1.lx_xy_24_25.your_game_name.user.UserService;

@SpringBootTest
public class AuthServiceTests {

	@Autowired
	protected AuthService authService;
	@Autowired
	protected UserService userService;	
	@Autowired
	protected AuthoritiesService authoritiesService;

	@Test
	@Transactional
	public void shouldCreateAdminUser() {
		SignupRequest request = createRequest("ADMIN", "admin2");
		int userFirstCount = ((Collection<User>) this.userService.findAll()).size();
		this.authService.createUser(request);
		int userLastCount = ((Collection<User>) this.userService.findAll()).size();
		assertEquals(userFirstCount + 1, userLastCount);
	}
	
	
	
	@Test
	@Transactional
	public void shouldCreatePlayerUser() {
		SignupRequest request = createRequest("PLAYER", "playertest");
		int userFirstCount = ((Collection<User>) this.userService.findAll()).size();
		//int playerFirstCount = ((Collection<Player>) this.playerService.findAll()).size();
		this.authService.createUser(request);
		int userLastCount = ((Collection<User>) this.userService.findAll()).size();
		//int playerLastCount = ((Collection<Player>) this.playerService.findAll()).size();
		assertEquals(userFirstCount + 1, userLastCount);
		//assertEquals(playFirstCount + 1, playerLastCount);
	}

	private SignupRequest createRequest(String auth, String username) {
		SignupRequest request = new SignupRequest();
		request.setAddress("prueba");
		request.setAuthority(auth);
		request.setCity("prueba");
		request.setFirstName("prueba");
		request.setLastName("prueba");
		request.setPassword("prueba");
		request.setTelephone("123123123");
		request.setUsername(username);

		if(auth == "PLAYER") {
			User playerUser = new User();
			playerUser.setUsername("clinicOwnerTest");
			playerUser.setPassword("clinicOwnerTest");
			playerUser.setAuthority(authoritiesService.findByAuthority("PLAYER"));
			userService.saveUser(playerUser);			
		}

		return request;
	}

}
