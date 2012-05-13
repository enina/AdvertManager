<%@ include file="../common/taglibs.jsp" %>


<c:choose>
    <c:when test="${project!=null}">

        <div class="verticalBrake1">
            
        </div>

        <div>
            <a href="${pageContext.request.contextPath}/mvc/billing/delete/${project.id}">Delete project</a>
        </div>
        <table  class="affProgGroupTable">
            <tr>
                <td class="affTd" align="left" >
                    <c:out value="Project Name" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${project.name}" />
                </td>
            </tr>    
            <tr>
                <td class="affTd" align="left" >
                    <c:out value="Project Base URL" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${project.baseURL}" />
                </td>
            </tr>    
            <tr>
                <td class="affTd" align="left" >
                    <c:out value="Cookie Name" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${project.cookieName}" />
                </td>
            </tr>    
            <tr>
                <td class="affTd" align="left" >
                    <c:out value="Home Page" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${project.homePage}" />
                </td>
            </tr>    
            <tr>
                <td class="affTd" align="left" >
                    <c:out value="Validation selector" />
                </td>
                <td class="affTd" align="left" >
                    <c:out value="${project.selector}" />
                </td>
            </tr>    

            <tr>
                <td  colspan="2">
                    Data items specifications
                </td>
            </tr>

            <tr>
                <td  colspan="2">
                <table  class="affProgGroupTable">
                    <thead>
                        <tr>
                            <th> Name </th>
                            <th> Data URL </th>
                            <th> Data pages number </th>
                            <th> Data root selector </th>
                            <th> Data row selector </th>
                        </tr>
                    </thead>

                    <c:forEach items="${project.dataSpecList}" var="dataSpec">
                        <tr>
                            <td class="affTd" >
                                <c:out value="${dataSpec.name}"/>
                            </td>
                            <td class="affTd" style="width:150px" >
                                <c:out value="${dataSpec.dataURL}" />
                            </td>
                            <td class="affTd" >
                                <c:out value="${dataSpec.numPages}" />
                            </td>
                            <td class="affTd" >
                                <c:out value="${dataSpec.selector}" />
                            </td>
                            <td class="affTd" >
                                <c:out value="${dataSpec.listEntrySelector}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="affTd" colspan="5"> Selectable Items</td>
                        </tr>

                        <tr>
                            <td class="affTd" colspan="5">
                                <table  class="affProgGroupTable">
                                    <thead>
                                        <tr>
                                            <th> Item Name </th>
                                            <th> Item Selector </th>
                                        </tr>
                                    </thead>
                                    <c:forEach items="${dataSpec.dataItems}" var="si">
                                        <tr>
                                            <td class="affTd" style="width:150px">
                                                <c:out value="${si.name}"/>
                                            </td>
                                            <td class="affTd">
                                                <c:out value="${si.selector}" />
                                            </td>
                                        </tr>
                                    </c:forEach>     
                                 </table>       
                            </td>
                        </tr>
                    </c:forEach> 
                </table>
             </td>       
            </tr>
        </table>            
    </c:when>
    <c:otherwise>
        <div style="color:red">Invalid project identifier</div>
    </c:otherwise>
</c:choose>        










