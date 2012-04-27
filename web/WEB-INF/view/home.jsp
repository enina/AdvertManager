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
    <table border="1" class="affTable">
        <c:forEach items="${data.apgCollection}" var="apg">
            <tr><td>
                <div>Program Group:<c:out value="${apg.groupName}" /></div>
                <table border="1" class="affTable">
                    <thead>
                        <tr style="background-color: #999999;">
                            <th>Program ID</td>
                            <td>Description</td>
                            <td>Link</td>
                            <td>User Name</td>
                            <td>Password</td>
                        </tr>
                    </thead>                
                    <c:forEach items="${apg.programCollection}" var="program">
                        <tr>
                            <td class="affTd" align="left" >
                                <c:out value="${program.id}" />
                            </td>
                 
                            </td>                                                
                            <td class="affTd" align="left" >
                                <c:out value="${program.description}" />
                            </td>
                            <td class="affTd" align="left" >
                                <c:out value="${program.price}" />
                            </td>
                            <td class="affTd" align="left" >
                                <c:out value="${program.programLink}" />
                            </td>
                        </tr>   
                    </c:forEach>
                </table>                    
            </td></tr>
        </c:forEach>
    </table>
</c:if>        

<!--
<div id="ext">
    
</div>
-->









