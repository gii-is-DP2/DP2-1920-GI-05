<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="fields">
    <h2>
        <c:if test="${field['new']}">New </c:if> Field
    </h2>
    <form:form modelAttribute="field" class="form-horizontal" id="add-field-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Name" name="name"/>
            <petclinic:inputField label="Photo (URL)" name="photoURL"/>
            <petclinic:inputField label="Lenght (in meters, 2 fractions)" name="lenght"/>
            <petclinic:inputField label="Breadth (in meters, 2 fractions)" name="breadth"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${field['new']}">
                        <button class="btn btn-default" type="submit">Add Field</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Field</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
