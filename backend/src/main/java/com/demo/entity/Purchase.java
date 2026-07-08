package com.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "purchase")
public class Purchase {

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

    @Column(name = "BNumber")
    private Integer number;

    public Purchase() {}

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
    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    @Override
    public String toString() {
        return "Purchase{" +
                "bno='" + bno + '\'' +
                ", bname='" + bname + '\'' +
                ", bauthor='" + bauthor + '\'' +
                ", bsource='" + bsource + '\'' +
                ", bedition='" + bedition + '\'' +
                ", bprice='" + bprice + '\'' +
                ", number=" + number +
                '}';
    }
}
