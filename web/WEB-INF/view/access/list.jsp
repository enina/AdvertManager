

<% 
    /* AdvertManager\web\WEB-INF\view\access\list.jsp
     * this file generate presentation of accesses for given affiliate pragram
     * 
    */
%> 


<%@ include file="../common/taglibs.jsp" %>
<script>
    var accessCtxPath = "${pageContext.request.contextPath}/mvc/affprograms/${program.id}";
</script>

<c:set var="ctxPath" scope="request"  value="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/items/5/accessPage" />
<c:choose >
    
<c:when test="${accessPage!=null && accessPage.items != null && accessPage.pageCtrl.totalPages > 0 }">
<table >
    
   
        
        <thead>
            <tr>
                <th> Time </th>
                <th> IP </th>
                <th> Referrer </th>
                <th> Target </th>
            </tr>
        </thead>
        
        <%-- prepare links for priv/next page --%>
        <div>
            <c:if test="${accessPage.pageCtrl.currentPage != 1}">
                <a   onclick="return getPageData('${ctxPath}/1')">First</a>
                <a   onclick="return getPageData('${ctxPath}/${accessPage.pageCtrl.prevPage}')">Previous</a>
            </c:if>
            Page <c:out value="${accessPage.pageCtrl.currentPage}"/> of <c:out value="${accessPage.pageCtrl.totalPages}"/>
            <c:if test="${accessPage.pageCtrl.currentPage != accessPage.pageCtrl.totalPages }">
                <a  onclick="return getPageData('${ctxPath}/${accessPage.pageCtrl.nextPage}')">Next </a>
                <a   onclick="return getPageData('${ctxPath}/${accessPage.pageCtrl.totalPages}')">Last</a>
            </c:if>
        </div>
        
        <%-- print all items of current page --%>
        <c:forEach items="${accessPage.items}" var="access">
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
         
</table>
        
</c:when>
<c:otherwise>
    No data yet
</c:otherwise>
    
</c:choose>

