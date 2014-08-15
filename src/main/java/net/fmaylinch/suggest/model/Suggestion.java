package net.fmaylinch.suggest.model;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "suggestions", noClassnameStored = true)
public class Suggestion extends Model {

	private String text;
	private String from;
	private String to;


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
