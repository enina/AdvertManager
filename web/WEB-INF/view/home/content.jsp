<%@ include file="../common/taglibs.jsp" %>


<c:if test="${status!=null}">
    <div style="color:red"><c:out value="${status}"/></div>
</c:if>
    
<c:if test="${data !=null && affStatisticsMap !=null}">
    
    <% //go over all programm groups and show thems %>
    <c:forEach items="${data.apgCollection}" var="apg">


        
            <div class="programGroup">

                <% //display program group name (apg = "affiliate program group") %>
                <span class="groupName">Program Group:<span><c:out value="${apg.groupName}" /></span></span>

                <table  class="affProgGroupTable dataTable">
                    <thead>
                        <tr style="background-color: #999999;">
                            <th class="affProgGroupTable-td-1">Program Name</th>
                            <th class="affProgGroupTable-td-2">Tot Accesses</th>
                            <th class="affProgGroupTable-td-3">Tot Purchase</th>
                            <th class="affProgGroupTable-td-4">Tot Commission</th>
                            <th class="affProgGroupTable-td-5">Conversion </th>

                        </tr>
                    </thead>   

                    <tbody>
                    <c:forEach items="${apg.programCollection}" var="program">
                        <c:set var="fbs" value="${affStatisticsMap.get(program) }"/>
                   <!--     <a href="${pageContext.request.contextPath}/mvc/affprograms/details/${program.id}" >  </a>  -->
                            <tr class="progOverview" onclick='window.location.href = "${pageContext.request.contextPath}/mvc/affprograms/details/${program.id}";'>
                           

                            <td><c:out value="${program.name}" /></td>
                            <td ><c:out value="${fbs.accessAmount}"/></td>   
                            <td ><c:out value="${fbs.purchaseAmount}"/></td>                    
                            <td ><c:out value="${fbs.totalCommision}"/></td>
                            <td ><c:out value="${fbs.purchaseAmount / fbs.accessAmount}"/></td>
                        </tr>   
                       
                    
                </c:forEach>
                    </tbody>
                </table>
            </div>

    </c:forEach>


</c:if>  












