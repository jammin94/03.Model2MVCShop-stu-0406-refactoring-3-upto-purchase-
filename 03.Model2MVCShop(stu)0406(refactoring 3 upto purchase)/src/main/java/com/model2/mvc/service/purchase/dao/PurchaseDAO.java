package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.user.dao.UserDAO;

public class PurchaseDAO {
	
	public PurchaseDAO() {}
	
	public Purchase findPurchase(int tranNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM TRANSACTION WHERE TRAN_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		Purchase purchase = null;
		
		ProductDAO productDAO=new ProductDAO();
		UserDAO userDAO = new UserDAO();
		
		while (rs.next()) {
			purchase = new Purchase();
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
			purchase.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchase.setOrderDate(rs.getDate("ORDER_DATE"));
			purchase.setDivyDate(rs.getString("DLVY_DATE"));
		}
		rs.close();
		stmt.close();
		con.close();
		
		return purchase;
	}
	
	
	
	
	
	
	
	
	
	
	public Map<String,Object> getPurchaseList(Search search, String buyerId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql ="SELECT * FROM TRANSACTION WHERE BUYER_ID='"+buyerId+"' ORDER BY TRAN_NO";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		System.out.println("Purchase::Original SQL :: " + sql);
	
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search);
		
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);
		
		ProductDAO productDAO=new ProductDAO();
		UserDAO userDAO = new UserDAO();

		List<Purchase> list = new ArrayList<Purchase>();
			while(rs.next()){
				Purchase purchase = new Purchase();
				purchase.setTranNo(rs.getInt("TRAN_NO"));
				purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
				purchase.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
				purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
				purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
				purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				purchase.setOrderDate(rs.getDate("ORDER_DATE"));
				purchase.setDivyDate(rs.getString("DLVY_DATE"));
				
				list.add(purchase);
			}
		
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}
	
	
	//admin ���忡�� getSaleList�ϱ�.
	public Map<String,Object> getSaleList(Search search) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql ="SELECT * FROM TRANSACTION ORDER BY TRAN_NO";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		System.out.println("Purchase::Original SQL :: " + sql);
	
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search);
		
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);
		
		ProductDAO productDAO=new ProductDAO();
		UserDAO userDAO = new UserDAO();

		List<Purchase> list = new ArrayList<Purchase>();
			while(rs.next()){
				Purchase purchase = new Purchase();
				purchase.setTranNo(rs.getInt("TRAN_NO"));
				purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
				purchase.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
				purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
				purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
				purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				purchase.setOrderDate(rs.getDate("ORDER_DATE"));
				purchase.setDivyDate(rs.getString("DLVY_DATE"));
				
				list.add(purchase);
			}
		
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}

	
	public void insertPurchase(Purchase purchase) throws Exception{
		System.out.println("insertPurchase call");
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO TRANSACTION VALUES (seq_transaction_tran_no.NEXTVAL,?,?,?,?,?,?,?,1,SYSDATE,TO_DATE(?,'YYYY-MM-DD'))";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		//stmt.setString(8, purchase.getTranCode());
		//0->���Ű���
		//1->���ſϷ�
		//2->�����
		//3->��ۿϷ�
		stmt.setString(8, purchase.getDivyDate());
	
		System.out.println("DAO�� DB�� insert�մϴ�");
		stmt.executeUpdate();
		
		con.close();
	}
	
	
	
	public void updatePurchase(Purchase purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE TRANSACTION SET PAYMENT_OPTION=?, RECEIVER_NAME=?, RECEIVER_PHONE=?, DLVY_ADDR=?, DLVY_REQUEST=?, DLVY_DATE=?"
				+ "WHERE TRAN_NO=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());
		
		stmt.executeUpdate();
		
		con.close();
		
	}
	
	//admin�� ����ϱ� ������ �� ���ſϷ�->��ۿϷ�� �����.
	public void updateTranCode(Purchase purchase) throws Exception{
		Connection con = DBUtil.getConnection();
		
		//purchase�� �ִ� trancode ����
		String sql1 = "UPDATE TRANSACTION SET TRAN_STATUS_CODE=? WHERE TRAN_NO=?";
		PreparedStatement stmt = con.prepareStatement(sql1);
		
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getTranNo());
		
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
	}
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
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
	
	// �Խ��� currentPage Row ��  return 
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
