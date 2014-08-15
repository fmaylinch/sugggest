package net.fmaylinch.suggest;

import com.hubspot.dropwizard.guice.InjectableHealthCheck;

public class SuggestHealthCheck extends InjectableHealthCheck
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
