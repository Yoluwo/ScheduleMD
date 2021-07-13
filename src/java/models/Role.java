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
 *
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

     public Role() {
     }

     public Role(Integer roleID) {
          this.roleID = roleID;
     }

     public Role(Integer roleID, String roleName) {
          this.roleID = roleID;
          this.roleName = roleName;
     }

     public Integer getRoleID() {
          return roleID;
     }

     public void setRoleID(Integer roleID) {
          this.roleID = roleID;
     }

     public String getRoleName() {
          return roleName;
     }

     public void setRoleName(String roleName) {
          this.roleName = roleName;
     }

     @XmlTransient
     public List<Shift> getShiftList() {
          return shiftList;
     }

     public void setShiftList(List<Shift> shiftList) {
          this.shiftList = shiftList;
     }

     @XmlTransient
     public List<User> getUserList() {
          return userList;
     }

     public void setUserList(List<User> userList) {
          this.userList = userList;
     }

     @Override
     public int hashCode() {
          int hash = 0;
          hash += (roleID != null ? roleID.hashCode() : 0);
          return hash;
     }

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

     @Override
     public String toString() {
          return "models.Role[ roleID=" + roleID + " ]";
     }

}
