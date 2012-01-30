<%@ include file="taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/adman1.css"/>" />
</head>
<body>
<div id="header">
    <div id="headerTitle"><tiles:insertAttribute name="header" /></div>
</div>
<div id="menu">
    <tiles:insertAttribute name="menu" />
</div>
<div id="content">
    <td><tiles:insertAttribute name="content" />
</div>
<div id="footer">
    <tiles:insertAttribute name="footer" />
</div>
</body>
</html>