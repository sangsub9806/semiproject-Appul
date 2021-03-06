package model.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.board.DataSourceManager;


public class MemberDAO {
	private static MemberDAO dao=new MemberDAO();
	private DataSource dataSource;
	private MemberDAO(){
		dataSource=DataSourceManager.getInstance().getDataSource();
	}
	public static MemberDAO getInstance(){		
		return dao;
	}	
	public void closeAll(PreparedStatement pstmt,
			Connection con) throws SQLException{
		closeAll(null,pstmt,con);
	}
	public void closeAll(ResultSet rs,PreparedStatement pstmt,
			Connection con) throws SQLException{
		if(rs!=null)
			rs.close();
		if(pstmt!=null)
			pstmt.close();
		if(con!=null)
			con.close();
	}
	
	
	public MemberVO login(String id,String password) throws SQLException{
		MemberVO vo=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			con=dataSource.getConnection();
			String sql="select mem_name from member where id=? and password=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs=pstmt.executeQuery();
			if(rs.next()){
				vo=new MemberVO(id,null,rs.getString(1));
			}
		}finally{
			closeAll(rs, pstmt,con);
		}
		return vo;
	}
	
	public void register(MemberVO vo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;		
		try{
			con=dataSource.getConnection();
			String sql = "insert into member (id, password, mem_name, gender, birth_date, mem_type, mem_number) VALUES (?, ?, ?, ?, ?, ?, member_Seq.nextval)";
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getGender());
			pstmt.setString(5, vo.getDateOfBirth());			
			pstmt.setString(6, vo.getMemberType());
			pstmt.executeUpdate();								
		}finally{
			closeAll(pstmt,con);
		}
	}
	public boolean idcheck(String id) throws SQLException{
		boolean flag=false;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;

		try{
			con=dataSource.getConnection();
			String sql="select count(*) from member where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,id);
			rs=pstmt.executeQuery();
			if(rs.next()&&(rs.getInt(1)>0)){				
				flag=false;
			}else{				
				flag=true;
			}
		}finally{
			closeAll(rs,pstmt,con);
		}
		return flag;
	}
	
	public void update(MemberVO vo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=dataSource.getConnection();
			String sql=
				"update member set password=?,mem_name=?,gender=?,birth_date=? ,mem_type=? where id=?";
			pstmt=con.prepareStatement(sql);			
			
			/*System.out.println("password" + vo.getPassword());
			System.out.println("name" + vo.getName());
			System.out.println("Date" + vo.getDateOfBirth());
			System.out.println("Gender" + vo.getGender());
			System.out.println("MemberType" + vo.getMemberType());
			System.out.println("id" + vo.getId());*/
			
			pstmt.setString(1,vo.getPassword());
			pstmt.setString(2,vo.getName());
			pstmt.setString(3, vo.getGender());
			pstmt.setString(4, vo.getDateOfBirth());	
			pstmt.setString(5, vo.getMemberType());
			pstmt.setString(6,vo.getId());
			pstmt.executeUpdate();			
		}finally{
			closeAll(pstmt,con);
		}
	}
	
	public void delete(String id) throws SQLException{
		System.out.println("탈퇴할 사람 아이디 " +  id);
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=dataSource.getConnection();
			String sql="delete from member where id=?";
			pstmt=con.prepareStatement(sql);			
			pstmt.setString(1,id);
			pstmt.executeUpdate();			
		}finally{
			closeAll(pstmt,con);
		}
	}
}















