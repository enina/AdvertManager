<%-- 
    Document   : orders
    Created on : May 12, 2012, 8:00:56 PM
    Author     : Misha
--%>

<%@ include file="../common/taglibs.jsp" %>
    
<table >
    
    <c:if test="${orderList!=null}">
        
        <thead>
            <tr>
                <th> ID </th>
                <th> IP </th>
                <th> Date & Time </th>
                <th> Order sum </th>
                <th> Commission </th>
                <th> Currency </th>
            </tr>
        </thead>
        
        <c:forEach items="${orderList}" var="order">
            <tr>
                <td   >
                    <c:out value="${order.originalOrderID}" />
                </td>
                <td  >
                    <c:out value="${order.ipAddress}"/>
                </td>
                <td  >
                    <c:out value="${order.ordertime}" />
                </td>
                <td  >
                    <c:out value="${order.sum}" />
                </td>
                <td  >
                    <c:out value="${order.commision}" />
                </td>
                <td  >
                    <c:out value="${order.currency}" />
                </td>
            </tr>   
        </c:forEach>
    </c:if>        
</table>