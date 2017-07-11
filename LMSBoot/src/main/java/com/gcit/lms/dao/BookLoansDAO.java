package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.BookLoans;

public class BookLoansDAO extends BaseDAO implements ResultSetExtractor<List<BookLoans>> {

	public void addBookLoans(BookLoans bookLoan) throws SQLException{
		template.update("insert into tbl_book_loans(bookid,branchid,cardNo,dateOut,dueDate,datein) values (?,?,?,?,?,?)", new Object[] {bookLoan.getBookId(),bookLoan.getBranchId(),bookLoan.getCardNo(),bookLoan.getCardNo(),bookLoan.getDueDate(),bookLoan.getDateIn()});
	}
	
	/*
	public Integer addBookLoansWithID(BookLoans bookLoan) throws SQLException{
		return template.update("insert into tbl_book_loans(bookid,branchid,cardNo,dateOut,dueDate,datein) values (?,?,?,?,?,?)", new Object[] {bookLoan.getBookId(),bookLoan.getBranchId(),bookLoan.getCardNo(),bookLoan.getCardNo(),bookLoan.getDueDate(),bookLoan.getDateIn()});
	}
	*/
	
	public void alterLoans(String cardNo, String bookId, String branchId, String days) throws SQLException{
		Date a = template.queryForObject("select dueDate from tbl_book_loans where bookId=? and branchId=? and cardNo=?",new Object[] {bookId,branchId,cardNo},Date.class);
		System.out.println(a);
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(a); 
		cal.add(Calendar.DATE, Integer.parseInt(days)+1);
		a = cal.getTime();
		String newstring = new SimpleDateFormat("yyyy-MM-dd").format(a);
		System.out.println(newstring);
		template.update("update tbl_book_loans set dueDate=? where bookId=? and branchId=? and cardNo=?",newstring,bookId,branchId,cardNo);
		//insertLoan("update tbl_book_loans set dueDate=? where bookId=? and branchId=? and cardNo=?",newstring,bookId,branchId,cardNo);
		
	}
	
	public List<BookLoans> readLoans(String cardNo) throws SQLException{
		System.out.println("before query");
		return template.query("select * from tbl_book_loans where cardNo="+cardNo, this); 
	}
	

	public void updateBookLoans(BookLoans bookLoan) throws SQLException{
		template.update("update tbl_book_loans set dueDate =?,dateIn=?,where bookId = ? and branchId = ? and cardNo = ?", new Object[] {bookLoan.getDateOut(),bookLoan.getDateIn(),bookLoan.getBookId(), bookLoan.getBranchId(),bookLoan.getCardNo()});
	}

	public void deleteBookLoans(BookLoans bookLoan) throws SQLException{
		template.update("delete from tbl_book_loans where cardNo = ?", new Object[] {bookLoan.getCardNo()});
	}


	@SuppressWarnings("unchecked")
	public List<BookLoans> readAllBookLoanss() throws SQLException{
		return template.query("select * from tbl_book_loans", this);
	}

	@SuppressWarnings("unchecked")
	public List<BookLoans> readAllBookLoanssByName(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		return  template.query("select * from tbl_book_loans where name like ?", new Object[]{searchString},this);
	}

	@SuppressWarnings("unchecked")
	public BookLoans readBookLoanssByPK(Integer BookLoansId) throws SQLException{
		List<BookLoans> BookLoanss = template.query("select * from tbl_book_loans where branchid = ?", new Object[]{BookLoansId},this);
		if(BookLoanss!=null){
			return BookLoanss.get(0);
		}
		return null;
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> BookLoanss = new ArrayList<>();
		while(rs.next()){
			BookLoans a = new BookLoans();
			a.setCardNo(rs.getInt("cardNo"));
			a.setBranchId(rs.getInt("branchId"));
			a.setBookId(rs.getInt("bookId"));
			a.setDateOut(rs.getDate("dateOut"));
			a.setDueDate(rs.getDate("dueDate"));
			a.setDateIn(rs.getDate("dateIn"));
			BookLoanss.add(a);
		}
		return BookLoanss;
	}
}
