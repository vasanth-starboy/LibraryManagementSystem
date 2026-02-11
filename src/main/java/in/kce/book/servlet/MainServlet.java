package in.kce.book.servlet;

import java.io.IOException;

import in.kce.book.bean.AuthorBean;
import in.kce.book.bean.BookBean;
import in.kce.book.dao.AuthorDAO;
import in.kce.book.service.AdministratorService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");

        if (operation.equals("AddBook")) {

            String result = addBook(request);

            if (result.equals("SUCCESS")) {
                response.sendRedirect("Menu.html");
            } else if (result.equals("INVALID")) {
                response.sendRedirect("Invalid.html");
            } else if (result.equals("FAILURE")) {
                response.sendRedirect("Failure.html");
            }

        } else if (operation.equals("Search")) {

            String ISBN = request.getParameter("ISBN");
            BookBean bookBean = viewBook(ISBN);

            if (bookBean == null) {
                response.sendRedirect("Invalid.html");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("book", bookBean);
                RequestDispatcher rd = request.getRequestDispatcher("ViewServlet");
                rd.forward(request, response);
            }
        }
    }

    public String addBook(HttpServletRequest request) {

        try {
            String ISBN = request.getParameter("ISBN");
            String bookName = request.getParameter("bookName");
            char bookType = request.getParameter("bookType").charAt(0);
            float cost = Float.parseFloat(request.getParameter("cost"));

            
            int authorCode = Integer.parseInt(request.getParameter("authorCode"));

            AuthorDAO authorDAO = new AuthorDAO();
            AuthorBean author = authorDAO.getAuthorByCode(authorCode);

            if (author == null) {
                return "INVALID";
            }

            BookBean bookBean = new BookBean();
            bookBean.setISBN(ISBN);
            bookBean.setBookName(bookName);
            bookBean.setBookType(bookType);
            bookBean.setCost(cost);
            bookBean.setAuthor(author);

            return new AdministratorService().addBook(bookBean);

        } catch (Exception e) {
            e.printStackTrace(); 
            return "FAILURE";
        }
    }

    public BookBean viewBook(String ISBN) {
        return new AdministratorService().viewBook(ISBN);
    }
}
