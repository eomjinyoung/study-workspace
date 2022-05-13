<%@ page language="java" contentType="text/html; charset=EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="net.board.db.*" %>

<%
	List<BoardBean> boardList=(List<BoardBean>)request.getAttribute("boardlist");
	int listcount=((Integer)request.getAttribute("listcount")).intValue();
	int nowpage=((Integer)request.getAttribute("page")).intValue();
	int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
	int startpage=((Integer)request.getAttribute("startpage")).intValue();
	int endpage=((Integer)request.getAttribute("endpage")).intValue();
%>

<html>
<head>
	<title>MVC 게시판</title>
</head>

<body>
<!-- 게시판 리스트 -->

<table width=50% border="0" cellpadding="0" cellspacing="0">
<%
if(listcount > 0){ //자료가 있다면 => 수행할 문장 82번라인까지이다.
%>

	<tr><td colspan="4">MVC게시판</td><td>글 개수 : ${list.count }</td></tr>
		
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>날짜</th>
		<th>조회수</th>
	</tr>
	
	<%
		for(int i=0;i<boardList.size();i++){ //for 반복문의 끝은 63번
			BoardBean bl=(BoardBean)boardList.get(i);
	%>
	<tr>
		<td><%=bl.getBOARD_NUM()%></td>
		<td>
			<%if(bl.getBOARD_RE_LEV()!=0){ %>
				<%for(int a=0;a<=bl.getBOARD_RE_LEV()*2;a++){ %>
				&nbsp;
				<%} %>
				▶
			<%}else{ %>
				▶
			<%} %>	<!--답변 글일 경우 들여쓰기를 해주는 구문이다. -->
			<a href="./BoardDetailAction.bo?num=<%=bl.getBOARD_NUM()%>">
				<%=bl.getBOARD_SUBJECT()%>
			</a>
		</td>
		
		<td><%=bl.getBOARD_NAME() %></td>
		<td><%=bl.getBOARD_DATE() %></td>	
		<td><%=bl.getBOARD_READCOUNT() %></td>
	</tr>
	
	<%} %> <!-- for 반복문의 끝 -->
	
	<tr>
		<td>
			<%if(nowpage<=1){ %>
			[이전]&nbsp;
			<%}else{ %>
			<a href="./BoardList.bo?page=<%=nowpage-1 %>">[이전]</a>&nbsp;
			<%} %>
			
			<%for(int a=startpage;a<=endpage;a++){//현재 3페이지이고 전체 페이지가 25페이지라면 [1] [2] [3] [4] [5] [6] [7] [8] [9] [10] 
				if(a==nowpage){%>
				[<%=a %>]
				<%}else{ %>
				<a href="./BoardList.bo?page=<%=a %>">[<%=a %>]</a>&nbsp;
				<%} %>
			<%} %>
			
			<%if(nowpage>=maxpage){ %>
			[다음]
			<%}else{ %>
			<a href="./BoardList.bo?page=<%=nowpage+1 %>">[다음]</a>
			<%} %>
		</td>
	</tr>
	<%
    } //25번 라인의 if(listcount > 0) { true 블럭의 끝을 나타냄
	else //자료가 없을 때 처리해야할 블럭
	{
	%>
	<tr>
		<td colspan="4">MVC 게시판</td>
		<td><font size=2>등록된 글이 없습니다.</font></td>
	</tr>
	<%
	}
	%>
	<tr>
		<td colspan="5"><a href="./BoardWrite.bo">[글쓰기]</a></td>
	</tr>
</table>

</body>
</html>