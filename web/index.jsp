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
        <script>
            function submitForm(){
                
                
            }
            
        </script>
        <style>
            a:hover{
                cursor: pointer;
            }
            
           /* cursor: hand*/
        </style>
    </head>
    <body onload='document.f.j_username.focus();' style="position: relative; background-image: url('${pageContext.request.contextPath}/images/ironPatern25X25.png');">
        
        <c:if test="${status!=null}">
            <div style="color:red"><c:out value="${status}"/></div>
        </c:if>
        <div style="position: relative; left:35%; top:25%; background-color: #888888; width:300px;">
        <form style="width:100%;" name='f' id="loginForm" action='${pageContext.request.contextPath}/j_spring_security_check' method='post'>
            <h3>Please Login</h3>
            <table>
                <tr>
                    <td>User Name:</td>
                    <td><input type='text' name='j_username' value='' style="width:150"></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type='password' name='j_password' style="width:150"/></td>
                </tr>
                <tr>
                    <td><input type='checkbox' name='_spring_security_remember_me'/></td>
                    <td>Remember me</td>
                </tr>
                <tr>
                    <td ><a style="display: block;  text-decoration: none; width:55px; border: solid 1px white; color:black;" onclick="document.getElementById('loginForm').submit();">Login</a></td>   <!-- <input name="submit" type="submit" value="Login"/> -->
                    <td ><a style="display: block;  text-decoration: none; width:55px; border: solid 1px white; color:black;" href="${pageContext.request.contextPath}/mvc/affiliates/new">Register</a></td>
                </tr>
            </table>
        </form>
            
        </div>
    
    </body>
</html>