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
    
    

<!--    <a href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/calculateAggregationData">Calculate Aggregation Data</a>
    <a href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/calcQueryStats">Calculate Search query rating</a>-->
    </table>
    <c:set var="totalStats" value="${statMap.get('Total') }"/>
    <c:set var="cmStats" value="${statMap.get('CurMonth') }"/>
    <c:set var="pmStats" value="${statMap.get('PrevMonth') }"/>
    <c:set var="dailyStats" value="${statMap.get('CurDay') }"/>
    

    <c:if test="${totalStats==null}">
         <c:set var="totalStats" value="${statMap.get('empty') }"/>
    </c:if>
    <c:if test="${pmStats==null}">
         <c:set var="pmStats" value="${statMap.get('empty') }"/>
    </c:if>
    <c:if test="${cmStats==null}">
         <c:set var="cmStats" value="${statMap.get('empty') }"/>
    </c:if>
    <c:if test="${dailyStats==null}">
         <c:set var="dailyStats" value="${statMap.get('empty') }"/>
    </c:if>
                
    <table class="dataTable">
        <tr>
            <th>Period</th>
            <th>Access Amount</th>
            <th>Purchase Amount</th>
            <th>Commision Sum</th>
            <th>Conversion Rate</th>
        </tr>
        <tr>
            <td>Total</td>
            <td><c:out value="${totalStats.accessAmount}"/></td>
            <td><c:out value="${totalStats.purchaseAmount}"/><span class="curency" style="float:right">RUB</span></td>
            <td>
		<fmt:formatNumber value="${totalStats.totalCommision}" maxFractionDigits="3" minFractionDigits="3" />
                <span class="curency" style="float:right">RUB</span>
	    </td>
            <td>
		<fmt:formatNumber value="${totalStats.conversionRate}" maxFractionDigits="3" minFractionDigits="3" />
                
	    </td>
        </tr>
        <tr>
            <td>Previous Month</td>
            <td><c:out value="${pmStats.accessAmount}"/></td>
            
            <td><c:out value="${pmStats.purchaseAmount}"/><span class="curency" style="float:right">RUB</span></td>
            <td>
		<fmt:formatNumber value="${pmStats.totalCommision}" maxFractionDigits="3" minFractionDigits="3" />
                <span class="curency" style="float:right">RUB</span>
	    </td>
            <td>
		<fmt:formatNumber value="${pmStats.conversionRate}" maxFractionDigits="3" minFractionDigits="3" />
                
	    </td>
        </tr>
        <tr>
            <td>Current Month</td>
            <td><c:out value="${cmStats.accessAmount}"/></td>
            <td><c:out value="${cmStats.purchaseAmount}"/><span class="curency" style="float:right">RUB</span></td>
            <td>
		<fmt:formatNumber value="${cmStats.totalCommision}" maxFractionDigits="3" minFractionDigits="3" />
                <span class="curency" style="float:right">RUB</span>
	    </td>
            <td>
		<fmt:formatNumber value="${cmStats.conversionRate}" maxFractionDigits="3" minFractionDigits="3" />
	    </td>
        </tr>
        <tr>
            <td>Today</td>
            <td><c:out value="${dailyStats.accessAmount}"/></td>
            <td><c:out value="${dailyStats.purchaseAmount}"/><span class="curency" style="float:right">RUB</span></td>
            <td>
		<fmt:formatNumber value="${dailyStats.totalCommision}" maxFractionDigits="3" minFractionDigits="3" />
                <span class="curency" style="float:right">RUB</span>
	    </td>
            <td>
		<fmt:formatNumber value="${dailyStats.conversionRate}" maxFractionDigits="3" minFractionDigits="3" />
	    </td>
        </tr>
    </table>



</div>
            
