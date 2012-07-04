

        <div id="dailyAccessStatChartdiv" style="width: 100%; height: 600px;"></div>
        <div style="height: 35px;">
            <input type="radio" name="group" id="rb1" onclick="setPanSelect()">Select
            <input type="radio" checked="true" name="group" id="rb2" onclick="setPanSelect()">Pan
	</div>  
     
        <script type="text/javascript">
            var chart;
            var chartData = [];
            var chartCursor;

            AmCharts.ready(function () {
                // generate some data first
//                generateChartData();
//                
//                var data = "2012-02-20";
//                
//                var yyyy = /(\d+)/.exec(data)[1];
//                var mm   = /\d+-(\d+)/.exec(data)[1];
//                var dd   = /\d+-\d+-(\d+)/.exec(data)[1];
//                //alert(yyyy + " " +mm + " " + dd);
//                
//                var myDate = new Date(yyyy, mm, dd);
                
        
                
                $.getJSON("http://localhost:8080/AdvertManager/mvc/affprograms/${programId}/accessPerDay",function(daylyAccess){
                    
                    var yyyy ="";
                    var mm   ="";
                    var dd   ="";
 
                    
                    for(var i=0; i < daylyAccess.length; i++){
                        yyyy = /(\d+)/.exec(daylyAccess[i].accessDay)[1];
                        mm   = /\d+-(\d+)/.exec(daylyAccess[i].accessDay)[1];
                        dd   = /\d+-\d+-(\d+)/.exec(daylyAccess[i].accessDay)[1];

                        var newDate = new Date(yyyy, mm, dd);
                        var accessAmount = daylyAccess[i].accessAmount;

                    chartData.push({
                        date: newDate,
                        accessAmount: accessAmount
                    });
                    
                    

                    }
                });

                // SERIAL CHART    
                chart = new AmCharts.AmSerialChart();
                chart.pathToImages = "../amcharts/images/";
                chart.zoomOutButton = {
                    backgroundColor: '#000000',
                    backgroundAlpha: 0.15
                };
                chart.dataProvider = chartData;
                chart.categoryField = "date";
                chart.balloon.bulletSize = 5;

                // listen for "dataUpdated" event (fired when chart is rendered) and call zoomChart method when it happens
                chart.addListener("dataUpdated", zoomChart);

                // AXES
                // category
                var categoryAxis = chart.categoryAxis;
                categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
                categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to DD
                categoryAxis.dashLength = 1;
                categoryAxis.gridAlpha = 0.15;
                categoryAxis.position = "top";
                categoryAxis.axisColor = "#DADADA";

                // value                
                var valueAxis = new AmCharts.ValueAxis();
                valueAxis.axisAlpha = 0;
                valueAxis.dashLength = 1;
                chart.addValueAxis(valueAxis);

                // GRAPH
                var graph = new AmCharts.AmGraph();
                graph.title = "red line";
                graph.valueField = "accessAmount:";
                graph.bullet = "round";
                graph.bulletBorderColor = "#FFFFFF";
				graph.bulletBorderThickness = 2; 
                graph.lineThickness = 2;
                graph.lineColor = "#5fb503";
                graph.negativeLineColor = "#efcc26";
                graph.hideBulletsCount = 50; // this makes the chart to hide bullets when there are more than 50 series in selection
                chart.addGraph(graph);

                // CURSOR
                chartCursor = new AmCharts.ChartCursor();
                chartCursor.cursorPosition = "mouse";
                chartCursor.pan = true; // set it to fals if you want the cursor to work in "select" mode
                chart.addChartCursor(chartCursor);

                // SCROLLBAR
                var chartScrollbar = new AmCharts.ChartScrollbar();
                chart.addChartScrollbar(chartScrollbar);

                // WRITE
                chart.write("dailyAccessStatChartdiv");
            });

            // generate some random data, quite different range
            function generateChartData() {
                var firstDate = new Date();
                firstDate.setDate(firstDate.getDate() - 500);

                for (var i = 0; i < 500; i++) {
                    var newDate = new Date(firstDate);
                    newDate.setDate(newDate.getDate() + i);

                    var visits = Math.round(Math.random() * 40) - 20;

                    chartData.push({
                        date: newDate,
                        visits: visits
                    });
                }
            }

            // this method is called when chart is first inited as we listen for "dataUpdated" event
            function zoomChart() {
                // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
                chart.zoomToIndexes(chartData.length - 5, chartData.length - 1);
            }
            
            // changes cursor mode from pan to select
            function setPanSelect() {
                if (document.getElementById("rb1").checked) {
                    chartCursor.pan = false;
                    chartCursor.zoomable = true;
                } else {
                    chartCursor.pan = true;
                }
                chart.validateNow();
            }   
			            
        </script>

        

