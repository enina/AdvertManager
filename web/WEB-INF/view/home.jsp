<%@ include file="common/taglibs.jsp" %>
<h1>
        <fmt:message key="welcome.title"/>
</h1>
<p>
        <c:out value="${message}"/>
</p>                

<div> <a href="/AdvertManager/affiliates/list.do">Affiliates</a>
      <a href="/AdvertManager/products/list.do"> Products </a>
</div>


<sec:authorize access="hasRole('ROLE_ADMIN')">
    <div> <a href="/AdvertManager/dataGen/generate.do">Generate Data</a></div>
    <div> <a href="/AdvertManager/users/new.do">Register New User</a></div>
    <div> <a href="/AdvertManager/users/list.do">Show All Users</a></div>
    <div> <a href="/AdvertManager/apps/parsergen.do">Configure Parser</a></div>
    
</sec:authorize>



