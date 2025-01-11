package it.simonecelia.weca.controller;

import io.quarkus.logging.Log;
import io.vertx.core.http.HttpServerRequest;
import it.simonecelia.weca.dto.MessageDTO;
import it.simonecelia.weca.service.MessageService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path ( "/message" )
public class MessageController {

	@Inject
	MessageService messageService;

	@Context
	HttpServerRequest request;

	@POST
	@Consumes ( MediaType.APPLICATION_JSON )
	@Produces ( MediaType.APPLICATION_JSON )
	public Response createMessage ( MessageDTO messageDTO ) {
		var clientIp = getIp ();
		Log.infof ( "Called Create Message with payload: %s from IP: %s", messageDTO, clientIp );
		messageService.createMessage ( clientIp, messageDTO );
		return Response.accepted ().build ();
	}

	private String getIp () {
		// First, check X-Forwarded-For
		var forwardedFor = request.getHeader ( "X-Forwarded-For" );
		if ( forwardedFor != null && !forwardedFor.isEmpty () ) {
			// X-Forwarded-For can contain a list of IPs, take the first one
			return forwardedFor.split ( "," )[0].trim ();
		}
		// Try X-Real-IP
		var realIp = request.getHeader ( "X-Real-IP" );
		if ( realIp != null && !realIp.isEmpty () ) {
			return realIp;
		}
		// As a last resort, use the remote IP
		return request.remoteAddress ().host ();
	}

}