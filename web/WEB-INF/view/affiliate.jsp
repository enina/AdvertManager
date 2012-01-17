<%-- 
    Document   : affiliate
    Created on : Jan 17, 2012, 9:03:21 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <LINK href="css/adman.css" rel="stylesheet" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Affiliates</title>
    </head>
    <body>
        <table border="1" class="affTable">
          <c:forEach items="${data}" var="affiliate">
            <tr>
                <td class="affTd" align="left" >
                    <c:out value="${affiliate.id}" />
                </td>
                <td class="affTd" align="left">
                    <c:out value="${affiliate.affiliateName}" escapeXml="false"/>
                </td>
                <td align="left" class="affTd">
                    <c:out value="${affiliate.email}" />
                </td>
          </c:forEach>
        </table>
    </body>
</html>