<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="judges">
	<h2>Owners</h2>

	<table id="tournaments" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Location</th>
				<th>Category</th>
				<th>Type</th>


			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tournaments}" var="tournament">
				<tr>
					<td><c:out value="${tournament.name} " /></td>
					<td><c:out value="${tournament.location} " /></td>
					<td><c:out value="${tournament.category} " /></td>
					<td><c:out value="${tournament.petType} " /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
