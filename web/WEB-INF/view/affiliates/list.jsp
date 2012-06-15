<%@ include file="../common/taglibs.jsp" %>
<c:if test="${status!=null}">
    <div style="color:red"><c:out value="${status}"/></div>
</c:if>
 
    
<table  id="affiliateListTable" class="dataTable">
    
    
    <c:if test="${data!=null}">
        <thead>
            <tr >
                <th>Affiliate ID</th><th> Name </th>
                
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <th> Email</th><th>Password</th><th>Is Enabled </th>
                </sec:authorize> 
            </tr>
        </thead>
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

