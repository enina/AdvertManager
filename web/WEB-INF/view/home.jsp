<%@ include file="common/taglibs.jsp" %>
<!--
<h1>
   <fmt:message key="welcome.title"/>
</h1>
-->

<c:if test="${status!=null}">
    <div style="color:red"><c:out value="${status}"/></div>
</c:if>
    
<c:if test="${data!=null}">

    <% //go over all programm groups and show thems %>
    <c:forEach items="${data.apgCollection}" var="apg">



            <div class="programGroupDev">

                <% //display program group name (apg = "affiliate program group") %>
                <span class="groupName">Program Group:<c:out value="${apg.groupName}" /></span>

                <table  class="affProgGroupTable">
                    <thead>
                        <tr style="background-color: #999999;">
                            <th class="affProgGroupTable-td-1">Program Name</th>
                            <th class="affProgGroupTable-td-2">Description</th>
                            <th class="affProgGroupTable-td-3">Link</th>
                            <th class="affProgGroupTable-td-4">User Name</th>
                            <th class="affProgGroupTable-td-5">Password</th>

                        </tr>
                    </thead>   

                    <tbody>
                    <c:forEach items="${apg.programCollection}" var="program">
                    <tr>
                        <td >
                            <a href="${pageContext.request.contextPath}/mvc/affprograms/details/${program.id}" >
                                <c:out value="${program.name}" />
                            </a>
                        </td>

                        <td ><c:out value="${program.description}"/></td>
                        <td ><c:out value="${program.affProgramLink}"/></td>                    
                        <td ><c:out value="${program.userName}"/></td>
                        <td ><c:out value="${program.password}" /></td>
                    </tr>   
                </c:forEach>
                    </tbody>
                </table>
            </div>

    </c:forEach>


</c:if>  


<!--
<div id="ext">
    
</div>
-->









