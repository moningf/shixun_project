package com.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @Column(name = "Tno")
    private String tno;

    @Column(name = "Tname")
    private String tname;

    @Column(name = "Tdept")
    private String tdept;

    @Column(name = "Ttel")
    private String ttel;

    @Column(name = "Temail")
    private String temail;

    @Column(name = "CCno")
    private String ccno;

    @Column(name = "password")
    private String password;

    public Teacher() {}

    public Teacher(String tno, String tname, String tdept, String ttel, String temail, String ccno, String password) {
        this.tno = tno;
        this.tname = tname;
        this.tdept = tdept;
        this.ttel = ttel;
        this.temail = temail;
        this.ccno = ccno;
        this.password = password;
    }

    public String getTno() { return tno; }
    public void setTno(String tno) { this.tno = tno; }
    public String getTname() { return tname; }
    public void setTname(String tname) { this.tname = tname; }
    public String getTdept() { return tdept; }
    public void setTdept(String tdept) { this.tdept = tdept; }
    public String getTtel() { return ttel; }
    public void setTtel(String ttel) { this.ttel = ttel; }
    public String getTemail() { return temail; }
    public void setTemail(String temail) { this.temail = temail; }
    public String getCcno() { return ccno; }
    public void setCcno(String ccno) { this.ccno = ccno; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Teacher{" +
                "tno='" + tno + '\'' +
                ", tname='" + tname + '\'' +
                ", tdept='" + tdept + '\'' +
                ", ttel='" + ttel + '\'' +
                ", temail='" + temail + '\'' +
                ", ccno='" + ccno + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
