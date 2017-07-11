package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.entity.BookLoans;

public class BorrowerService {
	
	@Autowired
	BorrowerDAO bodao;
	
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookLoansDAO bldao;
	
/*
	public void saveAuthor(Author author) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			if (author.getAuthorId() != null) {
				adao.updateAuthor(author);
			} else {
				adao.addAuthor(author);
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	*/

	/*
	//get author by PK
	public LibraryBranch getBranchsByPk(Integer branchId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		try {
			return ldao.readBranchByPK(branchId);
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}
	*/

	public Integer checkBorrowerId(String branchId) throws SQLException {
			return bodao.checkBorId(branchId);
	}
	
	
/*
	public Integer getCopiesForBranch(Integer bookId,Integer branchId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		System.out.println(bookId);
		System.out.println(branchId);

		try {
			Integer a = ldao.getCopiesforBook(bookId,branchId);
			return a;
			//conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return 0;
	}
*/

	public void updatecopy(String bookId,String branchId,String move,String numberOfCopies) throws SQLException {
			bodao.updateCopies(bookId,branchId,move,numberOfCopies);		
	}
	
	public List<BookLoans> getYourLoans(String cardNo) throws SQLException{
		System.out.println("get your loans reached");
			return bldao.readLoans(cardNo);
	}

}
