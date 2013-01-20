'use strict';

angular.module('wahlinfo', ['wahlinfoServices', 'wahlinfoDirectives']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.
    	when('/bundestag', {templateUrl: 'partials/bundestag.html', controller: BundestagCtrl}).
    	when('/wahlergebnisse', {templateUrl: 'partials/wahlergebnisse.html', controller: WahlergebnisseCtrl}).
    	when('/wahlmodul', {templateUrl: 'partials/wahlmodul.html', controller: WahlmodulCtrl}).
    	otherwise({redirectTo: '/bundestag'});
  }]);
