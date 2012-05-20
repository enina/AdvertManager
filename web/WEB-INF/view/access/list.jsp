

<% 
    /* AdvertManager\web\WEB-INF\view\access\list.jsp
     * this file generate presentation of accesses for given affiliate pragram
     * 
    */
%> 


<%@ include file="../common/taglibs.jsp" %>
<script>
    var accessCtxPath = "${pageContext.request.contextPath}/mvc/affprograms/${program.id}";
    
    $(function(){
       
        getPageAccess("${pageContext.request.contextPath}/mvc/affprograms/${program.id}/items/5/accessPage/1")
    });
</script>



