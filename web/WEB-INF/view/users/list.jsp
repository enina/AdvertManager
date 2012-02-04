<%-- 
    Document   : affiliate
    Created on : Jan 17, 2012, 9:03:21 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@ include file="../common/taglibs.jsp" %>
<c:if test="${status!=null}">
    <div style="color:red"><c:out value="${status}"/></div>
</c:if>    
<table border="1" class="affTable">
    <c:forEach items="${data}" var="user">
    <tr>
        <td class="affTd" align="left" >
            <c:out value="${user.username}" />
        </td>
        <td class="affTd" align="left">
            <c:out value="${user.password}"/>
        </td>
        <td class="affTd" align="left" >
            <c:out value="${user.enabled}" />
        </td>
        </tr>   
    </c:forEach>
</table>
