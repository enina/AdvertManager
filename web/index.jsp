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
    <body onload='document.f.j_username.focus();' style="position: relative; background-color: #9fccf4;">
        
        <c:if test="${status!=null}">
            <div style="color:red"><c:out value="${status}"/></div>
        </c:if>
        <div style="position: relative; left:35%; top:25%; background-image: url('${pageContext.request.contextPath}/images/loginbg300x200.png'); width:300px; height:200px;">
        <div style="position: absolute; bottom: 0px;">
        <form style="width:100%;" name='f' id="loginForm" action='${pageContext.request.contextPath}/j_spring_security_check' method='post'>
          
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
            
        </div>
    
    </body>
</html>