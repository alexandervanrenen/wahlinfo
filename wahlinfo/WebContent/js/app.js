'use strict';

angular.module('wahlinfo', ['wahlinfoServices', 'wahlinfoDirectives']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.
    	when('/wahlinfo', {templateUrl: 'partials/wahlinfo.html', controller: WahlinfoCtrl}).
    	when('/sitzverteilung', {templateUrl: 'partials/sitzverteilung.html', controller: SitzverteilungCtrl}).
    	when('/bundestagmitglieder', {templateUrl: 'partials/bundestagmitglieder.html', controller: BundestagmitgliederCtrl}).
    	when('/ueberhangmandate', {templateUrl: 'partials/ueberhangmandate.html', controller: UeberhangmandateCtrl}).
    	when('/wahlkreisuebersicht', {templateUrl: 'partials/wahlkreisuebersicht.html', controller: WahlkreisuebersichtCtrl}).
    	when('/wahlkreissieger', {templateUrl: 'partials/wahlkreissieger.html', controller: WahlkreissiegerCtrl}).
    	when('/knappstesieger', {templateUrl: 'partials/knappstesieger.html', controller: KnappstesiegerCtrl}).
    	when('/wahlomat', {templateUrl: 'partials/wahlomat.html', controller: WahlomatCtrl}).
    	otherwise({redirectTo: '/wahlinfo'});
  }]);
