

<%@ include file="../common/taglibs.jsp" %>
<table border="1" class="prodTable">
    

    <c:if test="${data!=null}">
        <tr style="background-color: #999999;">
        <td>Product ID</td><td>Author ID</td><td>Product Description</td>
    </tr>

        <c:forEach items="${data}" var="product">
        <tr>
            <td class="prodTd" align="left" >
                <c:out value="${product.id}" />
            </td>
            <td class="prodTd" align="left">
                <c:out value="${product.authorId}"/>
            </td>
            <td class="prodTd" align="left" >
                <c:out value="${product.description}" />
            </td>
            </tr>   
        </c:forEach>
    </c:if>

</table>



