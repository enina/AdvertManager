<%-- 
    Document   : orders
    Created on : May 12, 2012, 8:00:56 PM
    Author     : Misha
--%>

<%@ include file="../common/taglibs.jsp" %>
    
<table class="dataTable" id="phrchaseOrderTable">
    
    <c:if test="${orderList!=null}">
        
        <thead>
            <tr>
                <th> ID </th>
                <th> IP </th>
<!--                <th> Country Name </th>
                <th> Country Code </th>-->
                <th></th>
                <th> Date &amp; Time </th>
                <th> Order sum </th>
                <th> Commission </th>
                <th> Currency </th>
<!--                <th> Access Amount </th>-->
            </tr>
        </thead>
        
        <c:forEach items="${orderList}" var="order">
            <tr class="poDataRow">
                <td>
                    <c:out value="${order.originalOrderID}" />
                   
                </td>
                <td>
                    <c:out value="${order.ipAddress}"/>
                    <c:if test="${order.accessAmount > 0}" >
                        <span class="poInfo" ><img onclick='showAccess(this.parentElement,"${pageContext.request.contextPath}/mvc/access/po/${order.id}");' src="${pageContext.request.contextPath}/images/info.png" title="asociated Access"/></span>
                    </c:if>
                </td>
<!--                <td>
                    <c:out value="${order.countryName}" />
                </td>-->
                <td>
                    
                    <img src="${pageContext.request.contextPath}/images/countryFlag/${order.countryCode.toLowerCase().concat('.png')}" alt="${order.countryName}" title="${order.countryName}" />
               
                </td>            
                <td>
                    <c:out value="${order.ordertime}" />
                </td>
                <td>
                    <c:out value="${order.sum}" />
                </td>
                <td>
                    <c:out value="${order.commision}" />
                </td>
                <td>
                    <c:out value="${order.currency}" />
                </td>
            </tr>   
        </c:forEach>
    </c:if>        
</table>

