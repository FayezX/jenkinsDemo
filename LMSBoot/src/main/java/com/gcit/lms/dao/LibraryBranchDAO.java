package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.LibraryBranch;
import com.mysql.jdbc.Statement;

public class LibraryBranchDAO extends BaseDAO implements ResultSetExtractor<List<LibraryBranch>> {
	
	

	public void addLibraryBranch(LibraryBranch libraryBranch) throws SQLException{
		template.update("insert into tbl_library_branch(branchName,branchAddress) values (?,?)", new Object[] {libraryBranch.getBranchName(),libraryBranch.getBranchAddress()});
	}
	
	public Integer viewCopiesInBranch(Integer bookId,Integer branchId) throws SQLException{
		return template.queryForObject("select noOfCopies from tbl_book_copies where bookid=? and branchId=?", new Object[] {bookId,branchId},Integer.class);
	}
	

	public int addLibraryBranchWithID(LibraryBranch libraryBranch) throws SQLException{
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "insert into tbl_library_branch(branchName) values (?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, libraryBranch.getBranchName());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue(); 
	}


	public void updateLibraryBranch(LibraryBranch libraryBranch) throws SQLException{
		if((libraryBranch.getBranchName() == null) && (libraryBranch.getBranchAddress() == null)){
			//template.update("update tbl_library_branch set branchAddress=?  where branchId = ?", new Object[] {libraryBranch.getBranchAddress(), libraryBranch.getBranchId()});
			System.out.println("first");
			return;
		}
		if((libraryBranch.getBranchName() == null || libraryBranch.getBranchName().isEmpty() ) && (libraryBranch.getBranchAddress() != null || !libraryBranch.getBranchAddress().isEmpty() )){
			template.update("update tbl_library_branch set branchAddress=?  where branchId = ?", new Object[] {libraryBranch.getBranchAddress(), libraryBranch.getBranchId()});
			System.out.println("second");
			return;
		}
		if((libraryBranch.getBranchName() != null ) && (libraryBranch.getBranchAddress() == null)){
			template.update("update tbl_library_branch set branchName=?  where branchId = ?", new Object[] {libraryBranch.getBranchName(), libraryBranch.getBranchId()});
			System.out.println("third");
			return;
		}
		template.update("update tbl_library_branch set branchAddress=?, branchName=?  where branchId = ?", new Object[] {libraryBranch.getBranchAddress(),libraryBranch.getBranchName(), libraryBranch.getBranchId()});

}
	
	public Integer getCopiesforBook(Book book,LibraryBranch libraryBranch) throws SQLException{
		System.out.println("copies");
		System.out.println(book.getBookId());
		System.out.println(libraryBranch.getBranchId());
		return template.queryForObject("select noOfCopies from tbl_book_copies where bookid=? and branchId=?", new Object[] {book.getBookId(), libraryBranch.getBranchId()}, Integer.class);
	}
	
	public ArrayList<Integer> getArrayCopiesforBook(List<LibraryBranch> q) throws SQLException{
		System.out.println("copies");
		ArrayList<Integer> Copies = new ArrayList<Integer>();
		for(LibraryBranch lib : q){
				for(Book b : lib.getBooks()){
					Integer values = template.queryForObject("select noOfCopies from tbl_book_copies where bookid=? and branchId=?", new Object[] {b.getBookId(),lib.getBranchId()}, Integer.class);  
					System.out.println(values);
					if(values == null){
						Copies.add(0);
					}else{
						Copies.add(values);
					}
				}
				/*
				System.out.println("//////////////////");
				for(Integer i : Copies){
					System.out.println(i);
				}
				*/
	}
		return Copies;
}
	


	public void changeCopy(String name, String bookId,String branchId ) throws SQLException{
		template.update("Update tbl_book_copies set noOfCopies=? where bookId=? and branchId=?",name,bookId,branchId);
	}
	
	
	public void deleteLibraryBranch(LibraryBranch libraryBranch) throws SQLException{
		template.update("delete from tbl_library_branch where branchId = ?", new Object[] {libraryBranch.getBranchId()});
	}
	
	/*
	public void alterLoans(String cardNo, String bookId, String branchId, String days) throws SQLException, ParseException{
		Date a = overLoan("select * from tbl_book_loans where bookId=? and branchId=? and cardNo=?",bookId,branchId,cardNo);
		System.out.println(a);

		Calendar cal = Calendar.getInstance(); 
		cal.setTime(a); 
		cal.add(Calendar.DATE, Integer.parseInt(days)+1);
		a = cal.getTime();
		String newstring = new SimpleDateFormat("yyyy-MM-dd").format(a);
		System.out.println(newstring);
		
		insertLoan("update tbl_book_loans set dueDate=? where bookId=? and branchId=? and cardNo=?",newstring,bookId,branchId,cardNo);
	}
	*/

	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readAllLibraryBranchs() throws SQLException{
		return template.query("select * from tbl_library_branch", this);
	}

	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readAllLibraryBranchsByName(Integer pageNo, String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		setPageNo(pageNo);
		return template.query("select * from tbl_library_branch where branchName like ?", new Object[]{searchString},this);
	}

	@SuppressWarnings("unchecked")
	public LibraryBranch readLibraryBranchsByPK(Integer LibraryBranchId) throws SQLException{
		List<LibraryBranch> LibraryBranchs = template.query("select * from tbl_library_branch where branchid = ?", new Object[]{LibraryBranchId},this);
		if(LibraryBranchs!=null){
			return LibraryBranchs.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readAllBranchPagination(Integer pageNo) throws SQLException{
		setPageNo(pageNo);
		return template.query("select * from tbl_library_branch", this);
	}

	@SuppressWarnings("unchecked")
	public LibraryBranch readBranchByPK(Integer branchId) throws SQLException{
		List<LibraryBranch> libraryBranch = template.query("select * from tbl_library_branch where branchId = ?", new Object[]{branchId},this);
		if(libraryBranch!=null){
			return libraryBranch.get(0);
		}
		return null;
	}
	public Integer getBranchCount() throws SQLException{
		return template.queryForObject("select count(*) as Count from tbl_library_branch", Integer.class);
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> LibraryBranchs = new ArrayList<>();
		while(rs.next()){
			LibraryBranch a = new LibraryBranch();
			a.setBranchId(rs.getInt("branchId"));
			a.setBranchName(rs.getString("branchName"));
			a.setBranchAddress(rs.getString("branchAddress"));
			LibraryBranchs.add(a);
		}
		return LibraryBranchs;
	}


}
