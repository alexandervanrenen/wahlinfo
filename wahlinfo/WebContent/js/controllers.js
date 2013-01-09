'use strict';

/* Controllers */

function BundestagCtrl($scope, Sitzverteilung, Bundestagmitglieder, Ueberhangmandate) {
	Sitzverteilung.get({},
		    function (data) { // success callback
        		$scope.sitzverteilung = data.sitzverteilung;
    	}, {});
	
	Bundestagmitglieder.get({},
		    function (data) { // success callback
        		$scope.bundestagmitglieder = data.bundestagMitglied;
    	}, {});
	
	Ueberhangmandate.get({},
		    function (data) { // success callback
        		$scope.ueberhangmandate = data.ueberhangmandate;
    	}, {});
}


function WahlergebnisseCtrl() {}


function VorjahresvergleichCtrl() {}
