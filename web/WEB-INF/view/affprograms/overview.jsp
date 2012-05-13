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

<div id="" >
    
    <tiles:insertAttribute name="programspec" />
    
    
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