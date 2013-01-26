'use strict';

/* Controllers */

function WahlinfoCtrl() {
	
}

function DeutschlandCtrl($scope, Deutschland) {
	Deutschland.get({},
		    function (data) {
        		$scope.deutschland = data;
    	}, {});
}

function BundeslaenderCtrl($scope, Bundeslaender) {
	Bundeslaender.get({},
		    function (data) {
        		$scope.bundeslaender = data.bundesland;
    	}, {});
}

function WahlkreiseCtrl($scope, Wahlkreise) {
	Wahlkreise.get({},
		    function (data) {
        		$scope.wahlkreise = data.wahlkreis;
    	}, {});
}

function KandidatenCtrl($scope, Kandidaten) {
	Kandidaten.get({},
		    function (data) {
        		$scope.kandidaten = data.kandidat;
    	}, {});
}

function ParteienCtrl($scope, Parteien) {
	Parteien.get({},
		    function (data) {
        		$scope.parteien = data.partei;
    	}, {});
}

function SitzverteilungCtrl($scope, Sitzverteilung) {
	Sitzverteilung.get({},
		    function (data) {
        		$scope.sitzverteilung = data.sitzverteilung;
    	}, {});
}

function BundestagsmitgliederCtrl($scope, Bundestagsmitglieder) {
	Bundestagsmitglieder.get({},
		    function (data) {
        		$scope.bundestagsmitglieder = data.bundestagMitglied;
    	}, {});
}

function UeberhangmandateCtrl($scope, Ueberhangmandate) {
	Ueberhangmandate.get({},
		    function (data) {
        		$scope.ueberhangmandate = data.ueberhangmandate;
    	}, {});
}

function WahlkreisuebersichtCtrl($scope, Wahlkreisuebersicht) {
	$('#results').hide();
	$scope.getWahlkreisResults = function () {
		Wahlkreisuebersicht.get({ wahlkreisid: $scope.wahlkreisid },
			    function (data) {
	        		$scope.wahlkreisuebersicht = data;
	        		$('#results').show();
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
	$('#stimmzettel').hide();
	$scope.getStimmzettel = function () {
		Stimmzettel.get({ wahlkreisid: $scope.wahlkreisid },
			    function (data) {
	        		$scope.stimmzettel = data;
	        		$('#stimmzettel').show();
	    	}, {});
    };
    
    $scope.selected = {kandidat: "", partei: ""};
    $scope.submitStimme = function () {
    	Stimmabgabe.get({ wahlkreisid: $scope.wahlkreisid, 
    		kandidatid: $scope.selected.kandidat, parteiid: $scope.selected.partei },
			    function (data) {
    				if (data.erfolg == 'true') {
    					alert('Die Stimmabgabe war erfolgreich!');
    				} else {
    					alert(data.fehler);
    				}
	    	}, {});
    };
}
