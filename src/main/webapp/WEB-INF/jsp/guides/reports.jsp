<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="guides">
	<h2>Reports</h2>

	<table id="pets" class="table table-striped">
		<thead>
			<tr>
				<th>Points</th>
				<th>Pet name</th>
				<th>Comments</th>
				<th>Tournament</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${reports}" var="report">
				<tr>
					<td><c:out value="${report.points} " /></td>
					<td><c:out value="${report.pet} " /></td>
					<td><c:out value="${report.comments} " /></td>
					<td> <spring:url
							value="/tournaments/{tournamentId}/show" var="tournamentUrl">
							<spring:param name="tournamentId" value="${report.tournament.id}" />
						</spring:url> <a href="${fn:escapeXml(tournamentUrl)}"><c:out
								value="${report.tournament.name}" /></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
