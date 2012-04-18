
<%@ include file="taglibs.jsp" %>


<div style="height: 70px; background-color: #353535;">
    
    
     <span style="float: right; font-size: 1.3em; position: relative; left: -40px; top: 10px; color: white;">
        Hello
        <!---->
        <sec:authorize access="hasRole('ROLE_USER')">
            <sec:authentication property="principal.username"/>  
        </sec:authorize>
            
     </span>
    <table name=""  class="menuetable" > <!--style="; display: block; margin:0px; padding:2px; border-collapse: collapse; " -->
        <tr>
            <td class="affTd"><a name="menu" class="menuItem" href="${pageContext.request.contextPath}/mvc/home" >Home</a></td>
            <td class="affTd"> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/affiliates/list">Affiliates</a></td>
            <td class="affTd"> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/products/list"> Products </a></td>
            <td class="affTd"> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/access/list"> AccessLog </a></td>

            <td class="affTd"><a name="menu" class="menuItem" href="${pageContext.request.contextPath}/mvc/products/new">Add Product</a></td>
            <td class="affTd"><a name="menu" class="menuItem" href="${pageContext.request.contextPath}/mvc/authors/new">Add Author</a></td> 
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <td class="affTd"> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/dataGen/generate">Generate Data</a></td>
                <td class="affTd"> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/affiliates/new">Register Affiliate</a></td>
                <td class="affTd"> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/apps/parsergen">Configure Parser</a></td>  
                <td class="affTd"> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/billing/list">Billing sites</a></td>
            </sec:authorize> 
            <td class="affTd"><a name="menu" class="menuItem" href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a></td>
        </tr>
    </table>


  
</div>

 