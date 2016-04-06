<%-- 
    Document   : searchResults
    Created on : Jun 9, 2015, 8:35:28 PM
    Author     : kbuck
--%>

<%@page import="javax.servlet.jsp.jstl.core.LoopTagStatus"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/responsive-bootstrap.css">
        <link rel="stylesheet" href="styles/bootstrap.css">
        <link rel="stylesheet" href="styles/footer.css">
        <link rel="stylesheet" href="styles/main.css">
        <link rel="icon" type="image/png" href="images/Logo-trans.png">
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
        <title>Search Results</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <section>
            <div class="row-fluid well-lg">
                <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                <c:choose>
                    <c:when test="${fn:length(searchResults) == 0}">
                        <h5>No results found, please try again.</h5>
                    </c:when>
                    <c:when test="${fn:length(searchResults) <= 10}">
                        <h5>Showing all ${fn:length(searchResults)} results: (${searchTime})</h5>
                    </c:when>
                    <c:otherwise>
                        <h5>Showing the top 10 results: (${searchTime})</h5>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
        <section>
            <input type="hidden" id="user-info" value="${sessionScope.user.getPrimaryAddress().getLatString().concat(":").concat(sessionScope.user.getPrimaryAddress().getLonString())}">
            <!-- display search results -->
            <c:forEach var="single-result" items="${searchResults}" varStatus="loop" >
                <!-- use scriptlet to set a request attribute to forward to the jsp include -->
                <% request.setAttribute("result", pageContext.getAttribute("single-result")); 
                   request.setAttribute("index", ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex()); %>
                <jsp:include page="result.jsp"></jsp:include>
            </c:forEach>
        </section>
            
        <jsp:include page="footer.jsp" />
        <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="scripts/mapping.js" type="text/javascript"></script>
    </body>
</html>
