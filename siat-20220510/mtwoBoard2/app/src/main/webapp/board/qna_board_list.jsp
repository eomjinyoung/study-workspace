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
	<title>MVC �Խ���</title>
</head>

<body>
<!-- �Խ��� ����Ʈ -->

<table width=50% border="0" cellpadding="0" cellspacing="0">
<%
if(listcount > 0){ //�ڷᰡ �ִٸ� => ������ ���� 82�����α����̴�.
%>

	<tr><td colspan="4">MVC�Խ���</td><td>�� ���� : ${list.count }</td></tr>
		
	<tr>
		<th>��ȣ</th>
		<th>����</th>
		<th>�ۼ���</th>
		<th>��¥</th>
		<th>��ȸ��</th>
	</tr>
	
	<%
		for(int i=0;i<boardList.size();i++){ //for �ݺ����� ���� 63��
			BoardBean bl=(BoardBean)boardList.get(i);
	%>
	<tr>
		<td><%=bl.getBOARD_NUM()%></td>
		<td>
			<%if(bl.getBOARD_RE_LEV()!=0){ %>
				<%for(int a=0;a<=bl.getBOARD_RE_LEV()*2;a++){ %>
				&nbsp;
				<%} %>
				��
			<%}else{ %>
				��
			<%} %>	<!--�亯 ���� ��� �鿩���⸦ ���ִ� �����̴�. -->
			<a href="./BoardDetailAction.bo?num=<%=bl.getBOARD_NUM()%>">
				<%=bl.getBOARD_SUBJECT()%>
			</a>
		</td>
		
		<td><%=bl.getBOARD_NAME() %></td>
		<td><%=bl.getBOARD_DATE() %></td>	
		<td><%=bl.getBOARD_READCOUNT() %></td>
	</tr>
	
	<%} %> <!-- for �ݺ����� �� -->
	
	<tr>
		<td>
			<%if(nowpage<=1){ %>
			[����]&nbsp;
			<%}else{ %>
			<a href="./BoardList.bo?page=<%=nowpage-1 %>">[����]</a>&nbsp;
			<%} %>
			
			<%for(int a=startpage;a<=endpage;a++){//���� 3�������̰� ��ü �������� 25��������� [1] [2] [3] [4] [5] [6] [7] [8] [9] [10] 
				if(a==nowpage){%>
				[<%=a %>]
				<%}else{ %>
				<a href="./BoardList.bo?page=<%=a %>">[<%=a %>]</a>&nbsp;
				<%} %>
			<%} %>
			
			<%if(nowpage>=maxpage){ %>
			[����]
			<%}else{ %>
			<a href="./BoardList.bo?page=<%=nowpage+1 %>">[����]</a>
			<%} %>
		</td>
	</tr>
	<%
    } //25�� ������ if(listcount > 0) { true ���� ���� ��Ÿ��
	else //�ڷᰡ ���� �� ó���ؾ��� ��
	{
	%>
	<tr>
		<td colspan="4">MVC �Խ���</td>
		<td><font size=2>��ϵ� ���� �����ϴ�.</font></td>
	</tr>
	<%
	}
	%>
	<tr>
		<td colspan="5"><a href="./BoardWrite.bo">[�۾���]</a></td>
	</tr>
</table>

</body>
</html>