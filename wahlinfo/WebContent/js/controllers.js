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

function WahlergebnisseCtrl($scope, Wahlkreisuebersicht, Wahlkreissieger) {
	$scope.getWahlkreisResults = function () {
		Wahlkreisuebersicht.get({ wahlkreisid: $scope.wahlkreisid },
			    function (data) {
	        		$scope.wahlkreisuebersicht = data;
	    	}, {});
    };
    
    Wahlkreissieger.get({},
		    function (data) {
        		$scope.wahlkreissieger = data.wahlkreisSieger;
    	}, {});
}

function WahlmodulCtrl($scope, Stimmzettel, Stimmabgabe) {
	$scope.getStimmzettel = function () {
		Stimmzettel.get({ wahlkreisid: $scope.wahlkreisid },
			    function (data) {
	        		$scope.stimmzettel = data;
	    	}, {});
    };
    
    $scope.submitStimme = function () {
    	Stimmabgabe.get({ wahlkreisid: $scope.wahlkreisid, kandidatid: $scope.kandidatid, parteiid: $scope.parteiid },
			    function (data) {
    				if (data.erfolg == 'true') {
    					alert('Die Stimmabgabe war erfolgreich!');
    				} else {
    					alert(data.fehler);
    				}
	    	}, {});
    };
}
