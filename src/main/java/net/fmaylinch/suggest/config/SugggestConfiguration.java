package net.fmaylinch.suggest.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class SugggestConfiguration extends Configuration {

	@NotEmpty
	private String mongoUrl;

	@JsonProperty
	public String getMongoUrl() {
		return mongoUrl;
	}
}
