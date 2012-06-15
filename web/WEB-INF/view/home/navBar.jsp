<%@ include file="../common/taglibs.jsp" %>

    <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/home">Home</a></li>
    <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/affprograms/new">Add program</a></li>
<sec:authorize access="hasRole('ROLE_ADMIN')">

    <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/affiliates/list">Affiliates</a></li>
    <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/apps/parsergen">Configure Parser</a></li>  
    <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/billing/list">Billing sites</a></li>
<!--        <li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/dataGen/generate">Generate Data</a></li>-->
</sec:authorize> 