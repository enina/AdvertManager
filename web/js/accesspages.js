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
        container.empty();
        
        //prepare page object ( add methods )
        accessPage.getCurentPage = getCurentPage;
        accessPage.getNextPage = getNextPage;
        accessPage.getPrevPage = getPrevPage;
        accessPage.getTotalPages = getTotalPages;
        
        
        //test for emptiness
        if(accessPage == null || accessPage.getTotalPages() < 1){
            container.append("No access page found");
            return;
        }
        
        //items per page
        var items = 10;
        
        //hod path
        var basePath = accessCtxPath;
        basePath += "/items/" + items +"/accessPage/";
        
        //hold page navigation div
        var navDiv = $("<div>");
        navDiv.attr("id","accessNavBar");
        
        //prepare text inside the div text that describe curent page position
        var text = $("<span>").append( "Page " + accessPage.getCurentPage() + " of " + accessPage.getTotalPages()); 

        //create ul with navigation items
        var navUl = $("<ul>");
        //create list items
        var first = $("<li>First</li>").click(function(){getPageAccess(basePath + "1")});
        var previous = $("<li>Previous</li>").click(function(){getPageAccess( basePath +  accessPage.getPrevPage() )});
        var next = $("<li>Next</li>").click(function(){getPageAccess(basePath + accessPage.getNextPage() )});
        var last = $("<li>Last</li>").click( function(){getPageAccess(basePath + accessPage.getTotalPages())});
        
        first.addClass("navlink").attr({id:"accesNavFirst"});
        previous.addClass("navlink").attr({id:"accesNavPrev"});
        next.addClass("navlink").attr({id:"accesNavNext"});
        last.addClass("navlink").attr({id:"accesNavLast"});
        
        //add created items to nav ul
        navUl.append( first );
        navUl.append( previous );
        navUl.append( next );
        navUl.append( last ); 
        
        //set what nav links hiden now
        if( accessPage.getCurentPage() <= 1 ){
            
            first.addClass( "hidden" );
            previous.addClass( "hidden" );
        }
        if (accessPage.getCurentPage() >= accessPage.getTotalPages()){

            next.addClass( "hidden" );
            last.addClass( "hidden" );
        }
        
        //prepare table of content
        var table = $("<table>");
        var tr = $("<tr>");
    
        tr.append($("<th>").append("Time"));
        tr.append($("<th>").append("IP"));  
        tr.append($("<th>").append("Referrer"));
        tr.append($("<th>").append("Query"));  
        tr.append($("<th>").append("Target"));  
   
        table.append(tr);
        
        //insert data to table
        for(var i =0; i < accessPage.items.length ;i++){
            
            var item = accessPage.items[i];
            tr = $("<tr>");
            tr.append( $("<td>").append(item.accessTime) );
            tr.append( $("<td>").append(item.ipAddress) );
            
            var a=$("<a>");
            a.attr("href",item.location);
            if (item.sourceDomain)
                a.append(item.sourceDomain.accessSourceDomain);
            tr.append( $("<td>").append(a) );
            tr.append( $("<td>").append(item.query) ); 
            tr.append( $("<td>").append(item.url) );
            
            table.append(tr);
            
        }
        
        navDiv.append( text );
        navDiv.append( navUl );
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