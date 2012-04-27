/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var xhr = new XMLHttpRequest;

function apgSelected(ctxPath) {

    var selBox = document.getElementById("apGroups");
    document.getElementById("apGroupData").style.display="block";
    var pgId=selBox.options[selBox.selectedIndex].value;

    if (pgId != 0) {
        var url=ctxPath+"/mvc/affppgroups/"+pgId;
        xhr.open("GET", url,true);
        xhr.onreadystatechange = pgroupHandler;
        xhr.send();
    }else {
        document.getElementById("apg.groupName").value="";
        document.getElementById("apg.description").value="";                
    }
}


function apgHandler()
{
    if (xhr.readyState == 4 /* complete */) {
        if (xhr.status == 200) {
            var pg = JSON.parse(xhr.responseText);
            document.getElementById("apg.groupName").value=pg.groupName;
            document.getElementById("apg.description").value=pg.description;
        }
    }
}

