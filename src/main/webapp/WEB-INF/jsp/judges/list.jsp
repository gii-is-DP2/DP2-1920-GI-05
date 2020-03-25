<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="judges">
    <h2>Owners</h2>

    <table id="judgesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Address</th>
            <th>City</th>
            <th style="width: 120px">Telephone</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="judge">
            <tr>
                <td>
                    <spring:url value="/judges/{judgeId}" var="judgeUrl">
                        <spring:param name="judgeId" value="${judge.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(judgeUrl)}"><c:out value="${judge.firstName} ${judge.lastName}"/></a>
                </td>
                <td>
                    <c:out value="${judge.address}"/>
                </td>
                <td>
                    <c:out value="${judge.city}"/>
                </td>
                <td>
                    <c:out value="${judge.telephone}"/>
                </td>
      
                
      
<!--
                <td> 
                    <c:out value="${judge.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${judge.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
