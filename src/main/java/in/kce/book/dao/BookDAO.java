package in.kce.book.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.kce.book.bean.BookBean;
import in.kce.book.util.DbUtil;

public class BookDAO {
	public int createBook(BookBean bookBean) {
		String sql =
				"INSERT INTO Book_tbl (ISBN, BOOK_TITLE, BOOK_TYPE, AUTHOR_CODE, BOOK_COST) VALUES (?, ?, ?, ?, ?)";

	    try (Connection connection = DbUtil.getDbConnection();
	         PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, bookBean.getISBN());
	        ps.setString(2, bookBean.getBookName());
	        ps.setString(3, String.valueOf(bookBean.getBookType()));
	        ps.setInt(4, bookBean.getAuthor().getAuthorCode());
	        ps.setFloat(5, bookBean.getCost());
	        return ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
	}

	
	public BookBean fetchBook(String ISBN) {
		Connection connection = DbUtil.getDbConnection();
		String sql = "SELECT * from Book_tbl WHERE ISBN = ?";
		try {
		    PreparedStatement ps = connection.prepareStatement(sql);
		    ps.setString(1,ISBN);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

			    BookBean bookBean = new BookBean();

			    bookBean.setISBN(rs.getString("ISBN"));
			    bookBean.setBookName(rs.getString("BOOK_TITLE"));
			    bookBean.setBookType(rs.getString("BOOK_TYPE").charAt(0));
			    bookBean.setAuthor(new AuthorDAO()
			            .getAuthorByCode(rs.getInt("AUTHOR_CODE")));
			    bookBean.setCost(rs.getFloat("BOOK_COST"));

			    return bookBean;
			}


	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;       
	}
}
