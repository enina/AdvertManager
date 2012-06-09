

//this file hold javascript functionality responsible to bring silently 
//portions of access pages


//this file functions rely on follow lybrary and predefined variables:
//1.jquery
//2.jquery.cookie (plugin) https://github.com/carhartl/jquery-cookie
//3 hard coded context path to image directory(var imgCtxPath )


function getPageAccess(accessCtxPath,curentPage){
    

      
    window.basePath = accessCtxPath ;//;
      
    path = getBasePath() + curentPage;
    
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
       
        //hold imgdirectory context path
        var imgCtxPath = "http://" + window.location.host +"/AdvertManager/images/";
        
        //hold base path
        
        //hold page navigation div
        var navDiv = $("<div>");
        navDiv.attr("id","accessNavBar");
        
        
        var pageSize = $("<span>");
        pageSize.attr({id:"pageSize"});
        pageSize.append("Page size");
        //prepare selection of asseccessPerPage
        var pageSizeSelect = $("<select>");
        pageSizeSelect.append( $("<option>").val(10).append(10) );
        pageSizeSelect.append( $("<option>").val(20).append(20) );
        pageSizeSelect.append( $("<option>").val(30).append(30) );
        
        pageSizeSelect.change(function(){
            $.cookie("asseccessPerPage",$(this).val());
            getPageAccess(window.basePath , "1");
        });
        
        //set current selected page size
        pageSizeSelect.val($.cookie("asseccessPerPage"));
        
        pageSize.append(pageSizeSelect);
        
        //prepare text inside the div text that describe curent page position
        var text = $("<span>").append( "Page " + accessPage.getCurentPage() + " of " + accessPage.getTotalPages()); 

        //create ul with navigation items
        var navUl = $("<ul>");
        //create list items
        var first = $("<li>First</li>").click(function(){getPageAccess(window.basePath , "1")});
        var previous = $("<li>Previous</li>").click(function(){getPageAccess( window.basePath ,  accessPage.getPrevPage() )});
        var next = $("<li>Next</li>").click(function(){getPageAccess(window.basePath,  accessPage.getNextPage() )});
        var last = $("<li>Last</li>").click( function(){getPageAccess(window.basePath , accessPage.getTotalPages())});
        
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
            
            first.addClass("notActive"); //"hidden" );
            previous.addClass("notActive"); //"hidden" );
        }
        if (accessPage.getCurentPage() >= accessPage.getTotalPages()){

            next.addClass("notActive"); //"hidden" );
            last.addClass("notActive"); //"hidden" );
        }
        
        //prepare table of content
        var table = $("<table>");
        var tr = $("<tr>");
    
        tr.append($("<th>").append("Time"));
        tr.append($("<th>"));
        tr.append($("<th>").append("Referrer"));
        tr.append($("<th>").append("Query"));  
        tr.append($("<th>").append("Target"));  
   
        table.append(tr);
        

        //insert data to table
        for(var i =0; i < accessPage.items.length ;i++){
            
            var item = accessPage.items[i];
            tr = $("<tr>");
            tr.append( $("<td>").append(item.accessTime) );

            
            var img = "";
            var imgSrc = "";
            if(item.countryCode){
                imgSrc = imgCtxPath + "countryFlag/"+ item.countryCode.toLowerCase() + ".png";
                img = $("<img>").attr("src", imgSrc );
            }
            
            tr.append( $("<td>").append( img ));
           

            var a=$("<a>");
            a.attr("href",item.refererURL);
            if (item.sourceDomain)
                a.append(item.sourceDomain.accessSourceDomain);
            tr.append( $("<td>").append(a) );
            tr.append( $("<td>").append(item.query) ); 
            tr.append( $("<td>").append(item.targetURL) );
            
            table.append(tr);
            
        }
       
        navDiv.append( text );
        navDiv.append( navUl );
        navDiv.append( pageSize );
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

function getBasePath(){
    
      if(!$.cookie("asseccessPerPage")){
            $.cookie("asseccessPerPage",10);
      }
      
    return window.basePath + "/items/" + $.cookie("asseccessPerPage") +"/accessPage/";
}