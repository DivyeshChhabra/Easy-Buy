<%
    String success = (String)session.getAttribute("success");

    if(success!=null){
        %>
            <div class="alert alert-success alert-dismissible fade show text-center my-0" role="alert">
                <strong><%=success%></strong>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        <%
        session.removeAttribute("success");
    }
%>
