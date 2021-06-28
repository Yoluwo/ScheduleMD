package models;

import java.io.Serializable;
import java.util.Collection;
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
 * This class is used to model data from Hospital table from the database for 
 * use in the java program. This class contains all the getters and setters
 * as well as all the named queries for the database.
 * 
 * @author Alex Zecevic
 */

@Entity
@Table(name = "hospital")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hospital.findAll", query = "SELECT h FROM Hospital h")
    , @NamedQuery(name = "Hospital.findByHospitalID", query = "SELECT h FROM Hospital h WHERE h.hospitalID = :hospitalID")
    , @NamedQuery(name = "Hospital.findByHospitalName", query = "SELECT h FROM Hospital h WHERE h.hospitalName = :hospitalName")
    , @NamedQuery(name = "Hospital.findByHospitalType", query = "SELECT h FROM Hospital h WHERE h.hospitalType = :hospitalType")})
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private Collection<Schedule> scheduleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private Collection<User> userCollection;

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

    @XmlTransient
    public Collection<Schedule> getScheduleCollection() {
        return scheduleCollection;
    }

    public void setScheduleCollection(Collection<Schedule> scheduleCollection) {
        this.scheduleCollection = scheduleCollection;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hospitalID != null ? hospitalID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work if the case the id fields are not set
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