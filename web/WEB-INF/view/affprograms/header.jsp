<%@ include file="../common/taglibs.jsp" %>


<div style="height: 70px; background-color: #353535;">
    
    
     <span id="welcomeUser">
        Hello
        <!---->
        <sec:authorize access="hasRole('ROLE_USER')">
            <sec:authentication property="principal.username"/>  
        </sec:authorize>
            
     </span>
    
    
    <ul  id="topMenue" > 
    
            <li > <a  href="${pageContext.request.contextPath}/mvc/home" >Home</a></li>
            <c:choose>
             <c:when test="${program!=null}">
             <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/details/${program.id}"> Overview </a></li>
             <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/finance"> Finance </a></li>
             <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/access/list">Accesses</a></li>
             <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/partners">Partners</a></li>
             <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/orders">Orders</a></li>
             <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/programlinks">Your links</a></li>  
             </c:when>
            </c:choose> 
            <li class=""><a name="menu" class="menuItem" href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a></li>
      
    </ul>


  
</div>