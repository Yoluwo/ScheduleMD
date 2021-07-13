/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * This class describes the Role Entity
 * @author 743851
 */
@Entity
@Table(name = "role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
    , @NamedQuery(name = "Role.findByRoleID", query = "SELECT r FROM Role r WHERE r.roleID = :roleID")
    , @NamedQuery(name = "Role.findByRoleName", query = "SELECT r FROM Role r WHERE r.roleName = :roleName")})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RoleID")
    private Integer roleID;
    @Basic(optional = false)
    @Column(name = "RoleName")
    private String roleName;
    @OneToMany(mappedBy = "role")
    private List<Shift> shiftList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<User> userList;

    /**
     * The default constructor for the Role Object
     */
    public Role() {
    }

    /**
     * This constructs a Role object using the roleID
     * @param roleID - The integer value of the roleID
     */
    public Role(Integer roleID) {
        this.roleID = roleID;
    }

    /**
     * This constructs the Role object using the roleID and roleName
     * @param roleID - Integer value of the roleID
     * @param roleName - The name of the role
     */
    public Role(Integer roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

    /**
     * This method gets the value of the roleID
     * @return This returns the integer value of the roleID
     */
    public Integer getRoleID() {
        return roleID;
    }

    /**
     * This method sets the value of the roleID
     * @param roleID - The integer value of the roleID
     */
    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    /**
     * This method gets the roleName
     * @return This returns the name of the role
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method sets the value of the roleName
     * @param roleName - The name of the role
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * This method gets a list of type Shift
     * @return This returns the list of shifts in shiftList
     */
    @XmlTransient
    public List<Shift> getShiftList() {
        return shiftList;
    }

    /**
     * This method sets the values of setShiftList
     * @param shiftList - The list of shifts 
     */
    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    /**
     * This methods gets users in userList
     * @return The list of users derived from the User class
     */
    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    /**
     * This method sets the list of users
     * @param userList - The list of users of type User
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleID != null ? roleID.hashCode() : 0);
        return hash;
    }

    /**
     * 
     * @param object
     * @return 
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
     * This method prints the Role objects
     * @return - The attributes of the Role objects
     */
    @Override
    public String toString() {
        return "models.Role[ roleID=" + roleID + " ]";
    }
    
}
