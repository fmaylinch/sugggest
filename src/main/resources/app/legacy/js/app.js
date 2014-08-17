(function(){

    var app = angular.module('sugggestApp', ['ngRoute', 'sugggestControllers']);

    app.config(['$routeProvider', function($routeProvider)
    {
        $routeProvider.
                when('/', {
                    templateUrl: '/legacy/partials/suggestions.html',
                    controller: 'SuggestionCtrl'
                }).
                when('/friends', {
                    templateUrl: '/legacy/partials/friends.html',
                    controller: 'FriendCtrl'
                }).
                when('/you', {
                    templateUrl: '/legacy/partials/you.html',
                    controller: 'YouCtrl'
                }).
                otherwise({
                    redirectTo: '/'
                });
    }]);

})();
