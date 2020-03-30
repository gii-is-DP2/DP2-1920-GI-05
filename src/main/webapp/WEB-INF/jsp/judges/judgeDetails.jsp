<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="judges">

    <h2>Judge Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${judge.firstName} ${judge.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Address</th>
            <td><c:out value="${judge.address}"/></td>
        </tr>
        <tr>
            <th>City</th>
            <td><c:out value="${judge.city}"/></td>
        </tr>
        <tr>
            <th>Telephone</th>
            <td><c:out value="${judge.telephone}"/></td>
        </tr>
    </table>

    <spring:url value="{judgeId}/edit" var="editUrl">
        <spring:param name="judgeId" value="${judge.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Judge</a>

    <br/>
    <br/>
    <br/>

    
</petclinic:layout>
