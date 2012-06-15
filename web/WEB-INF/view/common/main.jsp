<%@ include file="taglibs.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <title><tiles:getAsString name="title" /></title>
        <tiles:insertAttribute name="commonInclude" />
        <tiles:insertAttribute name="customInclude" />
    </head>
    
    <body >
        
        
        <div id="menuBg"></div>
        
        <div id="container">
            <!-- logo -->
            <h1 id="logo"><a href="${pageContext.request.contextPath}/mvc/main"><img src="${pageContext.request.contextPath}/images/logo.png"/><span>Advert Manager</span></a></h1>
 
            <tiles:insertAttribute name="userControl" />
            <!-- nav bar -->
            <ul id="menu">
                <tiles:insertAttribute name="navBar" />
            </ul>
            
            <div id="content">
                <tiles:insertAttribute name="content" />
            </div>

            
        </div> <!-- end of container div -->
        <div id="footerBg">
        
        <div id="footer">
        <tiles:insertAttribute name="footer" />
        </div>
        </div>
    </body>
</html>