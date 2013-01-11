'use strict';

/* Directives */

angular.module('wahlinfoDirectives', []).
	directive('chart', function() {
		return {
			restrict: 'A',
			link: function(scope, elm, attrs) {
				scope.$watch('sitzverteilung', function(array) {
					// Create the data table.
			        var data = new google.visualization.DataTable();
			        data.addColumn('string', 'Partei');
			        data.addColumn('number', 'Anzahl der Sitze');
			        
			        // Add rows
			        var farben = new Array();
			        for (var i in array) {
			        	data.addRow([array[i].name, parseInt(array[i].sitze)]);
			        	farben[i] = array[i].farbe;
			        }
			        
			        // Set chart options
			        var options = {title: 'Sitzverteilung im Bundestag',
			                       width: 800,
			                       height: 400,
			                       // TODO set colors dynamically
			                       colors: ['green', 'black', 'blue', 'purple', 'yellow', 'red']
			        };
		
			        // Instantiate and draw our chart, passing in some options.
			        var chart = new google.visualization.PieChart(elm[0]);
			        chart.draw(data, options);
	            }, true);
			}
		}
	});
