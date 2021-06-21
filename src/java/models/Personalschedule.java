/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex Zecevic
 */
@Entity
@Table(name = "personalschedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personalschedule.findAll", query = "SELECT p FROM Personalschedule p")
    , @NamedQuery(name = "Personalschedule.findByPersonalSchduleID", query = "SELECT p FROM Personalschedule p WHERE p.personalSchduleID = :personalSchduleID")})
public class Personalschedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PersonalSchduleID")
    private Integer personalSchduleID;
    @JoinColumn(name = "Shift", referencedColumnName = "ShiftID")
    @ManyToOne(optional = false)
    private Shift shift;
    @JoinColumn(name = "User", referencedColumnName = "UserID")
    @ManyToOne(optional = false)
    private User user;

    public Personalschedule() {
    }

    public Personalschedule(Integer personalSchduleID) {
        this.personalSchduleID = personalSchduleID;
    }

    public Integer getPersonalSchduleID() {
        return personalSchduleID;
    }

    public void setPersonalSchduleID(Integer personalSchduleID) {
        this.personalSchduleID = personalSchduleID;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof Personalschedule)) {
            return false;
        }
        Personalschedule other = (Personalschedule) object;
        if ((this.personalSchduleID == null && other.personalSchduleID != null) || (this.personalSchduleID != null && !this.personalSchduleID.equals(other.personalSchduleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Personalschedule[ personalSchduleID=" + personalSchduleID + " ]";
    }
    
}
