<% 
// Tag file for rendering a collection property
// This property-tag is used if TrailsProperty.isCollection returns true.
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="trails" tagdir="/WEB-INF/tags" %>

<%@ attribute name="property" required="true" type="java.lang.Object" %>
<%@ attribute name="classDescriptor" type="java.lang.Object" %>
<%@ attribute name="identifierValue" type="java.lang.Object" %>
<%@ attribute name="action"%>

<c:if test="${property.valueInObjectTable}">
	<table>
	<c:forEach var="objectDescriptor" items="${property.value.rows}">
		<tr>
		<td>
		<trails:link url="//prepareToEditOrAddAnInstance.htm"
								 className="${property.propertyDescriptor.elementType.name}"
								 identifierValue="${objectDescriptor.id}"
								 linkName="${objectDescriptor.instance}"/>	
		</td>
		<c:if test='${action == "edit"}'>
		<td>
			<trails:link url="/deleteInstance.htm"
									 className="${property.propertyDescriptor.elementType.name}"
									 identifierValue="${objectDescriptor.id}"
									 linkName="delete"/>
		</td>
		</c:if>
		</tr>
	</c:forEach>
	</table>
</c:if>
<c:if test='${action != "search"}'>
	<a href="<c:url value="/prepareToEditOrAddAnInstance.htm">
				<c:param name="type" value="${property.propertyDescriptor.elementType.name}"/>
			 </c:url>">
		Add new
	</a> 
</c:if>

