package net.fmaylinch.suggest;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import net.fmaylinch.suggest.config.SugggestConfiguration;
import net.fmaylinch.suggest.model.User;
import net.fmaylinch.suggest.service.SuggestionService;
import net.fmaylinch.suggest.service.SuggestionServiceImpl;
import net.fmaylinch.suggest.service.UserService;
import net.fmaylinch.suggest.service.UserServiceImpl;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.inject.Singleton;
import java.net.UnknownHostException;

public class SugggestModule extends AbstractModule {

	private final SugggestConfiguration configuration;

	public SugggestModule(SugggestConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	protected void configure()
	{
		bind(UserService.class).to(UserServiceImpl.class);
		bind(SuggestionService.class).to(SuggestionServiceImpl.class);
	}

	@Provides
	@Singleton
	@Inject
	public Datastore providesDataStore(MongoClient mongoClient)
	{
		return new Morphia()
				.map(User.class)
				.createDatastore(mongoClient, "sugggest");
	}

	@Provides
	@Singleton
	public MongoClient providesMongoClient(SugggestConfiguration configuration) throws UnknownHostException
	{
		final MongoClientURI mongoURI = new MongoClientURI(configuration.getMongoUrl());
		return new MongoClient(mongoURI);
	}

}
