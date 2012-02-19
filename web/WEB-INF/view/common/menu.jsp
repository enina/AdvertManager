<%-- 
    Document   : common.jsp
    Created on : Jan 19, 2012, 4:35:57 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>
<%
String ctxName=request.getServletContext().getContextPath();
%>
<table border="1" class="affTable">
    <tr>
        <td class="affTd"><a href="<%=ctxName%>/home.do">Home</a></td>
        <td class="affTd"><a href="<%=ctxName%>/j_spring_security_logout">Logout</a></td>
    </tr>
</table>