/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//this file hold javascript functionality responsible to bring silently bring 
//portions of access pages


//var accessCtxPath = "${pageContext.request.contextPath}/mvc/affprograms/${program.id}";

function getPageData(path){
    

    //get data thru ajax
    $.getJSON(path,function(data){
        
        
        var container = $('#accesses');
        
        if(data == null || data.pageCtrl.totalPages < 1){
            $('#acesses').append("No data found");
            return;
        }
        
        var items = 5;
        var rv = "";
        var pageCtrl = data.pageCtrl;
        accessCtxPath += "/items/" + items +"/accessPage/";
        
        if( pageCtrl.currentPage != 1 ){
            
            container.append( $("<a>").click(getPageData(accessCtxPath + "/1")).text("First") );
            container.append( $("<a>").click(getPageData(accessCtxPath + pageCtrl.prevPage)).text("Previous") );
        }
        
        
        var table = $("<table>");
        var tr = $("<tr>");
    
        tr.append($("<th>").text("Time"));
        tr.append($("<th>").text("IP"));  
        tr.append($("<th>").text("Referrer"));  
        tr.append($("<th>").text("Target"));  
   
        table.append(tr);
        
        var td;
        for(var i in pageCtrl.items){
            
            tr = $("<tr>");
            tr.append( $("<td>").text(pageCtrl.items[i].accessTime) );
            tr.append( $("<td>").text(pageCtrl.items[i].ipAddress) );
            tr.append( $("<td>").text(pageCtrl.items[i].location) );
            tr.append( $("<td>").text(pageCtrl.items[i].accessTime) );
            
            
        }
        
        
        
    });
    
    return false;  
}


//<c:when test="${accessPage!=null && accessPage.items != null && accessPage.pageCtrl.totalPages > 0 }">
//
//        <div>
//            <c:if test="${accessPage.pageCtrl.currentPage != 1}">
//                <a  href="" onclick="getPageData('${ctxPath}/1')">First</a>
//                <a  href="" onclick="getPageData('${ctxPath}/${accessPage.pageCtrl.prevPage}')">Previous</a>
//            </c:if>
//            Page <c:out value="${accessPage.pageCtrl.currentPage}"/> of <c:out value="${accessPage.pageCtrl.totalPages}"/>
//            <c:if test="${accessPage.pageCtrl.currentPage != accessPage.pageCtrl.totalPages }">
//                <a  href="" onclick="getPageData('${ctxPath}/${accessPage.pageCtrl.nextPage}')">Next </a>
//                <a  href="" onclick="getPageData('${ctxPath}/${accessPage.pageCtrl.totalPages}')">Last</a>
//            </c:if>
//        </div>
//<table >
//    
//   
//        
//        <thead>
//            <tr>
//                <th> Time </th>
//                <th> IP </th>
//                <th> Referrer </th>
//                <th> Target </th>
//            </tr>
//        </thead>
//        
//        <%-- prepare links for priv/next page --%>

//        
//        <%-- print all items of current page --%>
//        <c:forEach items="${accessPage.items}" var="access">
//            <tr>
//                <td   >
//                    <c:out value="${access.timeAsString}" />
//                </td>
//                <td  >
//                    <c:out value="${access.ipAddress}"/>
//                </td>
//                <td  >
//                    <a href="<c:out value="${access.location}" />">
//                        <c:out value="${access.location}" />
//                    </a>
//                </td>
//                <td  >
//                    <c:out value="${access.url}" />
//                </td>
//            </tr>   
//        </c:forEach>
//         
//</table>
//        
//</c:when>
//<c:otherwise>
//    No data yet
//</c:otherwise>
//    
//</c:choose>