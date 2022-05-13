package net.board.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BoardDAO {
  DataSource ds;
  Connection con;
  PreparedStatement pstmt;
  ResultSet rs;
  SqlSessionFactory sqlSessionFactory;

  public BoardDAO() {
    try{
      String resource = "config/mybatis-config.xml";
      InputStream inputStream = Resources.getResourceAsStream(resource);
      sqlSessionFactory = 
          new SqlSessionFactoryBuilder().build(inputStream);


      Context init = new InitialContext();
      ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB"); //context.xml 부분의 name 속성

    }catch(Exception ex){
      System.out.println("DB 연결 실패 : " + ex);
      return;
    }

  }
  //게시판 자료 갯수 = 게시글의 갯수
  public int getListCount() {
    int x= 0;

    SqlSession sqlSession = sqlSessionFactory.openSession(); 
    try{
      x = sqlSession.selectOne("net.board.db.BoardDao.getListCount");
    }catch(Exception ex){
      System.out.println("getListCount 실패: " + ex);			
    }finally{
      if(sqlSession!=null) sqlSession.close();
    }
    return x;
  }

  //�� ��� ����
  public List<BoardBean> getBoardList(int page,int limit){

    int startrow=(page-1)*10+1; //�б� ������ row ��ȣ.
    int endrow=startrow+limit-1; //���� ������ row ��ȣ.		
    SqlSession sqlSession = sqlSessionFactory.openSession();

    try{
      HashMap map = new HashMap();
      map.put("startrow", startrow);
      map.put("endrow", endrow);

      return sqlSession.selectList("net.board.db.BoardDao.getBoardList", map);
    }catch(Exception ex){
      System.out.println("getBoardList 실패	 : " + ex);
    }finally{
      if(sqlSession!=null) sqlSession.close();
    }
    return null;
  }

  //�� ���� ����.
  public BoardBean getDetail(int num) throws Exception{

    BoardBean board = null;
    try{
      con = ds.getConnection();
      pstmt = con.prepareStatement(
          "select * from board where BOARD_NUM = ?");
      pstmt.setInt(1, num);

      rs= pstmt.executeQuery();

      if(rs.next()){
        board = new BoardBean();
        board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
        board.setBOARD_NAME(rs.getString("BOARD_NAME"));
        board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
        board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
        board.setBOARD_FILE(rs.getString("BOARD_FILE"));
        board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
        board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
        board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
        board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
        board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
      }
      return board;
    }catch(Exception ex){
      System.out.println("getDetail ���� : " + ex);
    }finally{
      if(rs!=null)try{rs.close();}catch(SQLException ex){}
      if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
      if(con!=null) try{con.close();}catch(SQLException ex){}
    }
    return null;
  }

  //�� ���
  public boolean boardInsert(BoardBean board){

    int num =0;
    String sql="";

    int result=0;

    try{
      con = ds.getConnection();
      pstmt=con.prepareStatement("select max(board_num) from board");
      rs = pstmt.executeQuery();

      if(rs.next())
        num =rs.getInt(1)+1;
      else
        num=1;

      sql="insert into board (BOARD_NUM,BOARD_NAME,BOARD_PASS,BOARD_SUBJECT,";
      sql+="BOARD_CONTENT, BOARD_FILE, BOARD_RE_REF,"+
          "BOARD_RE_LEV,BOARD_RE_SEQ,BOARD_READCOUNT,"+
          "BOARD_DATE) values(?,?,?,?,?,?,?,?,?,?,sysdate)";

      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, num);
      pstmt.setString(2, board.getBOARD_NAME());
      pstmt.setString(3, board.getBOARD_PASS());
      pstmt.setString(4, board.getBOARD_SUBJECT());
      pstmt.setString(5, board.getBOARD_CONTENT());
      pstmt.setString(6, board.getBOARD_FILE());
      pstmt.setInt(7, num);
      pstmt.setInt(8, 0);
      pstmt.setInt(9, 0);
      pstmt.setInt(10, 0);

      result=pstmt.executeUpdate();
      if(result==0)return false;

      return true;
    }catch(Exception ex){
      System.out.println("boardInsert ���� : "+ex);
    }finally{
      if(rs!=null) try{rs.close();}catch(SQLException ex){}
      if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
      if(con!=null) try{con.close();}catch(SQLException ex){}
    }
    return false;
  }

  //�� �亯
  public int boardReply(BoardBean board){

    String board_max_sql="select max(board_num) from board";
    String sql="";
    int num=0;
    int result=0;

    int re_ref=board.getBOARD_RE_REF();
    int re_lev=board.getBOARD_RE_LEV();
    int re_seq=board.getBOARD_RE_SEQ();

    try{
      con = ds.getConnection();
      pstmt=con.prepareStatement(board_max_sql);
      rs = pstmt.executeQuery();
      if(rs.next())num =rs.getInt(1)+1;
      else num=1;

      sql="update board set BOARD_RE_SEQ=BOARD_RE_SEQ+1 where BOARD_RE_REF=? ";
      sql+="and BOARD_RE_SEQ>?";

      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1,re_ref);
      pstmt.setInt(2,re_seq);
      result=pstmt.executeUpdate();

      re_seq = re_seq + 1;
      re_lev = re_lev+1;

      sql="insert into board (BOARD_NUM,BOARD_NAME,BOARD_PASS,BOARD_SUBJECT,";
      sql+="BOARD_CONTENT, BOARD_FILE,BOARD_RE_REF,BOARD_RE_LEV,BOARD_RE_SEQ,";
      sql+="BOARD_READCOUNT,BOARD_DATE) values(?,?,?,?,?,?,?,?,?,?,sysdate)";

      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, num);
      pstmt.setString(2, board.getBOARD_NAME());
      pstmt.setString(3, board.getBOARD_PASS());
      pstmt.setString(4, board.getBOARD_SUBJECT());
      pstmt.setString(5, board.getBOARD_CONTENT());
      pstmt.setString(6, ""); //���忡�� ������ ���ε����� ����.
      pstmt.setInt(7, re_ref);
      pstmt.setInt(8, re_lev);
      pstmt.setInt(9, re_seq);
      pstmt.setInt(10, 0);
      pstmt.executeUpdate();
      return num;
    }catch(SQLException ex){
      System.out.println("boardReply ���� : "+ex);
    }finally{
      if(rs!=null)try{rs.close();}catch(SQLException ex){}
      if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
      if(con!=null) try{con.close();}catch(SQLException ex){}
    }
    return 0;
  }

  //�� ����
  public boolean boardModify(BoardBean modifyboard) throws Exception{

    String sql="update board set BOARD_SUBJECT=?,BOARD_CONTENT=? where BOARD_NUM=?";

    try{
      con = ds.getConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, modifyboard.getBOARD_SUBJECT());
      pstmt.setString(2, modifyboard.getBOARD_CONTENT());
      pstmt.setInt(3, modifyboard.getBOARD_NUM());
      pstmt.executeUpdate();
      return true;
    }catch(Exception ex){
      System.out.println("boardModify ���� : " + ex);
    }finally{
      if(rs!=null)try{rs.close();}catch(SQLException ex){}
      if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
      if(con!=null) try{con.close();}catch(SQLException ex){}
    }
    return false;
  }

  //�� ����
  public boolean boardDelete(int num){

    String board_delete_sql="delete from board where BOARD_num=?";

    int result=0;

    try{
      con = ds.getConnection();
      pstmt=con.prepareStatement(board_delete_sql);
      pstmt.setInt(1, num);
      result=pstmt.executeUpdate();
      if(result==0)return false;

      return true;
    }catch(Exception ex){
      System.out.println("boardDelete ���� : "+ex);
    }	finally{
      try{
        if(pstmt!=null)pstmt.close();
        if(con!=null) con.close();
      }
      catch(Exception ex){}

    }

    return false;
  }

  //��ȸ�� ������Ʈ
  public void setReadCountUpdate(int num) throws Exception{

    String sql="update board set BOARD_READCOUNT = "+
        "BOARD_READCOUNT+1 where BOARD_NUM = "+num;

    try{
      con = ds.getConnection();
      pstmt=con.prepareStatement(sql);
      pstmt.executeUpdate();
    }catch(SQLException ex){
      System.out.println("setReadCountUpdate ���� : "+ex);
    }
    finally{
      try{
        if(pstmt!=null)pstmt.close();
        if(con!=null) con.close();
      }
      catch(Exception ex){}

    }
  }

  //�۾������� Ȯ��
  public boolean isBoardWriter(int num,String pass){

    String board_sql="select * from board where BOARD_NUM=?";

    try{
      con = ds.getConnection();
      pstmt=con.prepareStatement(board_sql);
      pstmt.setInt(1, num);
      rs=pstmt.executeQuery();
      rs.next();

      if(pass.equals(rs.getString("BOARD_PASS"))){
        return true;
      }
    }catch(SQLException ex){
      System.out.println("isBoardWriter ���� : "+ex);
    }
    finally{
      try{
        if(pstmt!=null)pstmt.close();
        if(con!=null) con.close();
      }
      catch(Exception ex){}

    }
    return false;
  }

}
