package com.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name = "Mno")
    private String mno;

    @Column(name = "Mname")
    private String mname;

    @Column(name = "Mtel")
    private String mtel;

    @Column(name = "Meamil")
    private String memail;

    @Column(name = "password")
    private String password;

    public Admin() {}

    public Admin(String mno, String mname, String mtel, String memail, String password) {
        this.mno = mno;
        this.mname = mname;
        this.mtel = mtel;
        this.memail = memail;
        this.password = password;
    }

    public String getMno() { return mno; }
    public void setMno(String mno) { this.mno = mno; }
    public String getMname() { return mname; }
    public void setMname(String mname) { this.mname = mname; }
    public String getMtel() { return mtel; }
    public void setMtel(String mtel) { this.mtel = mtel; }
    public String getMemail() { return memail; }
    public void setMemail(String memail) { this.memail = memail; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Admin{" +
                "mno='" + mno + '\'' +
                ", mname='" + mname + '\'' +
                ", mtel='" + mtel + '\'' +
                ", memail='" + memail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
