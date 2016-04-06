<%-- 
    Document   : header
    Created on : Jun 29, 2015, 6:37:35 PM
    Author     : kbuck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>CareMatcher.com</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles/responsive-bootstrap.css">
        <link rel="stylesheet" href="styles/bootstrap.css">
        <link rel="stylesheet" href="styles/header.css">
    </head>
    <body>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!--Navbar-->
	<nav class="navbar navbar-inverse navbar-fixed-top" id="my-navbar">
            <div class="container" id="navbar-container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a href="home" class="navbar-brand navbar-left">
                        <img src="images/Logo-trans.png" alt="logo" id="logo" class="img-rounded">
                    </a>
                </div><!--Navbar Collapse-->
                <div class="collapse navbar-collapse" id="navbar-collapse">
                    <ul class="nav navbar-nav" id="navbar-nav">
                        <li><a href="#About US">About US</a>
                        <li><a href="registration.jsp">Register</a>
                        <li><a href="#Services">Services</a>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Settings<strong class="caret"></strong></a>
                            <ul class="dropdown-menu">
                                <c:if test="${not empty sessionScope.user}">
                                    <li><a href="logout">Logout</a></li>
                                </c:if>
                                <c:if test="${not empty sessionScope.user and sessionScope.user.role() eq 'ADMINISTRATOR'}">
                                    <li><a href="admin?service=go-to-admin">Administration</a></li>
                                </c:if>
                            </ul>
                        </li>
                    </ul>
                    <form action="search" class="navbar-form navbar-right" >
                        <div class="form-group">
                            <input name="search-string" type="text" class="form-control" placeholder="Search for care:" />
			</div> 
                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>
                </div>
            </div>
	</nav>
    </body>
</html>
