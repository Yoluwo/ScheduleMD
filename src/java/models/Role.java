package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Auto generated model. This class models all of the data stored in the Role
 * table in the database as a Role object.
 */
@Entity
@Table(name = "role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
    , @NamedQuery(name = "Role.findByRoleID", query = "SELECT r FROM Role r WHERE r.roleID = :roleID")
    , @NamedQuery(name = "Role.findByRoleName", query = "SELECT r FROM Role r WHERE r.roleName = :roleName")})
public class Role implements Serializable {
    
    /**
     * Holds the serialization UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds the ID of the Role.
     */
    @Id
    @Basic(optional = false)
    @Column(name = "RoleID")
    private Integer roleID;
    
    /**
     * Holds the name of the Role.
     */
    @Basic(optional = false)
    @Column(name = "RoleName")
    private String roleName;
    
    /**
     * Holds the list of shifts associated to the Role.
     */
    @OneToMany(mappedBy = "role")
    private List<Shift> shiftList;
    
    /**
     * Holds the list of users associated to the Role.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<User> userList;

    /**
     * Default constructor for the Role class.
     */
    public Role() {
    }

    /**
     * Non default constructor for the Role class. This constructor takes in
     * the ID of the Role and sets it.
     * 
     * @param roleID The ID of the Role.
     */
    public Role(Integer roleID) {
        this.roleID = roleID;
    }

    /**
     * Non default constructor for the Role class. This constructor takes in
     * the ID of the Role and the name of the Role and sets them.
     * 
     * @param roleID ID of the Role.
     * @param roleName Name of the Role.
     */
    public Role(Integer roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

    /**
     * Gets the ID of the Role.
     * 
     * @return The ID of the Role.
     */
    public Integer getRoleID() {
        return roleID;
    }

    /**
     * Sets the ID of the Role.
     * 
     * @param roleID The ID of the Role.
     */
    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    /**
     * Gets the name of the Role.
     * 
     * @return The name of the Role.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the name of the Role.
     * 
     * @param roleName The name of the Role.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Gets the list of shifts associated with the Role.
     * 
     * @return The list of shifts associated with the Role.
     */
    @XmlTransient
    public List<Shift> getShiftList() {
        return shiftList;
    }

    /**
     * Sets the list of shifts associated with the Role.
     * 
     * @param shiftList The list of shifts associated with the Role.
     */
    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    /**
     * Gets the list of users associated with the Role.
     * 
     * @return List of users associated with the Role.
     */
    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    /**
     * Sets the list of users associated with the Role.
     * 
     * @param userList The list of users associated with the Role.
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    
    /**
     * Creates a hash code.
     * 
     * @return The created hash code.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleID != null ? roleID.hashCode() : 0);
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
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.roleID == null && other.roleID != null) || (this.roleID != null && !this.roleID.equals(other.roleID))) {
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
        return "models.Role[ roleID=" + roleID + " ]";
    }
}