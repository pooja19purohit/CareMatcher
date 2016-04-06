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
        <!--<link rel="stylesheet" href="styles/main.css">-->
        <!--<link rel="stylesheet" href="styles/registration.css">-->
        <script src="scripts/formValidation.js"></script>
        <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <div class="container">
            <div class="row">
                <div class="col-md-9 col-xs-10 col-md-offset-1" id="user-well">
                    <div class="well panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <h4>${errorMessage}</h4>
                                <div class="col-xs-12 col-sm-4 text-left">
                                    <h1>Appointment Confirmation:</h1><br>
                                    <c:if test="${infoMap['FOR_ME'] eq 'not-me'}">
                                        <p>Thanks ${sessionScope.user.firstName} for requesting an appointment for ${infoMap['F_NAME']}!
                                    </c:if>
                                    <c:if test="${infoMap['FOR_ME'] eq 'for-me'}">
                                        <p>Thanks ${sessionScope.user.firstName} for requesting an appointment!</p>
                                    </c:if>
                                    <p>Request Details:</p>
                                    <label>First Name:</label><span>${infoMap['F_NAME']}</span><br>
                                    <c:if test="${infoMap['M_INIT']}">
                                        <label>Middle Initial:</label><span>${infoMap['M_INIT']}</span><br>
                                    </c:if>
                                    <label>Last Name: </label><span>${infoMap['L_NAME']}</span><br>
                                    <label>Email: </label><span>${infoMap['EMAIL']}</span><br>
                                    <label>Preferred Date: </label><span>${infoMap['PREF_DATE']}</span><br>
                                    <label>Preferred Time: </label><span>${infoMap['PREF_TIME']}</span><br>
                                    <label>Weekends: </label><span>${infoMap['WEEKEND']}</span><br>
                                    <label>Weekdays: </label><span>${infoMap['WEEKDAY']}</span><br>
                                    <label>Mornings: </label><span>${infoMap['MORNING']}</span><br>
                                    <label>Afternoons: </label><span>${infoMap['AFTERNOON']}</span><br>
                                    <label>Evenings: </label><span>${infoMap['EVENING']}</span><br>
                                    <label>Comments: </label><p>${infoMap['COMMENT']}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<!--    <form action="" method="post">
        <input type="hidden" name="action" value="join">
        <input type="submit" value="Return">
    </form>-->
    <jsp:include page="footer.jsp" />
    </body>
</html>
