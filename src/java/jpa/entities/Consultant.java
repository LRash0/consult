/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lucas.freire
 */
@Entity
@Table(name = "consultant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consultant.findAll", query = "SELECT c FROM Consultant c"),
    @NamedQuery(name = "Consultant.findByConsultantId", query = "SELECT c FROM Consultant c WHERE c.consultantId = :consultantId"),
    @NamedQuery(name = "Consultant.findByStatusId", query = "SELECT c FROM Consultant c WHERE c.statusId = :statusId"),
    @NamedQuery(name = "Consultant.findByEmailCon", query = "SELECT c FROM Consultant c WHERE c.emailCon = :emailCon"),
    @NamedQuery(name = "Consultant.findByPasswordCon", query = "SELECT c FROM Consultant c WHERE c.passwordCon = :passwordCon"),
    @NamedQuery(name = "Consultant.findByHourlyRate", query = "SELECT c FROM Consultant c WHERE c.hourlyRate = :hourlyRate"),
    @NamedQuery(name = "Consultant.findByBillableHourlyRate", query = "SELECT c FROM Consultant c WHERE c.billableHourlyRate = :billableHourlyRate"),
    @NamedQuery(name = "Consultant.findByHireDate", query = "SELECT c FROM Consultant c WHERE c.hireDate = :hireDate"),
    @NamedQuery(name = "Consultant.findByRecruiterIdCons", query = "SELECT c FROM Consultant c WHERE c.recruiterIdCons = :recruiterIdCons"),
    @NamedQuery(name = "Consultant.findByResumeCons", query = "SELECT c FROM Consultant c WHERE c.resumeCons = :resumeCons")})
public class Consultant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CONSULTANT_ID")
    private Integer consultantId;
    @Basic(optional = false)
    @Column(name = "STATUS_ID")
    private char statusId;
    @Basic(optional = false)
    @Column(name = "EMAIL_CON")
    private String emailCon;
    @Basic(optional = false)
    @Column(name = "PASSWORD_CON")
    private String passwordCon;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "HOURLY_RATE")
    private BigDecimal hourlyRate;
    @Column(name = "BILLABLE_HOURLY_RATE")
    private BigDecimal billableHourlyRate;
    @Basic(optional = false)
    @Column(name = "HIRE_DATE")
    @Temporal(TemporalType.DATE)
    private Date hireDate;
    @Basic(optional = false)
    @Column(name = "RECRUITER_ID_CONS")
    private int recruiterIdCons;
    @Column(name = "RESUME_CONS")
    private String resumeCons;

    public Consultant() {
    }

    public Consultant(Integer consultantId) {
        this.consultantId = consultantId;
    }

    public Consultant(Integer consultantId, char statusId, String emailCon, String passwordCon, BigDecimal hourlyRate, Date hireDate, int recruiterIdCons) {
        this.consultantId = consultantId;
        this.statusId = statusId;
        this.emailCon = emailCon;
        this.passwordCon = passwordCon;
        this.hourlyRate = hourlyRate;
        this.hireDate = hireDate;
        this.recruiterIdCons = recruiterIdCons;
    }

    public Integer getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Integer consultantId) {
        this.consultantId = consultantId;
    }

    public char getStatusId() {
        return statusId;
    }

    public void setStatusId(char statusId) {
        this.statusId = statusId;
    }

    public String getEmailCon() {
        return emailCon;
    }

    public void setEmailCon(String emailCon) {
        this.emailCon = emailCon;
    }

    public String getPasswordCon() {
        return passwordCon;
    }

    public void setPasswordCon(String passwordCon) {
        this.passwordCon = passwordCon;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public BigDecimal getBillableHourlyRate() {
        return billableHourlyRate;
    }

    public void setBillableHourlyRate(BigDecimal billableHourlyRate) {
        this.billableHourlyRate = billableHourlyRate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public int getRecruiterIdCons() {
        return recruiterIdCons;
    }

    public void setRecruiterIdCons(int recruiterIdCons) {
        this.recruiterIdCons = recruiterIdCons;
    }

    public String getResumeCons() {
        return resumeCons;
    }

    public void setResumeCons(String resumeCons) {
        this.resumeCons = resumeCons;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consultantId != null ? consultantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consultant)) {
            return false;
        }
        Consultant other = (Consultant) object;
        if ((this.consultantId == null && other.consultantId != null) || (this.consultantId != null && !this.consultantId.equals(other.consultantId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Consultant[ consultantId=" + consultantId + " ]";
    }
    
}
