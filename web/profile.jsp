<%@page import="javax.servlet.jsp.jstl.core.LoopTagStatus"%>
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
        <link rel="icon" type="image/png" href="images/Logo-trans.png">
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
    </head>
    
    <body>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <jsp:include page="header.jsp" />
        <div class="container">
            <div class="row">
                <div class="col-md-9 col-xs-10 col-md-offset-1" id="user-well">
                    <div class="well panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <h4>${errorMessage}</h4>
                                <div class="col-xs-12 col-sm-4 text-center">
                                    <img src="${profile.imgUrl}" alt="" class="center-block img-circle img-thumbnail img-responsive">
                                    <ul class="list-inline ratings text-center" title="Ratings">
                                        <li><a href="#"><span class="fa fa-star fa-lg"></span></a></li>
                                        <li><a href="#"><span class="fa fa-star fa-lg"></span></a></li>
                                        <li><a href="#"><span class="fa fa-star fa-lg"></span></a></li>
                                        <li><a href="#"><span class="fa fa-star fa-lg"></span></a></li>
                                        <li><a href="#"><span class="fa fa-star fa-lg"></span></a></li>
                                    </ul>
                                </div>
                                <!--/col--> 
                                <div class="col-xs-8 col-sm-8">
                                    <h2>${user.firstName} ${user.midInit} ${user.lastName}</h2>
                                    <h4><strong>${user.primaryAddress.street}</strong></h4>
                                    <h5>
                                        ${user.primaryAddress.city},&nbsp;
                                        ${user.primaryAddress.st}&nbsp;
                                        ${user.primaryAddress.zip}
                                    </h5>
                                </div>
                                <div class="col-xs-4 col-sm-4">
                                    <c:if test="${sessionScope.user.role() eq 'CUSTOMER'
                                                  and 'CUSTOMER' ne profile.role()
                                                  and profile.role() != null }">
                                        <form method="POST" action="editProfile" class="form-inline col-lg-4">
                                            <input type="hidden" name="userid" value="${profile.email}">
                                            <c:if test="${sessionScope.user.isLinkedWithProvider(profile)}">
                                                <input type="hidden" name="service" value="unlink-user">
                                                <input type="submit" class="btn btn-primary" value="Unlink from this ${profile.role().toString()}">
                                            </c:if>
                                            <c:if test="${not sessionScope.user.isLinkedWithProvider(profile)}">
                                                <input type="hidden" name="service" value="link-user">
                                                <input type="submit" class="btn btn-warning" value="Link to this ${profile.role().toString()}">
                                            </c:if>
                                            <c:if test="${sessionScope.user.hasReviewedProvider(profile)}">
                                                <input type="hidden" name="service" value="show-reviews">
                                                <input type="submit" class="btn btn-primary" value="Show Reviews">
                                            </c:if>
                                            <c:if test="${not sessionScope.user.hasReviewedProvider(profile)}">
                                                <input type="hidden" name="service" value="review-user">
                                                <input type="submit" class="btn btn-warning" value="Review this ${profile.role().toString()}">
                                            </c:if>
                                        </form>
                                    </c:if>
                                </div>
                                <!--/col-->          
                                <div class="clearfix"></div>
                                <div class="col-xs-12 col-sm-6">
                                    <p><small>Phone:</small></p>
                                    <c:forEach var="phone" items="${ user.phones }" >
                                        <h4><strong>${phone.phoneNumber}</strong>:&nbsp; (${phone.phoneTypeString})</h4>
                                    </c:forEach>
                                </div>
                                <!--/col-->
                                <div class="col-xs-12 col-sm-6">
                                    <p><small>Email</small></p>
                                    <h4><strong>${user.email}</strong></h4>
                                </div>
                                <!--/col-->
                                <div>
                                    <div class="col-sm-6">
                                        <button class="btn btn-success btn-block"><span class="fa fa-plus-circle"></span> Get Directions </button>
                                    </div>
                                    <div class="col-sm-6">
                                        <button id="contact" class="btn btn-info btn-block"><span class="fa fa-user"></span> Contact </button>
                                    </div>
                                </div>
                                <!--/col-->
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
        <!--/container-->
        <div class="container">
            <div class="row">
                <div class="col-md-9 col-xs-10 col-md-offset-1" id="bio-well">
                    <div class="well panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-xs-12 text-left">
                                    <h3>Biography:</h3>
                                </div>
                                <div class="col-xs-12">
                                    <p id="bio-area" class="col-xs-12">${user.biography}</p>
                                </div>
                                <c:if test="${sessionScope.user.getUserId() eq profile.getUserId()}">
                                    <div class="col-xs-12 text-left">
                                        <%@include file="modalBioEdit.html" %>
                                    </div>
                                </c:if>
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
        <div class="container">
            <div class="row">
                <div class="col-md-9 col-xs-10 col-md-offset-1" id="content-1">
                    <div class="well panel panel-default">
                        <div class="panel-body" id="list-area">
                            <c:choose>
                                <c:when test="${user.role() eq 'CUSTOMER'}">
                                    <a name="linked-providers"></a>
                                    <h3>List of Linked Care Providers:</h3>
                                    <input type="hidden" id="user-info" value="${sessionScope.user.getPrimaryAddress().getLatString().concat(":").concat(sessionScope.user.getPrimaryAddress().getLonString())}">
                                    <c:forEach var="prov" items="${user.getLinkedProviders()}" varStatus="loop">
                                        <% request.setAttribute("result", pageContext.getAttribute("prov")); 
                                           request.setAttribute("index", ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex());%>
                                        <jsp:include page="result.jsp"></jsp:include>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${user.role() eq 'PROVIDER'}">
                                    <a name="serivces-provided"></a>
                                    <h3>List of Services Provided:</h3>
                                    <c:forEach var="serv" items="${user.servicesPerformed}">
                                        <% request.setAttribute("result", pageContext.getAttribute("serv")); %>
                                        <jsp:include page="serviceResult.jsp"/>
                                    </c:forEach>
                                    <c:if test="${sessionScope.user.role() eq 'PROVIDER'}">
                                        <jsp:include page="modalServiceSelect.jsp"></jsp:include>
                                    </c:if>
                                </c:when>
                                <c:when test="${user.role() eq 'PRACTICE'}">
                                    <a name="providers-employed"></a>
                                    <h3>List of Providers At Practice:</h3>
                                    <c:forEach var="prov" items="${user.employedProviders}">
                                        <% request.setAttribute("result", pageContext.getAttribute("prov")); %>
                                        <jsp:include page="result.jsp"></jsp:include>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                                
                            <!--/col-->          
                            <div class="clearfix"></div>
                            
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
        <!--
        <div class="container">
            <div class="row">
                <div class="col-md-9 col-xs-10 col-md-offset-1" id="content-2">
                    <div class="well panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-xs-12 col-sm-4 text-center">
                                    <img src="images/200x200.jpg" alt="" class="center-block img-thumbnail img-responsive">
                                </div>
                                
                                <div class="col-xs-12 col-sm-8">
                                    <h2>Get Directions:</h2>
                                </div>
                                      
                                <div class="clearfix"></div>
                            </div>
                            
                        </div>
                        
                    </div>
                    
                </div>
                
            </div>
            
        </div>
        -->
        <jsp:include page="footer.jsp" />
        <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="scripts/modalBioEdit.js"></script>
        <script src="scripts/modalServiceSelect.js" type="text/javascript"></script>
        <script src="scripts/modalServiceEdit.js" type="text/javascript"></script>
        <script src="scripts/mapping.js" type="text/javascript"></script>
    </body>
</html>
