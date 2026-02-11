package in.kce.book.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import in.kce.book.bean.BookBean;
import in.kce.book.dao.BookDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

    @WebServlet("/ViewBookServlet")
    public class ViewBookServlet extends HttpServlet {

        protected void doPost(HttpServletRequest request,
                            HttpServletResponse response)
                throws ServletException, IOException {

            response.setContentType("text/html");

            PrintWriter out = response.getWriter();

            String isbn = request.getParameter("isbn");

            BookDAO dao = new BookDAO();
            BookBean book = dao.fetchBook(isbn);

            out.println("<html>");
            out.println("<body>");

            out.println("<h2>Book Details</h2>");

            if (book != null) {

                out.println("Book ISBN : " + book.getISBN() + "<br><br>");
                out.println("Book Title : " + book.getBookName() + "<br><br>");
                out.println("Book Type : " + book.getBookType() + "<br><br>");

                if (book.getAuthor() != null) {
                    out.println("Author Name : " 
                            + book.getAuthor().getAuthorName() + "<br><br>");

                    out.println("Author Contact : " 
                            + book.getAuthor().getContactNo() + "<br><br>");
                }

                out.println("Book Cost : " + book.getCost() + "<br><br>");

            } else {

                out.println("<h3 style='color:red;'>Book NOT Found!</h3>");
            }

            out.println("<a href='viewBook.html'>Search Again</a>");

            out.println("</body>");
            out.println("</html>");
        }
    }
