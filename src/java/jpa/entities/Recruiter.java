/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lucas.freire
 */
@Entity
@Table(name = "recruiter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recruiter.findAll", query = "SELECT r FROM Recruiter r"),
    @NamedQuery(name = "Recruiter.findByRecruiterId", query = "SELECT r FROM Recruiter r WHERE r.recruiterId = :recruiterId"),
    @NamedQuery(name = "Recruiter.findByEmail", query = "SELECT r FROM Recruiter r WHERE r.email = :email"),
    @NamedQuery(name = "Recruiter.findByPasswordRe", query = "SELECT r FROM Recruiter r WHERE r.passwordRe = :passwordRe"),
    @NamedQuery(name = "Recruiter.findByClientName", query = "SELECT r FROM Recruiter r WHERE r.clientName = :clientName"),
    @NamedQuery(name = "Recruiter.findByClientDepartmentNumber", query = "SELECT r FROM Recruiter r WHERE r.clientDepartmentNumber = :clientDepartmentNumber")})
public class Recruiter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RECRUITER_ID")
    private Integer recruiterId;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "PASSWORD_RE")
    private String passwordRe;
    @Basic(optional = false)
    @Column(name = "CLIENT_NAME")
    private String clientName;
    @Column(name = "CLIENT_DEPARTMENT_NUMBER")
    private Short clientDepartmentNumber;

    public Recruiter() {
    }

    public Recruiter(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }

    public Recruiter(Integer recruiterId, String email, String passwordRe, String clientName) {
        this.recruiterId = recruiterId;
        this.email = email;
        this.passwordRe = passwordRe;
        this.clientName = clientName;
    }

    public Integer getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordRe() {
        return passwordRe;
    }

    public void setPasswordRe(String passwordRe) {
        this.passwordRe = passwordRe;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Short getClientDepartmentNumber() {
        return clientDepartmentNumber;
    }

    public void setClientDepartmentNumber(Short clientDepartmentNumber) {
        this.clientDepartmentNumber = clientDepartmentNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recruiterId != null ? recruiterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recruiter)) {
            return false;
        }
        Recruiter other = (Recruiter) object;
        if ((this.recruiterId == null && other.recruiterId != null) || (this.recruiterId != null && !this.recruiterId.equals(other.recruiterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Recruiter[ recruiterId=" + recruiterId + " ]";
    }
    
}
