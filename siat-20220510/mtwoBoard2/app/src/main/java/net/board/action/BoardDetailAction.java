package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;
import net.board.db.BoardBean;

 public class BoardDetailAction implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{ 
		request.setCharacterEncoding("euc-kr");
   		
		BoardDAO boarddao=new BoardDAO();
	   	BoardBean boarddata=new BoardBean();
	   	
		int num=Integer.parseInt(request.getParameter("num"));
		boarddao.setReadCountUpdate(num); //상세 내용을 보았다는 이야기는 내용을 읽었다는 이야기이므로 테이블에 있는 리드 카운트를 증가
	   	boarddata=boarddao.getDetail(num); //한개의 정보를 가져오면 된다.
	   	
	   	if(boarddata != null){
            request.setAttribute("boarddata", boarddata);
            ActionForward forward = new ActionForward();
            forward.setRedirect(false);
            forward.setPath("./board/qna_board_view_el.jsp");
            return forward;
         }
		return null;
	 }
}