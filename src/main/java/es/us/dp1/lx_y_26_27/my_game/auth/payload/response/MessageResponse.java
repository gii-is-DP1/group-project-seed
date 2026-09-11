package es.us.dp1.lx_y_26_27.my_game.auth.payload.response;

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
