'use strict';

angular.module('wahlinfo', ['wahlinfoServices', 'wahlinfoDirectives', 'SharedServices']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.
    	when('/wahlinfo', {templateUrl: 'partials/wahlinfo.html', controller: WahlinfoCtrl}).
    	when('/deutschland', {templateUrl: 'partials/deutschland.html', controller: DeutschlandCtrl}).
    	when('/bundeslaender', {templateUrl: 'partials/bundeslaender.html', controller: BundeslaenderCtrl}).
    	when('/wahlkreise', {templateUrl: 'partials/wahlkreise.html', controller: WahlkreiseCtrl}).
    	when('/parteien', {templateUrl: 'partials/parteien.html', controller: ParteienCtrl}).
    	when('/kandidaten', {templateUrl: 'partials/kandidaten.html', controller: KandidatenCtrl}).
    	when('/wahlinfo', {templateUrl: 'partials/wahlinfo.html', controller: WahlinfoCtrl}).
    	when('/sitzverteilung', {templateUrl: 'partials/sitzverteilung.html', controller: SitzverteilungCtrl}).
    	when('/bundestagsmitglieder', {templateUrl: 'partials/bundestagsmitglieder.html', controller: BundestagsmitgliederCtrl}).
    	when('/ueberhangmandate', {templateUrl: 'partials/ueberhangmandate.html', controller: UeberhangmandateCtrl}).
    	when('/wahlkreisuebersicht', {templateUrl: 'partials/wahlkreisuebersicht.html', controller: WahlkreisuebersichtCtrl}).
    	when('/wahlkreissieger', {templateUrl: 'partials/wahlkreissieger.html', controller: WahlkreissiegerCtrl}).
    	when('/knappstesieger', {templateUrl: 'partials/knappstesieger.html', controller: KnappstesiegerCtrl}).
    	when('/wahlomat', {templateUrl: 'partials/wahlomat.html', controller: WahlomatCtrl}).
    	otherwise({redirectTo: '/wahlinfo'});
  }]);

angular.module('SharedServices', [])
.config(function ($httpProvider) {
    $httpProvider.responseInterceptors.push('myHttpInterceptor');
    var spinnerFunction = function (data, headersGetter) {
        $('#loading').show();
        $('#main').hide();
        return data;
    };
    $httpProvider.defaults.transformRequest.push(spinnerFunction);
})

// Register the interceptor as a service, intercepts ALL angular ajax http calls
.factory('myHttpInterceptor', function ($q, $window) {
    return function (promise) {
        return promise.then(function (response) {
            $('#loading').hide();
            $('#main').fadeTo("slow", 1);
            return response;

        }, function (response) {
            $('#main').fadeTo("slow", 1);
            return $q.reject(response);
        });
    };
});