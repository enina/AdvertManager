
<%@ include file="taglibs.jsp" %>


<div id="subHeader" >

        <ul  id="topMenue" > 

            <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/home" >Home</a></li>
            <li class=""><a class="menuItem" href="${pageContext.request.contextPath}/mvc/affprograms/new">Add Program</a></li>

        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/dataGen/generate">Generate Data</a></li>
            <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/affiliates/list">Affiliates</a></li>
            <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/affiliates/new">Register Affiliate</a></li>
            <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/apps/parsergen">Configure Parser</a></li>  
            <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/billing/list">Billing sites</a></li>
        </sec:authorize> 
        <li class=""><a  class="menuItem" href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a></li>

    </ul>



</div>

