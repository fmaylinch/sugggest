package net.fmaylinch.suggest.model;

import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;

import java.util.Date;

public class Model {

	@Id
	private String id;

	private Date created;
	private Date updated;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}

	@PrePersist
	void prePersist()
	{
		updated = new Date();
		if (created == null) {
			created = updated;
		}
	}
}
