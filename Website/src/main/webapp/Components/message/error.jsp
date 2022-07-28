<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% 
    String error = (String) session.getAttribute("error"); 
    pageContext.setAttribute("error", error);
%>
    
<c:if test="${error != null}">
    <div class="alert alert-danger alert-dismissible fade show text-center my-0" role="alert">
        <strong>${error}</strong>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
        
    <% session.removeAttribute("error"); %>
</c:if>
