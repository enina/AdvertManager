/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var xhr = new XMLHttpRequest;
function productAuthorSelected(ctxPath) {
    var selBox = document.getElementById("authors");
    document.getElementById("authorData").style.display="block";
    var authorId=selBox.options[selBox.selectedIndex].value;
    if (authorId != 0) {
        var url=ctxPath+"/mvc/authors/"+authorId;
        xhr.open("GET", url,true);
        xhr.onreadystatechange = authorHandler;
        xhr.send();
    }else {
        document.getElementById("authorId.authorName").value="";
        document.getElementById("authorId.email").value="";        
    }
    
}

function productGroupSelected(ctxPath) {

    var selBox = document.getElementById("pGroups");
    document.getElementById("productGroupData").style.display="block";
    var pgId=selBox.options[selBox.selectedIndex].value;

    if (pgId != 0) {
        var url=ctxPath+"/mvc/pgroups/"+pgId;
        xhr.open("GET", url,true);
        xhr.onreadystatechange = pgroupHandler;
        xhr.send();
    }else {
        document.getElementById("productGroupId.groupName").value="";
        document.getElementById("productGroupId.description").value="";                
    }
}


function pgroupHandler()
{
    if (xhr.readyState == 4 /* complete */) {
        if (xhr.status == 200) {
            var pg = JSON.parse(xhr.responseText);
            document.getElementById("productGroupId.groupName").value=pg.groupName;
            document.getElementById("productGroupId.description").value=pg.description;
        }
    }
}

function authorHandler()
{
    if (xhr.readyState == 4 /* complete */) {
        if (xhr.status == 200) {
            var author = JSON.parse(xhr.responseText);
            document.getElementById("authorId.authorName").value=author.authorName;
            document.getElementById("authorId.email").value=author.email;
        }
    }
}