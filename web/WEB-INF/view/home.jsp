<%@ include file="common/taglibs.jsp" %>
<!--
<h1>
   <fmt:message key="welcome.title"/>
</h1>
-->

<c:if test="${status!=null}">
    <div style="color:red"><c:out value="${status}"/></div>
</c:if>
<c:if test="${data!=null}">
    <table border="1" class="affTable">
        <c:forEach items="${data.productGroupCollection}" var="pg">
            <tr><td>
                <div>Product Group:<c:out value="${pg.groupName}" /></div>
                <table border="1" class="affTable">
                    <thead>
                        <tr style="background-color: #999999;">
                            <th>Product ID</td><td>Author Name</td><td>Author Email</td><td>Description</td><td>Price</td><td>Link</td>
                        </tr>
                    </thead>                
                    <c:forEach items="${pg.productCollection}" var="product">
                        <tr>
                            <td class="affTd" align="left" >
                                <c:out value="${product.id}" />
                            </td>
                            <td class="affTd" align="left" >
                                <c:out value="${product.authorId.authorName}" />
                            </td>                        
                            <td class="affTd" align="left" >
                                <c:out value="${product.authorId.email}" />
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
                </table>                    
            </td></tr>
        </c:forEach>
    </table>
</c:if>        

<!--
<div id="ext">
    
</div>
-->


<div> <a href="${pageContext.request.contextPath}/affiliates/list.do">Affiliates</a>
      <a href="${pageContext.request.contextPath}/products/list.do"> Products </a>
</div>


<sec:authorize access="hasRole('ROLE_ADMIN')">
    <div> <a href="${pageContext.request.contextPath}/dataGen/generate.do">Generate Data</a></div>
    <div> <a href="${pageContext.request.contextPath}/affiliates/new.do">Register Affiliate</a></div>
    <div> <a href="${pageContext.request.contextPath}/apps/parsergen.do">Configure Parser</a></div>  
</sec:authorize>



