<%-- 
    Document   : index
    Created on : May 27, 2015, 5:23:46 PM
    Author     : kbuck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carematcher.com</title>
    </head>
    <body>
        <section>
            <%@include file="header.html" %>
        </section>
        <section>
            <h1>Hello ${sessionScope.customer.firstName}!</h1><br>
        </section>
        
            <%@include file="footer.html" %>
        
        <%--
        <h2>Enter a Customer:</h2><br>
        
        <form action="custAdmin" method="post">
        <input type="hidden" name="action" value="addUser">        
        <label class="pad_top">Email:</label>
        <input type="email" name="email" value="${customer.email}"><br>
        <label class="pad_top">First Name:</label>
        <input type="text" name="firstName" value="${customer.firstName}" 
               required><br>
        <label class="pad_top">Last Name:</label>
        <input type="text" name="lastName" value="${customer.lastName}"  
               required><br>        
        <label>&nbsp;</label>
        <input type="submit" value="Update" class="margin_left">
    </form>
    --%>
    </body>
</html>
