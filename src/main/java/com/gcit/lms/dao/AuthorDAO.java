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

import com.gcit.lms.entity.Author;
import com.mysql.jdbc.Statement;

public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>>{
	

	public void addAuthor(Author author) throws SQLException{
		template.update("insert into tbl_author(authorName) values (?)", new Object[] {author.getAuthorName()});
		
	}
	
	public void bookAndAuthor(Integer bookId,Integer authorId) throws SQLException{
		template.update("insert into tbl_book_authors(bookId,authorId) values (?,?)", new Object[] {bookId,authorId});
	}
	
	/*
	public Integer addAuthorWithID(Author author) throws SQLException{
		return saveWithID("insert into tbl_author(authorName) values (?)", new Object[] {author.getAuthorName()});
	}
	*/
	
	public void updateAuthor(Author author) throws SQLException{
		template.update("update tbl_author set authorName =? where authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()});
	}
	
	public void deleteAuthor(Author author) throws SQLException{
		template.update("delete from tbl_author where authorId = ?", new Object[] {author.getAuthorId()});
	}
	public Integer addAuthorWithID(Author author,String[] ids) throws SQLException{

		//Integer newAuthorId = saveWithID("insert into tbl_author(authorName) values (?)", new Object[] {author.getAuthorName()});

		/*
		for(int i = 0; i < ids.length; i++){
			template.update("insert into tbl_book_authors(authorId,bookId) values(?,?)",newAuthorId,ids[i]);
		}
		*/
		//Git hub

		return 0; 
	}
	
	
	public Integer addAuthorWithID(Author author) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "insert into tbl_author(authorName) values (?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, author.getAuthorName());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Author> readAllAuthors(Integer pageNo) throws SQLException{
		setPageNo(pageNo);
		String query = "";
		if (getPageNo() > 0) {
			System.out.println(">0");
			int count = 0;
			int index = (getPageNo() - 1) * 10;
			query = " Limit " + index + " , " + getPageSize();
		}
		return template.query("select * from tbl_author" + query, this);
	}
	
	public List<Author> readAllAuthoreByBookId(Integer bookId) throws SQLException {
		return template.query("select * from tbl_author where authorId in (select authorId from tbl_book_authors where bookId = ?)", new Object[]{bookId}, this);
	}
	
	@SuppressWarnings("unchecked")
	public List<Author> readAllAuthors() throws SQLException{
		return template.query("select * from tbl_author", this);
	}
	
	
	public Integer getAuthorsCount() throws SQLException{
		return template.queryForObject("select count(*) as Count from tbl_author", Integer.class);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Author> readAllAuthorsByName(Integer pageNo, String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		setPageNo(pageNo);
		return (List<Author>) template.query("select * from tbl_author where authorName like ?", this);
	}
	
	@SuppressWarnings("unchecked")
	public List<Author> readAllAuthorsNoPage(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		return template.query("select * from tbl_author where authorName like ?",new Object[] {searchString}, this);
	}
	
	
	@SuppressWarnings("unchecked")
	public Author readAuthorsByPK(Integer authorId) throws SQLException{
		System.out.println("AuthorDAO");
		List<Author> authors = template.query("select * from tbl_author where authorId = ?",new Object[] {authorId}, this);
		if(authors!=null){
			return authors.get(0);
		}
		return null;
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}
	
}
