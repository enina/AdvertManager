<%-- 
    Document   : partners
    Created on : May 12, 2012, 8:00:25 PM
    Author     : Misha
--%>
<%@ include file="../common/taglibs.jsp" %>

<div id="partnersData">
    <c:choose>

    <c:when test="${partnerList != null}" >
        
        <table>
            <tr>
                <th>Name</th><th>Email</th>
                
            </tr>
            <c:forEach items="${partnerList}" var="partner">
                
                <tr>
                    <td><c:out value="${partner.name}"/> </td><td><c:out value="${partner.email}"/></td>
                </tr>
                 
            </c:forEach>
            
        </table>
        
    </c:when>
    <c:otherwise>
        No partners related to this program
    </c:otherwise>
    </c:choose>
    
</div>

