<%@ include file="../common/taglibs.jsp" %>
<table border="1" class="affTable">
    <c:if test="${data!=null}">
        <thead>
            <tr >
                <th>Product ID</th><th>Product Description</th><th>Price</th><th>Link</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${data}" var="product">
                <tr>
                    <td class="affTd" align="left" >
                        <c:out value="${product.id}" />
                    </td>
                    <td class="affTd" align="left" >
                        <c:out value="${product.description}" />
                    </td>
                    <td class="affTd" align="left" >
                        <c:out value="${product.price}" />
                    </td>
                    <td class="affTd" align="left" >
                        <c:out value="${product.productLink}" />
                    </td>                
                </tr>   
            </c:forEach>
        </tbody>            
    </c:if>

</table>



