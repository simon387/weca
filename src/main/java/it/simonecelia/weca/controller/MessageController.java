package it.simonecelia.weca.controller;

import io.quarkus.logging.Log;
import it.simonecelia.weca.dto.MessageDTO;
import it.simonecelia.weca.service.MessageService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path ( "/message" )
public class MessageController extends ControllerUtil {

	@Inject
	MessageService messageService;

	@POST
	@Consumes ( MediaType.APPLICATION_JSON )
	@Produces ( MediaType.APPLICATION_JSON )
	public Response createMessage ( MessageDTO messageDTO, @Context HttpServletRequest httpServletRequest ) {
		var clientIp = getIp ( httpServletRequest );
		Log.infof ( "Called Create Message with payload: %s from IP: %s", messageDTO, clientIp );
		messageService.createMessage ( clientIp, messageDTO );
		return Response.accepted ().build ();
	}
}
