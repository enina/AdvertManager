<%-- 
    Document   : index
    Created on : Jan 13, 2012, 7:58:38 PM
    Author     : nina
--%>
<%@ include file="WEB-INF/view/common/taglibs.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Advert Manager Login</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
        
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/index.css"/>" />
        <script type="text/javascript" src="<c:url value="/js/jquery-1.7.2.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/jquery-ui-1.8.19.custom.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/index.js"/>"></script>
    </head>
    
    
    <body onload='document.f.j_username.focus();' >
        
        <div id="menuBg"></div>
        
        <div id="container">
        
        <%//display status message if any %>
        <c:if test="${status!=null}">
            <div style="color:red"><c:out value="${status}"/></div>
        </c:if>
            
            <!-- logo -->
            <h1 id="logo"><a href="${pageContext.request.contextPath}/"><img src="images/logo.png"/><span>Advert Manager</span></a></h1>
            <!-- login form -->
            <form id="loginForm" name='f' action='${pageContext.request.contextPath}/j_spring_security_check' method='post' >
                <span>Login form </span>
                <ul >                 
                    <li id="emailInput" ><span>User Name</span><input  type='text' name='j_username' value='' ></li>
                    <li id="passwordInput"><span>Password</span><input  type='password' name='j_password' /></li>       
                    <li id="loginButton" ><button type="submit"  value="login" accesskey=""><span>Login</span></button></li>
                    <li id="registerButton"><a href="${pageContext.request.contextPath}/mvc/affiliates/new" ><button type="button" value="register"><span>Register</span></button></a></li>
                    <li id="rememberMe" ><span>Remember me</span><input type='checkbox' name='_spring_security_remember_me'/></li>
                </ul>
            </form>   
             
            <!-- nav bar -->
            <ul id="menu">
                <li>Home</li>
                <li>Learn</li>
                <li>About</li>
            </ul>
            
            <!-- advertisement -->
            <div id="advert1" class="advert currentAdvert">
                <div class="bulletinBoard">
                    <span class="bulletinHeader">
                        Explore the location of your clients
                    </span>
                    <span class="bulletinBody">
                        With the Geolocation tool you<br/>
                        can know where your clients<br/>
                        came from. Better understand<br/>
                        your niche and come up with<br/>
                        better advertisement to the target.<br/>
                    </span>
                    
                </div>
                <img src="images/world-map.png"/>
            </div>     
            <div id="advert2" class="advert hidden">
                <div class="bulletinBoard">
                    <span class="bulletinHeader">
                        Consolidate your work
                    </span>
                    <span class="bulletinBody">
                        <br/>Save valuable time by having
                        <br/>all your affiliate programs data
                        <br/>daily updated, analyzed and
                        <br/>waiting to you click away in
                        <br/>one place.
                    </span>
                    
                </div>
                <img src="images/folders.png"/>
            </div>
            <div id="advert3" class="advert hidden">
                <div class="bulletinBoard">
                    <span class="bulletinHeader">
                        Control your traffic flow
                    </span>
                    <span class="bulletinBody">
                        <br/>Watch your traffic flow per
                        <br/>domain or even per specific 
                        <br/>advertisement, be it a banner 
                        <br/>or a link word in the middle of 
                        <br/>a text paragraph.
                        <br/>
                        <br/>Get all the key words that 
                        <br/>your customers use in 
                        <br/>search engines to find your 
                        <br/>products.
                    </span>
                    
                </div>
                <img src="images/traffic-graph.png"/>
            </div>    
        </div><!-- end of container -->
    </body>
</html>