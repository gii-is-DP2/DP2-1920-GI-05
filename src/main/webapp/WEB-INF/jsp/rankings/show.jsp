<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="ranking">

	<h2>Ranking Information</h2>

	<td><b><c:out value="${ranking.tournament.name}" /></b></td>

	<table id="rankingsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>	
				<th>Score </th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ranking.podium}" var="podium">
				
				<tr>
					<td><c:out value="${podium.key}" /></td>
					<td><c:out value="${podium.value}" /></td>	
				</tr>
			</c:forEach>
		</tbody>
	</table>


</petclinic:layout>
