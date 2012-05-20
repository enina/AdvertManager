<%@ include file="../common/taglibs.jsp" %>


<c:choose>
    <c:when test="${project!=null}">

        <div class="verticalBrake1">
            
        </div>

        <div>
            <a href="${pageContext.request.contextPath}/mvc/billing/delete/${project.id}">Delete project</a>
        </div>
        <table>
            <tr>
                <td class="billingDetailsTd" >
                    <c:out value="Project Name" />
                </td>
                <td class="billingDetailsTd">
                    <c:out value="${project.name}" />
                </td>
            </tr>    
            <tr>
                <td class="billingDetailsTd"  >
                    <c:out value="Project Base URL" />
                </td>
                <td class="billingDetailsTd"  >
                    <c:out value="${project.baseURL}" />
                </td>
            </tr>    
            <tr>
                <td class="billingDetailsTd"  >
                    <c:out value="Cookie Name" />
                </td>
                <td class="billingDetailsTd"  >
                    <c:out value="${project.cookieName}" />
                </td>
            </tr>    
            <tr>
                <td class="billingDetailsTd"  >
                    <c:out value="Home Page" />
                </td>
                <td class="billingDetailsTd"  >
                    <c:out value="${project.homePage}" />
                </td>
            </tr>    
            <tr>
                <td class="billingDetailsTd"  >
                    <c:out value="Validation selector" />
                </td>
                <td class="billingDetailsTd"  >
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
                    <table>
                        <thead>
                            <tr>
                                <th> Name </th>
                                <th> Data URL </th>
                                <th> Number of data pages</th>
                                <th> Data item name </th>
                                <th> Data item selector </th>
                            </tr>
                        </thead>

                        <tr>
                            <td class="billingDetailsTd">
                                <form:select id="dataSpecList" path="selectedDataSpec" onchange="dsSelected('${pageContext.request.contextPath}','${project.id}')">
                                    <form:option value="0" label="Select data spec"/>
                                    <form:options items="${project.dataSpecList}" itemValue="id"  itemLabel="name"/>
                                </form:select>
                            </td>
                            <td class="billingDetailsTd" id="ds.url"></td>
                            <td class="billingDetailsTd" id="ds.numPages"></td>
                            <td class="billingDetailsTd" id="si.name">
                                <select id="siList" onchange="siSelected('${pageContext.request.contextPath}')" disabled="true">
                                    <option value="0" selected="true">Select data item</option> />
                                </select>
                            </td>
                            <td class="billingDetailsTd" id="si.selector"></td>
                        </tr>
                    </table>
                </td>       
            </tr>
        </table>            
    </c:when>
    <c:otherwise>
        <div style="color:red">Invalid project identifier</div>
    </c:otherwise>
</c:choose>        
