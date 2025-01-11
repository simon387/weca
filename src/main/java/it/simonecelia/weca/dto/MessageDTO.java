package it.simonecelia.weca.dto;

import java.io.Serial;
import java.io.Serializable;


public class MessageDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 5862246848455720588L;

	private String message;

	public String getMessage () {
		return message;
	}

	public void setMessage ( String message ) {
		this.message = message;
	}

	@Override public String toString () {
		return "MessageDTO{" +
						"message='" + message + '\'' +
						'}';
	}
}
