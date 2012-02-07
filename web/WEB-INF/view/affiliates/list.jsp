<%@ include file="../common/taglibs.jsp" %>
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
            <td class="affTd" align="left" >
                <c:out value="${affiliate.email}" />
            </td>
            </tr>   
        </c:forEach>
    </c:if>        
</table>
