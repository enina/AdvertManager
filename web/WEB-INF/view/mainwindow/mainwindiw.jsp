<%@ include file="../common/taglibs.jsp" %>
<%-- 
    Document   : mainwindiw
    Created on : May 27, 2012, 6:04:12 PM
    Author     : Misha and Nina 
--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title><tiles:getAsString name="title" /></title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/mainwindow.css"/>" />
        <script type="text/javascript" src="<c:url value="/js/program.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/accesspages.js"/>"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="cache-control" content="no-cache, no-store"/>
        
        <!-- jquery -->
        <link type="text/css" href="<c:url value="/css/black-tie/jquery-ui-1.8.19.custom.css"/>" rel="stylesheet" />
        <script type="text/javascript" src="<c:url value="/js/jquery-1.7.2.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/jquery-ui-1.8.19.custom.min.js"/>"></script>
        
    </head>
    <body>
        <div id="menuBg"></div>
        
        <div id="container">
             <h1 id="logo"><a href="${pageContext.request.contextPath}/"><img src="images/logo.png"/><span>Advert Manager</span></a></h1>

             <!-- user menue -->
             <div id="userMenu">
            <sec:authorize access="hasRole('ROLE_USER')">
                <sec:authentication property="principal.username"/>
            </sec:authorize>
            <a href="${pageContext.request.contextPath}/j_spring_security_logout" ><button type="button" value="register"><span>Logout</span></button></a>
            </div>
             
            <!-- nav bar -->
            <ul id="menu">
                <li><a href="${pageContext.request.contextPath}/mvc/home" >Home</a></li>
                <li><a href="${pageContext.request.contextPath}/mvc/affprograms/new">Add Program</a></li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">     
                <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/affiliates/list">Affiliates</a></li> 
                <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/apps/parsergen">Configure Parser</a></li>  
                <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/billing/list">Billing sites</a></li>
                <%//<li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/affiliates/new">Register Affiliate</a></li> 
                //<li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/dataGen/generate">Generate Data</a></li> %>
                </sec:authorize> 
            </ul>
                
                <%// affiliate programs agregation %>
                <div id="content">
                    
                    
                </div>
                
                
            
        </div><!-- end of container -->
    </body>
</html>
