<%@ include file="../common/taglibs.jsp" %>
    
<table border="1" class="affTable">
    
    <c:if test="${data!=null}">
        
        <thead>
            <tr>
                <th> Time </th>
                <th> IP </th>
                <th> Referrer </th>
                <th> Target </th>
            </tr>
        </thead>
        
        <c:forEach items="${data}" var="access">
            <tr>
                <td class="affTd" align="left" >
                    <c:out value="${access.accessTime}" />
                </td>
                <td class="affTd" align="left">
                    <c:out value="${access.ipAddress}"/>
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${access.location}" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${access.url}" />
                </td>
            </tr>   
        </c:forEach>
    </c:if>        
</table>

