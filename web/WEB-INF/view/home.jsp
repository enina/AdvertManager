<%@ include file="common/taglibs.jsp" %>
<%
String ctxName=request.getServletContext().getContextPath();
%>
<h1>
        <fmt:message key="welcome.title"/>
</h1>
<p>
        <c:out value="${message}"/>
</p>                

<div> <a href="<%=ctxName%>/affiliates/list.do">Affiliates</a>
      <a href="<%=ctxName%>/products/list.do"> Products </a>
</div>


<sec:authorize access="hasRole('ROLE_ADMIN')">
    <div> <a href="<%=ctxName%>/dataGen/generate.do">Generate Data</a></div>
    <div> <a href="<%=ctxName%>/users/new.do">Register New User</a></div>
    <div> <a href="<%=ctxName%>/users/list.do">Show All Users</a></div>
    <div> <a href="<%=ctxName%>/apps/parsergen.do">Configure Parser</a></div>  
</sec:authorize>



