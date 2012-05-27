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
                <th>Period</td>
                <th>Access Amount</td>
                <th>Purchase Amount</td>
                <th>Purchase Sum</td>
                <th>Rate</td>
            </tr>
            <tr>
                <td>Total</td>
                <td><c:out value="${poTAD.accessAmount}"/></td>
                <td><c:out value="${poTAD.purchaseAmount}"/></td>
                <td><c:out value="${poTAD.totalSum}"/></td>
                <td><c:out value="${poTAD.conversionRate}"/></td>
            </tr>
            <c:if test="${poPMAD!=null}">
                <tr>
                    <td>Previous Month</td>
                    <td><c:out value="${poPMAD.accessAmount}"/></td>
                    <td><c:out value="${poPMAD.purchaseAmount}"/></td>
                    <td><c:out value="${poPMAD.totalSum}"/></td>
                    <td><c:out value="${poPMAD.conversionRate}"/></td>
                </tr>
            </c:if>
            <c:if test="${poCMAD!=null}">
                <tr>
                    <td>Current Month</td>
                    <td><c:out value="${poCMAD.accessAmount}"/></td>
                    <td><c:out value="${poCMAD.purchaseAmount}"/></td>
                    <td><c:out value="${poCMAD.totalSum}"/></td>
                    <td><c:out value="${poCMAD.conversionRate}"/></td>
                </tr>
            </c:if>
            <c:if test="${poDAD!=null}">
                <tr>
                    <td>Today</td>
                    <td><c:out value="${poDAD.accessAmount}"/></td>
                    <td><c:out value="${poDAD.purchaseAmount}"/></td>
                    <td><c:out value="${poDAD.totalSum}"/></td>
                    <td><c:out value="${poDAD.conversionRate}"/></td>
                </tr>
            </c:if>
        </table>
    </c:if>

</div>