package es.us.dp1.lx_xy_26_27.your_game_name.auth.payload.request;

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
