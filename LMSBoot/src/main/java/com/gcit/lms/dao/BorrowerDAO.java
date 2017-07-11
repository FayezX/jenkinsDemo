package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.Borrower;
import com.mysql.jdbc.Statement;

public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<List<Borrower>> {

	public void addBorrower(Borrower borrower) throws SQLException{
		template.update("insert into tbl_borrower(name,address,phone) values (?,?,?)", new Object[] {borrower.getName(),borrower.getAddress(),borrower.getPhone()});
	}
	
	public Integer addBorrowerWithID(Borrower borrower) throws SQLException{
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "insert into tbl_borrower(name,address,phone) values (?,?,?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, borrower.getName());
				ps.setString(2, borrower.getAddress());
				ps.setString(3, borrower.getPhone());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();		
	}
	
	public void updateBorrower(Borrower borrower) throws SQLException{
		template.update("update tbl_borrower set name =?,address=?,phone=? where cardNo = ?", new Object[] {borrower.getName(),borrower.getAddress(),borrower.getPhone(), borrower.getCardNo()});
	}
	
	public void updateCopies(String bookId, String branchId, String move,String numberOfCopies) throws SQLException{
		System.out.println("move is : " + move);
		System.out.println("numbe rof copies is : " + numberOfCopies);
		System.out.println("book id : " + bookId);
		System.out.println("branchId : " + branchId);

		
		if(move.equalsIgnoreCase("1")){
			template.update("update tbl_book_copies set noOfCopies=? where bookId = ? and branchId = ? ",Integer.parseInt(numberOfCopies)+1,bookId,branchId);
		}else{
			template.update("update tbl_book_copies set noOfCopies=? where bookId = ? and branchId = ? ",Integer.parseInt(numberOfCopies)-1,bookId,branchId);
		}
		
	}
	
	public void checkOut(String bookId,String branchId) throws SQLException{
		Integer copies = template.queryForObject("select noOfCopies from tbl_book_copies where bookId = ? and branchId = ?", new Object[] {bookId,branchId},Integer.class);
		if(copies == 0){
			return;
		}else{
			template.update("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?", new Object[] {Integer.toString(copies-1),bookId,branchId});
		}
    }
	
	public void returnBook(String bookId,String branchId) throws SQLException{
		Integer copies = template.queryForObject("select noOfCopies from tbl_book_copies where bookId = ? and branchId = ?", new Object[] {bookId,branchId},Integer.class);
		template.update("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?", new Object[] {Integer.toString(copies+1),bookId,branchId});
	}
	
	public void deleteBorrower(Borrower borrower) throws SQLException{
		template.update("delete from tbl_borrower where cardNo = ?", new Object[] {borrower.getCardNo()});
	}
	
	public Integer checkBorId(String borrowerId) throws SQLException{
		//return template.queryForObject("select * from tbl_borrower where cardNo=?",new Object[] {borrowerId},Integer.class);
		String query = "select count(*) as Count from tbl_borrower where cardNo=";
		query+=borrowerId;
		return template.queryForObject(query, Integer.class);

	}

	@SuppressWarnings("unchecked")
	public List<Borrower> readAllBorrowers() throws SQLException{
		return template.query("select * from tbl_borrower", this);
	}
	
	public LinkedHashMap<Integer,String> readLoans(String cardNo) throws SQLException{
		/*
		LinkedHashMap<Integer,String> a = readAllLoans("select * from tbl_book_loans");	
		for(Integer b : a.keySet()){
			System.out.println(a.get(b));
		}	
		*/
		/*
		LinkedHashMap<Integer,String> a = template.q("select * from tbl_book_loans where cardNo=" + cardNo,String.class);	
		for(Integer b : a.keySet()){
			System.out.println(a.get(b));
		}
		*/
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Borrower> readAllBorrowersByName(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		return template.query("select * from tbl_borrower where name like ?", new Object[]{searchString},this);
	}
	
	@SuppressWarnings("unchecked")
	public Borrower readBorrowersByPK(Integer BorrowerId) throws SQLException{
		List<Borrower> Borrowers = template.query("select * from tbl_borrower where branchid = ?", new Object[]{BorrowerId},this);
		if(Borrowers!=null){
			return Borrowers.get(0);
		}
		return null;
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> Borrowers = new ArrayList<>();
		while(rs.next()){
			Borrower a = new Borrower();
			a.setCardNo(rs.getInt("cardNo"));
			a.setName(rs.getString("name"));
			a.setAddress(rs.getString("address"));
			a.setPhone(rs.getString("phone"));
		}
		return Borrowers;
	}
		
}
