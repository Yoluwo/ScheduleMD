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
 *
 * @author epaul
 */
public class UserResetTokenDB {
        public UserResetToken getToken(String token) throws Exception {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();

            try {
                UserResetToken userToken = em.createNamedQuery("UserResetToken.findByToken", UserResetToken.class).setParameter("token", token).getSingleResult();
                return userToken;
            } finally {
                em.close();
            }
        }
        public UserResetToken getUserID(int userID) throws Exception {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();

            try {
                UserResetToken userToken = em.createNamedQuery("UserResetToken.findByUserID", UserResetToken.class).setParameter("userID", userID).getSingleResult();
                return userToken;
            } finally {
                em.close();
            }
        }
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
