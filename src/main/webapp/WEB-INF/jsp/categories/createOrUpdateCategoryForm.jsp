<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="categories">

	<h2>
		<c:if test="${category['new']}">New </c:if>
		Category
	</h2>
	<form:form modelAttribute="category" class="form-horizontal" id="add-field-form">
		<input type="hidden" name="id" value="${category.id}" />
		<div class="form-group has-feedback">

			<petclinic:inputField label="Name" name="name" />

		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">

				<button class="btn btn-default" type="submit">Add Category</button>

			</div>
		</div>
	</form:form>
	<c:if test="${!category['new']}">
	</c:if>

</petclinic:layout>