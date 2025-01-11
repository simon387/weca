package it.simonecelia.weca.service;

import it.simonecelia.weca.dto.MessageDTO;
import it.simonecelia.weca.repository.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class MessageService {

	@Inject
	MessageRepository messageRepository;

	public void createMessage ( String clientIp, MessageDTO messageDTO ) {
		messageRepository.createMessage ( clientIp, messageDTO.getMessage () );
	}
}
