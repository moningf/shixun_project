package com.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "CCno")
    private String cno;

    @Column(name = "CCname")
    private String cname;

    public Course() {}

    public Course(String cno, String cname) {
        this.cno = cno;
        this.cname = cname;
    }

    public String getCno() { return cno; }
    public void setCno(String cno) { this.cno = cno; }
    public String getCname() { return cname; }
    public void setCname(String cname) { this.cname = cname; }

    @Override
    public String toString() {
        return "Course{" +
                "cno='" + cno + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }
}
