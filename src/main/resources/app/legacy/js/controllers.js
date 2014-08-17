(function(){

    var app = angular.module('sugggestControllers', ['sugggestServices']);

    app.controller('UserCtrl',
            ['$scope', '$http', 'Base64', function($scope, $http, Base64)
    {
        var user = {
            name: "Ferran",
            email: "ferranmaylinch@gmail.com",
            password: "WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=",
            id: "11111"
        };

        $http.defaults.headers.common['Authorization'] =
            'Basic ' + Base64.encode(user.email + ':' + user.password);

        $scope.user = user;
    }]);

    app.controller('NavBarCtrl',
            ['$scope', function($scope)
    {
        $scope.sections = [
            { name:"Suggestions", url:"#/" },
            { name:"Friends", url:"#/friends" },
            { name:"you", url:"#/you" }
        ];

        $scope.currentSection = $scope.sections[0];

        $scope.setCurrentSection = function(section) {
            $scope.currentSection = section;
        };

        $scope.isCurrentSection = function(section) {
            return section === $scope.currentSection;
        };

    }]);

    app.controller('SuggestionCtrl',

            ['$scope', '$http', '$log', function($scope, $http, $log)
    {
        $scope.suggestions = [];

        $log.info('Looking for suggestions for ' + $scope.user.name);

        // User that we want is passed in basic auth
        $http.get('/api/suggestions/to')
            .success(function(suggestions) {
                $scope.suggestions = suggestions;
            });
    }]);

    app.controller('FriendCtrl',

            ['$scope', '$http', '$log', function($scope, $http, $log) {

        $scope.friends = [];

        $log.info('Looking for friends of ' + $scope.user.name);

        $http.get('/api/users/friends')
            .success(function(friends) {
                $scope.friends = friends;
            });
    }]);

    app.controller('YouCtrl',

            ['$scope', '$http', '$log', function($scope, $http, $log) {

    }]);

})();
