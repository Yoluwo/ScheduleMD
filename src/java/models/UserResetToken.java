/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alexz
 */
@Entity
@Table(name = "user_reset_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserResetToken.findAll", query = "SELECT u FROM UserResetToken u")
    , @NamedQuery(name = "UserResetToken.findByUserID", query = "SELECT u FROM UserResetToken u WHERE u.userID = :userID")
    , @NamedQuery(name = "UserResetToken.findByToken", query = "SELECT u FROM UserResetToken u WHERE u.token = :token")
    , @NamedQuery(name = "UserResetToken.findByExpirationdate", query = "SELECT u FROM UserResetToken u WHERE u.expirationdate = :expirationdate")
    , @NamedQuery(name = "UserResetToken.findByIsActive", query = "SELECT u FROM UserResetToken u WHERE u.isActive = :isActive")})
public class UserResetToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "UserID")
    private Integer userID;
    @Basic(optional = false)
    @Column(name = "Token")
    private String token;
    @Column(name = "Expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationdate;
    @Column(name = "is_active")
    private Boolean isActive;

    public UserResetToken() {
    }

    public UserResetToken(Integer userID) {
        this.userID = userID;
    }

    public UserResetToken(Integer userID, String token) {
        this.userID = userID;
        this.token = token;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(Date expirationdate) {
        this.expirationdate = expirationdate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserResetToken)) {
            return false;
        }
        UserResetToken other = (UserResetToken) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.UserResetToken[ userID=" + userID + " ]";
    }
    
}
