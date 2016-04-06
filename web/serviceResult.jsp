<%-- 
    Document   : serviceResult
    Created on : Jun 29, 2015, 1:02:48 PM
    Author     : Pooja Purohit
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
        <div class="row well-lg" id="service-container"> 
            <div class="col-lg-10">
                <input type="hidden" id="serv-name" value="${result.name}">
                <!-- Service Title -->
                <h3>${result.name}</h3>
                <!-- Result Description: If null, link to add description -->
                <div class="careInfo">
                    <c:if test="${not empty result.description}">
                        <p class="col-sm-offset-1">${result.description}</p>
                    </c:if>
                    <c:if test="${empty result.description}">
                        <jsp:include page="modalServiceEdit.html"></jsp:include>
                    </c:if>
                </div>
            </div>
            <div class="col-lg-2 text-right">
                <c:if test="${sessionScope.user.role() eq 'PROVIDER'}">
                    <input type="button" id="serv-delete" class="btn col-md-3 btn-xs btn-link" value="X">
                </c:if>
            </div>
        </div>
    </body>
</html>
