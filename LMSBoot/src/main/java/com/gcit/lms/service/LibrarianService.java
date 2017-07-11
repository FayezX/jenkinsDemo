package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.LibraryBranch;

public class LibrarianService {
	@Autowired
	LibraryBranchDAO ldao;

	public List<LibraryBranch> getAllBranch(Integer pageNo, String searchString) throws SQLException {
			if(searchString != null){
				return ldao.readAllLibraryBranchsByName(pageNo, searchString);
			}else{
				return ldao.readAllBranchPagination(pageNo);
			}
	}

	public LibraryBranch getBranchsByPk(Integer branchId) throws SQLException {
			return ldao.readBranchByPK(branchId);
	}

	public void saveBranchName(LibraryBranch lib) throws SQLException {
			if (lib.getBranchId() != null) {
				ldao.updateLibraryBranch(lib);
			} else {
				ldao.addLibraryBranch(lib);
			}
	}

	public String saveBranchNameByPk(Integer branchId) throws SQLException {
			LibraryBranch a = ldao.readBranchByPK(branchId);
			return a.getBranchName();
			//conn.commit();
	}
	
	public void updateCopies(String newCopy,String bookId,String branchId) throws SQLException{
		ldao.changeCopy(newCopy, bookId, branchId);
	}
	
	/*
	public List<Integer> getBooksIdForBranch(Integer branchId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			bdao.readBooksByPK(bookId) ;
			//conn.commit();
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
	
	public Integer getCopiesForBranch(Book book,LibraryBranch libraryBranch) throws SQLException {
		System.out.println("service");
			Integer a = ldao.getCopiesforBook(book,libraryBranch);
			System.out.println(a);
			return a;
	}
	
	public ArrayList<Integer> getArrayCopiesForBranch(List<LibraryBranch> q) throws SQLException {
		System.out.println("service");
		ArrayList<Integer> arr = ldao.getArrayCopiesforBook(q);
			return arr;
	}

	public void alterCopy(String name, Integer bookId, Integer branchId) throws SQLException{
			//ldao.changeCopy(name,bookId,branchId);
	}
	
	@Transactional
	public Integer saveBranch(LibraryBranch libraryBranch) throws SQLException {
				ldao.updateLibraryBranch(libraryBranch);
			return 0;
	}




}
