package com.gcit.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
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
@RequestMapping(value = "/admin")
public class AdminController {
    //jenkindssdfewfew
    //jenkins 10hgfhfhdferr

	@Autowired
	AdminService adminService;

	@Autowired
	BorrowerService borrowerService;

	@Autowired
	LibrarianService librarianService;

	@Autowired
	AuthorDAO adao;
	
	@Autowired
	Author author;
	
	@Autowired
	Book book;

	@Autowired
	BookDAO bdao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	BookLoansDAO bldao;

	@Autowired
	LibraryBranchDAO ldao;

	@Autowired
	LibraryBranch libraryBranch;

	@Autowired
	BookLoans bookLoans;

	private static final Logger logger = LoggerFactory
			.getLogger(AdminController.class);
	// /////////////////////////////////////////////Author Methods/////////////////////////////////////////////////////////
	@RequestMapping(value = "/viewauthor/{pageNo}/{searchString}", method = RequestMethod.GET, produces = "application/json")
	public List<Author> viewAuthor(@PathVariable Integer pageNo,
			@PathVariable String searchString) {
		List<Author> authors = new ArrayList<>();
		try {
			if (searchString != null && searchString.length() > 0) {
				authors = adao.readAllAuthorsNoPage(searchString);
			} else {
				authors = adao.readAllAuthors(pageNo);
			}
			for (Author a : authors) {
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
			return authors;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewAuthor/{pageNo}", method = RequestMethod.GET, produces = "application/json")
	public List<Author> viewAuthorNoString(@PathVariable Integer pageNo) {
		List<Author> authors = new ArrayList<>();
		try {
			authors = adao.readAllAuthors(pageNo);
			for (Author a : authors) {
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
			return authors;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Author> addAuthor(@RequestBody Author author) throws SQLException {
		Integer aId = null;
		try {
			aId = adao.addAuthorWithID(author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		author.setAuthorId(aId);
		return new ResponseEntity<Author>(author, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/updateAuthor", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public Integer updateAuthor(@RequestBody Author author) {
		List<Author> authors = new ArrayList<>();
		try {
			adao.updateAuthor(author);
			authors = adao.readAllAuthors();
			for (Author a : authors) {
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;

	}

	@RequestMapping(value = "/deleteAuthor", method = RequestMethod.DELETE)
	public Integer deleteAuthor(@RequestParam("authorId") Integer authorId) {
		author.setAuthorId(authorId);
		List<Author> authors = new ArrayList<>();
		try {
			adao.deleteAuthor(author);
			authors = adao.readAllAuthors();
			for (Author a : authors) {
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	// /////////////////////////////////////////////Book
	// Methods/////////////////////////////////////////////////////////
	@RequestMapping(value = "/viewBooks", method = RequestMethod.GET, produces = "application/json")
	public List<Book> viewBooks() {
		List<Book> books = new ArrayList<>();
		try {
			books = bdao.readAllBooks();
			for (Book b : books) {
				b.setAuthors(adao.readAllAuthoreByBookId(b.getBookId()));
			}
			for (Book b : books) {
				b.setGenres(gdao.readAllGenreByBookId(b.getBookId()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	@RequestMapping(value = "/addBook", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		Integer bId = null;
		try {
			//bdao.addBook(book);
			bId = bdao.addBookWithID(book);
			;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		book.setBookId(bId);
		return new ResponseEntity<Book>(book, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/updateBook", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public List<Book> updateBook(@RequestBody Book book) {
		List<Book> books = new ArrayList<>();
		try {
			bdao.updateBook(book);
			;
			books = bdao.readAllBooks();
			for (Book b : books) {
				b.setAuthors(adao.readAllAuthoreByBookId(b.getBookId()));
				;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;

	}

	@RequestMapping(value = "/deleteBook", method = RequestMethod.DELETE)
	public List<Book> deleteBook(@RequestParam("bookId") Integer bookId) {
		book.setBookId(bookId);
		List<Book> books = new ArrayList<>();
		try {
			bdao.deleteBook(book);
			for (Book b : books) {
				b.setAuthors(adao.readAllAuthoreByBookId(b.getBookId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	// /////////////////////////////////////////////Library Branch
	// Methods/////////////////////////////////////////////////////////
	@RequestMapping(value = "/viewBranch", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> viewBranch() {
		List<LibraryBranch> branch = new ArrayList<>();
		try {
			branch = ldao.readAllLibraryBranchs();
			for (LibraryBranch l : branch) {
				l.setBooks(bdao.readAllBooksByBranchId(l.getBranchId()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return branch;
	}

	@RequestMapping(value = "/addBranch", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<LibraryBranch> addBranch(@RequestBody LibraryBranch branch) {
		Integer brId = null;
		try {
			//ldao.addLibraryBranch(branch);
			brId = ldao.addLibraryBranchWithID(branch);
			libraryBranch.setBranchId(brId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<LibraryBranch>(branch, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/updateBranch", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public List<LibraryBranch> updateBranch(@RequestBody LibraryBranch branch) {
		List<LibraryBranch> libraryBranch = new ArrayList<>();
		try {
			ldao.updateLibraryBranch(branch);
			libraryBranch = ldao.readAllLibraryBranchs();
			for (LibraryBranch l : libraryBranch) {
				l.setBooks(bdao.readAllBooksByBranchId(l.getBranchId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return libraryBranch;

	}

	@RequestMapping(value = "/deleteBranch", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public List<LibraryBranch> deleteAuthor(@RequestBody LibraryBranch branch) {
		List<LibraryBranch> libBranch = new ArrayList<>();
		try {
			ldao.deleteLibraryBranch(branch);
			for (LibraryBranch l : libBranch) {
				l.setBooks(bdao.readAllBooksByBranchId(l.getBranchId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return libBranch;
	}
	// /////////////////////////////////////////////Publisher
	// Methods/////////////////////////////////////////////////////////

	// /////////////////////////////////////////////Borrower
	// Methods/////////////////////////////////////////////////////////

	///////////////////////////////////////////////Override
	//Method/////////////////////////////////////////////////////////
	
	@RequestMapping(value = "/overrideBook", method = RequestMethod.PUT)
	public Integer overrideBook(@RequestParam("bookId") String bookId,@RequestParam("branchId") String branchId,@RequestParam("cardNo") String cardNo,@RequestParam("days") String days) {
		try {
			bldao.alterLoans(cardNo, bookId, branchId, days);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}	

}
