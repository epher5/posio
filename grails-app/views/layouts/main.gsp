<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--[if lt IE 7 ]> <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en"lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><g:layoutTitle default="POSIO" /></title>
    <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
    <asset:stylesheet src="application.css"/>
    <g:layoutHead />
</head>
<body>
<div id="wrapper">
    <div id="grailsLogo" role="banner"><a href="http://grails.org"><asset:image src="grails_logo.png" alt="Grails"/></a></div>

    <!-- header picture -->
    <div id="header">
        <img src="${assetPath(src:'header.jpg')}" alt="header" /></div>
<!-- END #header -->

<!-- username | logout link -->
    <g:if test="${session?.user}">
        <div id="login">
            ${session?.user?.firstName} ${session?.user?.lastName} |
            <g:link controller="phone" action="sms">SMS</g:link> |
            <g:link controller="phone" action="call">Call</g:link> |
            <g:link controller="payment" action="create">Create Authorization</g:link> |
            <g:link controller="user" action="logout">Logout</g:link></div>
        <!-- END #login -->
    </g:if>
    <h1>POSIO</h1>
    <div id="nav"></div>
    <!-- END #nav -->
    <div id="content">
        <g:layoutBody /></div>
    <!-- END #content -->
    <div class="footer">
        <g:render template="/common/footer" /></div>

    %{--<div class="footer" role="contentinfo"></div>--}%

    <!-- END #footer --></div>
<!-- END #wrapper -->
<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
</body>
</html>