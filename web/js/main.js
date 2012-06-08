


$( ( function(){ 
    
    //login button on enter key handler
    $('input').keyup(function(e){ 
        if(e.which == 13){ 
            // submit via ajax or 
            $('form').submit();  
        }
    });
    
    //email input field code
    $("#emailInput input").blur( function(){
            if(this.value == ""){
                $(this).css("background-position", "0px 0px")
            }
            else if($("#passwordInput input").val() != ""){
                $("#passwordInput input").css("background-position","0px -81px");
            }
        }).focus( function(){
                $(this).css("background-position", "0px -27px")
    });
    
    //pasword input field code
    $("#passwordInput input").blur( function(){
        if(this.value == "")
            $(this).css("background-position", "0px -54px")
        }).focus( function(){
                $(this).css("background-position","0px -81px")
    });
 })); 

//global advert index - hold current advert num
var advertindex = 0;

//set repeat interval with rotate advert functionality
var intervalTimer = window.setInterval( function(){

    //get all adverts
    var advertArr = $(".advert");
    // fade out current then fade in next
    $(advertArr[advertindex]).fadeOut(600,function(){
            advertindex = (advertindex + 1) % advertArr.length;
            $(advertArr[advertindex]).fadeIn(800);
    });

}, 10000); 
