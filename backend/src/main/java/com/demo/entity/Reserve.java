package com.demo.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "reserve")
public class Reserve {

    @Id
    @Column(name = "Rno")
    private String id;

    @Column(name = "Cno")
    private String cno;

    @Column(name = "Bno")
    private String bno;

    @Column(name = "Rnum")
    private Integer num;

    @Column(name = "totalPrice")
    private Double totalPrice;

    @Column(name = "R_time")
    private String rTime;

    @Column(name = "Q_time")
    private String qTime;

    @Column(name = "Bstatus")
    private String bstatus;

    @Column(name = "Place")
    private String place;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Bno", referencedColumnName = "Bno", insertable = false, updatable = false)
    private Book book;

    public Reserve() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCno() { return cno; }
    public void setCno(String cno) { this.cno = cno; }
    public String getBno() { return bno; }
    public void setBno(String bno) { this.bno = bno; }
    public Integer getNum() { return num; }
    public void setNum(Integer num) { this.num = num; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public String getRTime() { return rTime; }
    public void setRTime(String rTime) { this.rTime = rTime; }
    public String getQTime() { return qTime; }
    public void setQTime(String qTime) { this.qTime = qTime; }
    public String getBstatus() { return bstatus; }
    public void setBstatus(String bstatus) { this.bstatus = bstatus; }
    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    // Aliases for backward compat
    public String getR_time() { return rTime; }
    public void setR_time(String rTime) { this.rTime = rTime; }
    public String getQ_time() { return qTime; }
    public void setQ_time(String qTime) { this.qTime = qTime; }

    @Override
    public String toString() {
        return "Reserve{" +
                "id='" + id + '\'' +
                ", cno='" + cno + '\'' +
                ", bno='" + bno + '\'' +
                ", num=" + num +
                ", totalPrice=" + totalPrice +
                ", rTime='" + rTime + '\'' +
                ", qTime='" + qTime + '\'' +
                ", bstatus='" + bstatus + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
