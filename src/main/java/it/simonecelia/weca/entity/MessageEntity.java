package it.simonecelia.weca.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;


@Entity
public class MessageEntity extends PanacheEntity {

	public LocalDateTime creationTime;

	public LocalDateTime lastUpdateTime;

	public String ip;

	public String message;

	public MessageEntity ( String ip, String message ) {
		super ();
		this.ip = ip;
		this.message = message;
	}

	public MessageEntity () {

	}

	@PrePersist
	public void prePersist () {
		this.creationTime = LocalDateTime.now ();
		this.lastUpdateTime = this.creationTime;
	}

	@PreUpdate
	public void preUpdate () {
		this.lastUpdateTime = LocalDateTime.now ();
	}

	@Override
	public String toString () {
		return "MessageEntity{" +
						"creationTime=" + creationTime +
						", lastUpdateTime=" + lastUpdateTime +
						", ip='" + ip + '\'' +
						", message='" + message + '\'' +
						", id=" + id +
						'}';
	}
}
