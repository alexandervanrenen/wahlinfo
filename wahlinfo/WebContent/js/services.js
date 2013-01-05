'use strict';

/* Services */

angular.module('wahlinfoServices', ['ngResource']).
  factory('Sitzverteilung', function($resource){
  return $resource('json/sitzverteilung.json', {}, {});
  }).
  factory('Bundestagmitglieder', function($resource){
  return $resource('json/bundestagmitglieder.json', {}, {});
  });
