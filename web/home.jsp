<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>CareMatcher.com</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap CSS & Template -->
        <link rel="stylesheet" href="styles/responsive-bootstrap.css">
        <link rel="stylesheet" href="styles/bootstrap.css">
        <link rel="stylesheet" href="styles/homepage.css" type="text/css">
        <link rel="icon" type="image/png" href="images/Logo-trans.png">
    </head>
    <body>
        <%@include file="header.html" %>
        
                <!-- jumbotron-->
        <div class="jumbotron">
            <div class="container text-center">
                <h1> Care Matcher </h1>
                <p>Helping you find the the best care you are looking for!</p>
            </div><!-- end of container-->
        </div><!-- END of jumbotron-->
        <section>
            <div class="container well">
                <div class="text-center">
                    <h6>${errorMessage}</h6>
                    <h1>Already a member?</h1>
                </div>
                <div class="container">
                    <div class="row">
                        <form action="login" method="post" class="form-group">
                            <div class="col-sm-3 column col-sm-offset-3">
                                <input name="email" type="email" class="form-control" id="inputEmail3" placeholder="Email:" required="true" />
                                <div class="checkbox">
                                    <label><input type="checkbox" />Remember me</label><br>
                                </div>
                            </div>
                            <div class="col-sm-3 column">
                                <input name="password" type="password" class="form-control" id="inputPassword3" placeholder="Password:" required="true" />
                                <a href="/register" class="checkbox btn-link" type="button">Forgot Password:</a>
                            </div>
                            <div class="col-md-3 column">
                                <button type="submit" class="btn btn-default">Login</button>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-3 column col-sm-offset-3">

                                </div>
                            </div>
                        </form>
                    </div>
                </div> 
                <div class="text-center">
                    <h1>If not, register for free!</h1>
                    <a href="registration.jsp">
                        <button type="button" class="btn-lg btn-info">Register</button>
                    </a>
                    <!--
                    <form action="register" class="form-inline" method="post">
                        
                    </form>
                    -->
                </div>
            </div>   
            
            <!--
            <div class="well">
                <div class="container text-center">
                    <h1>Already a member?</h1>
                    <form action="login" class="form-horizontal" role="form" method="post">
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
                            <div class="col-sm-10">
				<input type="email" class="form-control" id="inputEmail3" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
                            <div class="col-sm-10">
				<input type="password" class="form-control" id="inputPassword3" />
                            </div>
			</div>
                        <div class="form-group left">
                            <div class="left">
                                <div class="checkbox">
                                    <label><input type="checkbox" /> Remember me</label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default">Sign in</button>
                            </div>
                        </div>
                    </form>
                    <!--
                    <form action="login" class="form-horizontal" onsubmit="return checkFields()" method="post">
                        <div class="form-group">
                            <input type="email" class="form-action" name="email" placeholder="Email:" required="true">
                            <button type="submit" class="btn btn-info">Login</button><br>
                            <input type="password" class="form-action" name="password" placeholder="Password:" required="true">
                            <input type="checkbox" class="checkbox-inline" name="rememberme">Remember Me:
                        </div>
                    </form>
                    <form action="register" class="form-inline" method="post">
                        <div class="form-group">
                            <h2>If not, sign up for free!</h2>
                            <button type="submit" class="btn btn-block btn-info">Register</button>
                            <hr>
                        </div>
                    </form>
                    
                </div>	 	  		
            </div>
            -->
        </section>

        <section>
            <div class="well">				
                <!-- About Us-->
                <div class="container text-left"> 
                    <div class="page-header" id="About US">
                        <h1>Why Use Carematcher?</h1>
                        <p>Care Matcher is County's best care match website. Our website will assist you in navigating the entire process of finding the perfect caregiver for you or your loved one.</p>
                    </div>
                </div>
            </div>
        </section>

        <!--Gallery-->
        <div class="container">
            <section>
                <div class="page-hearder" id="Services">
                    <h2><small>Our Services</small></h2>

                    <div class="carousel slide" id="myCarousel" dtat-ride="carousel">

                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="2"></li>
                            <li data-target="#myCarousel" data-slide-to="3"></li>
                        </ol>

                        <div class="carousel-inner" role="listbox">
                            <div class="item active">
                                <img src="./images/healthcare.jpg" alt="Healthcare" width="500" height="500">
                                    <div class="carousel-caption">
                                        <h3>Health Care</h3>	
                                    </div>	 	           	
                            </div>
                            <div class="item">
                                <img src="./images/elderlycare.jpg" alt="Elderlycare" width="500" height="500">
                                <div class="carousel-caption">
                                    <h3>Elderly Care</h3>	
                                </div>	 	           	
                            </div>
                            <div class="item">
                                <img src="./images/childcare.jpg" alt="Childcare" width="500" height="500">
                                <div class="carousel-caption">
                                    <h3>Child Care</h3>	
                                </div>	 	           	
                            </div>
                            <div class="item">
                                <img src="./images/insurance.jpg" alt="Insurance" width="500" height="500">
                                <div class="carousel-caption">
                                    <h3>Insurance</h3>	
                                </div>	 	           	
                            </div>
                            <!-- Left and right controls -->
                            <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>	 	         	
                        </div>
                    </div>    	
                </div>
            </section>
        </div>
        
        <jsp:include page="footer.jsp" />
        <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    </body>
</html>
