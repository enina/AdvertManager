
<%@ include file="../common/taglibs.jsp" %>

<table border="1" class="affTable">
    <c:if test="${data!=null}">
        <thead>
            <tr >
                <th>Program ID</th><th>Program Description</th><th>Link</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${data}" var="affProgram">
                <tr>
                    
                    <td class="affTd" align="left" >
                        <a href="${pageContext.request.contextPath}/mvc/affprograms/details/${affProgram.id}">${affProgram.name}</a>
                    </td> 
                    <td class="affTd" align="left" >
                        <c:out value="${affProgram.description}" />
                    </td>
                    <td class="affTd" align="left" >
                        <c:out value="${affProgram.price}" />
                    </td>
                    <td class="affTd" align="left" >
                        
                            <c:out value="${affProgram.affProgramLink}" />
                    
                    </td>                
                </tr>   
            </c:forEach>
        </tbody>            
    </c:if>

</table>



