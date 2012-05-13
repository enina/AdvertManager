<%-- 
    Document   : menu
    Created on : May 12, 2012, 2:09:26 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@ include file="../common/taglibs.jsp" %>


<div id="programMenu" >
    
    
    
    <ul  > 
    
        <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/details/${program.id}"> Overview </a></li>
        <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/finance${program.id}"> Finance </a></li>
        <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/access/${program.id}">Accesses</a></li>
        <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/partners/${program.id}">Partners</a></li>
        <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/orders/${program.id}">Orders</a></li>
        <li > <a  href="${pageContext.request.contextPath}/mvc/affprograms/programlinks/${program.id}">Your links</a></li>  

    </ul>


  
</div>

