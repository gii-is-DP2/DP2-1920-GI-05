<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="createTournament">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#apply_Date").datepicker({dateFormat: 'dd/mm/yy'}) &&
                $("#start_date").datepicker({dateFormat: 'dd/mm/yy'}) &&
                $("#end_date").datepicker({dateFormat: 'dd/mm/yy'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${tournament['new']}">New </c:if> Tournament
        </h2>
        <form:form modelAttribute="tournament" class="form-horizontal">
            <input type="hidden" name="id" value="${tournament.id}"/>
            <div class="form-group has-feedback">
               
                <petclinic:inputField label="Name" name="name"/>   
                <petclinic:inputField label="Location" name="location"/>   
                <petclinic:selectField label="Category" name="category"  names="${categories}" size="${categories.size()}"/>
                <petclinic:selectField label="Pet type" name="petType"  names="${types}" size="${types.size()}"/>
                 <petclinic:selectField label="Select field"  name="field"  names="${fields}" size="${fields.size()}"/>
                <petclinic:selectField label="Select judge" name="judge" names="${judges}" size="${judges.size()}"/>
                <petclinic:inputField label="Apply Date" name="applyDate"/>
                <petclinic:inputField label="Start Date" name="startDate"/>
                <petclinic:inputField label="End Date" name="endDate"/>
               	<petclinic:inputField  label="Prize amount" name="prize.amount"/>
               	<petclinic:inputField  label="Prize currency" name="prize.currency"/>
              
                      
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                   
                    <button class="btn btn-default" type="submit">Add tournament</button>
                        
                </div>
            </div>
        </form:form>
        <c:if test="${!tournament['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>