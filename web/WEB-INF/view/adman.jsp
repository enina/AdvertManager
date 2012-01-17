<%-- 
    Document   : newjsp
    Created on : Jan 17, 2012, 2:51:06 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Advert Manager</title>
    </head>
    <body>
        <h1>
		<fmt:message key="welcome.title"/>
	</h1>
        <p>
                <c:out value="${message}"/>
        </p>                
        <div> <a href="/AdvertManager/dataGen.do">Generate Data</a></div>
        <div> <a href="/AdvertManager/affiliates.do">Affiliates</a></div>
  </body>
</html>
