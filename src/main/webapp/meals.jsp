<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {color: green;}
        .excess {color: red;}
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h3>Meal list</h3>
<hr>
<table border="1" cellspacing="0">
<tr>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
</tr>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                    <fmt:parseDate value="${meal.dateTime}" pattern="y-M-d'T'H:m" var="time"/>
                    <fmt:formatDate value="${time}" pattern="yyyy.MM.dd HH:mm" var="dateTime"/>
                    ${dateTime}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>
    </c:forEach>
</table>
</body>
</html>
