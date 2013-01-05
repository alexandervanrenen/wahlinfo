'use strict';

/* Controllers */

function BundestagCtrl($scope, Sitzverteilung, Bundestagmitglieder) {
	$scope.sitzverteilung = Sitzverteilung.query();
	$scope.bundestagmitglieder = Bundestagmitglieder.query();
}


function WahlergebnisseCtrl() {}


function VorjahresvergleichCtrl() {}
