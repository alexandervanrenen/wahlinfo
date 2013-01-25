'use strict';

/* Controllers */

function WahlinfoCtrl() {
	
}

function SitzverteilungCtrl($scope, Sitzverteilung) {
	Sitzverteilung.get({},
		    function (data) {
        		$scope.sitzverteilung = data.sitzverteilung;
    	}, {});
}

function BundestagmitgliederCtrl($scope, Bundestagmitglieder) {
	Bundestagmitglieder.get({},
		    function (data) {
        		$scope.bundestagmitglieder = data.bundestagMitglied;
    	}, {});
}

function UeberhangmandateCtrl($scope, Ueberhangmandate) {
	Ueberhangmandate.get({},
		    function (data) {
        		$scope.ueberhangmandate = data.ueberhangmandate;
    	}, {});
}

function WahlkreisuebersichtCtrl($scope, Wahlkreisuebersicht) {
	$scope.getWahlkreisResults = function () {
		Wahlkreisuebersicht.get({ wahlkreisid: $scope.wahlkreisid },
			    function (data) {
	        		$scope.wahlkreisuebersicht = data;
	    	}, {});
    };
}

function WahlkreissiegerCtrl($scope, Wahlkreissieger) {
	Wahlkreissieger.get({},
		    function (data) {
        		$scope.wahlkreissieger = data.wahlkreisSieger;
    	}, {});
}

function KnappstesiegerCtrl($scope, Knappstesieger) {
	Knappstesieger.get({},
		    function (data) {
        		$scope.knappstesieger = data;
    	}, {});
}

function WahlomatCtrl($scope, Stimmzettel, Stimmabgabe) {
	$scope.getStimmzettel = function () {
		Stimmzettel.get({ wahlkreisid: $scope.wahlkreisid },
			    function (data) {
	        		$scope.stimmzettel = data;
	    	}, {});
    };
    
    $scope.selected = {kandidat: "", partei: ""};
    
    $scope.submitStimme = function () {
//    	if ($scope.selected.kandidat == -1)
//    		$scope.selected.kandidat = 0;
//    	
//    	if ($scope.selected.partei == -1)
//    		$scope.selected.partei = 0;
    	
    	alert('Kandidat-ID: ' + $scope.selected.kandidat 
    			+ '\n' + 'Partei-ID: ' + $scope.selected.partei);
//    	Stimmabgabe.get({ wahlkreisid: $scope.wahlkreisid, kandidatid: $scope.kandidatid, parteiid: $scope.parteiid },
//			    function (data) {
//    				if (data.erfolg == 'true') {
//    					alert('Die Stimmabgabe war erfolgreich!');
//    				} else {
//    					alert(data.fehler);
//    				}
//	    	}, {});
    };
}
