'use strict';

/* Controllers */

function BundestagCtrl($scope, Sitzverteilung, Bundestagmitglieder, Ueberhangmandate) {
	$scope.sitzverteilung = Sitzverteilung.query();
	$scope.bundestagmitglieder = Bundestagmitglieder.query();
	$scope.ueberhangmandate = Ueberhangmandate.query();
}


function WahlergebnisseCtrl() {}


function VorjahresvergleichCtrl() {}
