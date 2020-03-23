<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="tournaments">
	<h2>Tournaments</h2>

	<table id="tournamentesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Pet type</th>	
				<th>Category </th>				
				<th>Applications date</th>
				<th>Start date</th>	
				<th>End Date</th>									
				<th>Prize</th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tournaments}" var="tournament">
				<tr>
					<td><c:out value="${tournament.name}" /></td>
					<td><c:out value="${tournament.petType.name}" /></td>	
					<td><c:out value="${tournament.category.name}" /></td>									
					<td><petclinic:localDate date="${tournament.applyDate}" pattern="yyyy-MM-dd"/></td>
					<td><petclinic:localDate date="${tournament.startDate}" pattern="yyyy-MM-dd"/></td>
					<td><petclinic:localDate date="${tournament.endDate}" pattern="yyyy-MM-dd"/></td>					
					<td><c:out value="${tournament.prize.amount} ${tournament.prize.currency}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	        <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="tournaments/new" htmlEscape="true"/>'>Add tournament</a>
	</sec:authorize>
	
	

</petclinic:layout>
