'use strict';

/* Services */

angular.module('wahlinfoServices', ['ngResource']).
  factory('Sitzverteilung', function($resource){
	  return $resource('json/sitzverteilung.json', {}, {});
//	  return $resource('http://localhost\\:8080/wahlinfo/rest/sitzverteilung', {}, {});
  }).
  factory('Bundestagmitglieder', function($resource){
	  return $resource('json/bundestagmitglieder.json', {}, {});
//	  return $resource('http://localhost\\:8080/wahlinfo/rest/bundestagmitglieder', {}, {});
  }).
  factory('Ueberhangmandate', function($resource){
	  return $resource('json/ueberhangmandate.json', {}, {});
//	  return $resource('http://localhost\\:8080/wahlinfo/rest/ueberhangmandate', {}, {});
  }).
  factory('Wahlkreisuebersicht', function($resource){
	  return $resource('http://localhost\\:8080/wahlinfo/rest/wahlkreisuebersicht/:wahlkreisid', {}, {});
  }).
  factory('Wahlkreissieger', function($resource){
	  return $resource('http://localhost\\:8080/wahlinfo/rest/wahlkreissieger', {}, {});
  }).
  factory('Stimmzettel', function($resource){
	  return $resource('http://localhost\\:8080/wahlinfo/rest/stimmzettel/:wahlkreisid', {}, {});
  }).
  factory('Stimmabgabe', function($resource){
	  return $resource('http://localhost\\:8080/wahlinfo/rest/stimmabgabe/:wahlkreisid/:kandidatid/:parteiid', {}, {});
  });
