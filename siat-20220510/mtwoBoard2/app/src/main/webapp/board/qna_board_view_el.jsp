<%@ page language="java" contentType="text/html; charset=euc-kr"%>
<%@ page import="net.board.db.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%
   BoardBean board = (BoardBean)request.getAttribute("boarddata");
%>
 --%>
<html>
<head>
   <title>MVC �Խ���</title>
</head>

<body>
<!-- �Խ��� ���� -->
<table>
   <tr>
      <td colspan="5">MVC �Խ���</td>
   </tr>
   
   <tr>
      <td>�� ��&nbsp;&nbsp;</td>
      <td>${requestScope.boarddata.getBOARD_SUBJECT()}</td>
   </tr>
   
   <tr>
      <td colspan="2"></td><!-- �� �� ���� -->
   </tr>
   
   <tr>
      <td>�� ��</td>
      <td>${boarddata.getBOARD_CONTENT()}</td>
   </tr>
   
   <tr>
      <td>÷������</td>
      <td>
	      <c:if test="${boarddata.getBOARD_FILE() != null }">
	      	<a href="./boardupload/${boarddata.getBOARD_FILE()}">
	      	  ${boarddata.getBOARD_FILE()}
	        </a>
	      </c:if>
      </td>
      <!-- �ڹ� �ڵ忡 ���� �� �Ǵ� ó���� ���� jstl�� �̿��Ͽ� �±� �������� ������, 
      jstl�� el�� �̿��ؼ� �ڹ� �ڵ尡 ���� jsp�� ���� �� �ִ�.-->
   </tr>
   
   <tr><td colspan="2">&nbsp;</td></tr> <!-- ���� ���� -->
   
   <tr>
      <td colspan="2">
         <a href="./BoardReplyView.bo?num= ${boarddata.getBOARD_NUM()}">[�亯]</a>&nbsp;&nbsp;
         <a href="./BoardModify.bo?num= ${boarddata.getBOARD_NUM()}">[����]</a>&nbsp;&nbsp;
         <a href="./BoardDelete.bo?num= ${boarddata.getBOARD_NUM()}">[����]</a>&nbsp;&nbsp;
         <a href="./BoardList.bo">[���]</a>
      </td>
   </tr>
</table>
<!-- �Խ��� ���� -->
<%
	pageContext.setAttribute("irum", "siat777");
%>
${ irum }<br/>

</body>
</html>