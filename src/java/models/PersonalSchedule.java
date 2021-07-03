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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alexz
 */
@Entity
@Table(name = "personalschedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonalSchedule.findAll", query = "SELECT p FROM PersonalSchedule p")
    , @NamedQuery(name = "PersonalSchedule.findByPersonalSchduleID", query = "SELECT p FROM PersonalSchedule p WHERE p.personalSchduleID = :personalSchduleID")
    , @NamedQuery(name = "PersonalSchedule.findByIsFridaySunday", query = "SELECT p FROM PersonalSchedule p WHERE p.isFridaySunday = :isFridaySunday")
    , @NamedQuery(name = "PersonalSchedule.findByIsSaturday", query = "SELECT p FROM PersonalSchedule p WHERE p.isSaturday = :isSaturday")})
public class PersonalSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PersonalSchduleID")
    private Integer personalSchduleID;
    @Basic(optional = false)
    @Column(name = "IsFridaySunday")
    private boolean isFridaySunday;
    @Basic(optional = false)
    @Column(name = "IsSaturday")
    private boolean isSaturday;
    @JoinColumn(name = "User", referencedColumnName = "UserID")
    @ManyToOne(optional = false)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personalSchedule")
    private List<Shift> shiftList;

    public PersonalSchedule() {
    }

    public PersonalSchedule(Integer personalSchduleID) {
        this.personalSchduleID = personalSchduleID;
    }

    public PersonalSchedule(Integer personalSchduleID, boolean isFridaySunday, boolean isSaturday) {
        this.personalSchduleID = personalSchduleID;
        this.isFridaySunday = isFridaySunday;
        this.isSaturday = isSaturday;
    }

    public Integer getPersonalSchduleID() {
        return personalSchduleID;
    }

    public void setPersonalSchduleID(Integer personalSchduleID) {
        this.personalSchduleID = personalSchduleID;
    }

    public boolean getIsFridaySunday() {
        return isFridaySunday;
    }

    public void setIsFridaySunday(boolean isFridaySunday) {
        this.isFridaySunday = isFridaySunday;
    }

    public boolean getIsSaturday() {
        return isSaturday;
    }

    public void setIsSaturday(boolean isSaturday) {
        this.isSaturday = isSaturday;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @XmlTransient
    public List<Shift> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personalSchduleID != null ? personalSchduleID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonalSchedule)) {
            return false;
        }
        PersonalSchedule other = (PersonalSchedule) object;
        if ((this.personalSchduleID == null && other.personalSchduleID != null) || (this.personalSchduleID != null && !this.personalSchduleID.equals(other.personalSchduleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.PersonalSchedule[ personalSchduleID=" + personalSchduleID + " ]";
    }
    
}
