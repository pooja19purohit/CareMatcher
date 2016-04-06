<%-- 
    Document   : administration
    Created on : Jun 28, 2015, 5:32:12 PM
    Author     : kbuck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/responsive-bootstrap.css">
        <link rel="stylesheet" href="styles/bootstrap.css">
        <link rel="stylesheet" href="styles/header.css">
        <link rel="stylesheet" href="styles/footer.css">
        <link rel="icon" type="image/png" href="images/Logo-trans.png">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
        <title>Carematcher Administration</title>
    </head>
    <body>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <jsp:include page="header.jsp"></jsp:include>
        
        <div class="container">
            <div class="row">
                <div class="col-md-9 col-xs-10 col-md-offset-1" id="user-well">
                    <div class="well panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <div class="form-group" id="type-group">
                                    <label for="select" class="col-lg-2 control-label">Administration Options:</label>
                                    <div class="col-lg-10">
                                        <form method="post" action="admin" class="form-horizontal">
                                            <input type="hidden" name="service" value="delete-user">
                                            <select class="form-control" id="type-select" name="delselect">
                                                <c:forEach var="deluser" items="${users}">
                                                    <option value="${deluser.email}">${deluser.firstName} ${deluser.lastName}</option>
                                                </c:forEach>
                                            </select>
                                            <button type="submit">Delete User:</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-9 col-xs-10 col-md-offset-1" id="user-well">
                    <div class="well panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <div class="form-group">
                                    <div class="col-lg-10">
                                        <form method="post" action="admin" class="form-horizontal" id="updateForm">
                                            <input type="hidden" name="service" value="loc-update">
                                            <c:forEach var="address" items="${updates}">
                                                <input type="hidden" class="updateAdd" value="${address.getUser().getEmail().concat(':').concat(address.street).concat(", ").concat(address.city).concat(", ").concat(address.st)}">
                                            </c:forEach>
                                            <button type="submit">Update Addresses:</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <jsp:include page="footer.jsp"></jsp:include>
        <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="scripts/getLocation.js" type="text/javascript"></script>
    </body>
</html>
