package com.gcit.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

/**
 * Handles requests for the application home page.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/librarian")
public class LibrarianController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	BorrowerService borrowerService;
	
	@Autowired
	LibrarianService librarianService;
	
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	LibraryBranchDAO ldao;
	
	@Autowired
	LibraryBranch libraryBranch;
	
	@Autowired
	BookLoans bookLoans;
	
	private static final Logger logger = LoggerFactory.getLogger(LibrarianController.class);

	@RequestMapping(value = "/librarianMenu", method = RequestMethod.GET, produces="application/json")
	public List<LibraryBranch> librarianMenu(){
		List<LibraryBranch> libraryBranch = new ArrayList<>();
		try {
			libraryBranch = ldao.readAllLibraryBranchs();
			for(LibraryBranch a : libraryBranch){
				a.setBooks(bdao.readAllBooksByBranchId(a.getBranchId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return libraryBranch;
		
	}
	
	@RequestMapping(value = "/updateBranch", method = RequestMethod.PUT, consumes="application/json", produces="application/json")
	public List<LibraryBranch> updateBranch(@RequestBody LibraryBranch branch){
		List<LibraryBranch> libraryBranch = new ArrayList<>();
		try {
			ldao.updateLibraryBranch(branch);
			libraryBranch = ldao.readAllLibraryBranchs();
			for(LibraryBranch l : libraryBranch){
				l.setBooks(bdao.readAllBooksByBranchId(l.getBranchId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return libraryBranch;
	}
	
	@RequestMapping(value = "/changeCopyOfBook", method = RequestMethod.PUT, consumes="application/json", produces="application/json")
	public void changeCopyOfBook(){
		
	}
	
	@RequestMapping(value = "/viewCopyOfBook", method = RequestMethod.GET,produces="application/json")
	public Integer viewCopyOfBook(@RequestParam("bookId") Integer bookId,@RequestParam("branchId") Integer branchId){
		Integer copies = 0;
		try {
			copies = ldao.viewCopiesInBranch(bookId,branchId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copies;
	}
	
}
