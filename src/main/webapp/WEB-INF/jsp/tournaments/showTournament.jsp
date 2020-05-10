<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="tournaments">
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
	<div align="center">
	        <h2>
            	<c:out value="${tournament.name} " />
       		 </h2>
     </div>
       <h3>Basic data</h3>
        
  	<table id="tournamentsTable" class="table table-striped">  		
  		<tr>  		
    		<th>Location</th>
    		<td><c:out value="${tournament.location} " /></td>    		
  		</tr>
  		
  		<tr>
  		<th>Pet type</th>
    	<td><c:out value="${tournament.petType} " /></td>    		    		
  		</tr>
  		
  		<tr>
  		<th>Category</th>
  		<td><c:out value="${tournament.category} " /></td>
  		</tr>
  		
  		<tr>
  		<th>Prize</th>
  		<td><c:out value="${tournament.prize.amount} " />  <c:out value="${tournament.prize.currency} " /></td>
  		</tr>  		
	</table>
		
	<h3>Important dates</h3>
	  <table id="tournamentsTable" class="table table-striped">
  			<tr>  			
    		<th>Applications available until:</th>
    		<td>  <c:out value="${tournament.applyDate} " /></td>	
  			</tr>
  		<tr>
    		<th>Starts at</th>
    		<td><c:out value="${tournament.startDate} " /></td>     		
  		</tr>
  		<tr>
  		    	<th>Ends at</th>   
  		    	<td><c:out value="${tournament.endDate} " /></td>    	
  		</tr>
	</table>
	
	<sec:authorize access="hasAuthority('owner')">	
	   <c:choose>
                    <c:when test="${hasApplication == true}">
                        <b>Sorry, but you have already applied to participate with a pet in this tournament"</b>
                    </c:when>
                    <c:when test="${hasApplication == false }">
                        <a class="btn btn-default" href='<spring:url value="/applications/${tournament.id}/new" htmlEscape="true"/>'>Apply for this tournament</a>
                    </c:when>
        </c:choose>
	</sec:authorize>


    </jsp:body>
</petclinic:layout>