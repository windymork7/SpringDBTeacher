package kr.co.DAO;

import java.util.ArrayList;


import kr.co.DTO.BDto;

public class BDao
{
	
	
	// 글목록
	public ArrayList<BDto> boardList()
	{
		ArrayList<BDto> boardList = new ArrayList<BDto>();
		

		return boardList;
	}

	// 글쓰기 등록
	public void write(String bName, String bTitle, String bContent)
	{
		
		
		
	}
	

	// 해당게시글 보기
	public BDto contentView(int bId)
	{
		BDto dto = new BDto();

		return dto;
	}

	// 게시글 수정
	public void modify(int bId, String bName, String bTitle, String bContent)
	{

	}

	// 글 삭제
	public void delete(int bId)
	{
	}
	
	// 댓글 작업
	public void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent)
	{
	}
	
	// 해당 댓글 조회
	public BDto reply_view(int bId)
	{
		BDto dto = new BDto();
		
		
		return dto;
	}

}
