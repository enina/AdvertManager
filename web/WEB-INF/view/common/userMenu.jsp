<%@ include file="taglibs.jsp" %>

 <sec:authorize access="!hasRole('ROLE_USER')">
<!-- login form -->
<form id="loginForm" name='f' action='${pageContext.request.contextPath}/j_spring_security_check' method='post' >
<span>Login form </span>
    <ul >                 
        <li id="emailInput" ><span>User Name</span><input  type='text' name='j_username' value='' ></li>
        <li id="passwordInput"><span>Password</span><input  type='password' name='j_password' /></li>       
        <li id="loginButton" ><button type="submit"  value="login" accesskey=""><span>Login</span></button></li>
        <li id="registerButton"><a href="${pageContext.request.contextPath}/mvc/affiliates/new" ><button type="button" value="register"><span>Register</span></button></a></li>
        <li id="rememberMe" ><span>Remember me</span><input type='checkbox' name='_spring_security_remember_me'/></li>
    </ul>
</form>
        
<script>
    $( function(){ document.f.j_username.focus();});
</script>
</sec:authorize>
        

<sec:authorize access="hasRole('ROLE_USER')">
<div id="userMenu">
    <span id="userEmail">
        <sec:authentication property="principal.username"/>
    </span>
    <div id="userMenuInner" class="hidden">
    <a href="${pageContext.request.contextPath}/j_spring_security_logout" ><button type="button" value="Logout"><span>Logout</span></button></a>
    </div>
</div>
    <script type="text/javascript">
        $("#userMenu").click(function(){
            $("#userMenuInner").toggleClass("hidden");
            $("#userMenuInner button").focus();
        });
        
        $("#userMenuInner button").blur(function(){
            $("#userMenuInner").toggleClass("hidden");
        });
        
    </script>
</sec:authorize>
