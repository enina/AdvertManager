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