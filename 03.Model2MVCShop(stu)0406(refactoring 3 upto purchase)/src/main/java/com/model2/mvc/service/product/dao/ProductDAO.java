package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDAO {
	
	public ProductDAO() {}
	
	
	public int findProductNo(String prodName) throws Exception{
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM PRODUCT WHERE PROD_NAME=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, prodName);
		
		ResultSet rs = stmt.executeQuery();
		
		int no=0;
		while (rs.next()) {
			no = rs.getInt("PROD_NO");
		
		}
		con.close();
		
		return no;
		
	}
	
	
	public Product findProduct(int no) throws Exception{
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM PRODUCT WHERE PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, no);
		
		ResultSet rs = stmt.executeQuery();
		
		Product product = null;
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
		}
		
		con.close();
		
		return product;
	}
	
	
	
	
	
	public Map<String, Object> getProductList(Search search) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql ="SELECT * FROM PRODUCT ";
		
		if(search.getSearchCondition() != null) {
			if(search.getSearchCondition().equals("0")) {
				sql += "WHERE PROD_NO LIKE '%" + search.getSearchKeyword()+"%'";
			}else if(search.getSearchCondition().equals("1")) {
				sql += "WHERE PROD_NAME LIKE '%" + search.getSearchKeyword() + "%'";
			}else if(search.getSearchCondition().equals("2")) {
				sql += "WHERE PRICE LIKE '%" + search.getSearchKeyword()+"%'";
			}
		}
		
		sql +=" ORDER BY PROD_NO";
		
		System.out.println("ProductDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage ???????? ?????? Query ????????
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);
		

		List<Product> list = new ArrayList<Product>();
			while(rs.next()){
				Product product = new Product();
				product.setProdNo(rs.getInt("PROD_NO"));
				product.setProdName(rs.getString("PROD_NAME"));
				product.setProdDetail(rs.getString("PROD_DETAIL"));
				product.setManuDate(rs.getString("MANUFACTURE_DAY"));
				product.setPrice(rs.getInt("PRICE"));
				product.setFileName(rs.getString("IMAGE_FILE"));
				product.setRegDate(rs.getDate("REG_DATE"));
				
				list.add(product);
			}
		
		//==> totalCount ???? ????
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage ?? ?????? ???? ???? List ????
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}
	
	
	
	
	
	public void insertProduct(Product product) throws Exception {
		System.out.println("jdbc ????");
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO PRODUCT VALUES (seq_product_prod_no.NEXTVAL,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
	
		stmt.executeUpdate();
		
		con.close();
	}
	
	
	
	
	
	public void updateProduct(Product product) throws Exception {
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE PRODUCT SET PROD_NAME=?, PROD_DETAIL=?, MANUFACTURE_DAY=?, PRICE=? WHERE PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setInt(5, product.getProdNo());
		
		stmt.executeUpdate();
		
		con.close();
	}
	
	
	// ?????? Page ?????? ???? ???? Row(totalCount)  return
		private int getTotalCount(String sql) throws Exception {
			
			sql = "SELECT COUNT(*) "+
			          "FROM ( " +sql+ ") countTable";
			
			Connection con = DBUtil.getConnection();
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			int totalCount = 0;
			if( rs.next() ){
				totalCount = rs.getInt(1);
			}
			
			pStmt.close();
			con.close();
			rs.close();
			
			return totalCount;
		}
		
		// ?????? currentPage Row ??  return 
		private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT * "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			
			System.out.println("UserDAO :: make SQL :: "+ sql);	
			
			return sql;
		}
	
}
