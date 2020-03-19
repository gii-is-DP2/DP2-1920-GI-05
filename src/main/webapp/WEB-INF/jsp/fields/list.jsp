<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="fields">
    <h2>Fields</h2>

    <table id="fieldsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Photo</th>
            <th>Lenght</th>
            <th>Breadth</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="field">
            <tr>
                <td>
                    <c:out value="${field.name}"/>
                </td>
                <td>
                    <img SRC="${field.photoURL}" width="250" height="250">
                </td>
                <td>
                    <c:out value="${field.lenght}"/>
                </td>
                <td>
                    <c:out value="${field.breadth}"/>
                </td>       
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
