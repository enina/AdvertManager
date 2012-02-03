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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="cache-control" content="no-cache">
    </head>
    <body>
        <div id="header">
            <tiles:insertAttribute name="header" />
        </div>
        <div id="menu">
            <tiles:insertAttribute name="menu" />
        </div>
        <div id="content">
            <tiles:insertAttribute name="content" />
        </div>
        <div id="footer">
            <tiles:insertAttribute name="footer" />
        </div>
    </body>
</html>