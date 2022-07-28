package com.mycompany.easybuy.servlets;

import com.mycompany.easybuy.model.Member;
import com.mycompany.easybuy.model.Category;
import com.mycompany.easybuy.model.Product;
import com.mycompany.easybuy.utility.CategoryUtility;
import com.mycompany.easybuy.utility.ProductUtility;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.annotation.Resource;
import javax.servlet.annotation.* ;

@MultipartConfig(maxFileSize = 16777215)
public class CategoryProductControllerServlet extends HttpServlet {
    
    CategoryUtility categoryUtility;
    ProductUtility productUtility;

    @Resource(name="jdbc/easy_buy")
    DataSource dataSource;
    
    @Override
    public void init(){
        try{
            categoryUtility = new CategoryUtility(dataSource);
            productUtility = new ProductUtility(dataSource);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        try ( PrintWriter out = response.getWriter()) {
            
            String demand = request.getParameter("demand");
            if(demand == null) demand = (String) request.getAttribute("demand");
            
            if(demand != null){
                
                String command = request.getParameter("command");
                if(command == null) command = (String) request.getAttribute("command");
                
                HttpSession session = request.getSession();
                
                if(demand.equals("product")){    

                    switch(command){
                        case "add":
                            addProduct(request,response,session);
                            break;
                        case "get":
                            Product product = getProduct(request,response);

                            request.setAttribute("product", product);

                            RequestDispatcher dispatcherIndex = request.getRequestDispatcher("product.jsp");
                            dispatcherIndex.forward(request, response);
                            break;
                        case "getSellerWise":
                            getProductSellerWise(request,response,session);
                            break;
                        case "update":
                            updateProduct(request,response,session);
                            break;
                        default:
                            break;
                    }
                }else{

                    switch(command){
                        case "add":
                            addCategory(request,response,session);
                            break;
                        default:
                            break;
                    }
                }

                return;
            }
            
            getProductsAndCategories(request,response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /*
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryProductControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryProductControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void addCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, ServletException, IOException {
        
        String name = request.getParameter("category_name");
        String description = request.getParameter("description");
        
        Category category = new Category(name,description);
        categoryUtility.addCategory(category);
        
        session.setAttribute("success", "Category Added Successfully!!");
        response.sendRedirect("sell.jsp");
    }
    
    private void addProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
        
        Member member = (Member) session.getAttribute("current-member");
        
        String name = request.getParameter("product_name");
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        int sellerId = member.getSellerId();
        double unitPrice = Double.parseDouble(request.getParameter("unit_price"));
        double discount = Double.parseDouble(request.getParameter("discount"));
        int inventory = Integer.parseInt(request.getParameter("quantities"));
        Part productImage = request.getPart("product_image");
        double rating = 0.0;
        int reviewCount = 0;
        
        String categoryName = getCategory(categoryId).getName();
        String sellerName = member.getFirstName() + " " + member.getLastName();
        
        int[] ratingDistribution = {0,0,0,0,0};
        
        InputStream webInputStream = null;
        InputStream appInputStream = null;
        
        FileOutputStream webOutputStream = null;
        FileOutputStream appOutputStream = null;
        
        String productImagePath = productImage.getSubmittedFileName();

        if(productImage != null){
            webInputStream = productImage.getInputStream();
            appInputStream = productImage.getInputStream();
        }

        if(!"".equals(productImagePath)){

            String webPath = request.getRealPath("Image") + File.separator + "Products" + File.separator + productImagePath;
            String appPath = "E:" + File.separator + "Study" + File.separator + "Projects" + File.separator + "AP" + File.separator + "EasyBuy" + File.separator + "Mobile App" + File.separator + "app" + File.separator + "src" + File.separator + "main" + File.separator + "res" + File.separator + "drawable" + File.separator + productImagePath.toLowerCase();
            System.out.println(appPath);
            webOutputStream = new FileOutputStream(webPath);
            appOutputStream = new FileOutputStream(appPath);
            System.out.println(appOutputStream);
            byte[] webData = new byte[webInputStream.available()];
            byte[] appData = new byte[webInputStream.available()];
            
            webInputStream.read(webData);
            appInputStream.read(appData);
            
            webOutputStream.write(webData);
            appOutputStream.write(appData);
            
            webOutputStream.close();
            appOutputStream.close();
        }else{
            productImagePath = null;
        }
        
        Product product = new Product(name,description,categoryId,sellerId,unitPrice,discount,inventory,productImagePath,rating,reviewCount,sellerName,categoryName,ratingDistribution);
        productUtility.addProduct(product);
        
        session.setAttribute("success", "Product Added Successfully!!");
        response.sendRedirect("sell.jsp");
    }
    
    private void getProductsAndCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        
        if(request.getParameter("category_id") == null || "all".equals(request.getParameter("category_id"))) getProducts(request,response);
        else getProductsCategoryWise(request,response);
        
        getCategories(request,response);
            
        RequestDispatcher dispatcherIndex = request.getRequestDispatcher("index.jsp");
        dispatcherIndex.forward(request, response);
    }
    
    private void getCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Category> categories = categoryUtility.getCategories();
        request.setAttribute("categories", categories);
    }

    private void getProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        List<Product> products = productUtility.getProducts();
        request.setAttribute("products", products);
    }

    private void getProductsCategoryWise(HttpServletRequest request, HttpServletResponse response) throws SQLException{
        
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        List<Product> productsCategoryWise = productUtility.getProductsCategoryWise(categoryId);
        
        request.setAttribute("products", productsCategoryWise);        
    }
    
    private void getProductSellerWise(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
        
        Member member = (Member) session.getAttribute("current-member");
        if(member == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        int sellerId = member.getSellerId();
        List<Product> myProducts = productUtility.getProductsSellerWise(sellerId);
        
        request.setAttribute("my_products", myProducts);
        request.setAttribute("visit", "my_products");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(request, response);
    }
    
    private Category getCategory(int categoryId) throws SQLException{
        
        Category category = categoryUtility.getCategory(categoryId);
        return category;
    }
    
    private Product getProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        
        int productId = 0;
        if(request.getParameter("product_id") != null) productId = Integer.parseInt(request.getParameter("product_id"));
        else if(request.getAttribute("product_id") != null) productId = (int) request.getAttribute("product_id");
        
        Product product = productUtility.getProduct(productId);
        return product;
    }
    
    private void updateProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, ServletException, IOException{
        
        Product product = getProduct(request,response);
        if(product == null){
            session.setAttribute("error", "Product Not Found");
            response.sendRedirect("dashboard.jsp");
            
            return;
        }
        
        String operation = (String) request.getAttribute("operation");
        if(operation == null) operation = request.getParameter("operation");
        
        switch(operation){
            case "delQuantity":
                int delQuantity = Integer.parseInt(request.getParameter("product_qty"));
                
                product.setInventory(product.getInventory() - delQuantity);
                productUtility.updateProduct(product);
                
                if(request.getParameter("info") != null){
                    response.getWriter().write("");
                    return;
                }

                request.setAttribute("payment_id", request.getAttribute("payment_id"));
                request.setAttribute("command", "add");

                RequestDispatcher dispatcher = request.getRequestDispatcher("orderPlaced");
                dispatcher.forward(request, response);
                
                break;
            case "addQuantity":
                int addQuantity = Integer.parseInt(request.getParameter("quantity"));
                
                product.setInventory(product.getInventory() + addQuantity);
                productUtility.updateProduct(product);
                
                session.setAttribute("success", "Inventory Updated!!");
                response.sendRedirect("dashboard.jsp");
                
                break;
            case "discount":
                double discount = Double.parseDouble(request.getParameter("discount"));
                
                product.setDiscount(discount);
                productUtility.updateProduct(product);
                
                response.sendRedirect("dashboard.jsp");
                
                break;
            case "addReview":
                int rating = (int) request.getAttribute("rating");
                
                double initRating = product.getRating() * product.getReviews();
                
                product.setReviews(product.getReviews() + 1);
                
                double newRating = (initRating + rating) / product.getReviews();
                product.setRating(newRating);
                
                int[] ratingDistribution = product.getRatingDistribution();
                ratingDistribution[rating-1]++;
                product.setRatingDistribution(ratingDistribution);
                
                productUtility.updateProduct(product);
                response.sendRedirect("dashboard.jsp");
                
                break;
            default:
                break;
        }
    }
}
