<%-- 
    Document   : overview
    Created on : May 12, 2012, 3:44:05 PM
    Author     : Nina Eidelshtein and Misha Lebedev

    this document present overview data of selected program 
    usage: affprogram -> overview 

    data presented:
    table with agregation:

    descriprion |partners|accesses|orderspayd orders|access convert rate|order convert rate|
    ----------------------------------------------------------------------------------------
    today       |        |        |                 |                   |                  |
    past week   |        |        |                 |                   |                  |
    past month  |        |        |                 |                   |                  |
    all times   |        |        |                 |                   |                  |

    program back office data:
    
    description |income from partners|total income|costs|net income|paid|remains to be paid
    ----------------------------------------------------------------------------------------
    today       |                    |            |     |          |    |                  |
    past week   |                    |            |     |          |    |                  |
    past month  |                    |            |     |          |    |                  |
    all times   |                    |            |     |          |    |                  |
--%>
<%@ include file="../common/taglibs.jsp" %>

<c:if test="${status!=null}">
    <div style="color:red"><c:out value="${status}"/></div>
</c:if>

<div id="" >
    
    <tiles:insertAttribute name="programspec" />
    <table>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/calculateAggregationData">Calculate Aggregation Data</a>
            </td>    
        </tr>    
    </table>    
    <c:if test="${poTAD!=null}">
        <table>
            <tr>
                <td>Period</td>
                <td>Access Amount</td>
                <td>Purchase Amount</td>
                <td>Commision Sum</td>
                <td>Rate</td>
            </tr>
            <tr>
                <td>Total</td>
                <td><c:out value="${poTAD.accessAmount}"/></td>
                <td><c:out value="${poTAD.purchaseAmount}"/></td>
                <td><c:out value="${poTAD.totalCommision}"/></td>
                <td><c:out value="${poTAD.conversionRate}"/></td>
            </tr>
            <c:if test="${poPMAD!=null}">
                <tr>
                    <td>Previous Month</td>
                    <td><c:out value="${poPMAD.accessAmount}"/></td>
                    <td><c:out value="${poPMAD.purchaseAmount}"/></td>
                    <td><c:out value="${poPMAD.totalCommision}"/></td>
                    <td><c:out value="${poPMAD.conversionRate}"/></td>
                </tr>
            </c:if>
            <c:if test="${poCMAD!=null}">
                <tr>
                    <td>Current Month</td>
                    <td><c:out value="${poCMAD.accessAmount}"/></td>
                    <td><c:out value="${poCMAD.purchaseAmount}"/></td>
                    <td><c:out value="${poCMAD.totalCommision}"/></td>
                    <td><c:out value="${poCMAD.conversionRate}"/></td>
                </tr>
            </c:if>
            <c:if test="${poDAD!=null}">
                <tr>
                    <td>Today</td>
                    <td><c:out value="${poDAD.accessAmount}"/></td>
                    <td><c:out value="${poDAD.purchaseAmount}"/></td>
                    <td><c:out value="${poDAD.totalCommision}"/></td>
                    <td><c:out value="${poDAD.conversionRate}"/></td>
                </tr>
            </c:if>
        </table>
    </c:if>

    <table>
        
        <thead id="analiticsAgregationTable" class="dataTable">
        <tr>
            <th> description </th><th>partners</th><th>accesses</th><th>payed orders</th><th>access convert rate</th><th>order convert rate</th>
        </tr>
        </thead>

        <tbody>
        <tr>
            <td>today</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td>
        </tr> 
        <tr>
            <td>past week</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td>
        </tr> 
        <tr>
            <td>past month</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td>
        </tr> 
        <tr>
            <td>all times</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td>
        </tr> 
        
        </tbody>
             
    </table>
    
    
    <table>
        <thead id="prograBbackOfficeData" class="dataTable">
        <tr>                                                                                 
            <th> description </th><th>income from partners</th><th>total income</th><th>costs</th><th>net income</th><th>paid</th><th>remains to be paid</th>
        </tr>
        </thead>

        <tbody>
        <tr>
            <td>today</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td>
        </tr> 
        <tr>
            <td>past week</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td>
        </tr> 
        <tr>
            <td>past month</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td>
        </tr> 
        <tr>
            <td>all times</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td><td>data</td>
        </tr> 
        
        </tbody>
             
    </table>
    
        
</div>