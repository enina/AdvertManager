

<% 
    /* AdvertManager\web\WEB-INF\view\access\list.jsp
     * this file generate presentation of accesses for given affiliate pragram
     * 
    */
%> 


<%@ include file="../common/taglibs.jsp" %>
    
<table >
    
    <c:if test="${acessList!=null}">
        
        <thead>
            <tr>
                <th> Time </th>
                <th> IP </th>
                <th> Referrer </th>
                <th> Target </th>
            </tr>
        </thead>
        
        <c:forEach items="${acessList}" var="access">
            <tr>
                <td   >
                    <c:out value="${access.timeAsString}" />
                </td>
                <td  >
                    <c:out value="${access.ipAddress}"/>
                </td>
                <td  >
                    <a href="<c:out value="${access.location}" />">
                        <c:out value="${access.location}" />
                    </a>
                </td>
                <td  >
                    <c:out value="${access.url}" />
                </td>
            </tr>   
        </c:forEach>
    </c:if>        
</table>

