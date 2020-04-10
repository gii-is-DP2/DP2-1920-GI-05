<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="reports">
	<h2>Owners</h2>

	<table id="reports" class="table table-striped">
		<thead>
			<tr>
				<th>Points</th>
				<th>Comments</th>
				<th>Tournament</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${reports}" var="report">
				<tr>
					<td><c:out value="${report.points} " /></td>
					<td><c:out value="${report.comments} " /></td>
					<td><c:out value="${report.tournament} " /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
