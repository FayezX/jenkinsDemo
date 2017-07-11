package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>> {

	public void addPublisher(Publisher publisher) throws SQLException{
		template.update("insert into tbl_publisher(publisherName, publisherAddress, publisherPhone) values (?,?,?)", new Object[] {publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getpublisherPhone()});
	}
	
	/*
	public Integer addPublisherWithID(Publisher publisher) throws SQLException{
		return saveWithID("insert into tbl_publisher(publisherName) values (?)", new Object[] {publisher.getPublisherName()});
	}
	*/

	public void updatePublisher(Publisher publisher) throws SQLException{
		template.update("update tbl_publisher set publisherName =?,publisherAddress =?,publisherPhone=? where PublisherId = ?", new Object[] {publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getpublisherPhone(),publisher.getPublisherId()});
	}

	public void deletePublisher(Publisher publisher) throws SQLException{
		template.update("delete from tbl_publisher where PublisherId = ?", new Object[] {publisher.getPublisherId()});
	}


	@SuppressWarnings("unchecked")
	public List<Publisher> readAllPublishers() throws SQLException{
		return template.query("select * from tbl_publisher",this);
	}

	@SuppressWarnings("unchecked")
	public List<Publisher> readAllPublishersByName(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		return (List<Publisher>) template.query("select * from tbl_publisher where branchName like ?", new Object[]{searchString},this);
	}

	@SuppressWarnings("unchecked")
	public Publisher readPublishersByPK(Integer PublisherId) throws SQLException{
		List<Publisher> Publishers = template.query("select * from tbl_publisher where branchid = ?", new Object[]{PublisherId},this);
		if(Publishers!=null){
			return Publishers.get(0);
		}
		return null;
	}
	/*
	public Integer getPublisherCount() throws SQLException{
		return getCount("select count(*) as Count from tbl_publisher", null);
	}
	*/

	public List<Publisher> readAllPublishersByName(Integer pageNo, String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		setPageNo(pageNo);
		return template.query("select * from tbl_publisher where publisherName like ?", new Object[]{searchString},this);
	}

	@SuppressWarnings("unchecked")
	public List<Publisher> readAllPublishersPagination(Integer pageNo) throws SQLException{
		setPageNo(pageNo);
		return template.query("select * from tbl_publisher", this);
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> Publishers = new ArrayList<>();
		while(rs.next()){
			Publisher a = new Publisher();
			a.setPublisherId(rs.getInt("publisherid"));
			a.setPublisherName(rs.getString("publisherName"));
			a.setPublisherAddress(rs.getString("publisherAddress"));
			a.setpublisherPhone(rs.getString("publisherPhone"));
			Publishers.add(a);
		}
		return Publishers;
	}	
}
