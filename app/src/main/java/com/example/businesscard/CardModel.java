package com.example.businesscard;

public class CardModel {
    String name;
    String company;
    String department;
    String address;
    String position;
    String mobile;
    String tel;
    String fax;
    String email;
    String homepage;

    public CardModel(String name, String company, String department, String address, String position, String mobile, String tel, String fax, String email, String homepage) {
        this.name = name;
        this.company = company;
        this.department = department;
        this.address = address;
        this.position = position;
        this.mobile = mobile;
        this.tel = tel;
        this.fax = fax;
        this.email = email;
        this.homepage = homepage;
    }

    public CardModel() {
        name = "";
        company = "";
        department = "";
        address = "";
        position = "";
        mobile = "";
        tel = "";
        fax = "";
        email = "";
        homepage = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public String toString() {
        return "CardModel{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", address='" + address + '\'' +
                ", position='" + position + '\'' +
                ", mobile='" + mobile + '\'' +
                ", tel='" + tel + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", homepage='" + homepage + '\'' +
                '}';
    }
}