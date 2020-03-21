<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="My Applications">
    <h2>Peticiones</h2>

    <table id="applicationsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Status</th>
            <th>Moment</th>
            <th>Credit Card</th>
            <th>Owner</th>
            <th>Pet</th>
            <th>Tournament</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${applications}" var="application">
            <tr>
                <td>
                    <c:out value="${application.status} "/>
                </td>
                <td>
                    <c:out value="${application.moment} "/>
                </td>
                <td>
                    <c:out value="${application.creditCard} "/>
                </td>
                <td>
                    <c:out value="${pet.id} "/>
                </td>
                <td>
                    <c:out value="${tournament.id} "/>
                </td>     
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>
