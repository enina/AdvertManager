<%-- 
    Document   : registration
    Created on : Jan 28, 2012, 11:28:49 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@include file="../common/taglibs.jsp" %>
<form:form  modelAttribute="billingSpec" method="post" action="add" enctype="multipart/form-data">
    <table>
        <tr>
            <td><form:label path="specFile">Please select a billing specification file for upload</form:label></td>
            <td><input type="file" name="specFile" /></td>
        </tr>

        <tr>
            <td colspan="2">
                <input type="submit" value="upload"/>
            </td>
        </tr>
    </table>  

</form:form>
