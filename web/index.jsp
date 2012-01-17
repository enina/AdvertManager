<%-- 
    Document   : index
    Created on : Jan 13, 2012, 7:58:38 PM
    Author     : nina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
request.getServletContext().getRequestDispatcher("/home.do").forward(request, response);
%>
