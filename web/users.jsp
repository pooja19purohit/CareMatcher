<%-- 
    Document   : users
    Created on : May 31, 2015, 12:55:40 PM
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
        <h1>Users</h1>

<table>

  <tr>
    <th>First Name</th>
    <th>Last Name</th>
    <th colspan="3">Email</th>
  </tr>

  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <c:forEach var="customer" items="${customers}">
  <tr>
    <td>${customer.firstName}</td>
    <td>${customer.lastName}</td>
    <td>${customer.email}</td>
    <td><a href="userAdmin?action=display_user&amp;email=${customer.email}">Update</a></td>
    <td><a href="userAdmin?action=delete_user&amp;email=${customer.email}">Delete</a></td>
  </tr>
  </c:forEach>

</table>

<p><a href="userAdmin">Refresh</a></p>
    </body>
</html>
