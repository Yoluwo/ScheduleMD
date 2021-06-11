/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import model.User_nonjpa;
/**
 *
 * @author alexz
 */
public class UserDB {
    public UserDB() {
        
    }
    
    public User_nonjpa get(String email) {
        
        return null;
    }
    public User_nonjpa[] getAll() {
        
        return null;
    }
    public void insert(String email, String password,String firstName, String lastName, int roleTypeID){
        
    }
    public void update(String email, String password, String firstName, String lastName, int roleTypeID){
        
    }
    public void delete(String email){
        
    }
}