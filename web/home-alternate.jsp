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
        <jsp:include page="header.jsp" />
        
                <!-- jumbotron-->
        <div class="jumbotron">
            <div class="container text-center">
                <h1> Care Matcher </h1>
                <p>Here to help you find the the best care around!</p>
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
                </div>
            </div>   
        </section>

        <section>
            <div class="well">				
                <!-- About Us-->
                <div class="container text-justify"> 
                    <div class="page-header" id="About US">
                        <h1>Why Use CareMatcher?</h1>
                        <p>Our mission is to provide you with the information you need to to find the best possible care 
                           in your area, that meets all of your individual care requirements. CareMatcher can help you find and connect with a specific care provider,
                           or look up services provided in your area. Connecting with the right care the first time can make all the difference between a successful long term care relationship, or enduring a long 
                           frustrating process of trial and error.
                           
                           At CareMatcher.com you can explore your care options. Learn about differences between types of care providers and practices. Once you’ve decided
                           which option is right for you, you can conduct a customized and focused search for providers in your area. Build your profile and tell us a little
                           more about yourself, your care needs and your preferences. Your search results will then provide you with a list of care providers that most closely
                           match your individual profile and needs.
                        </p>
                    </div>
                </div>
            </div>
        </section>

        <!--Gallery-->
        <div class="container">
            <section>
                <div class="page-hearder" id="Services">
                    <h2><small>Our Services</small></h2>

                    <div class="carousel slide" id="myCarousel" data-ride="carousel">

                        <ol class="carousel-indicators">
                            <li data-target="#Services" data-slide-to="0" class="active"></li>
                            <li data-target="#Services" data-slide-to="1"></li>
                            <li data-target="#Services" data-slide-to="2"></li>
          
                        </ol>

                        <div class="carousel-inner" role="listbox">
                            <div class="item active">
                                <div class="carousel-caption- text-center">
                                    <img src="images/healthcare.jpg" alt="Healthcare" height="337" width ="553">
                                    <h3>Health Care</h3>	
                                </div>	 	           	
                            </div>
                            <div class="item">
                                <div class="carousel-caption-defaul text-center">
                                    <img src="images/elderlycare.jpg" alt="Elderlycare" height="337" width="553">
                                    <h3>Elderly Care</h3>	
                                </div>	 	           	
                            </div>
                            <div class="item">
                                <div class="carousel-caption-default text-center">
                                    <img src="images/childcare.jpg" alt="Childcare" height="337" width="553">
                                    <h3>Child Care</h3>	
                                </div>	 	           	
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
