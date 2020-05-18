<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="New Ranking">
    
    <jsp:body>
        <h2>
            New Ranking
        </h2>
        <form:form modelAttribute="ranking"class="form-horizontal">
            <input type="hidden" name="id" value="${ranking.id}"/>
            <input type="hidden" name="tournament" value="${ranking.tournament}"/>
            <div class="form-group has-feedback">
                
                <div class="control-group">
                    <petclinic:selectField name="pet" label="Type " names="${types}" size="5"/>
                </div>
                 <div class="control-group">
                <petclinic:selectField label="Select guide" name="guide" names="${guides}" size="${guides.size()}"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${pet['new']}">
                            <button class="btn btn-default" type="submit">Add Pet</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Pet</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!pet['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>
