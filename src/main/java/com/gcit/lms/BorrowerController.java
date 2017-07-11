package com.gcit.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
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
@RequestMapping(value = "/borrower")
public class BorrowerController {
	
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
	BorrowerDAO bodao;
	
	@Autowired
	LibraryBranchDAO ldao;
	
	@Autowired
	LibraryBranch libraryBranch;
	
	@Autowired
	BookLoans bookLoans;
	
	private static final Logger logger = LoggerFactory.getLogger(BorrowerController.class);
	
	@RequestMapping(value = "/borrowerMenu", method = RequestMethod.GET, produces="application/json")
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
	
	 @RequestMapping(value = "/checkCardNo", method = RequestMethod.GET)
	public Integer checkCardNo(@RequestParam("cardNo") String cardNo){
		try {
			return bodao.checkBorId(cardNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
		
	 @RequestMapping(value = "/checkout", method = RequestMethod.PUT)
	public Integer checkOut(@RequestParam("bookId") String bookId,@RequestParam("branchId") String branchId){
		try {
		     bodao.checkOut(bookId,branchId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		return null;
	}
	 
	@RequestMapping(value = "/return", method = RequestMethod.PUT)
	public Integer returnBook(@RequestParam("bookId") String bookId,@RequestParam("branchId") String branchId ){
		try {
		     bodao.returnBook(bookId,branchId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
}
