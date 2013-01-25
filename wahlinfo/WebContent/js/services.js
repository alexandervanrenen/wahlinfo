'use strict';

/* Services */

angular.module('wahlinfoServices', ['ngResource']).
  factory('Sitzverteilung', function($resource){
	  return $resource('api/sitzverteilung', {}, {});
  }).
  factory('Bundestagmitglieder', function($resource){
	  return $resource('api/bundestagmitglieder', {}, {});
  }).
  factory('Ueberhangmandate', function($resource){
	  return $resource('api/ueberhangmandate', {}, {});
  }).
  factory('Wahlkreisuebersicht', function($resource){
	  return $resource('api/wahlkreisuebersicht/:wahlkreisid', {}, {});
  }).
  factory('Wahlkreissieger', function($resource){
	  return $resource('api/wahlkreissieger', {}, {});
  }).
  factory('Knappstesieger', function($resource){
	  return $resource('api/knappstesieger', {}, {});
  }).
  factory('Stimmzettel', function($resource){
	  return $resource('api/stimmzettel/:wahlkreisid', {}, {});
  }).
  factory('Stimmabgabe', function($resource){
	  return $resource('api/stimmabgabe/:wahlkreisid/:kandidatid/:parteiid', {}, {});
  });
