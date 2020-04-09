<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="applications ">
    <h2>Applications</h2>

    <table id="applicationsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Tournament</th>
            <th>Pet</th>
            <th>Status</th>
            <th>Moment</th>
            <th>Credit card</th>
             <sec:authorize access="hasAuthority('admin')">
            <th>Update </th>
            </sec:authorize>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${applications}" var="application">
            <tr>
				<sec:authorize access="hasAuthority('admin')">
				<td><a href="<c:url value="/tournaments/${application.tournament.id}/edit" />"><c:out value="${application.tournament.name}" /></a></td>
				</sec:authorize>
				<sec:authorize access="hasAnyAuthority('owner','judge','guide')">
				<td><a href="<c:url value="/tournaments/${application.tournament.id}/show" />"><c:out value="${application.tournament.name}" /></a></td>
				</sec:authorize>
                <td>
                	<c:out value="${application.pet} "/>
                </td>
                <td>
                   <c:out value="${application.status} "/>
                </td>
                <td>
                    <c:out value="${application.moment} "/>
                </td>
                <td>
                     <c:out value="${application.creditCard} "/>
                </td>
                 <sec:authorize access="hasAuthority('admin')">
                 <td><a href="<c:url value="/applications/${application.id}/edit" />">Edit</a></td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>
