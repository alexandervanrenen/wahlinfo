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
			        for (var i in array) {
			        	data.addRow([array[i].partei.kurzbezeichnung, parseInt(array[i].sitze)]);
			        }
			        
			        // Set chart options
			        var options = {title: 'Sitzverteilung im Bundestag',
			                       width: 900,
			                       height: 500,
			                       colors: ['ffd34e', 'd3224e', '4db075', '9e6dce', '5d5d9a'],
			                       is3D: true
			        };
		
			        // Instantiate and draw our chart, passing in some options.
			        var chart = new google.visualization.PieChart(elm[0]);
			        chart.draw(data, options);
	            }, true);
			}
		}
	});
