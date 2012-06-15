<%-- 
    Document   : programspec
    Created on : May 12, 2012, 2:13:19 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@ include file="../common/taglibs.jsp" %>



<div Id="programSpec">
    <div>
	<a href="${pageContext.request.contextPath}/mvc/billing/import/${program.id}">Import Data for user</a>
	<span style="margin: 0px;padding: 0px"><c:out value="${program.userName}" />@</span><a style="margin: 0px;padding: 0px" target="_blank" href="<c:out value='${program.affProgramLink}'/>" title="<c:out value='${program.description}'/>">
	    <c:out value="${program.name}" />
	</a>
    </div>
 </div>
                 
