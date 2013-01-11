'use strict';

/* Controllers */

function BundestagCtrl($scope, Sitzverteilung, Bundestagmitglieder, Ueberhangmandate) {
	Sitzverteilung.get({},
		    function (data) {
        		$scope.sitzverteilung = data.sitzverteilung;
    	}, {});
	
	Bundestagmitglieder.get({},
		    function (data) {
        		$scope.bundestagmitglieder = data.bundestagMitglied;
    	}, {});
	
	Ueberhangmandate.get({},
		    function (data) {
        		$scope.ueberhangmandate = data.ueberhangmandate;
    	}, {});
}

function WahlergebnisseCtrl($scope, Wahlkreisuebersicht) {
	$scope.getWahlkreisResults = function () {
		Wahlkreisuebersicht.get({wahlkreisid:$scope.wahlkreisid},
			    function (data) {
	        		$scope.parteiergebnisse = data.parteiergebnisse;
	    	}, {});
    };
}

function VorjahresvergleichCtrl() {}
