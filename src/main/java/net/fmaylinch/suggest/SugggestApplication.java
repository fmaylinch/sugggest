package net.fmaylinch.suggest;

import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.fmaylinch.suggest.auth.UserAuthenticator;
import net.fmaylinch.suggest.config.SugggestConfiguration;
import net.fmaylinch.suggest.model.User;
import net.fmaylinch.suggest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SugggestApplication extends Application<SugggestConfiguration> {

	private static final Logger logger = LoggerFactory.getLogger(SugggestApplication.class);

	private GuiceBundle<SugggestConfiguration> guiceBundle;

	public static void main(String[] args) throws Exception {
		new SugggestApplication().run(args);
	}

	@Override
	public String getName() {
		return "sugggest";
	}
	@Override
	public void initialize(Bootstrap<SugggestConfiguration> bootstrap) {

//		bootstrap.addBundle(new AssetsBundle("/app/", "/"));
		bootstrap.addBundle(new AssetsBundle("/polymerapp/", "/"));

		guiceBundle = GuiceBundle.<SugggestConfiguration>newBuilder()
				.addModule(new SugggestModule(null))
				.enableAutoConfig(getClass().getPackage().getName())
				.setConfigClass(SugggestConfiguration.class)
				.build(Stage.DEVELOPMENT);

		bootstrap.addBundle(guiceBundle);
	}

	@Override
	public void run(SugggestConfiguration configuration, Environment environment) throws Exception {

		// I couldn't use applicationContextPath as explained in the docs
		// Workaround: http://comments.gmane.org/gmane.comp.java.dropwizard.user/178
		environment.jersey().setUrlPattern("/api/*");

		final UserService userService = guiceBundle.getInjector().getInstance(UserService.class);

		environment.jersey().register(new BasicAuthProvider<User>(new UserAuthenticator(userService), "Sugggest Realm"));
	}
}
