<%@ include file="../common/taglibs.jsp" %>

<div id="accessesPerCountry">
    <h2>Accesses Per Country</h2> 
        
    <div id="flashcontent">
            <strong>You need to upgrade your Flash Player</strong>
    </div>
 

    <script type="text/javascript" src="${pageContext.request.contextPath}/amMap/swfobject.js""></script>
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