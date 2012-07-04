  

function showAccess(element,url){
      //var data = "";
      var data =  $(element).children("table");
      if(data.length > 0){
          data.toggle();
          return;
      }
      
      var curPo = $(element);
      
     $.getJSON(url,function(accesses){
         
        // alert(accesses);
         var table = $("<table>");
         table.attr("class", "poAccessTable");
         var trHeader =   $("<tr>");

         var thTime =   $("<th>").append("Date & Time");
         var thflag =   $("<th>");
         var thReferrer =   $("<th>").append("Referrer");
         var thQuery =   $("<th>").append("Query");
         var thTarget =   $("<th>").append("Target");
         
        trHeader.append(thTime,thflag,thReferrer,thQuery,thTarget);
        table.append(trHeader);
        
        for(var i =0; i < accesses.length; i++){
            
            var tr = $("<tr>");
            
            var trTime = $("<td>").append(accesses[i].accessTime);
            var trFlag = $("<td>").append(accesses[i].countryCode);
            var trReferrer = $("<td>");
            if(accesses[i].refererURL){
               var sourceLink = $("<a>").append(accesses[i].sourceDomain.accessSourceDomain); 
               sourceLink.attr("href",accesses[i].refererURL );
               sourceLink.attr("target","_blank" );
               trReferrer.append(sourceLink);
            }
            var trQuery = $("<td>").append(accesses[i].query);
            var trTarget = $("<td>").append(accesses[i].targetURL);
            
            tr.append(trTime,trFlag,trReferrer,trQuery,trTarget);
            
            table.append(tr);
        }
        curPo.append(table);
//        table.css({"position":"absolut","top":"100%","background-color":"white"});

     });

    }
    
    