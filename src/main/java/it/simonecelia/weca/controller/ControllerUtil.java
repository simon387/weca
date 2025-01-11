package it.simonecelia.weca.controller;

import jakarta.servlet.http.HttpServletRequest;


public class ControllerUtil {

	protected String getIp ( HttpServletRequest httpServletRequest ) {
		var clientIp = httpServletRequest.getHeader ( "X-Forwarded-For" );
		if ( clientIp == null || clientIp.isEmpty () ) {
			clientIp = httpServletRequest.getRemoteAddr ();
		}
		return clientIp;
	}
}
