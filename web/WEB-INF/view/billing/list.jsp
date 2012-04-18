<%@ include file="../common/taglibs.jsp" %>
<c:if test="${status!=null}">
    <div style="color:red"><c:out value="${status}"/></div>
</c:if>

<table name=""  class="menuetable" >
    <tr>
        <td class="affTd"><a name="menu" class="menuItem" href="${pageContext.request.contextPath}/billing/new" >Upload Specification</a></td>
    </tr>
</table>
    
<table border="1" class="affTable">
    
    <c:if test="${data!=null}">
        
        <thead>
            <tr>
                <th> Name </th>
                <th> Base URL </th>
                <th> Home Page </th>
                <th> Validation selector </th>
                <th> Action </th>
            </tr>
        </thead>
        
        <c:forEach items="${data}" var="billingProj">
            <tr>
                <td class="affTd" align="left" >
                    <c:out value="${billingProj.name}" />
                </td>
                <td class="affTd" align="left">
                    <c:out value="${billingProj.baseURL}"/>
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${billingProj.homePage}" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${billingProj.selector}" />
                </td>
                <td class="affTd" align="left" >
                   <a href="${pageContext.request.contextPath}/billing/import" >Import Data</a>
                </td>                
            </tr>   
        </c:forEach>
    </c:if>        
</table>
