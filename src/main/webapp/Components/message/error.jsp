<%
    String error = (String)session.getAttribute("error");
    
    if(error!=null){
        %>
            <div class="alert alert-warning alert-dismissible fade show text-center my-0" role="alert">
                <strong><%=error%></strong>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        <%
        session.removeAttribute("error");
    }

%>
