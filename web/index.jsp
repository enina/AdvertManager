<%-- 
    Document   : index
    Created on : Jan 13, 2012, 7:58:38 PM
    Author     : nina
--%>
<%@ include file="WEB-INF/view/common/taglibs.jsp" %>
<!--
<%--
    response.sendRedirect("home.do");
--%>
-->
<html>
    <head>
        <title>Login Page</title>
        
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/index.css"/>" />
        <script type="text/javascript" src="<c:url value="/js/jquery-1.7.2.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/jquery-ui-1.8.19.custom.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/index.js"/>"></script>
    </head>
    
    
    <body onload='document.f.j_username.focus();' >
        
        <%//display status message if any %>
        <c:if test="${status!=null}">
            <div style="color:red"><c:out value="${status}"/></div>
        </c:if>
            
        
        <div id="login">
            <div id="loginForm" >
                <form  name='f' action='${pageContext.request.contextPath}/j_spring_security_check' method='post'>
                        <ul id="formInput">
                        <li >User Name:</li>
                        
                        
                        <li><input type='text' name='j_username' value='' style="width:150"></li>

                        <li>Password:</li>
                        <li><input type='password' name='j_password' style="width:150"/></li>
                        
                        <li >  
                        Remember me
                        <input type='checkbox' name='_spring_security_remember_me'/>
                        </li>
                        
                        </ul>
                         
                        <ul id="formButtons">
                            <li><a  id="loginButton"    onclick="$('form').submit();">Login</a></li>  <!-- <input name="submit" type="submit" value="Login"/> -->
                            <li><a  id="registerButton" href="${pageContext.request.contextPath}/mvc/affiliates/new">Register</li></a>
                        </ul>
                        
                </form>
            </div>
            
        </div>
    
    </body>
</html>