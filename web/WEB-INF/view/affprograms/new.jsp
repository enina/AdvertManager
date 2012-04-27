<%-- 
    Document   : registration
    Created on : Jan 28, 2012, 11:28:49 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@include file="../common/taglibs.jsp" %>
<form:form  modelAttribute="affprogram" method="post" action="add" accept-charset="UTF-8">
    <table>

        <tr>
     
            <td>
                Program Group:
                <form:select id="apGroups" path="affProgramGroup.id" onchange="apgSelected('${pageContext.request.contextPath}')">
                    <form:option value="0" label="Define new program group" />
                    <form:options items="${apGroups}" itemValue="id" itemLabel="groupName" />
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
            <td><form:label path="affProgramLink">Link</form:label></td>
            <td><form:input path="affProgramLink" /></td>
        </tr>
        <tr>
            <td><form:label path="userName">User Name</form:label></td>
            <td><form:input path="userName" /></td>
        </tr>
        <tr>
            <td><form:label path="password">Password</form:label></td>
            <td><form:input path="password" /></td>
        </tr>
        <tr>
            <td><form:label path="redirectLink">Redirect Link</form:label></td>
            <td><form:input path="redirectLink" /></td>
        </tr>
        

    </table>

    


    <table id="apGroupData" style="display:none">
        <tr>
            <td><form:label    path="affProgramGroup.groupName">Group Name</form:label></td>
            <td><form:input    path="affProgramGroup.groupName" /></td>
        </tr>
        <tr>
            <td><form:label    path="affProgramGroup.description">Group Description</form:label></td>
            <td><form:input    path="affProgramGroup.description" /></td>
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
