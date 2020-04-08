<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="tournaments">
	<h2>Tournaments</h2>
	<h3>Click on the name for more information</h3>
	<table id="tournamentesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Pet type</th>	
				<th>Category </th>
				<th>Location</th>				
				<th>Applications available until</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tournaments}" var="tournament">
				<tr>
				 	<sec:authorize access="hasAuthority('admin')">
					<td><a href="<c:url value="/tournaments/${tournament.id}/edit" />"><c:out value="${tournament.name}" /></a></td>
					</sec:authorize>
					<sec:authorize access="hasAnyAuthority('owner','judge','guide')">
					<td><a href="<c:url value="/tournaments/${tournament.id}/show" />"><c:out value="${tournament.name}" /></a></td>
					</sec:authorize>
					<td><c:out value="${tournament.petType.name}" /></td>	
					<td><c:out value="${tournament.category.name}" /></td>		
					<td><c:out value="${tournament.location}" /></td>									
					<td><petclinic:localDate date="${tournament.applyDate}" pattern="yyyy-MM-dd"/></td>
<%-- 					<td><petclinic:localDate date="${tournament.startDate}" pattern="yyyy-MM-dd"/></td>
					<td><petclinic:localDate date="${tournament.endDate}" pattern="yyyy-MM-dd"/></td>		 --%>					
					<%-- <c:out value="${tournament.prize.amount} ${tournament.prize.currency}" />	 --%>				
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	        <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/tournaments/new" htmlEscape="true"/>'>Add tournament</a>
	</sec:authorize>
	
	

</petclinic:layout>
