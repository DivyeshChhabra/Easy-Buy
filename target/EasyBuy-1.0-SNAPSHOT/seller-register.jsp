<%@ page import="com.mycompany.easybuy.model.Member" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    Member currentMember = (Member) session.getAttribute("current-member");
    pageContext.setAttribute("member", currentMember);
    
    if(currentMember!=null && currentMember.getSellerId() != -1){
        response.sendRedirect("sell.jsp");
        return;
    }
%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Become a Seller</title>
        
        <link rel="stylesheet" href="CSS/style.css">
        
        <%@ include file="Components/common_css_js.jsp" %>
    </head>
    
    <body>
        <%@ include file="Components/navbar.jsp" %>
        
        <div class="container-fluid">
            <div class="row py-5">
                <div class="col-md-6 offset-md-3">
                    <div class="card-body px-5 py-5 bg-white text-black" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h2 class="text-center my-3"><b>BECOME A SELLER</b></h2>
                        
                        <c:if test="${member != null}">
                            <div class="text-center">
                                <b>EMAIL & MOBILE VERIFICATION</b><br><br>
                            </div>

                            <div class="form-group">
                                <p style="width: 100%; text-align:left">
                                    <b>
                                        ${member.getPhoneNumber()}
                                        <span style="float:right; color: green">verified</span>
                                    </b>
                                </p>
                            </div>

                            <div class="form-group">
                                <p style="width: 100%; text-align:left">
                                    <b>
                                        ${member.getEMailAddress()}
                                        <span style="float:right; color: green">verified</span>
                                    </b>
                                </p>
                            </div>

                            <div class="dropdown-divider"></div>
                        </c:if>
                        
                        <form autocomplete="off" action="sell" method="POST" onsubmit="return onSubmit(this)">
                            <input type="hidden" name="demand" value="seller"/>
                            <input type="hidden" name="command" value="add"/>
                            
                            <div class="text-center">
                                <b>ID & SIGNATURE VERIFICATION</b><br><br>
                            </div>
                            
                            <div class="form-group">
                                <label for="account_no"><b>GST ID Number</b></label>
                                <input type="text" class="form-control" id="account_no" placeholder="GSTIN" name="gstin" required />
                            </div>
                            
                            <div class="form-group">
                                <label for="e-signature"><b>e-Signature</b></label> <br/>
                                <a href="#" data-toggle="modal" data-target="#e-signature-modal">Draw Your Signature</a>
                                <input type="hidden" name="verified" value="true"/>
                            </div>
                            
                            <div class="dropdown-divider"></div>
                            
                            <div class="container">
                                <div class="text-center">
                                    <b>BUSINESS DETAILS</b><br><br>
                                </div>

                                <div class="form-group">
                                    <label for="busines_name"><b>Business Name</b></label>
                                    <input type="text" class="form-control" id="company_name" placeholder="Business Name" name="business_name" required/>
                                </div>

                                <div class="form-group">
                                    <label for="store_address"><b>Store Address</b></label>
                                    <textarea class="form-control" id="address" placeholder="Store Address" name="store_address" required></textarea>
                                </div>
                            </div> <br/>
                            
                            <input type="submit" class="btn btn-primary form-control" value="Become A Seller"><br>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
        <%@include file="Components/common_modal.jsp"%>
        
        <script src="Javascript\canvas_script.js"></script>
    </body>
</html>
