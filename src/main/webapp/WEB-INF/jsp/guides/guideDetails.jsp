<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="guides">

    <h2>Guide Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${guide.firstName} ${guide.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Address</th>
            <td><c:out value="${guide.address}"/></td>
        </tr>
        <tr>
            <th>City</th>
            <td><c:out value="${guide.city}"/></td>
        </tr>
        <tr>
            <th>Telephone</th>
            <td><c:out value="${guide.telephone}"/></td>
        </tr>
    </table>

    <spring:url value="{guideId}/edit" var="editUrl">
        <spring:param name="guideId" value="${guide.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Guide</a>

    <br/>
    <br/>
    <br/>

    
</petclinic:layout>
