
<%@ include file="taglibs.jsp" %>


<div style="height: 50px; background-color: #999999;">
    <table name="" border="1" class="affTable" style="background-color: #999999; display: block; margin:0px; padding:0px; border-collapse: collapse;">
        <tr>
            <td class="affTd"><a name="menu" href="${pageContext.request.contextPath}/mvc/home">Home</a></td>
            <td class="affTd"><a name="menu" href="${pageContext.request.contextPath}/mvc/products/new">Add Product</a></td>
            <td class="affTd"><a name="menu" href="${pageContext.request.contextPath}/mvc/authors/new">Add Author</a></td>
            <td class="affTd"><a name="menu" href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a></td>
        </tr>
    </table>
    <div>
        Hello
        <!---->
        <sec:authorize access="hasRole('ROLE_USER')">
            <sec:authentication property="principal.username"/> . 
        </sec:authorize>            
    </div> 
</div>

 