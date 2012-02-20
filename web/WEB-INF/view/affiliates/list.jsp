<%@ include file="../common/taglibs.jsp" %>
<c:if test="${status!=null}">
    <div style="color:red"><c:out value="${status}"/></div>
</c:if>
<table border="1" class="affTable">
    <c:if test="${data!=null}">
        <c:forEach items="${data}" var="affiliate">
        <tr>
            <td class="affTd" align="left" >
                <c:out value="${affiliate.id}" />
            </td>
            <td class="affTd" align="left">
                <c:out value="${affiliate.affiliateName}"/>
            </td>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <td class="affTd" align="left" >
                    <c:out value="${affiliate.email}" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${affiliate.password}" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${affiliate.enabled}" />
                </td>            
            </sec:authorize>            
            </tr>   
        </c:forEach>
    </c:if>        
</table>
