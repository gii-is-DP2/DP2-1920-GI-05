<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="create report">

    <jsp:body>
        <h2>
            New Report
        </h2>
        <form:form modelAttribute="report"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${report.id}"/>
            <input type="hidden" name="id" value="${report.judge}"/>
            <input type="hidden" name="id" value="${report.tournament}"/>
            <div class="form-group has-feedback">
        
                <petclinic:inputField label="Points" name="points"/>
                <petclinic:inputField label="Comments" name="comments"/>
                <div class="control-group">
                    <petclinic:selectField name="pet" label="Pet " names="${pets}" size="5"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
            
                    <button class="btn btn-default" type="submit">Add Report</button>
                       
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
