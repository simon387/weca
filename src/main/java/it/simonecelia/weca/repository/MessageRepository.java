package it.simonecelia.weca.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.simonecelia.weca.entity.MessageEntity;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class MessageRepository implements PanacheRepository<MessageEntity> {

	public void createMessage ( String clientIp, String message ) {
		var messageEntity = new MessageEntity ( clientIp, message );
		persist ( messageEntity );
	}
}
