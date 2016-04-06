<%-- 
    Document   : registration
    Created on : Jun 10, 2015, 5:26:32 PM
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
        <!--<link rel="stylesheet" href="styles/main.css">-->
        <link rel="stylesheet" href="styles/registration.css">
        <script src="scripts/formValidation.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
        <title>Carematcher Registration:</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <section>
            <h5>${errorMessage}</h5>
            <form action="registration" method="post" class="form-horizontal" style="margin-left: 250px;" id="registrationForm">
                <fieldset>
                    <legend>Register</legend>
                    <div class="form-group" id="type-group">
                        <label for="select" class="col-lg-2 control-label">Register As</label>
                        <div class="col-lg-10">
                            <select class="form-control" id="type-select" name="userType">
                                <option value="default" selected="true">-- Select an Option --</option>
                                <option value="customer">Customer</option>
                                <option value="provider">Care Provider</option>
                                <option value="practice">Care Practice</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" id="fname-group">
                        <label id="first-name-label" for="inputFirstName" class="col-lg-2 control-label">First Name<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input name="firstName" type="text" class="form-control" id="inputFName" placeholder="First Name:" required>
                        </div>
                    </div>
                    <div class="form-group" id="mi-group">
                        <label for="middleName" class="col-lg-2 control-label">Middle Initial</label>
                        <div class="col-lg-10">
                            <input name="midInit" type="text" class="form-control" id="middleName" placeholder="MI:">
                        </div>
                    </div>
                    <div class="form-group" id="lname-group">
                        <label for="inputLastName" class="col-lg-2 control-label">Last Name<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input name="lastName" type="text" class="form-control" id="inputLName" placeholder="Last Name:" >
                        </div>
                    </div>
                    <div class="form-group" id="dob-group">
                        <label for="dob" class="col-lg-2 control-label">Date of Birth</label>
                        <div class="col-lg-10">
                            <input name="dateOB" type="date" class="form-control" id="dob">
                        </div>
                    </div>
                    <div class="form-group" id="email-group">
                        <label for="inputEmail" class="col-lg-2 control-label">Email<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input name="email" type="text" class="form-control" id="inputEmail" placeholder="Email:" required>
                        </div>
                    </div>
                    <div class="form-group" id="pass-group">
                        <label for="inputPassword" class="col-lg-2 control-label">Password<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input name="password" type="password" class="form-control" id="inputPassword" placeholder="Password:" required>
                        </div>
                    </div>
                    <div class="form-group" id="conf-group">
                        <label for="inputConfirmPassword" class="col-lg-2 control-label">Confirm Password<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input type="password" class="form-control" id="inputConfirmPassword" placeholder="Confirm Password:" required>
                        </div>
                    </div>
                    <div class="form-group" id="exp-group">
                        <label for="inputExperience" class="col-lg-2 control-label">Years of Experience</label>
                        <div class="col-lg-10">
                            <input name="experience" type="number" min="0" class="form-control" id="inputExperience" placeholder="Enter years practicing:">
                        </div>
                    </div>
                    <div class="form-group" id="avail-group">
                        <label for="inputAvailable" class="col-lg-2 control-label">Availability</label>
                        <div class="col-lg-10">
                            <div class="checkbox">
                                <label class="checkbox-inline"><input name="willTravel" type="checkbox" value="" id="willTravel">Will Travel</label>
                                <label class="checkbox-inline"><input name="accepting" type="checkbox" value="" id="acceptingNew">Accepting New Patients</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" id="addr1-group">
                        <label for="inputAddressLine1" class="col-lg-2 control-label">Address Line 1<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input name="address1" type="text" class="form-control" id="inputAddressLine1" placeholder="Street address, P.O. box, company name, c/o" required>
                        </div>
                    </div>
                    <div class="form-group" id="addr2-group">
                        <label for="inputAddressLine2" class="col-lg-2 control-label">Address Line 2</label>
                        <div class="col-lg-10">
                            <input name="address2" type="text" class="form-control" id="inputAddressLine2" placeholder="Apartment, suite , unit, building, floor, etc.">
                        </div>
                    </div>
                    <div class="form-group" id="city-group">
                        <label for="city" class="col-lg-2 control-label">city<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input name="city" type="text" class="form-control" id="city" placeholder="City / Town" required>
                        </div>
                    </div>
                    <div class="form-group" id="st-group">
                        <label for="select" class="col-lg-2 control-label">State<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <select name="state" class="form-control" id="selectState">
                                <option value="AL">Alabama</option>
                                <option value="AK">Alaska</option>
                                <option value="AZ">Arizona</option>
                                <option value="AR">Arkansas</option>
                                <option value="CA">California</option>
                                <option value="CO">Colorado</option>
                                <option value="CT">Connecticut</option>
                                <option value="DE">Delaware</option>
                                <option value="DC">District Of Columbia</option>
                                <option value="FL">Florida</option>
                                <option value="GA">Georgia</option>
                                <option value="HI">Hawaii</option>
                                <option value="ID">Idaho</option>
                                <option value="IL">Illinois</option>
                                <option value="IN">Indiana</option>
                                <option value="IA">Iowa</option>
                                <option value="KS">Kansas</option>
                                <option value="KY">Kentucky</option>
                                <option value="LA">Louisiana</option>
                                <option value="ME">Maine</option>
                                <option value="MD">Maryland</option>
                                <option value="MA">Massachusetts</option>
                                <option value="MI">Michigan</option>
                                <option value="MN">Minnesota</option>
                                <option value="MS">Mississippi</option>
                                <option value="MO">Missouri</option>
                                <option value="MT">Montana</option>
                                <option value="NE">Nebraska</option>
                                <option value="NV">Nevada</option>
                                <option value="NH">New Hampshire</option>
                                <option value="NJ">New Jersey</option>
                                <option value="NM">New Mexico</option>
                                <option value="NY">New York</option>
                                <option value="NC">North Carolina</option>
                                <option value="ND">North Dakota</option>
                                <option value="OH">Ohio</option>
                                <option value="OK">Oklahoma</option>
                                <option value="OR">Oregon</option>
                                <option value="PA">Pennsylvania</option>
                                <option value="RI">Rhode Island</option>
                                <option value="SC">South Carolina</option>
                                <option value="SD">South Dakota</option>
                                <option value="TN">Tennessee</option>
                                <option value="TX">Texas</option>
                                <option value="UT">Utah</option>
                                <option value="VT">Vermont</option>
                                <option value="VA">Virginia</option>
                                <option value="WA">Washington</option>
                                <option value="WV">West Virginia</option>
                                <option value="WI">Wisconsin</option>
                                <option value="WY">Wyoming</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" id="zip-group">
                        <label for="zipCode" class="col-lg-2 control-label">Zip Code<span style="color:red;">*</span></label>
                        <div class="col-lg-10">
                            <input name="zip" type="text" class="form-control" id="zipCode" placeholder="zip or postal code" required>
                        </div>
                    </div>  
                    <div class="form-group" id="country-group">
                        <label for="select" class="col-lg-2 control-label">Country</label>
                        <div class="col-lg-10">
                            <select name="country" class="form-control" id="selectCountry">
                                <option>United States</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" id="phone-group">
                        <label for="phone" class="col-lg-2 control-label">Phone</label>
                        <div class="col-lg-10">
                            <input name="phoneNum" type="text" class="form-control" id="phone" placeholder="xxx-xxx-xxxx" required>
                            <select name="phoneType" class="form-control" id="selectPhoneType" style="width:140px;">
                                <option value="home">Home</option>
                                <option value="office">Office</option>
                                <option value="cell">Cell</option>
                                <option value="fax">Fax</option>
                            </select> 
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
        <script src="scripts/registrationFormChange.js" type="text/javascript"></script>
        <script src="scripts/getLocation.js" type="text/javascript"></script>
    </body>
</html>
