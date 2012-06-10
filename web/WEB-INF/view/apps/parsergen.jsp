<%-- 
    Document   : parsergen
    Created on : Feb 3, 2012, 4:16:22 PM
    Author     : Nina Eidelshtein and Misha Lebedev
--%>

<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="application/x-java-jnlp-file" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
    response.setHeader("Content-Disposition", "attachment; filename=parsergen.jnlp"); 
%>

<jnlp spec="1.0+" codebase="${codebase}">
    <information>
        <title>
            ParserGen
        </title>
        <vendor>
            MNE
        </vendor>
    </information>
    <security>
      <all-permissions/>
    </security>    
    <resources>
        <j2se version="1.6+" initial-heap-size="512m" max-heap-size="512m"/>
        <jar href="extlib/jsoup-1.6.1.jar" download="eager"/>
        <jar href="extlib/logback-classic-1.0.0.jar" download="eager"/>
        <jar href="extlib/logback-core-1.0.0.jar" download="eager"/>
        <jar href="extlib/slf4j-api-1.6.4.jar" download="eager"/>
        <jar href="parsergen.jar" download="eager"/>
        
    </resources>
    <application-desc main-class="com.mne.advertmanager.parsergen.ParserGenMainFrame"/>
</jnlp>