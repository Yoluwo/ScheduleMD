/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.User;
import models.UserResetToken;

/**
 * UserResetTokenDB contains all database access methods required for the
 * UserResetToken model.
 *
 * @author epaul
 */
public class UserResetTokenDB {

    /**
     * getToken() method will get and return a UserResetToken if the token parameter matches any token in the database
     * @param token - Token to be selected from the Database
     * @return userToken
     * @throws Exception
     */
    public UserResetToken getToken(String token) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            UserResetToken userToken = em.createNamedQuery("UserResetToken.findByToken", UserResetToken.class).setParameter("token", token).getSingleResult();
            return userToken;
        } finally {
            em.close();
        }
    }
    /**
     * getUserID() will get and return a UserResetToken if the parameter userID matches in the UserResetToken table
     * @param userID - UserID to find a UserResetToken in Database
     * @return userToken
     * @throws Exception 
     */
    public UserResetToken getUserID(int userID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            UserResetToken userToken = em.createNamedQuery("UserResetToken.findByUserID", UserResetToken.class).setParameter("userID", userID).getSingleResult();
            return userToken;
        } finally {
            em.close();
        }
    }
    /**
     * update() will update a UserResetToken object in the database to include newly added information
     * @param urt - The UserResetToken to be updated in the database
     * @throws Exception 
     */
    public void update(UserResetToken urt) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(urt);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

}
