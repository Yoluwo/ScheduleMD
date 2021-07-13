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
 * This class describes the Hospital entity
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

    /**
     * This is the default constructor for the Hospital class
     */
    public Hospital() {
    }

    /**
     * Constructs the Hospital class using the hospitalID
     * @param hospitalID The ID number of the hospital
     */
    public Hospital(Integer hospitalID) {
        this.hospitalID = hospitalID;
    }

    /**
     * This constructs the Hospital class using the hospitalID, hospitalName and hospitalType
     * @param hospitalID The ID number of the hospital
     * @param hospitalName The name of the hospital
     * @param hospitalType The type of the hospital
     */
    public Hospital(Integer hospitalID, String hospitalName, String hospitalType) {
        this.hospitalID = hospitalID;
        this.hospitalName = hospitalName;
        this.hospitalType = hospitalType;
    }

    /**
     * This method gets the hospitalID of this hospital
     * @return The integer value of hospitalID
     */
    public Integer getHospitalID() {
        return hospitalID;
    }

    /**
     * This method sets the value of hospitalID
     * @param hospitalID - Integer value of hospitalID
     */
    public void setHospitalID(Integer hospitalID) {
        this.hospitalID = hospitalID;
    }

    /**
     * This method gets the name of this hospital
     * @return The String value of hospitalName
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * This method sets the hospital name
     * @param hospitalName - String value of hospitalName
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    /**
     * This method gets the hospital type
     * @return The String value of hospitalType
     */
    public String getHospitalType() {
        return hospitalType;
    }

    /**
     * This method sets the value of hospitalType
     * @param hospitalType - The String value of hospitalType
     */
    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    /**
     * This method gets the roleList
     * @return The String value of roleList
     */
    public String getRoleList() {
        return roleList;
    }

    /**
     * This method sets the roleList
     * @param roleList - The String value of roleList
     */
    public void setRoleList(String roleList) {
        this.roleList = roleList;
    }

    /**
     * This method gets the list of type Schedule
     * @return The value of scheduleList
     */
    @XmlTransient
    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    /**
     * This method sets the scheduleList with a list of type Schedule
     * @param scheduleList - The collection of schedules 
     */
    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    /**
     * This method gets the list of users of derived from the User class
     * @return The list of users in userList
     */
    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    /**
     * This method sets the list of user objects 
     * @param userList - The list of users derived from the User class
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
        hash += (hospitalID != null ? hospitalID.hashCode() : 0);
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
        if (!(object instanceof Hospital)) {
            return false;
        }
        Hospital other = (Hospital) object;
        if ((this.hospitalID == null && other.hospitalID != null) || (this.hospitalID != null && !this.hospitalID.equals(other.hospitalID))) {
            return false;
        }
        return true;
    }

    /**
     * This method prints the Hospital objects
     * @return - The values of the attributes of the Hospital objects
     */
    @Override
    public String toString() {
        return "models.Hospital[ hospitalID=" + hospitalID + " ]";
    }
    
}

