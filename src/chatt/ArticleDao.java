package chatt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Articleを操作するDao.
 * @author hasumi.tsuchida
 *
 */
public class ArticleDao {
	static String tableName = "articles"; 
	/**
	 * 全件検索
	 * @return 記事情報一覧
	 */
	public static ArrayList<Article> findAll(){
		Connection con = DBManager.createConnection();
		String sql = "select id,name,content from "+tableName+" order by id";
		try{
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		ArrayList<Article> articleList = new ArrayList<>();
		
		while(rs.next()){
			Article article = new Article();
			article.setId(rs.getInt("id"));
			article.setName(rs.getString("name"));
			article.setContent(rs.getString("content"));
			articleList.add(article);
		}return articleList;
		
		}catch(SQLException ex){
			System.err.println("SQL ="+sql);
			throw new RuntimeException();
		}finally{
			DBManager.closeConnection(con);
		}
	}
	/**
	 * 情報を追加する
	 * 
	 * @param article　記事
	 */
	public static void insert(Article article){
		Connection con = DBManager.createConnection();
		String sql = "Insert into "+ tableName +"(name, content) values(?,?);";
		try{
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			
			pstmt.setString(1,article.getName());
			pstmt.setString(2,article.getContent());
			
			pstmt.executeUpdate();
		}catch(SQLException ex){
			System.err.println("SQL = "+sql);
			throw new RuntimeException("insert処理に失敗しました",ex);
		}finally{
			DBManager.closeConnection(con);
		}
	}
	
	/**
	 * 情報を消去する
	 * 
	 * @param id　消去するID
	 */
	public static void deleteById(int id){
		Connection con = DBManager.createConnection();
		String sql = "delete from "+tableName + " where id = ?";
		CommentDao.deleteByArticleId(id);
		try{
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}catch(SQLException ex){
			System.err.println("SQL = "+sql);
			throw new RuntimeException("delete処理に失敗しました",ex);
		}finally{
			DBManager.closeConnection(con);
		}
	}
	
	}
