'use strict';

var app = angular.module('wahlinfo', ['wahlinfoServices']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.
    	when('/bundestag', {templateUrl: 'partials/bundestag.html', controller: BundestagCtrl}).
    	when('/wahlergebnisse', {templateUrl: 'partials/wahlergebnisse.html', controller: WahlergebnisseCtrl}).
    	when('/vorjahresvergleich', {templateUrl: 'partials/vorjahresvergleich.html', controller: VorjahresvergleichCtrl}).
    	otherwise({redirectTo: '/bundestag'});
  }]);

//app.directive('chart', function() {
//    return function($scope, $elm, $attr, Sitzverteilung) { // TODO inject Sitzverteilung
//    	
//        // Create the data table.
//        var data = new google.visualization.DataTable();
//        data.addColumn('string', 'Partei');
//        data.addColumn('number', 'Anzahl der Sitze');
////        data.addRows([
////          ['Mushrooms', 3],
////          ['Onions', 1],
////          ['Olives', 1],
////          ['Zucchini', 1],
////          ['Pepperoni', 2]
////        ]);
//        
//        
//
//        // Set chart options
//        var options = {'title':'Sitzverteilung im Bundestag',
//                       'width':400,
//                       'height':300};
//
//        // Instantiate and draw our chart, passing in some options.
//        var chart = new google.visualization.PieChart($elm[0]);
//        chart.draw(data, options);
//        
////        $scope.$watch($attr.chart, function(value) {
////        	alert('watch');
////	            // Create the data table.
////	            var data = new google.visualization.DataTable();
////	            data.addColumn('string', 'Partei');
////	            data.addColumn('number', 'Anzahl der Sitze');
////	            data.addRows(value);
////	            
////	            // Set chart options
////	            var options = {'title':'Sitzverteilung im Bundestag',
////	                           'width':400,
////	                           'height':300};
////	            
////	            chart.draw(data, options);
////    		});        
//    }
//});