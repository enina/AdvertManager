

    

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
                
        <script type="text/javascript">
            $(function(){
                document.f.j_username.focus();
            });
        </script>
         
