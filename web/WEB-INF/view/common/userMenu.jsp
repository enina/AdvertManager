<%@ include file="taglibs.jsp" %>



<div id="userControl">
    
        <span id="userEmail">
            <sec:authorize access="hasRole('ROLE_USER')">
                <sec:authentication property="principal.username"/> <%//shuld be user email %>
            </sec:authorize>
        </span>
            
            
        <a id="logoutButton" href="${pageContext.request.contextPath}/j_spring_security_logout" ><button type="button" value="logout"><span>Logout</span></button></a>

</div>
        <sctipt>
            $( function(){
                $('#userControl');
            });
        </sctipt>