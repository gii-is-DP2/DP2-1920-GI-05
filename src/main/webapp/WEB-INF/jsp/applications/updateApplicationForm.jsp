<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="applications">
    <h2>
        Application
    </h2>
    <form:form modelAttribute="application" class="form-horizontal" id="add-field-form">
        <div class="form-group has-feedback">
        	
        
        	<petclinic:readField label="Tournament" name="tournament" />
        	<petclinic:readField label="Owner" name="owner" />
       	   <petclinic:readField label="Pet" name="pet" />
       	   <petclinic:readField label="Moment" name="moment" />
       	   <petclinic:readField  label="Credit Card" name="creditCard" />				
           <petclinic:selectField label="Status" name="status"  names="${statusType}" size="${statusType.size()}"/>           
           <%-- <petclinic:inputField label="Rejected Reason" name="rejectReason"/> --%>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">                             
                        <button class="btn btn-default" type="submit">Throw application</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
