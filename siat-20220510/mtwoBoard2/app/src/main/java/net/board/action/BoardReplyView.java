package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;
import net.board.db.BoardBean;

public class BoardReplyView implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	 	throws Exception{
		 	ActionForward forward = new ActionForward();
		 	
			BoardDAO boarddao=new BoardDAO();
	   		BoardBean boarddata=new BoardBean();
	   		
	   		int num=Integer.parseInt(request.getParameter("num"));
	   		
	   		boarddata=boarddao.getDetail(num);
	   		
	   		if(boarddata==null){
	   			System.out.println("답변글을 작성하려고 했으나 해당 원 글이 삭제되었거나 존재하지 않습니다.");
	   			return null;
	   		}
	   		System.out.println("���� ������ �̵� �Ϸ�");
	   		
	   		request.setAttribute("boarddata", boarddata);
	   		
	   		forward.setRedirect(false);
	   		forward.setPath("./board/qna_board_reply.jsp");
	   		return forward;
	}
}