<%-- 
    Document   : registration
    Created on : Jan 28, 2012, 11:28:49 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@include file="../common/taglibs.jsp" %>
<form:form  modelAttribute="affprogram" method="post" action="add">
    <table>

        <tr>
     
            <td>
                Program Group:
                <form:select id="apGroups" path="apGroups.id" onchange="apGroupSelected('${pageContext.request.contextPath}')">
                    <form:option value="0" label="Define new program group" />
                    <form:options items="${prdGroups}" itemValue="id" itemLabel="groupName" />
                </form:select>
            </td>        

        </tr>

        <tr>
            <td><form:label path="name">Program Name</form:label></td>
            <td><form:input path="name" /></td>
        </tr>

        <tr>
            <td><form:label path="description">Description</form:label></td>
            <td><form:input path="description" /></td>
        </tr>        

        <tr>
            <td><form:label path="programLink">Link</form:label></td>
            <td><form:input path="programLink" /></td>
        </tr>

        <tr>
            <td><form:label path="redirectLink">Redirect Link</form:label></td>
            <td><form:input path="redirectLink" /></td>
        </tr>
        

    </table>

    


    <table id="apGroupData" style="display:none">
        <tr>
            <td><form:label    path="apGroupId.groupName">Group Name</form:label></td>
            <td><form:input    path="apGroupId.groupName" /></td>
        </tr>
        <tr>
            <td><form:label    path="apGroupId.description">Group Description</form:label></td>
            <td><form:input    path="apGroupId.description" /></td>
        </tr>
    </table>

    <table>                
        <tr>
            <td colspan="2">
                <input type="submit" value="Add Program"/>
            </td>
        </tr>

    </table>  

</form:form>
