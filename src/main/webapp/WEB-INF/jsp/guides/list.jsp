<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="guides">
    <h2>Owners</h2>

    <table id="guidesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Address</th>
            <th>City</th>
            <th style="width: 120px">Telephone</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="guide">
            <tr>
                <td>
                    <spring:url value="/guides/{guideId}" var="guideUrl">
                        <spring:param name="guideId" value="${guide.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(guideUrl)}"><c:out value="${guide.firstName} ${guide.lastName}"/></a>
                </td>
                <td>
                    <c:out value="${guide.address}"/>
                </td>
                <td>
                    <c:out value="${guide.city}"/>
                </td>
                <td>
                    <c:out value="${guide.telephone}"/>
                </td>
      
                
      
<!--
                <td> 
                    <c:out value="${guide.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${guide.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
