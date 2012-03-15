<%-- 
    Document   : registration
    Created on : Jan 28, 2012, 11:28:49 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@include file="../common/taglibs.jsp" %>
<form:form  modelAttribute="product" method="post" action="add">
    <table>

        <tr>
            <td>
                <form:label path="authorId.id">Author</form:label>
                <form:select id="authors" path="authorId.id" onchange="productAuthorSelected('${pageContext.request.contextPath}')">
                    <form:option value="0" label="Define new author" />
                    <form:options items="${authors}"  itemValue="id" itemLabel="authorName" />
                </form:select>
            </td>        
            <td>
                Product Group:
                <form:select id="pGroups" path="productGroupId.id" onchange="productGroupSelected('${pageContext.request.contextPath}')">
                    <form:option value="0" label="Define new product group" />
                    <form:options items="${prdGroups}" itemValue="id" itemLabel="groupName" />
                </form:select>
            </td>        

        </tr>

        <tr>
            <td><form:label path="name">Product Name</form:label></td>
            <td><form:input path="name" /></td>
        </tr>

        <tr>
            <td><form:label path="description">Description</form:label></td>
            <td><form:input path="description" /></td>
        </tr>        

        <tr>
            <td><form:label path="price">Price</form:label></td>
            <td><form:input path="price" /></td>
        </tr>

        <tr>
            <td><form:label path="commision">Commission</form:label></td>
            <td><form:input path="commision" /></td>
        </tr>

        <tr>
            <td><form:label path="productLink">Link</form:label></td>
            <td><form:input path="productLink" /></td>
        </tr>

        <tr>
            <td><form:label path="redirectLink">Redirect Link</form:label></td>
            <td><form:input path="redirectLink" /></td>
        </tr>
        

    </table>

    <table  id="authorData" style="display:none">
        <tr>
            <td><form:label    path="authorId.authorName">Author Name</form:label></td>
            <td><form:input    path="authorId.authorName" /></td>
        </tr>
        <tr>
            <td><form:label    path="authorId.email">Author Email</form:label></td>
            <td><form:input    path="authorId.email" /></td>
        </tr>
    </table>            


    <table id="productGroupData" style="display:none">
        <tr>
            <td><form:label    path="productGroupId.groupName">Product Group Name</form:label></td>
            <td><form:input    path="productGroupId.groupName" /></td>
        </tr>
        <tr>
            <td><form:label    path="productGroupId.description">Product Group Description</form:label></td>
            <td><form:input    path="productGroupId.description" /></td>
        </tr>
    </table>

    <table>                
        <tr>
            <td colspan="2">
                <input type="submit" value="Add Product"/>
            </td>
        </tr>

    </table>  

</form:form>
