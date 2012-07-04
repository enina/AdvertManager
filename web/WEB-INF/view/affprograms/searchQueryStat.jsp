<%@ include file="../common/taglibs.jsp" %>


<div id="queryRating" >
    
    <h2>Top 15 Search Queries</h2>
<c:if test="${queryStats!=null}">
    <table id="totalDomainAccessesTable" class="tablesorter">
        <thead>
            <tr>
                <th>Search Query</th><th>Rating</th>
            </tr>
        </thead>

        <tbody>
        <c:forEach items="${queryStats}" var="queryStatsItem"  >
        <tr>
            <td><c:out value="${queryStatsItem.query}"/></td> <td><c:out value="${queryStatsItem.rating}"/></td>
        </tr>
        </c:forEach>
        </tbody>

    </table>

    <script type="text/javascript">
        $(document).ready(function() { 
        // call the tablesorter plugin, the magic happens in the markup 
            $('.tablesorter').tablesorter({sortList: [[1,1]] }); 
        }); 
    </script>
</c:if>
<div id="queryRatingChartdiv"></div>

    

<script type="text/javascript">
    
    $(function(){
            var chart;

            var chartData = new Array();

                
                
            <c:forEach items="${queryStats}" var="queryStatsItem"  >

                chartData.push({query:"${queryStatsItem.query}",rating:${queryStatsItem.rating}});

            </c:forEach>   
    
            AmCharts.ready(function () {
                // PIE CHART


                chart = new AmCharts.AmPieChart();

                // title of the chart
                chart.dataProvider = chartData;
                chart.titleField = "query";
                chart.valueField = "rating";
                chart.sequencedAnimation = true;
                chart.startEffect = "elastic";
                chart.innerRadius = "30%";
                chart.startDuration = 2;
                chart.labelRadius = 5;

                // the following two lines makes the chart 3D
                chart.depth3D = 10;
                chart.angle = 15;

                // WRITE                                 
                chart.write("queryRatingChartdiv");
            });
            
    });
 </script>
</div>         