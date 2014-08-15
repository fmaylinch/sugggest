(function(){

    var app = angular.module('sugggestApp', ['ngRoute', 'sugggestControllers']);

    app.config(['$routeProvider', function($routeProvider)
    {
        $routeProvider.
                when('/', {
                    templateUrl: '/partials/suggestions.html',
                    controller: 'SuggestionCtrl'
                }).
                when('/friends', {
                    templateUrl: '/partials/friends.html',
                    controller: 'FriendCtrl'
                }).
                otherwise({
                    redirectTo: '/'
                });
    }]);

})();
