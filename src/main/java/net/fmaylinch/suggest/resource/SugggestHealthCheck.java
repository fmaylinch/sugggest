package net.fmaylinch.suggest.resource;

import com.hubspot.dropwizard.guice.InjectableHealthCheck;

public class SugggestHealthCheck extends InjectableHealthCheck
{
	@Override
	protected Result check() throws Exception {
		return Result.healthy("This is a dummy health check");
	}

	@Override
	public String getName() {
		return "health";
	}
}
