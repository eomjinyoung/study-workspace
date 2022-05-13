package net.board.action;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;

 public class BoardListAction implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BoardDAO boarddao=new BoardDAO();
		List boardlist=new ArrayList();
		
		int page=1; //보고 싶어하는 페이지를 담을 변수 기본값으로는 1
		int limit=10; //한 페이지에 나타낼 자료의 개수
		
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		int listcount=boarddao.getListCount(); //�� ����Ʈ ���� �޾ƿ�
		boardlist = boarddao.getBoardList(page,limit); //����Ʈ�� �޾ƿ�
		
		//�� ������ ��
 		int maxpage=(int)((double)listcount/limit+0.95); //전체 페이지 수를 계산
 		//���� �������� ������ ���� ������ ��(1, 11, 21 ��...)
 		int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
 		//���� �������� ������ ������ ������ ��(10, 20, 30 ��...)
		int endpage = startpage+10-1;

 		if (endpage> maxpage) endpage= maxpage;

 		
 		request.setAttribute("page", page); //���� ������ ��
 		request.setAttribute("maxpage", maxpage); //�ִ� ������ ��
 		request.setAttribute("startpage", startpage); //���� �������� ǥ���� ù ������ ��
 		request.setAttribute("endpage", endpage); //���� �������� ǥ���� �� ������ ��
		request.setAttribute("listcount",listcount); //�� ��
		request.setAttribute("boardlist", boardlist);
		
		ActionForward forward= new ActionForward();
	 	forward.setRedirect(false);
 		forward.setPath("./board/qna_board_list.jsp");
 		return forward;
	 }
 }