<%-- 
    Document   : index
    Created on : Jan 13, 2012, 7:58:38 PM
    Author     : nina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
        <form name='f' action='j_spring_security_check' method='POST'>
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
        <div> <a href="users/new.do">Register New User</a></div>
    </body>
</html>