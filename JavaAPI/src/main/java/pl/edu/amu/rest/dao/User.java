package pl.edu.amu.rest.dao;


import java.sql.Date;
import java.util.List;

public class User{

    private int id;
    private String login;
    private String hashPassword;
    private String firstName;
    private String lastName;
    private Boolean permissions;
    private String email;
    private String city;
    private String address;
    private String phone;
    private String zipCode;
    private Date createdAt;
    private Boolean confirmed;
    private List<Offer> userOffers;

    public User() {
        //
    }

    public User(String login, String passwd) {
        this.hashPassword = passwd;
        this.login = login;
    }

    public User(String login, String passwd, String firstName, String lastName,
                String email, String city, String addr, String phone, String zipCode) {
        this.hashPassword = passwd;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.permissions = false;
        this.email = email;
        this.city = city;
        this.address = addr;
        this.phone = phone;
        this.zipCode = zipCode;
        java.util.Date utilDate = new java.util.Date();             //data w formacie util
        this.createdAt = new java.sql.Date(utilDate.getTime());     //pobranie aktualnej daty i konwersja na format sql
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getPermissions() {
        return permissions;
    }

    public void setPermissions(Boolean permissions) {
        this.permissions = permissions;
    }

    public Boolean getConfirmed() { return confirmed; }

    public void setConfirmed(Boolean confirmed) { this.confirmed = confirmed; }

    public List<Offer> getUserOffers() {
        return userOffers;
    }

    public void setUserOffers(List<Offer> userOffers) {
        this.userOffers = userOffers;
    }

    @Override
      public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        if (!login.equals(user.login)) return false;

        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", permissions='" + permissions + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", UserOffers='" + userOffers +"'" +
                '}';
    }
}
