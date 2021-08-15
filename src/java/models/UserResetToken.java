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
 * Auto generated model. This class models all of the data stored in the UserResetToken
 * table in the database as a UserResetToken object.
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
    
    /**
     * Holds the serialization UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds the ID of the user that the UserResetToken belongs to.
     */
    @Id
    @Basic(optional = false)
    @Column(name = "UserID")
    private Integer userID;
    
    /**
     * Holds the token of the UserResetToken.
     */
    @Basic(optional = false)
    @Column(name = "Token")
    private String token;
    
    /**
     * Holds the expiration date of the UserResetToken.
     */
    @Column(name = "Expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationdate;
    
    /**
     * Holds the boolean that states if the UserResetToken is active or not.
     */
    @Column(name = "is_active")
    private Boolean isActive;

    /**
     * Default constructor of the UserResetToken class.
     */
    public UserResetToken() {
    }

    /**
     * Non default constructor of the UserResetToken class. This constructor
     * takes in the ID of the user and sets it.
     * 
     * @param userID ID of the user.
     */
    public UserResetToken(Integer userID) {
        this.userID = userID;
    }

    /**
     * Non default constructor of the UserResetToken class. This constructor
     * takes in the ID of the User and the token then sets them.
     * 
     * @param userID ID of the user.
     * @param token Token of the UserResetToken.
     */
    public UserResetToken(Integer userID, String token) {
        this.userID = userID;
        this.token = token;
    }

    /**
     * Gets the user ID of the UserResetToken.
     * 
     * @return The user ID of the UserResetToken.
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * Sets the user ID of the UserResetToken.
     * 
     * @param userID The user ID of the UserResetToken.
     */
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    /**
     * Gets the token of the UserResetToken.
     * 
     * @return The token of the UserResetToken.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token of the UserResetToken.
     * 
     * @param token The token of the UserResetToken.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the expiration date of the UserResetToken.
     * 
     * @return The expiration date of the UserResetToken.
     */
    public Date getExpirationdate() {
        return expirationdate;
    }

    /**
     * Sets the expiration date of the UserResetToken.
     * 
     * @param expirationdate The expiration date of the UserResetToken.
     */
    public void setExpirationdate(Date expirationdate) {
        this.expirationdate = expirationdate;
    }

    /**
     * Gets the boolean that states if the UserResetToken is active or not.
     * 
     * @return The boolean that states if the UserResetToken is active or not.
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets the boolean that states if the UserResetToken is active or not.
     * 
     * @param isActive The boolean that states if the UserResetToken is active or not.
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    /**
     * Creates a hash code.
     * 
     * @return The created hash code.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }
    
    /**
     * Compares the parameter object to this object. If the objects
     * are the same then the method returns true otherwise false.
     * 
     * @param object The object being compared to this object.
     * @return True if the same otherwise false.
     */
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
    
    /**
     * String formatting for output.
     * 
     * @return Formatted string for output.
     */
    @Override
    public String toString() {
        return "models.UserResetToken[ userID=" + userID + " ]";
    }
}