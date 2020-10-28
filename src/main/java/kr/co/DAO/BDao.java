package kr.co.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import kr.co.DTO.BDto;
import kr.co.Util.Constant;

public class BDao
{
	
	JdbcTemplate template;
	
	public BDao()
	{
		this.template = Constant.template;
	}
	
	
	// 글목록
	public ArrayList<BDto> boardList()
	{
		
		String sql = "SELECT * FROM MVC_BOARD START WITH BINDENT=0 CONNECT BY PRIOR BID = BSTEP ORDER SIBLINGS BY BGROUP DESC";

		return (ArrayList<BDto>)template.query(sql, new BeanPropertyRowMapper<BDto>(BDto.class));
	}

	// 글쓰기 등록
	public void write(final String BNAME, final String BTITLE, final String BCONTENT)
	{
		template.update(new PreparedStatementCreator()
		{
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException
			{
				String sql = "INSERT INTO MVC_BOARD (BID, BNAME, BTITLE, BCONTENT, BHIT, BGROUP, BSTEP, BINDENT) VALUES(MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, 0, MVC_BOARD_SEQ.CURRVAL, 0, 0)";
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, BNAME);
				pstmt.setString(2, BTITLE);
				pstmt.setString(3, BCONTENT);
				
				return pstmt;
			}
		});
	}
	

	// 해당게시글 보기
	public BDto contentView(int bId)
	{
		hit(bId);
		
		String sql = "SELECT * FROM MVC_BOARD WHERE BID = " + bId;
		
		return template.queryForObject(sql, new BeanPropertyRowMapper<BDto>(BDto.class));
	}

	// 게시글 수정
	public void modify(final int BID, final String BNAME, final String BTITLE, final String BCONTENT)
	{
		template.update(new PreparedStatementCreator()
		{
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException
			{
				String sql = "UPDATE MVC_BOARD SET BNAME=?, BTITLE=?, BCONTENT=? WHERE BID = ?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, BNAME);
				pstmt.setString(2, BTITLE);
				pstmt.setString(3, BCONTENT);
				pstmt.setInt(4, BID);
				
				return pstmt;
			}
		});
	}

	// 글 삭제
	public void delete(final int BID)
	{
		template.update(new PreparedStatementCreator()
		{
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException
			{
				String sql = "DELETE FROM MVC_BOARD WHERE BID = ?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, BID);
				
				return pstmt;
			}
		});
		
	}
	
	// 댓글 작업
	public void reply(final int BID, final String BNAME, final String BTITLE, final String BCONTENT, final int BGROUP, final int BSTEP, final int BINDENT)
	{
		template.update(new PreparedStatementCreator()
		{
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException
			{
				String sql = "INSERT INTO MVC_BOARD (BID, BNAME, BTITLE, BCONTENT, BHIT, BGROUP, BSTEP, BINDENT) VALUES(MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, 0, ?, ?, ?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, BNAME);
				pstmt.setString(2, BTITLE);
				pstmt.setString(3, BCONTENT);
				pstmt.setInt(4, BGROUP);
				pstmt.setInt(5, BID);
				pstmt.setInt(6, BINDENT+1);
				
				return pstmt;
			}
		});
	}
	
	// 해당 댓글 조회
	public BDto reply_view(int bId)
	{
		String sql = "SELECT * FROM MVC_BOARD WHERE BID = " + bId;
		
		return template.queryForObject(sql, new BeanPropertyRowMapper<BDto>(BDto.class));
	}
	
	// 조회수
	public void hit(final int BID)
	{
		template.update(new PreparedStatementCreator()
		{
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException
			{
				String sql = "UPDATE MVC_BOARD SET BHIT = BHIT+1 WHERE BID = ?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, BID);
				
				return pstmt;
			}
		});
	}

}
