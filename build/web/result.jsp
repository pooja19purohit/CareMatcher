<%-- 
    Document   : result.jsp
    Created on : Jun 27, 2015, 12:57:24 PM
    Author     : Pooja Purohit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <div class="row well-lg"> 
            <div class="span3" id="${'map'.concat(index)}">
                <img src="${result.imgUrl}">
            </div>
            <div class="span5">
                <input type="hidden" class="search-result" value="${result.getPrimaryAddress().getLatString().concat(":").concat(result.getPrimaryAddress().getLonString())}">
                <!-- Profile Link -->
                <h3><a href="${pageContext.request.contextPath}/profile?name-link=${result.email}">
                        ${result.firstName} ${result.midInit} ${result.lastName}
                    </a></h3>
                <!-- Distance from user -->
                <div class="careInfo">
                    Distance: <label id="${index}" class="distance-label"></label>
                </div>
                <!-- Profile/Practice info -->
                <div class="careInfo">
                    Speciality:
                </div>
                <c:if test="${result.role() eq 'PROVIDER'}">
                <div class="careInfo">
                    Rating: ${result.getOverallRating()}
                </div>
                </c:if>
                <c:if test="${result.role() eq 'PROVIDER'}">
                    <div class="careInfo">
                        # Years Experience: ${result.yearsPracticing}
                    </div>
                    <div class="btn-group btn-group-justified">
                        <a href="requestAppointment.jsp" class="btn btn-primary">Request Appointment</a>
                        <a href="${pageContext.request.contextPath}/profile?name-link=${result.email}&#more-info" class="btn btn-primary">More Info</a>
                    </div>
                </c:if>
            </div>
        </div>
    </body>
</html>
