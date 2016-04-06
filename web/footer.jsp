<%-- 
    Document   : footer
    Created on : Jun 29, 2015, 5:19:40 PM
    Author     : kbuck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Footer</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles/responsive-bootstrap.css">
        <link rel="stylesheet" href="styles/bootstrap.css">
        <link rel="stylesheet" href="styles/footer.css">
    </head>
    <body>
        <footer class="footer-distributed">
            <div class="footer-left"> 
                <h3><img src="images/Logo-trans.png" alt="Care Matcher Logo" width="140" height= "100"></h3>

                <br>

                <p class="footer-company-name">&copy; ${sessionScope.year}, CareMatcher, West Haven CT 06516</p>
            </div>
            <div class="footer-center">
                <div>
                    <i class="fa fa-map-marker"></i>
                    <p><span>300 Boston Post Road</span> West Haven CT, 06516</p>
                </div>

                <div>
                    <i class="fa fa-phone"></i>
                    <p>+1 (860) 555-4567</p>
                </div>

                <div>
                    <i class="fa fa-envelope"></i>
                    <p><a href="mailto:support@carematcher.com">support@carematcher.com</a></p>
                </div>
            </div>
            <div class="footer-right">
                <p class="footer-company-about">
                    <span>About the company</span>
                    CareMatcher is a Connecticut based service that provides a venue 
                    for people to search and connect to care providers in their area. 
                    CareMatcher offers information and resources to help you make an 
                    informed decision regarding your individual care requirements.
                </p>
                <div class="footer-icons">
                    <ul class="list-inline">
                        <li><a href="https://www.facebook.com"><img src="images/icons/fb_logo.png" alt="fb_logo"></a></li>
                        <li><a href="https://twitter.com"><img src="images/icons/twitter_logo.gif" alt="twitter"></a></li>
                        <li><a href="https://www.linkedin.com"><img src="images/icons/linkedin_logo.gif" alt="linkedin"></a></li>
                    </ul>
                </div>
            </div>
        </footer>
        <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    </body>
</html>

