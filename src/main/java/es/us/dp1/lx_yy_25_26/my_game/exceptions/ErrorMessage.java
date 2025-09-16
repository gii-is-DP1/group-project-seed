package es.us.dp1.lx_yy_25_26.my_game.exceptions;

import java.util.Date;

import lombok.Getter;

@Getter
public class ErrorMessage {
	
	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;
	
	public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
	    this.statusCode = statusCode;
	    this.timestamp = timestamp;
	    this.message = message;
	    this.description = description;
	  }

}
