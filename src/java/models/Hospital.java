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
 * Auto generated model. This class models all of the data stored in the Hospital
 * table in the database as a Hospital object.
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

    /**
     * Holds the serialization UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds the ID of the Hospital.
     */
    @Id
    @Basic(optional = false)
    @Column(name = "HospitalID")
    private Integer hospitalID;
    
    /**
     * Holds the name of the Hospital.
     */
    @Basic(optional = false)
    @Column(name = "HospitalName")
    private String hospitalName;
    
    /**
     * Holds the Hospital type.
     */
    @Basic(optional = false)
    @Column(name = "HospitalType")
    private String hospitalType;
    
    /**
     * Holds the roles available at the Hospital.
     */
    @Column(name = "RoleList")
    private String roleList;
    
    /**
     * Holds the list of schedules at the Hospital.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private List<Schedule> scheduleList;
    
    /**
     * Holds the list of users at the Hospital.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private List<User> userList;

    /**
     * Default constructor for the Hospital class.
     */
    public Hospital() {
    }

    /**
     * Non default constructor for the Hospital class. This constructor takes
     * in the ID of the Hospital and sets it.
     * 
     * @param hospitalID The ID of the Hospital.
     */
    public Hospital(Integer hospitalID) {
        this.hospitalID = hospitalID;
    }

    /**
     * Non default constructor for the Hospital class. This constructor takes in
     * the ID of the Hospital, name of the Hospital, and the type of the Hospital
     * and sets them.
     * 
     * @param hospitalID ID of the Hospital.
     * @param hospitalName Name of the Hospital.
     * @param hospitalType Type of Hospital.
     */
    public Hospital(Integer hospitalID, String hospitalName, String hospitalType) {
        this.hospitalID = hospitalID;
        this.hospitalName = hospitalName;
        this.hospitalType = hospitalType;
    }

    /**
     * Gets the ID of the Hospital.
     * 
     * @return The ID of the Hospital.
     */
    public Integer getHospitalID() {
        return hospitalID;
    }

    /**
     * Sets the ID of the Hospital.
     * 
     * @param hospitalID ID of the Hospital.
     */
    public void setHospitalID(Integer hospitalID) {
        this.hospitalID = hospitalID;
    }

    /**
     * Gets the name of the Hospital.
     * 
     * @return The name of the Hospital.
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * Sets the name of the Hospital.
     * 
     * @param hospitalName Name of the Hospital.
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    /**
     * Gets the type of Hospital.
     * 
     * @return The type of Hospital.
     */
    public String getHospitalType() {
        return hospitalType;
    }

    /**
     * Sets the type of Hospital.
     * 
     * @param hospitalType Type of Hospital.
     */
    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    /**
     * Gets the list of roles available at the Hospital.
     * 
     * @return The list of roles available at the Hospital.
     */
    public String getRoleList() {
        return roleList;
    }

    public void setRoleList(String roleList) {
        this.roleList = roleList;
    }

    /**
     * Gets the list of schedules that are created for the Hospital.
     * 
     * @return The list of schedules created for the Hospital.
     */
    @XmlTransient
    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    /**
     * Sets the list of schedules that are created for the Hospital.
     * 
     * @param scheduleList The list of schedules created for the Hospital.
     */
    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    /**
     * Gets the list of users that are assigned to the Hospital.
     * 
     * @return The list of users that are assigned to the Hospital.
     */
    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    /**
     * Sets the list of users that are assigned to the Hospital.
     * 
     * @param userList The list of users that are assigned to the Hospital.
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
        hash += (hospitalID != null ? hospitalID.hashCode() : 0);
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
     * String formatting for output.
     * 
     * @return Formatted string for output.
     */
    @Override
    public String toString() {
        return "models.Hospital[ hospitalID=" + hospitalID + " ]";
    }
}