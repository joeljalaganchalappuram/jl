<%@page contentType="text/html" pageEncoding="UTF-8" import="java.sql.*" %>
<html>
<head><title>Employee List</title></head>
<body>
<h2>Employee Records</h2>
<table border="1">
<tr>
    <th>Employee Code</th>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Email</th>
</tr>
<%
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABC", "root", "Joel*123*abc");
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM employee");
        while (rs.next()) {
%>
<tr>
    <td><%= rs.getInt("employeecode") %></td>
    <td><%= rs.getString("firstname") %></td>
    <td><%= rs.getString("lastname") %></td>
    <td><%= rs.getString("email") %></td>
</tr>
<%
        }
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    }
%>
</table>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <title>Registration Form</title>
</head>
<body>
    <h2>User Registration</h2>
    <form action="RegisterServlet" method="post">
        Name: <input type="text" name="name" required><br><br>
        Email: <input type="text" name="email" required><br><br>
        Age: <input type="number" name="age" required><br><br>
        <input type="submit" value="Register">
    </form>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
        <p style="color:red;"><%= message %></p>
    <%
        }
    %>
</body>
</html>
