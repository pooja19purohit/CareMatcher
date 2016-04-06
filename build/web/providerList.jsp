<%-- 
    Document   : providerList
    Created on : Jun 21, 2015, 3:35:48 PM
    Author     : kbuck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <div class="container">
            <div class="row">
                <div class="col-md-9 col-xs-10 col-md-offset-1" id="content-1">
                    <div class="well panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <h3>List of Linked Care Providers:</h3>
                                    <c:forEach var="provider" items="${customer.linkedProviders}">
                                        <div class="row well-lg"> 
                                            <div class="span3">
                                                <img src="images/200x200.jpg">
                                            </div>
                                            <div class="span5">
                                                <h3>${provider.firstName} ${provider.midInit} ${provider.lastName}</h3>
                                                <div class="careInfo">
                                                    ${provider.email}
                                                </div>
                                                <div class="careInfo">
                                                    Speciality:
                                                </div>
                                                <div class="careInfo">
                                                    Rating:
                                                </div>
                                                <div class="careInfo">
                                                    # Years Experience:
                                                </div>
                                                <div class="btn-group btn-group-justified">
                                                    <a href="#" class="btn btn-primary">Request Appointment</a>
                                                    <a href="#" class="btn btn-primary">More Info</a>
                                                    <a href="#" class="btn btn-primary">Unlink</a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <!--/col-->          
                                <div class="clearfix"></div>
                            </div>
                            <!--/row-->
                        </div>
                        <!--/panel-body-->
                    </div>
                    <!--/panel-->
                </div>
                <!--/col--> 
            </div>
            <!--/row--> 
        </div>
    </body>
</html>
