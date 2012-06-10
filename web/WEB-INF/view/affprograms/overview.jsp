<%-- 
    Document   : overview
    Created on : May 12, 2012, 3:44:05 PM
    Author     : Nina Eidelshtein and Misha Lebedev

    this document present overview data of selected program 
    usage: affprogram -> overview 

    data presented:
    table with agregation:

    descriprion |partners|accesses|orderspayd orders|access convert rate|order convert rate|
    ----------------------------------------------------------------------------------------
    today       |        |        |                 |                   |                  |
    past week   |        |        |                 |                   |                  |
    past month  |        |        |                 |                   |                  |
    all times   |        |        |                 |                   |                  |

    program back office data:
    
    description |income from partners|total income|costs|net income|paid|remains to be paid
    ----------------------------------------------------------------------------------------
    today       |                    |            |     |          |    |                  |
    past week   |                    |            |     |          |    |                  |
    past month  |                    |            |     |          |    |                  |
    all times   |                    |            |     |          |    |                  |
--%>
<%@ include file="../common/taglibs.jsp" %>

<c:if test="${status!=null}">
    <div style="color:red"><c:out value="${status}"/></div>
</c:if>

<div id="" >
    
    
    <table>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/calculateAggregationData">Calculate Aggregation Data</a>
            </td>    
        </tr>    
    </table>    
    <c:if test="${totalStats!=null}">
        <table class="dataTable">
            <tr>
                <th>Period</th>
                <th>Access Amount</th>
                <th>Purchase Amount</th>
                <th>Commision Sum</th>
                <th>Rate</th>
            </tr>
            <tr>
                <td>Total</td>
                <td><c:out value="${totalStats.accessAmount}"/></td>
                <td><c:out value="${totalStats.purchaseAmount}"/></td>
                <td><c:out value="${totalStats.totalCommision}"/></td>
                <td><c:out value="${totalStats.conversionRate}"/></td>
            </tr>
            <c:if test="${pmStats!=null}">
                <tr>
                    <td>Previous Month</td>
                    <td><c:out value="${pmStats.accessAmount}"/></td>
                    <td><c:out value="${pmStats.purchaseAmount}"/></td>
                    <td><c:out value="${pmStats.totalCommision}"/></td>
                    <td><c:out value="${pmStats.conversionRate}"/></td>
                </tr>
            </c:if>
            <c:if test="${cmStats!=null}">
                <tr>
                    <td>Current Month</td>
                    <td><c:out value="${cmStats.accessAmount}"/></td>
                    <td><c:out value="${cmStats.purchaseAmount}"/></td>
                    <td><c:out value="${cmStats.totalCommision}"/></td>
                    <td><c:out value="${cmStats.conversionRate}"/></td>
                </tr>
            </c:if>
            <c:if test="${dailyStats!=null}">
                <tr>
                    <td>Today</td>
                    <td><c:out value="${dailyStats.accessAmount}"/></td>
                    <td><c:out value="${dailyStats.purchaseAmount}"/></td>
                    <td><c:out value="${dailyStats.totalCommision}"/></td>
                    <td><c:out value="${dailyStats.conversionRate}"/></td>
                </tr>
            </c:if>
        </table>
    </c:if>
            
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

</div>
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
                chart.addTitle("Top 10 Domains", 30);

                chart.dataProvider = chartData;
                chart.titleField = "domain";
                chart.valueField = "visitors";
                chart.sequencedAnimation = true;
                chart.startEffect = "elastic";
                chart.innerRadius = "30%";
                chart.startDuration = 2;
                chart.labelRadius = 2;

                // the following two lines makes the chart 3D
                chart.depth3D = 10;
                chart.angle = 15;

                // WRITE                                 
                chart.write("chartdiv");
            });
            
            });
        </script>
        <div id="chartdiv"></div>