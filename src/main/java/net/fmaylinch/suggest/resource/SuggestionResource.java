package net.fmaylinch.suggest.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import net.fmaylinch.suggest.model.Suggestion;
import net.fmaylinch.suggest.model.SuggestionDTO;
import net.fmaylinch.suggest.model.User;
import net.fmaylinch.suggest.service.SuggestionService;
import net.fmaylinch.suggest.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("suggestions")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class SuggestionResource {

	private final SuggestionService suggestionService;
	private final UserService userService;

	@Inject
	public SuggestionResource(SuggestionService suggestionService, UserService userService)
	{
		this.suggestionService = suggestionService;
		this.userService = userService;
	}

	@Path("to")
	@GET
	public Response findByUserTo(@Auth User user) {

		final List<Suggestion> suggestions = suggestionService.findByUserTo(user.getId());

		final List<SuggestionDTO> suggestionDtos = new ArrayList<SuggestionDTO>();

		for (Suggestion suggestion : suggestions)
		{
			SuggestionDTO dto = new SuggestionDTO();
			dto.setText(suggestion.getText());
			dto.setFromId(suggestion.getFrom());
			dto.setFromName(userService.findById(suggestion.getFrom()).getName());

			suggestionDtos.add(dto);
		}

		return Response.ok(suggestionDtos).build();
	}

	@Path("from")
	@GET
	public Response findByUserFrom(@Auth User user) {

		return Response.ok(suggestionService.findByUserFrom(user.getId())).build();
	}

	@POST
	public Response create(@Auth User user, Suggestion suggestion)
	{
		// TODO: Move these checks to the service

		if (!user.getId().equals(suggestion.getFrom())) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("'from' user is not equal to authenticated user").build();
		}

		if (userService.findById(suggestion.getTo()) == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("'to' user not found").build();
		}

		if (StringUtils.isEmpty(suggestion.getText())) {
			return Response.status(Response.Status.PRECONDITION_FAILED).entity("'text' is empty").build();
		}

		suggestionService.create(suggestion);

		return Response.ok(suggestion).build();
	}
}
