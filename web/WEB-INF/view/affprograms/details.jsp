<%@ include file="../common/taglibs.jsp" %>


<%//this file show details box of affiliate program %>
<c:if test="${status!=null}">
    <div style="color:red"><c:out value="${status}"/></div>
</c:if>

<c:choose>
    <c:when test="${program!=null}">

        <div class="verticalBrake1"></div>

        <table  class="affProgGroupTable">
            <thead>
                <tr style="background-color: #999999;">
                    <th class="affProgGroupTable-td-1">Program Name</td>
                    <th class="affProgGroupTable-td-2">Description</td>
                    <th class="affProgGroupTable-td-3">Link</td>
                    <th class="affProgGroupTable-td-4">User Name</td>
                    <th class="affProgGroupTable-td-5">Password</td>
                    <th class="affProgGroupTable-td-6">Refresh Data</td>

                </tr>
            </thead>                

            <tr>
                <td class="affTd" align="left" >
                    <c:out value="${program.name}" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${program.description}" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${program.affProgramLink}" />
                </td>                    
                <td class="affTd" align="left" >
                    <c:out value="${program.userName}" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${program.password}" />
                </td>
                <td class="affTd" align="left" >
                    <a href="${pageContext.request.contextPath}/mvc/billing/import/${program.id}">Refresh Button</a>
                </td>   
            </tr>   

        </table>                    
    </c:when>
    <c:otherwise>
        <div style="color:red">Specified program id is not assigned to you </div>
    </c:otherwise>
</c:choose>        










