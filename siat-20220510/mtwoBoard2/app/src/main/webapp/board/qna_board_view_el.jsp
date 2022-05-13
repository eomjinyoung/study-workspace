<%@ page language="java" contentType="text/html; charset=euc-kr"%>
<%@ page import="net.board.db.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%
   BoardBean board = (BoardBean)request.getAttribute("boarddata");
%>
 --%>
<html>
<head>
   <title>MVC 게시판</title>
</head>

<body>
<!-- 게시판 수정 -->
<table>
   <tr>
      <td colspan="5">MVC 게시판</td>
   </tr>
   
   <tr>
      <td>제 목&nbsp;&nbsp;</td>
      <td>${requestScope.boarddata.getBOARD_SUBJECT()}</td>
   </tr>
   
   <tr>
      <td colspan="2"></td><!-- 빈 줄 삽입 -->
   </tr>
   
   <tr>
      <td>내 용</td>
      <td>${boarddata.getBOARD_CONTENT()}</td>
   </tr>
   
   <tr>
      <td>첨부파일</td>
      <td>
	      <c:if test="${boarddata.getBOARD_FILE() != null }">
	      	<a href="./boardupload/${boarddata.getBOARD_FILE()}">
	      	  ${boarddata.getBOARD_FILE()}
	        </a>
	      </c:if>
      </td>
      <!-- 자바 코드에 의한 비교 판단 처리를 위의 jstl을 이용하여 태그 형식으로 적용함, 
      jstl과 el을 이용해서 자바 코드가 없는 jsp를 만들 수 있다.-->
   </tr>
   
   <tr><td colspan="2">&nbsp;</td></tr> <!-- 빈줄 삽입 -->
   
   <tr>
      <td colspan="2">
         <a href="./BoardReplyView.bo?num=${boarddata.getBOARD_NUM()}">[답변]</a>&nbsp;&nbsp;
         <a href="./BoardModify.bo?num=${boarddata.getBOARD_NUM()}">[수정]</a>&nbsp;&nbsp;
         <a href="./BoardDelete.bo?num=${boarddata.getBOARD_NUM()}">[삭제]</a>&nbsp;&nbsp;
         <a href="./BoardList.bo">[목록]</a>
      </td>
   </tr>
</table>
<!-- 게시판 수정 -->
<%
	pageContext.setAttribute("irum", "siat777");
%>
${ irum }<br/>

</body>
</html>