package net.fmaylinch.suggest.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.fmaylinch.suggest.model.Suggestion;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

import java.util.List;

@Singleton
public class SuggestionServiceImpl implements SuggestionService {

	private final Datastore ds;

	@Inject
	public SuggestionServiceImpl(Datastore ds)
	{
		this.ds = ds;
	}

	@Override
	public void create(Suggestion suggestion)
	{
		final Key<Suggestion> key = ds.save(suggestion);

		suggestion.setId((String) key.getId());
	}

	@Override
	public List<Suggestion> findByUserTo(String userToId)
	{
		return ds.find(Suggestion.class).filter(FIELD_TO, userToId).asList();
	}

	@Override
	public List<Suggestion> findByUserFrom(String userFromId)
	{
		return ds.find(Suggestion.class).filter(FIELD_FROM, userFromId).asList();
	}
}
