## Sugggest

This is a webapp for sharing suggestions between friends.

It uses
[MongoDB](http://www.mongodb.org/) + [Morphia](https://github.com/mongodb/morphia),
[Dropwizard](http://dropwizard.io/) + [Guice](https://github.com/google/guice) and
[Polymer](http://www.polymer-project.org/).

### Executing locally

Execute the `SugggestApplication` class with these arguments: `server src/main/resources/env/local/sugggest.yml`. Edit that `sugggest.yml` file to change the configuration.

### Deploying on Heroku

You need to add an environment variable for the Mongo URI. Example:

`heroku config:add SUGGGEST_MONGO_URI=mongodb://admin:thepass@kahana.mongohq.com:12345/sugggest`
