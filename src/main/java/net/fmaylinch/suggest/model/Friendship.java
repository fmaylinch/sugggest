package net.fmaylinch.suggest.model;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "friendships", noClassnameStored = true)
public class Friendship extends Model {

	private String from;
	private String to;

	public Friendship() { }

	public Friendship(String from, String to) {
		this.from = from;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
