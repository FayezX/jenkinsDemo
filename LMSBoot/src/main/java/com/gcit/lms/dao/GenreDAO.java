package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>>{

	public void addGenre(Genre genre) throws SQLException{
		template.update("insert into tbl_genre(genre_name) values (?)", new Object[] {genre.getGenreName()});
	}
	
   /*
	public Integer addGenreWithID(Genre genre) throws SQLException{
		return saveWithID("insert into tbl_genre(genre_name) values (?)", new Object[] {genre.getGenreName()});
	}
	*/

	public void updateGenre(Genre genre) throws SQLException{
		template.update("update tbl_genre set genre_name =? where genre_id = ?", new Object[] {genre.getGenreName(), genre.getGenreId()});
	}
	
	public List<Genre> readAllGenre() throws SQLException{
		return template.query("select * from tbl_genre", this);
	}

	public void deleteGenre(Genre genre) throws SQLException{
		template.update("delete from tbl_genre where genre_id = ?", new Object[] {genre.getGenreId()});
	}

	@SuppressWarnings("unchecked")
	public List<Genre> readAllGenres() throws SQLException{
		return template.query("select * from tbl_genre", this);
	}

	@SuppressWarnings("unchecked")
	public List<Genre> readAllGenresByName(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		return template.query("select * from tbl_genre where genre_name like ?", new Object[]{searchString},this);
	}
	
	public List<Genre> readAllGenreByBookId(Integer bookId) throws SQLException {
		return template.query("select * from tbl_genre where genre_id in (select genre_id from tbl_book_genres where bookId = ?)", new Object[]{bookId}, this);
	}

	@SuppressWarnings("unchecked")
	public Genre readGenresByPK(Integer genreId) throws SQLException{
		List<Genre> genres = template.query("select * from tbl_genre where genre_id = ?", new Object[]{genreId},this);
		if(genres!=null){
			return genres.get(0);
		}
		return null;
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		while(rs.next()){
			Genre a = new Genre();
			a.setGenreId(rs.getInt("genre_id"));
			a.setGenreName(rs.getString("genre_name"));
			genres.add(a);
		}
		return genres;
	}

}
