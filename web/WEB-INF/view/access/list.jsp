

<% 
    /* AdvertManager\web\WEB-INF\view\access\list.jsp
     * this file generate presentation of accesses for given affiliate pragram
     * 
    */
%> 


<%@ include file="../common/taglibs.jsp" %>
    
<table >
    
    <c:if test="${accessPage!=null && accessPage.items != null}">
        
        <thead>
            <tr>
                <th> Time </th>
                <th> IP </th>
                <th> Referrer </th>
                <th> Query </th>                
                <th> Target </th>
            </tr>
        </thead>
        
        <div>
            <c:if test="${accessPage.pageCtrl.currentPage != 1}">
                <a  href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/accessPage/1">First</a>
                <a  href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/accessPage/${accessPage.pageCtrl.prevPage}">Previous</a>
            </c:if>
            Page <c:out value="${accessPage.pageCtrl.currentPage}"/> of <c:out value="${accessPage.pageCtrl.totalPages}"/>
            <c:if test="${accessPage.pageCtrl.currentPage != accessPage.pageCtrl.totalPages }">
                <a  href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/accessPage/${accessPage.pageCtrl.nextPage}">Next </a>
                <a  href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/accessPage/${accessPage.pageCtrl.totalPages}">Last</a>
            </c:if>
        </div>
        
        <c:forEach items="${accessPage.items}" var="access">
            <tr>
                <td>
                    <c:out value="${access.timeAsString}" />
                </td>
                <td>
                    <c:out value="${access.ipAddress}"/>
                </td>
                <td>
                    <a href="${access.location}"><c:out value="${access.sourceDomain.accessSourceDomain}" /></a>
                </td>                
                <td>
                    <c:out value="${access.query}" />
                </td>                
                <td>
                    <c:out value="${access.url}" />
                </td>
            </tr>   
        </c:forEach>
    </c:if>        
</table>

