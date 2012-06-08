/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var xhr = new XMLHttpRequest;

var emptyApg = new Object();
emptyApg.groupName="";
emptyApg.description="";

var emptyDS = new Object();
emptyDS.dataURL="";
emptyDS.numPages="";
emptyDS.subItems=null;




function ajaxRequestProcessor(method,url,responseHandler) {
    xhr.open(method, url,true);
    xhr.onreadystatechange = responseHandler;
    xhr.send();
}

function ajaxResponseProcessor(dataHandler) {
    if (xhr.readyState == 4 /* complete */) {
        if (xhr.status == 200) {
            try {
                var data = JSON.parse(xhr.responseText);
            }catch(err) {
                window.location.reload();
            }
            dataHandler(data);
        }
    }
}


var apgAjaxResponseProcessor = function () {
    ajaxResponseProcessor(apgDataHandler);
};

var dsAjaxResponseProcessor = function () {
    ajaxResponseProcessor(dsDataHandler);
}

function apgSelected(ctxPath) {

    var selBox = document.getElementById("apGroups");
    var pgId=selBox.options[selBox.selectedIndex].value;
    
    
    if (pgId <0) {
        document.getElementById("apGroupData").style.display="none";
    }else {
        document.getElementById("apGroupData").style.display="block";
        if (pgId != 0) {
            var url=ctxPath+"/mvc/afprgroups/"+pgId;
            ajaxRequestProcessor("GET", url,apgAjaxResponseProcessor);
        }else {
            apgDataHandler(emptyApg);
        }
    }
}


function dsSelected(ctxPath,bpId) {

    var selBox = document.getElementById("dataSpecList");
    if (selBox != null) {
        var dsId=selBox.options[selBox.selectedIndex].value;

        if (dsId==0) {
            dsDataHandler(emptyDS);
        }else {
            var url=ctxPath+"/mvc/billing/details/"+bpId+"/ds/"+dsId;
            ajaxRequestProcessor("GET", url,dsAjaxResponseProcessor);
        }
    }
}


function siSelected() {
    var selBox = document.getElementById("siList");
    if (selBox != null) {
        var selector=selBox.options[selBox.selectedIndex].value;
        if (selector != null)
            document.getElementById("si.selector").innerHTML=selector;
    }
}


var apgDataHandler = function (apg)  {
    document.getElementById("affProgramGroup.groupName").value=apg.groupName;
    document.getElementById("affProgramGroup.description").value=apg.description;
}

var dsDataHandler = function (ds)  {

    var selBox = document.getElementById("siList");
    
    selBox.options.length = 1;
    selBox.selectedIndex = 0;
    selBox.disabled=true;
    document.getElementById("ds.url").innerHTML=ds.dataURL;
    document.getElementById("ds.numPages").innerHTML=ds.numPages;
    document.getElementById("si.selector").innerHTML="";
    
    
    var siList =  ds.subItems;

    if (siList) {
        for (i = 0; i < siList.length;++i) {
            var oOption = document.createElement("OPTION");
            oOption.text = siList[i].name;
            oOption.value = siList[i].selector;
            selBox.add(oOption);
        }
        selBox.disabled=false;
    }
}






