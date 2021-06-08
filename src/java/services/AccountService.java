/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
/**
 *
 * @author epaul
 */
public class AccountService {
    
    private String dbUrl = "jdbc:mysql://localhost:3306/userdb";
    private String dbUname = "root";
    private String dbPassword = "rootpasswordgiven";
    private String dbDriver = "com.mysql.cj.jdbc.Driver";
    
    public void loadDriver(String dbDriver) {
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(dbUrl, dbUname, dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public boolean validate(LoginBean loginBean) {
        loadDriver(dbDriver);
        Connection con = getConnection();
        boolean status = false;
        
        String sql = "Select * from login where username = ? and password = ?";
        
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, loginBean.getUsername());
            ps.setString(2, loginBean.getPassword());
            
        ResultSet rs = ps.executeQuery();
        status = rs.next();
        
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return status;
        
    }
    
    
    public User login(String email, String password) {
        //UserDB userDB = new UserDB();
        User user = null;
        try{
            //user = userDB.get(email);
            //if(password.equals(user.getPassword()) && user.getActive()) {
            //    return user;
            //}
            //else{
            //    user = null;
            //}
        } catch(Exception e){
            
        }
        return user;
    }
    public void changePassword(String email, String oldPassword, String newPassword) {
        
    }
}