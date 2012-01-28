<%-- 
    Document   : registration
    Created on : Jan 28, 2012, 11:28:49 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
    <head>
      <%@include file="commonheader.jsp" %>
      <title>Registration</title>
    </head>
    <body>
        <%@include file="toolbar.jsp" %>
        <form:form method="post" action="addUser.do">
            <table>
                <tr>
                    <td><form:label path="username">User Name</form:label></td>
                    <td><form:input path="username" /></td>
                </tr>
                <tr>
                    <td><form:label path="password">Password</form:label></td>
                    <td><form:input path="password" /></td>
                </tr>
                <tr>
                    <td><form:label path="enabled">Enabled</form:label></td>
                    <td><form:checkbox path="enabled" /></td>
                </tr>
                
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Register"/>
                    </td>
                </tr>
            </table>  
 
        </form:form>
        <%@include file="toolbar.jsp" %>
    </body>
</html>
