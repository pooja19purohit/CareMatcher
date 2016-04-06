<%-- 
    Document   : result.jsp
    Created on : Jun 27, 2015, 2:50:24 AM
    Author     : Pooja Purohit
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
        <!--<link rel="stylesheet" href="styles/main.css">-->
        <!--<link rel="stylesheet" href="styles/registration.css">-->
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />  
        <section>
            <form action="confirm" method="post" class="form-horizontal" style="margin-left: 250px;" id="appt-form">
                <h1>Request Appointment</h1>
                <fieldset>
                    <div class="form-group" id="for-me-radio">
                        <label for="for-me-radio" class="col-lg-2 control-label">Appointment</label>
                        <div class="col-lg-10">
                            <input id="for-me-id" name="for-me" type="radio" value="for-me" checked> is for me
                            <input id="not-me-id" name="for-me" type="radio" value="not-me"> is for someone else
                        </div>
                    </div>
                    <div class="form-group" id="fname-group" >
                        <label for="inputFirstName" class="col-lg-2 control-label">First Name:<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input name="firstName" type="text" class="form-control" id="inputFName" value="${sessionScope.user.firstName}" placeholder="First Name" required>
                        </div>
                    </div>
                    <div class="form-group" id="mi-group">
                        <label for="inputMidInit" class="col-lg-2 control-label">MI:<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input name="midInit" type="text" class="form-control" id="inputMInit" value="${sessionScope.user.midInit}" placeholder="MI:">
                        </div>
                    </div>
                    <div class="form-group" id="lname-group">
                        <label for="inputLastName" class="col-lg-2 control-label">Last Name:<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input name="lastName" type="text" class="form-control" id="inputLName" value="${sessionScope.user.lastName}" placeholder="Last Name" required>
                        </div>
                    </div>
                    <div class="form-group" id="email-group">
                        <label for="inputEmail" class="col-lg-2 control-label">Email:<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input name="email" type="text" class="form-control" id="inputEmail" value="${sessionScope.user.email}" placeholder="Email" required>
                        </div>
                    </div>
                    <div class="form-group" id="date-group">
                        <label for="date" class="col-lg-2 control-label">Preferred Date:</label>
                        <div class="col-lg-10">
                            <input name="prefDate" type="date" class="form-control" id="datexx">
                        </div>
                    </div> 
                    <div class="form-group" id="time-group">
                        <label for="time" class="col-lg-2 control-label">Preferred Time:</label>
                        <div class="col-lg-10">
                            <input name="prefTime" type="time" class="form-control" id="time">
                        </div>
                    </div>
                    <div class="form-group" id="checkbox-group">
                        <label for="avail" class="col-lg-2 control-label">Availability:</label>
                        <div class="col-lg-10">
                            <div class="col-md-2">
                                <input name="wkend" type="checkbox" value="weekends"><span> Weekends</span><br>
                                <input name="morn" type="checkbox" value="mornings"><span> Mornings</span>
                            </div>
                            <div class="col-md-2">
                                <input name="wkday" type="checkbox" value="weekdays"><span> Weekdays</span> <br>
                                <input name="after" type="checkbox" value="afternoons"><span> Afternoon</span>
                            </div>
                            <div class="col-md-2">
                                <br>
                                <input name="even" type="checkbox" value="evenings"><span> Evenings</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" id="comment-group">
                        <label for="textArea" class="col-lg-2 control-label">Comments:</label>
                        <div class="col-lg-10">
                            <textarea name="comments" class="form-control" id="textArea"></textarea>
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group" id="submit-group">
                        <div class="col-lg-10 col-lg-offset-2">
                            <button type="reset" class="btn btn-default">Cancel</button>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </section>
        <jsp:include page="footer.jsp" />
        <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="scripts/appointmentFormChange.js" type="text/javascript"></script>
    </body>
</html>
