package it.simonecelia.weca.service;

import io.quarkus.logging.Log;
import it.simonecelia.weca.dto.MessageDTO;
import it.simonecelia.weca.entity.MessageEntity;
import it.simonecelia.weca.repository.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;


@ApplicationScoped
@Transactional
public class MessageService {

	@Inject
	MessageRepository messageRepository;

	public void createMessage ( String clientIp, MessageDTO messageDTO ) {
		messageRepository.createMessage ( clientIp, messageDTO.getMessage () );
	}

	public boolean modifyMessage ( String clientIp, Long id, MessageDTO messageDTO ) {
		var message = messageRepository.findById ( id );
		if ( message == null ) {
			Log.infof ( "Message with id %s not found", id );
			return false;
		}
		if ( message.ip == null || !message.ip.equalsIgnoreCase ( clientIp ) ) {
			Log.infof ( "Message with ip %s not matching", clientIp );
			return false;
		}
		if ( messageDTO.getMessage () == null || messageDTO.getMessage ().isEmpty () ) {
			Log.infof ( "Message with message is empty" );
			return false;
		}
		message.message = messageDTO.getMessage ();
		messageRepository.persist ( message );
		return true;
	}

	public boolean deleteMessage ( Long id ) {
		var message = messageRepository.findById ( id );
		if ( message == null ) {
			Log.infof ( "Message with id %s not found", id );
			return false;
		}
		messageRepository.delete ( message );
		return true;
	}

	public List<MessageEntity> getAllMessages () {
		return messageRepository.listAll ();
	}

	public void deleteMessages () {
		messageRepository.deleteAll ();
	}
}
