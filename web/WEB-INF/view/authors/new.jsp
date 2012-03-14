<%-- 
    Document   : registration
    Created on : Jan 28, 2012, 11:28:49 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@include file="../common/taglibs.jsp" %>
<form:form  modelAttribute="author" method="post" action="add">
    <table>


        <tr>
            <td><form:label path="authorName">Author Name</form:label></td>
            <td><form:input path="authorName" /></td>
        </tr>

        <tr>
            <td><form:label path="email">Email</form:label></td>
            <td><form:input path="email" /></td>
        </tr>    
        
        <tr>
            <td colspan="2">
                <input type="submit" value="Add Author"/>
            </td>
        </tr>

    </table>

</form:form>
