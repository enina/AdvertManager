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
    </head>
    <body onload='document.f.j_username.focus();'>
        <h3>Login with Username and Password</h3>
        <c:if test="${status!=null}">
            <div style="color:red"><c:out value="${status}"/></div>
        </c:if>
        <form name='f' action='${pageContext.request.contextPath}/j_spring_security_check' method='POST'>
            <table>
                <tr>
                    <td>User:</td>
                    <td><input type='text' name='j_username' value=''></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type='password' name='j_password'/></td>
                </tr>
                <tr>
                    <td><input type='checkbox' name='_spring_security_remember_me'/></td>
                    <td>Remember me on this computer.</td>
                </tr>
                <tr>
                    <td colspan='2'><input name="submit" type="submit" value="Login"/></td>
                </tr>
            </table>
        </form>
        <div> <a href="${pageContext.request.contextPath}/affiliates/new.do">Register</a></div>
    </body>
</html>