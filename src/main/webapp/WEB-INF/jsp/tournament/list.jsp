<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="tournaments">
	<h2>Tournaments</h2>

	<table id="tournamentesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Applications available until</th>
				<th>Prize</th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tournaments}" var="tournament">
				<tr>
					<td><c:out value="${tournament.name}" /></td>
					<td><petclinic:localDate date="${tournament.applyDate}" pattern="yyyy-MM-dd"/></td>
					<td><c:out value="${tournament.prize.amount} ${tournament.prize.currency}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>
