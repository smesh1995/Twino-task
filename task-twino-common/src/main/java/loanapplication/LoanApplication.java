package loanapplication;

import status.StatusEnum;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sandro on 2/11/17.
 */
@Entity
@XmlRootElement(name="LoanApplication")
public class LoanApplication  implements Serializable {
    private long id;
    private StatusEnum status;
    private long userId;
    private String firstName;
    private String LastName;
    private String birthDate;
    private String employer;
    private int salary;
    private int perMonth;
    private int reqAmount;
    private String reqTerm;
    private int score;

    public int getScore() {
        return score;
    }

    @XmlElement
    public void setScore(int score) {
        this.score = score;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    @XmlElement
    public void setId(long id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    @XmlElement
    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    @XmlElement
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    @XmlElement
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    @XmlElement
    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    @XmlElement
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmployer() {
        return employer;
    }

    @XmlElement
    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public int getSalary() {
        return salary;
    }

    @XmlElement
    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getPerMonth() {
        return perMonth;
    }

    @XmlElement
    public void setPerMonth(int perMonth) {
        this.perMonth = perMonth;
    }

    public int getReqAmount() {
        return reqAmount;
    }

    @XmlElement
    public void setReqAmount(int reqAmount) {
        this.reqAmount = reqAmount;
    }

    public String getReqTerm() {
        return reqTerm;
    }

    @XmlElement
    public void setReqTerm(String reqTerm) {
        this.reqTerm = reqTerm;
    }


}
