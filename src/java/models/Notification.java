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
 * @author 743851
 */
@Entity
@Table(name = "notification")
@XmlRootElement
@NamedQueries({
     @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n")
     , @NamedQuery(name = "Notification.findByNotificationID", query = "SELECT n FROM Notification n WHERE n.notificationID = :notificationID")
     , @NamedQuery(name = "Notification.findByNote", query = "SELECT n FROM Notification n WHERE n.note = :note")
     , @NamedQuery(name = "Notification.findByUserID", query = "SELECT n FROM Notification n WHERE n.user = :user")})
public class Notification implements Serializable {

     private static final long serialVersionUID = 1L;
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Basic(optional = false)
     @Column(name = "NotificationID")
     private Integer notificationID;
     @Basic(optional = false)
     @Column(name = "Note")
     private String note;
     @JoinColumn(name = "User", referencedColumnName = "UserID")
     @ManyToOne(optional = false)
     private User user;

     public Notification() {
     }

     public Notification(Integer notificationID) {
          this.notificationID = notificationID;
     }

     public Notification(Integer notificationID, String note) {
          this.notificationID = notificationID;
          this.note = note;
     }

     public Integer getNotificationID() {
          return notificationID;
     }

     public void setNotificationID(Integer notificationID) {
          this.notificationID = notificationID;
     }

     public String getNote() {
          return note;
     }

     public void setNote(String note) {
          this.note = note;
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
          hash += (notificationID != null ? notificationID.hashCode() : 0);
          return hash;
     }

     @Override
     public boolean equals(Object object) {
          // TODO: Warning - this method won't work in the case the id fields are not set
          if (!(object instanceof Notification)) {
               return false;
          }
          Notification other = (Notification) object;
          if ((this.notificationID == null && other.notificationID != null) || (this.notificationID != null && !this.notificationID.equals(other.notificationID))) {
               return false;
          }
          return true;
     }

     @Override
     public String toString() {
          return "models.Notification[ notificationID=" + notificationID + " ]";
     }

}
