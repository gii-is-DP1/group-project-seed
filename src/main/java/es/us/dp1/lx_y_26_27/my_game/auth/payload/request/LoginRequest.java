package es.us.dp1.lx_y_26_27.my_game.auth.payload.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	
	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
