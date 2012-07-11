<%@ include file="../common/taglibs.jsp" %>

<div id="accessPerDomainStats" >
    
    <h2>Top 10 Domains</h2>
<c:if test="${domainStats!=null}">
    <table id="totalDomainAccessesTable" class="tablesorter">
        <thead>
            <tr>
                <th>Domain Name</th><th>Total Access amount</th>
            </tr>
        </thead>

        <tbody>
        <c:forEach items="${domainStats}" var="domainItem"  >
        <tr>
            <td><c:out value="${domainItem.source.accessSourceDomain}"/></td> <td><c:out value="${domainItem.accessAmount}"/></td>
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
<div id="chartdiv"></div>

    

<script type="text/javascript">
    
    $(function(){
            var chart;

            var chartData = new Array();

                
                
            <c:forEach items="${domainStats}" var="domainItem"  >

                chartData.push({domain:"${domainItem.source.accessSourceDomain}",visitors:${domainItem.accessAmount}});

            </c:forEach>   
    
            AmCharts.ready(function () {
                // PIE CHART


                chart = new AmCharts.AmPieChart();

                // title of the chart
                chart.dataProvider = chartData;
                chart.titleField = "domain";
                chart.valueField = "visitors";
                chart.sequencedAnimation = true;
                chart.startEffect = "elastic";
                chart.innerRadius = "30%";
                chart.startDuration = 2;
                chart.labelRadius = 6;

                // the following two lines makes the chart 3D
                chart.depth3D = 10;
                chart.angle = 15;

                // WRITE                                 
                chart.write("chartdiv");
            });
            
    });
 </script>
</div>      