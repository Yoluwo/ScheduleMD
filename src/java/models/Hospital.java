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
@Table(name = "hospital")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hospital.findAll", query = "SELECT h FROM Hospital h")
    , @NamedQuery(name = "Hospital.findByHospitalID", query = "SELECT h FROM Hospital h WHERE h.hospitalID = :hospitalID")
    , @NamedQuery(name = "Hospital.findByHospitalName", query = "SELECT h FROM Hospital h WHERE h.hospitalName = :hospitalName")
    , @NamedQuery(name = "Hospital.findByHospitalType", query = "SELECT h FROM Hospital h WHERE h.hospitalType = :hospitalType")
    , @NamedQuery(name = "Hospital.findByRoleList", query = "SELECT h FROM Hospital h WHERE h.roleList = :roleList")})
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HospitalID")
    private Integer hospitalID;
    @Basic(optional = false)
    @Column(name = "HospitalName")
    private String hospitalName;
    @Basic(optional = false)
    @Column(name = "HospitalType")
    private String hospitalType;
    @Column(name = "RoleList")
    private String roleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private List<Schedule> scheduleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private List<User> userList;

    public Hospital() {
    }

    public Hospital(Integer hospitalID) {
        this.hospitalID = hospitalID;
    }

    public Hospital(Integer hospitalID, String hospitalName, String hospitalType) {
        this.hospitalID = hospitalID;
        this.hospitalName = hospitalName;
        this.hospitalType = hospitalType;
    }

    public Integer getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(Integer hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public String getRoleList() {
        return roleList;
    }

    public void setRoleList(String roleList) {
        this.roleList = roleList;
    }

    @XmlTransient
    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
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
        hash += (hospitalID != null ? hospitalID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hospital)) {
            return false;
        }
        Hospital other = (Hospital) object;
        if ((this.hospitalID == null && other.hospitalID != null) || (this.hospitalID != null && !this.hospitalID.equals(other.hospitalID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Hospital[ hospitalID=" + hospitalID + " ]";
    }
    
}
