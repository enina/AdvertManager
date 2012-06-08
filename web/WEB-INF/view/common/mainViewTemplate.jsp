<%@ include file="taglibs.jsp" %>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
%>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="cache-control" content="no-cache, no-store"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
        
        <title><tiles:getAsString name="title" /></title>
        
        <tiles:insertAttribute name="htmlInclude" />
    </head>
    
    
    <body >
        
        <div id="menuBg"></div>
        
        <div id="container">
            <!-- logo -->
            <h1 id="logo"><a href="${pageContext.request.contextPath}/"><img src="<c:url value="images/logo.png" />" /><span>Advert Manager</span></a></h1>
            <tiles:insertAttribute name="userControl" />
            <tiles:insertAttribute name="menu" />
            
            <tiles:insertAttribute name="content" />

        </div> <!-- end of container -->
    </body>
</html>