<%@ include file="../common/taglibs.jsp" %>



<tiles:insertAttribute name="programspec" />



<!-- Tabs -->
<div id="programDetails">
    <ul>
        <li > <a  href="#tabs-1">Overview </a></li>
<!--        <li > <a  href="#tabs-2">Finance </a></li>-->
        <li > <a  href="#accesses">Accesses</a></li>
        <li > <a  href="#tabs-4">Orders</a></li>
        <li > <a  href="#tabs-5">Partners</a></li>
<!--        <li > <a  href="#tabs-6">Your links</a></li>  -->
    </ul>

    <div id="tabs-1"><tiles:insertAttribute name="overview" /></div>

    <div id="accesses"><tiles:insertAttribute name="accesses" /></div>
    <div id="tabs-4"><tiles:insertAttribute name="orders" /></div>
    <div id="tabs-5"><tiles:insertAttribute name="partners" /></div>   


</div>


<script>
    $(function(){
        // Tabs
        $('#programDetails').tabs();

    });
</script>



<!--QueryStats-->

<div id="OverviewGraphicStatistics">
    
    <tiles:insertAttribute name="domainPooularityStat" />
    <tiles:insertAttribute name="accessByCountryStat" />
    <tiles:insertAttribute name="searchQueryRating" />
   
    


    <script type="text/javascript" src="${pageContext.request.contextPath}/amMap/swfobject.js""></script>
</div>
