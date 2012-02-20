<%-- 
    Document   : registration
    Created on : Jan 28, 2012, 11:28:49 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@include file="../common/taglibs.jsp" %>
<form:form  modelAttribute="affiliate" method="post" action="add.do">
    <table>
        <tr>
            <td><form:label path="affiliateName">Affiliate Name</form:label></td>
            <td><form:input path="affiliateName" /></td>
        </tr>
        
        <tr>
            <td><form:label path="password">Password</form:label></td>
            <td><form:password path="password" /></td>
        </tr>
        
        <tr>
            <td><form:label path="email">Email</form:label></td>
            <td><form:input path="email" /></td>
        </tr>
        
        <tr>
            <td><form:label path="enabled">Enabled</form:label></td>
            <td><form:checkbox path="enabled" /></td>
        </tr>

        <tr>
            <td colspan="2">
                <input type="submit" value="Register"/>
            </td>
        </tr>
    </table>  

</form:form>
