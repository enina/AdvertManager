/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




$(document).ready(function(){ 
    $('input').keyup(function(e){ 
        
       
      if(e.which == 13){ 
           // submit via ajax or 
           
           $('form').submit();  
       } 
    }); 
}); 
