<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="guides">
	<h2>Pets</h2>

	<table id="pets" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>birth_date</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pets}" var="pet">
				<tr>
					<td><c:out value="${pet.name} " /></td>
					<td><c:out value="${pet.birthDate} " /></td>
									</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
