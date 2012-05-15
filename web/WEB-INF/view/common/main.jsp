<%@ include file="taglibs.jsp" %>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
%>
<!DOCTYPE html>
<html>
    <head>
        <title><tiles:getAsString name="title" /></title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/adman1.css"/>" />
        <script type="text/javascript" src="<c:url value="/js/program.js"/>"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="cache-control" content="no-cache, no-store"/>
        
        <!-- jquery -->
        <link type="text/css" href="<c:url value="/css/black-tie/jquery-ui-1.8.19.custom.css"/>" rel="stylesheet" />
        <script type="text/javascript" src="<c:url value="/js/jquery-1.7.2.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/jquery-ui-1.8.19.custom.min.js"/>"></script>
        
    </head>
    
    
    <body >
        <div id="container">
            
            <div id="header">
                <tiles:insertAttribute name="header" />
            </div>
            <div id="middle">
                <tiles:insertAttribute name="content" />
            </div>
            <div id="footer" style="height: 20px;">

            </div>
        </div>
    </body>
</html>