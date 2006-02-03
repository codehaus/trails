<%
// Tag file for rendering an ObjectReference property.
// This property-tag is used if TrailsProperty.isObjectReference returns true.
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="trails" tagdir="/WEB-INF/tags" %>

<%@ attribute name="property" required="true" type="java.lang.Object" %>
<%@ attribute name="readOnly" required="true" %>
<c:choose>
	<c:when test='${property.propertyDescriptor.readOnly || readOnly == "true"}'>
		<c:choose>
			<c:when test="${property.valueInObjectTable}">
				<c:forEach var="row" items="${property.value.rows}">
					<c:out value="${row.instance}"/>
				</c:forEach>						
			</c:when>
			<c:otherwise>
				<c:out value="${property.value}"/>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<% // The name should be something like project.projectId. %>
		<select name="<c:out value="${property.propertyDescriptor.name}"/>.<c:out value="${property.value.classDescriptor.identifierDescriptor.name}"/>">
			<c:forEach var="row" items="${property.value.rows}">
				<c:forEach var="column" items="${row.columns}">
					<c:if test="${column.propertyDescriptor.identifier}">
						<c:choose>
							<c:when test="${row.selected}">
								<option value="<c:out value="${column.value}"/>" SELECTED><c:out value="${row.instance}"/>
							</c:when>
							<c:otherwise>
								<option value="<c:out value="${column.value}"/>"><c:out value="${row.instance}"/>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</c:forEach>
		</select>		
	</c:otherwise>
</c:choose>