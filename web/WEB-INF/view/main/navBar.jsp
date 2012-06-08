<%@ include file="../common/taglibs.jsp" %>



<sec:authorize access="hasRole('ROLE_ADMIN')">
<li class=""> <a class="menuItem" href="${pageContext.request.contextPath}/mvc/home">Home</a></li>
</sec:authorize> 
<li > <a href="${pageContext.request.contextPath}/mvc/learn">Learn</a></li>
<li > <a  href="${pageContext.request.contextPath}/mvc/privacy">Privacy policy</a></li>

        
