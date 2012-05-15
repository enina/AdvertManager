<%-- 
    Document   : programspec
    Created on : May 12, 2012, 2:13:19 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@ include file="../common/taglibs.jsp" %>



<div class="programSpec">

    
    
    <table>
        
    <tr>
    <td>Program Name</td><td><c:out value="${program.name}" /></td>
    </tr>
    
    <tr>
    <td>Description</td><td><c:out value="${program.description}" /></td>
    </tr>
    
    <tr>
    <td>User Name</td><td><c:out value="${program.userName}" /></td>
    </tr>

    <tr>
        <td>Back office url</td><td><a href="<c:out value='${program.affProgramLink}' />"><c:out value="${program.affProgramLink}" /></a></td>
    </tr>
    
    <tr>
    <td>Refresh Data</td><td><a href="${pageContext.request.contextPath}/mvc/billing/import/${program.id}">Refresh Button</a></td>
    </tr>

    </table>
</div>
                 
