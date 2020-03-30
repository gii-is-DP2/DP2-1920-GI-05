<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="Categories">
    <h2>Applications</h2>

    <table id="categoriesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${categories}" var="category">
            <tr>
                <td>
                    <c:out value="${category.name} "/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
        <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/categories/new" htmlEscape="true"/>'>Add category</a>
	</sec:authorize>

</petclinic:layout>
