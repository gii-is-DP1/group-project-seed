package es.us.dp1.lx_xy_26_27.your_game_name.auth.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
	
	 private String message;

	  public MessageResponse(String message) {
	    this.message = message;
	  }

}
