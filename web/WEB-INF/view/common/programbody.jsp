
<%-- 
    Document   : programbody
    Created on : May 12, 2012, 1:43:21 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@ include file="taglibs.jsp" %>

<div id="content">

    <script>
        $(function(){
            // Tabs
            $('#programDetails').tabs();
            
        });
    </script>
       
    
    <!-- Tabs -->
   
    
    <div id="programDetails">
        <ul>
            <li > <a  href="#tabs-1">Overview </a></li>
            <li > <a  href="#tabs-2">Finance </a></li>
            <li > <a  href="#acesses">Accesses</a></li>
            <li > <a  href="#tabs-4">Orders</a></li>
            <li > <a  href="#tabs-5">Partners</a></li>
            <li > <a  href="#tabs-6">Your links</a></li>  
        </ul>
        
        <div id="tabs-1"><tiles:insertAttribute name="overview" /></div>
        <div id="tabs-2"><tiles:insertAttribute name="finance" /></div>
        <div id="acesses"><tiles:insertAttribute name="accesses" /></div>
        <div id="tabs-4"><tiles:insertAttribute name="orders" /></div>
        <div id="tabs-5"><tiles:insertAttribute name="partners" /></div>   
        <div id="tabs-6"><tiles:insertAttribute name="afflinks" /></div>
     
    </div>

</div>

