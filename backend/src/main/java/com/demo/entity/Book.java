package com.demo.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "Bno")
    private String bno;

    @Column(name = "Bname")
    private String bname;

    @Column(name = "Bauthor")
    private String bauthor;

    @Column(name = "Bsource")
    private String bsource;

    @Column(name = "Bedition")
    private String bedition;

    @Column(name = "Bprice")
    private String bprice;

    @Column(name = "Bnum")
    private String bnum;

    @Column(name = "CCno")
    private String ccno;

    @Column(name = "Tno")
    private String tno;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Tno", referencedColumnName = "Tno", insertable = false, updatable = false)
    private Teacher teacher;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CCno", referencedColumnName = "CCno", insertable = false, updatable = false)
    private Course course;

    public Book() {}

    public String getBno() { return bno; }
    public void setBno(String bno) { this.bno = bno; }
    public String getBname() { return bname; }
    public void setBname(String bname) { this.bname = bname; }
    public String getBauthor() { return bauthor; }
    public void setBauthor(String bauthor) { this.bauthor = bauthor; }
    public String getBsource() { return bsource; }
    public void setBsource(String bsource) { this.bsource = bsource; }
    public String getBedition() { return bedition; }
    public void setBedition(String bedition) { this.bedition = bedition; }
    public String getBprice() { return bprice; }
    public void setBprice(String bprice) { this.bprice = bprice; }
    public String getBnum() { return bnum; }
    public void setBnum(String bnum) { this.bnum = bnum; }
    public String getCcno() { return ccno; }
    public void setCcno(String ccno) { this.ccno = ccno; }
    public String getTno() { return tno; }
    public void setTno(String tno) { this.tno = tno; }
    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    @Override
    public String toString() {
        return "Book{" +
                "bno='" + bno + '\'' +
                ", bname='" + bname + '\'' +
                ", bauthor='" + bauthor + '\'' +
                ", bsource='" + bsource + '\'' +
                ", bedition='" + bedition + '\'' +
                ", bprice='" + bprice + '\'' +
                ", bnum='" + bnum + '\'' +
                ", ccno='" + ccno + '\'' +
                ", tno='" + tno + '\'' +
                '}';
    }
}
