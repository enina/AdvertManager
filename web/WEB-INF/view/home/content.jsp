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
                            <th class="affProgGroupTable-td-5">Conversion Rate </th>

                        </tr>
                    </thead>   

                    <tbody>
                    <c:forEach items="${apg.programCollection}" var="program">
                        <c:set var="fbs" value="${affStatisticsMap.get(program) }"/>
                  
                            <tr class="progOverview" onclick='window.location.href = "${pageContext.request.contextPath}/mvc/affprograms/details/${program.id}";'>
                           
                                                                                                                           
                                <td><c:out value="${program.name}" /><a class="deleteProgBut" href="${pageContext.request.contextPath}/mvc/affprograms/${program.id}/delete" style="float: right;"><img  src="${pageContext.request.contextPath}/images/deleteProg.png" alt="Delete Program" title="Delete Program"/></a></td>
                                <td ><c:out value="${fbs.accessAmount}"/></td>              
                            <td ><c:out value="${fbs.purchaseAmount}"/><span class="curency" style="float:right">RUB</span></td>                    
                            <td ><c:out value="${fbs.totalCommision}"/><span class="curency" style="float:right">RUB</span></td>
			    <td>
				<fmt:formatNumber value="${fbs.conversionRate}" maxFractionDigits="3" minFractionDigits="3" />
			    </td>			    
                            
                        </tr>   
                       
                    
                </c:forEach>
                    </tbody>
                </table>
            </div>

    </c:forEach>


</c:if>  
    
    <script type="text/javascript">
        $( function(){ $(".deleteProgBut").click(function(){
                            var callElement = this;
                            var div = $("<div>");
                            div.attr("class", "confirmBox");
                            var msg = $("<span>").append("Delete This Program ?");
                            var okBut = $("<button>").attr({"type":"button","value":"OK","class":"deleteOk"}).click(function(){
                                
                                var deleteLink = $(callElement).attr("href");
                                $.post(deleteLink, function(data) {
                                //notify that program deleted here
                                });
                                $(".confirmBox").remove();
                                $(callElement).closest("tr").remove();
                            }).append("OK");

                            var cancelBut = $("<button>").attr({"type":"button","value":"Cancel","class":"deleteCancel"}).click(function(){

                                $(".confirmBox").remove();
                                
                            }).append("Cancel");
                        div.append(msg,okBut,cancelBut);
                        $("body").append(div);

                            return false;
                        }
                     )
        });
    </script>












