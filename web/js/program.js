/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var xhr = new XMLHttpRequest;

function apgSelected(ctxPath) {

    var selBox = document.getElementById("apGroups");
    var pgId=selBox.options[selBox.selectedIndex].value;
    
    if (pgId <0) {
        document.getElementById("apGroupData").style.display="none";
    }else {
        document.getElementById("apGroupData").style.display="block";
        if (pgId != 0) {
            var url=ctxPath+"/mvc/afprgroups/"+pgId;
            xhr.open("GET", url,true);
            xhr.onreadystatechange = apgHandler;
            xhr.send();
        }else {
            document.getElementById("affProgramGroup.groupName").value="";
            document.getElementById("affProgramGroup.description").value="";                
        }
    }
}


function apgHandler()
{
    if (xhr.readyState == 4 /* complete */) {
        if (xhr.status == 200) {
            var pg = JSON.parse(xhr.responseText);
            document.getElementById("affProgramGroup.groupName").value=pg.groupName;
            document.getElementById("affProgramGroup.description").value=pg.description;
        }
    }
}

