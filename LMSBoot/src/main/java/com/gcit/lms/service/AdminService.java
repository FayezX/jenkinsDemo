package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;

public class AdminService {
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	LibraryBranchDAO ldao;
	
	
	/*
	// add or update Author
	@Transactional
	public Integer saveAuthor(Author author) throws SQLException {
			if (author.getAuthorId() != null) {
				adao.updateAuthor(author);
			} else {
				//adao.addAuthor(author);
				return adao.addAuthorWithID(author);
			}
			return 0;
	}
	
	@Transactional
	public void addBookAndAuthor(Integer bookId, Integer authorId) throws SQLException {
				adao.bookAndAuthor(bookId,authorId);
	}

	// add or update Author
	@Transactional
	public void saveBook(Book book) throws SQLException {
			if (book.getBookId() != null) {
				bdao.updateBook(book);
			} else {
				bdao.addBook(book);
			}
	}
	*/

	// add or update Author
	/*
	public void saveBookWithId(Book book,String[] ids,String[] genreId) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			if (book.getBookId() != null) {
				bdao.updateBook(book);
			} else {
				bdao.addBookWithID(book,ids,genreId);
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
	
	// add or update Author
		/*
		public void saveAuthorWithId(Author author,String[] bookids) throws SQLException {
			Connection conn = null;

			conn = cUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			try {
				if (author.getAuthorId() != null) {
					adao.updateAuthor(author);
				} else {
					adao.addAuthorWithID(author, bookids);
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
		
		// add or update Author
	/*
				public void saveBranchWithId(LibraryBranch libraryBranch,String[] bookids) throws SQLException {
					Connection conn = null;

					conn = cUtil.getConnection();
					LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
					try {
						if (libraryBranch.getBranchId() != null) {
							ldao.updateLibraryBranch(libraryBranch);
						} else {
							ldao.addLibraryBranchWithID(libraryBranch,bookids);
							//adao.addAuthorWithID(libraryBranch, bookids);
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
	// add or update Author
	public void savePublisher(Publisher publisher) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		try {
			if (publisher.getPublisherId() != null) {
				pdao.updatePublisher(publisher);
			} else {
				pdao.addPublisher(publisher);
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
	// Delete Author
	@Transactional
	public void DeleteAuthor(Author author) throws SQLException {
			if (author.getAuthorId() != null) {
				adao.deleteAuthor(author);
			}
	}
	
	// Delete Author
	@Transactional
	public void DeleteBook(Book book) throws SQLException {
			if (book.getBookId() != null) {
				bdao.deleteBook(book);
			}
	}
	
	// Delete Author
	public void deleteBranch(LibraryBranch libraryBranch) throws SQLException {
			if (libraryBranch.getBranchId() != null) {
				ldao.deleteLibraryBranch(libraryBranch);
			}
	}
	
	//Get All authors as a list
	public List<Author> getAllAuthors(Integer pageNo, String searchString) throws SQLException {
		List<Author> authors = new ArrayList<>();

			if(searchString != null){
				authors = adao.readAllAuthorsByName(pageNo,searchString);
			}else{
				authors = adao.readAllAuthors(pageNo);

			}
			
			for(Author a : authors){
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
			return authors;
	}
	
	public List<Author> getAllAuthorsNoPage(String searchString) throws SQLException {
			if(searchString != null){
				return adao.readAllAuthorsNoPage(searchString);
			}
			return null;
	}
	
	public List<Author> readAllAuthors() throws SQLException {
			return adao.readAllAuthors();
	}

	public List<Genre> readAllGenre() throws SQLException {
			return gdao.readAllGenres();
	}

	public List<Book> getAllBooks(Integer pageNo, String searchString) throws SQLException {
		List<Book> books = new ArrayList<>();
			if(searchString != null){
				books = bdao.readAllBooksByName(pageNo, searchString);
			}else{
				books = bdao.readAllBooksPagination(pageNo);
			}
			for(Book b : books){
				//a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
				b.setAuthors(adao.readAllAuthoreByBookId(b.getBookId()));
				b.setGenres(gdao.readAllGenreByBookId(b.getBookId()));
			}
			return books;
	}

	public List<LibraryBranch> getAllBranch(Integer pageNo, String searchString) throws SQLException {
		List<LibraryBranch> branches = new ArrayList<>();
			if(searchString != null){
				branches = ldao.readAllLibraryBranchsByName(pageNo, searchString);
			}else{
				branches = ldao.readAllBranchPagination(pageNo);
			}
			
			for(LibraryBranch b : branches){
				//a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
				b.setBooks(bdao.readAllBooksByBranchId(b.getBranchId()));
			}
			return branches;
	}
	*/
	
	
	/*
	public List<Publisher> getAllPublishers(Integer pageNo, String searchString) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		PublisherDAO bdao = new PublisherDAO(conn);
		try {
			if(searchString != null){
				return bdao.readAllPublishersByName(pageNo, searchString);
			}else{
				return bdao.readAllPublishersPagination(pageNo);
			}
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
/*
	//Get All books
	public List<Book> getAllBooks() throws SQLException {
			return bdao.readAllBooks();
	}
	
	//Get All books
	public List<Genre> getAllGenre() throws SQLException {
			return gdao.readAllGenre();
	}
	

	//searchbar
	public List<Author> searchAuthors(Integer pageNo) throws SQLException {
			return adao.readAllAuthors(pageNo);
	}

	//Get count of total authors 
	
	public Integer getAuthorsCount() throws SQLException {
			return adao.getAuthorsCount();
	}
	
	
	//Get count of total authors 
	
	public Integer getBooksCount() throws SQLException {
			return bdao.getBookCount();
	}
	
	
	//Get count of branches 
	public Integer getBranchCount() throws SQLException {
			return ldao.getBranchCount();
	}
	*/
	
	/*
	//Get count of total authors 
	public Integer getPublishersCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		try {
			return pdao.getPublisherCount();
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
/*
	//get author by PK
	public Author getAuthorsByPk(Integer authorId) throws SQLException {
		System.out.println("AdminService");
			return adao.readAuthorsByPK(authorId);
	}
	//get author by PK
	public Book getBooksByPk(Integer bookId) throws SQLException {
			return bdao.readBooksByPK(bookId);
	}
	*/
	
	/*
	public void overrideLoan(String cardNo, String bookId, String branchId, String days) throws SQLException, ParseException {
		Connection conn = null;

		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		try {
				ldao.alterLoans(cardNo,bookId,branchId,days);
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
	public List<Book> getBooksNameTitle(String searchString) throws SQLException{
			return bdao.readAllBooksByTitle(searchString);
	}
	*/
	
	/*
	public LinkedHashMap<Integer,String> getAllLoans() throws SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		BorrowerDAO bdao = new BorrowerDAO(conn);	
		try {
			return bdao.readLoans();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	/*
	@RequestMapping(value = "/viewauthor/{pageNo}/{searchString}", method = RequestMethod.GET, produces="application/json")
	public List<Author> viewAuthor(@PathVariable Integer pageNo, @PathVariable String searchString){
		List<Author> authors = new ArrayList<>(); 
		try {
			if(searchString!=null && searchString.length() > 0){
				authors = adao.readAllAuthorsNoPage(searchString);
			}else{
				authors = adao.readAllAuthors(pageNo); 
			}
			for(Author a : authors){
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
			return authors;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewAuthors", method = RequestMethod.GET)
	public List<Author> viewAuthorNoString(){
		List<Author> authors = new ArrayList<>(); 
		try {
				authors = adao.readAllAuthors();
			for(Author a : authors){
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
			return authors;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST, consumes="application/json")
	public String addAuthor(@RequestBody Author author){
		try {
			adao.addAuthor(author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Author Added!";
		
	}
	
	@RequestMapping(value = "/updateAuthor", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	public List<Author> updateAuthor(@RequestBody Author author){
		List<Author> authors = new ArrayList<>();
		try {
			adao.updateAuthor(author);
			authors = adao.readAllAuthors();
			for(Author a : authors){
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
		
	}
	
	@RequestMapping(value = "/deleteAuthor", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	public List<Author> deleteAuthor(@RequestBody Author author){
		List<Author> authors = new ArrayList<>();
		try {
			adao.deleteAuthor(author);
			authors = adao.readAllAuthors();
			for(Author a : authors){
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}
	
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
	*/
}


// aspect oriented programming //concurrent java
