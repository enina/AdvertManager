<%@ include file="../common/taglibs.jsp" %>



<tiles:insertAttribute name="programspec" />



<!-- Tabs -->
<div id="programDetails">
    <ul>
        <li > <a  href="#tabs-1">Overview </a></li>
        <li > <a  href="#tabs-2">Finance </a></li>
        <li > <a  href="#accesses">Accesses</a></li>
        <li > <a  href="#tabs-4">Orders</a></li>
        <li > <a  href="#tabs-5">Partners</a></li>
        <li > <a  href="#tabs-6">Your links</a></li>  
    </ul>

    <div id="tabs-1"><tiles:insertAttribute name="overview" /></div>
    <div id="tabs-2"><tiles:insertAttribute name="finance" /></div>
    <div id="accesses"><tiles:insertAttribute name="accesses" /></div>
    <div id="tabs-4"><tiles:insertAttribute name="orders" /></div>
    <div id="tabs-5"><tiles:insertAttribute name="partners" /></div>   
    <div id="tabs-6"><tiles:insertAttribute name="afflinks" /></div>

</div>


<script>
    $(function(){
        // Tabs
        $('#programDetails').tabs();

    });
</script>


<div id="OverviewGraphicStatistics">
    
    
    

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
                //chart.addTitle("Top 10 Domains", 30);

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
</div>       
        
        
<div id="accessesPerCountry">
    <h2>Accesses Per Country</h2> 
        
    <div id="flashcontent">
            <strong>You need to upgrade your Flash Player</strong>
    </div>
 

    <script type="text/javascript" src="${pageContext.request.contextPath}/amMap/swfobject.js"></script>
    <script type="text/javascript">

        var countries = "";

        <c:forEach items="${countryStats}" var="countryStat"  >
            countries += '<area title="${countryStat.countryName}" mc_name="${countryStat.countryCode}" value="${countryStat.accessAmount}" ></area>';
        </c:forEach>   


        var so = new SWFObject("${pageContext.request.contextPath}/amMap/ammap.swf", "ammap", "1160", "600", "8", "#FFFFFF");

        so.addVariable("map_data", escape('<map map_file="world3.swf" url="#movie1" zoom="100%" zoom_x="15%" zoom_y="10%" ><areas>'+ countries+'</areas></map>'));
        so.addVariable("path", "${pageContext.request.contextPath}/amMap/");
        so.addVariable("settings_file", escape("${pageContext.request.contextPath}/amMap/ammap_settings.xml"));		
        so.addVariable("preloader_color", "#999999");
        so.write("flashcontent");
    </script>
</div>
        
        
</div>