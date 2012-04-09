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
        <script type="text/javascript" src="<c:url value="/js/product.js"/>"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="cache-control" content="no-cache, no-store"/>
    </head>
    <body style="margin: 0px; background-color: #f5f5f5; position: relative;">
        <div id="header">
            <tiles:insertAttribute name="header" />
        </div>
        <div id="content">
            <table style="width:100%;"><tr><td style="width: 10%;">&nbsp;</td><td>
            <tiles:insertAttribute name="content" />
            </td><td style="width: 10%;">&nbsp;</td>
            </tr>
            </table>
        </div>
        <div id="footer" style="height: 20px;">
          
        </div>
    </body>
</html>