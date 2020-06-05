<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="rankings">
	<h2>Rankings</h2>
	<h3>Click on the name to see the ranking</h3>
	<table id="rankingsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Tournament</th>
				<th>date</th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${rankings}" var="ranking">
				<tr>
					<td><a href="<c:url value="/rankings/${ranking.id}/show" />"><c:out value="${ranking.tournament.name}" /></a></td>
					<td><c:out value="${ranking.tournament.endDate}" /></td>	
				</tr>
			</c:forEach>
		</tbody>
	</table>
	

</petclinic:layout>
