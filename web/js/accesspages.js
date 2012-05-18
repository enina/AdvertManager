/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//this file hold javascript functionality responsible to bring silently 
//portions of access pages



function getPageAccess(path){
    

    //get accessPage thru ajax
    $.getJSON(path,function(accessPage){
        
        
        var container = $('#accesses');
        
        accessPage.getCurentPage = getCurentPage;
        accessPage.getNextPage = getNextPage;
        accessPage.getPrevPage = getPrevPage;
        accessPage.getTotalPages = getTotalPages;
        
        container.empty();
        
        if(accessPage == null || accessPage.getTotalPages() < 1){
            container.append("No access page found");
            return;
        }
        
        var items = 10;
        
        var basePath = accessCtxPath;
        basePath += "/items/" + items +"/accessPage/";
        
        var navDiv = $("<div>");
        
        if( accessPage.getCurentPage() > 1 ){
            
            var first = $("<a>First</a>").click(function(){getPageAccess(basePath + "1")});
            var previous = $("<a>Previous</a>").click(function(){ getPageAccess( basePath +  accessPage.getPrevPage() )});
            first.attr("class","navlink");
            previous.attr("class","navlink");
            
            navDiv.append( first );
            navDiv.append(" ");
            navDiv.append( previous );
        }
        
        navDiv.append( " Page " + accessPage.getCurentPage() + " of " + accessPage.getTotalPages() + " ");
        

        if (accessPage.getCurentPage() < accessPage.getTotalPages()){
            
            var next = $("<a>Next</a>").click(function(){getPageAccess(basePath + accessPage.getNextPage() )});
            var lasst = $("<a>Last</a>").click( function(){getPageAccess(basePath + accessPage.getTotalPages())});
            next.attr("class","navlink");
            lasst.attr("class","navlink");
            navDiv.append( next );
            navDiv.append(" ");
            navDiv.append( lasst );
            
        }
        
        
        var table = $("<table>");
        var tr = $("<tr>");
    
        tr.append($("<th>").append("Time"));
        tr.append($("<th>").append("IP"));  
        tr.append($("<th>").append("Referrer"));
        tr.append($("<th>").append("Query"));  
        tr.append($("<th>").append("Target"));  
   
        table.append(tr);
        
        for(var i =0; i < accessPage.items.length ;i++){
            
            var item = accessPage.items[i];
            tr = $("<tr>");
            tr.append( $("<td>").append(item.accessTime) );
            tr.append( $("<td>").append(item.ipAddress) );
            tr.append( $("<td>").append(item.location) );
            tr.append( $("<td>") ); //Query accessPage
            tr.append( $("<td>").append(item.url) );
            
            table.append(tr);
            
        }
        
        container.append(navDiv);
        container.append(table);
        
        
    });
    
    return false;  
}

//================= getCurentPage ======================
function getCurentPage(){
    
    return this.pageCtrl.pageIdx +1;
    
}
//================= getPrevPage ======================
function getPrevPage(){
    
    
    
    var prevPage = this.pageCtrl.pageIdx;
    
    if ( ( prevPage) > 1){
         return prevPage;
    }
    return 1;
}
//================= getNextPage ======================
function getNextPage(){
   
    var nextPage = this.getCurentPage() +1;
    
    if(this.pageCtrl.totalPages < nextPage ){
        return this.pageCtrl.totalPages;
    }
    return nextPage;
}
//================= getTotalPages() =====================
function getTotalPages(){
    
    return this.pageCtrl.totalPages;
    
}