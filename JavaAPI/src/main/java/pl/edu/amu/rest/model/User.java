package pl.edu.amu.rest.model;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class User{

    private Long id;
    @NotBlank
    @Pattern(message = "{User.wrong.login}",regexp = "[a-zA-Z0-9ęóąśłżźćńĘÓĄŚŁŻŹĆŃ]{3,35}")
    private String login;
    @NotBlank(message = "{User.wrong.hashPassword}")
    private String hashPassword;
    @NotBlank
    @Pattern(message = "{User.wrong.firstName}",regexp = "[a-zA-ZęóąśłżźćńĘÓĄŚŁŻŹĆŃ]{3,15}")
    private String firstName;
    @NotBlank
    @Pattern(message = "{User.wrong.lastName}",regexp = "[a-zA-ZęóąśłżźćńĘÓĄŚŁŻŹĆŃ]{3,30}")
    private String lastName;

    private Boolean permissions;

    @NotBlank
    @Pattern(regexp = "\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b", message = "{User.wrong.email}")
    private String email;
    @NotBlank
    @Pattern(message = "{User.wrong.city}",regexp = "^[a-zA-ZóąśłżźćńĘĄÓĄŚŁŻŹĆŃ]{1,30}(?:[\\s-][a-zA-Z]{1,30})*$")
    private String city;
    @NotBlank
    @Pattern(message = "{User.wrong.address}",regexp = "[a-zA-ZęóąśłżźćńĘĄÓĄŚŁŻŹĆŃ]{1,20}(\\\\.)?[a-zA-ZóąśłżźćńĘĄÓĄŚŁŻŹĆŃ]{1,15} \\d+")
    private String address;
    @NotBlank
    @Pattern(message = "{User.wrong.phone}",regexp = "^[0-9]{9,9}$")
    private String phone;
    @NotBlank
    @Pattern(message = "{User.wrong.zipCode}",regexp = "[0-9][0-9]\\-[0-9][0-9][0-9]")
    private String zipCode;
    //@NotBlank(message = "{User.wrong.createdAt}")
    private Timestamp createdAt;
    private Boolean confirmed;
    private List<Offer> userOffers;
    private List<Comment>userComments;

    public User() {
        //
    }

    public User(Long id, String login, String hashPassword, String firstName, String lastName, Boolean permissions, String email, String city, String address, String phone, String zipCode, Timestamp createdAt, Boolean confirmed, List<Offer> userOffers, List<Comment> userComments) {
        this.id = id;
        this.login = login;
        this.hashPassword = hashPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.permissions = permissions;
        this.email = email;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.zipCode = zipCode;
        this.createdAt = createdAt;
        this.confirmed = confirmed;
        this.userOffers = userOffers;
        this.userComments = userComments;
    }
    public User(Long id, String login, String hashPassword, String firstName, String lastName, Boolean permissions, String email, String city, String address, String phone, String zipCode, Timestamp createdAt, Boolean confirmed) {
        this.id = id;
        this.login = login;
        this.hashPassword = hashPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.permissions = permissions;
        this.email = email;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.zipCode = zipCode;
        this.createdAt = createdAt;
        this.confirmed = confirmed;
        this.userOffers = null;
        this.userComments = null;
    }


    public User(String login, String passwd) {
        this.hashPassword = passwd;
        this.login = login;
        this.setCreatedAt();
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
        this.setCreatedAt();
    }

    public User(String login, String hashPassword, String firstName, String lastName, Boolean permissions, String email, String city, String address, String phone, String zipCode, Boolean confirmed) {
        this.login = login;
        this.hashPassword = hashPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.permissions = permissions;
        this.email = email;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.zipCode = zipCode;
        this.confirmed = confirmed;
    }

    public Long getId() {return id;}


    //@ApiModelProperty(value = "User first name", required = true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }
    //@ApiModelProperty(value = "User login", required = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    //@ApiModelProperty(value = "User Hash Password", required = true)
    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }
    //@ApiModelProperty(value = "User email", required = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    //@ApiModelProperty(value = "User city", required = true)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    //@ApiModelProperty(value = "User address", required = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    //@ApiModelProperty(value = "User phone", required = true)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    //@ApiModelProperty(value = "User Zip Code", required = true)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    //@ApiModelProperty(value = "User register date", required = true)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        java.util.Date utilDate = new java.util.Date();             //data w formacie util
        this.createdAt = new java.sql.Timestamp(utilDate.getTime());     //pobranie aktualnej daty i konwersja na format sql}
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    //@ApiModelProperty(value = "User last name", required = true)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    //@ApiModelProperty(value = "User permission", required = false)
    public Boolean getPermissions() {
        return permissions;
    }

    public void setPermissions(Boolean permissions) {
        this.permissions = permissions;
    }
    //@ApiModelProperty(value = "User conformation", required = true)
    public Boolean getConfirmed() { return confirmed; }

    public void setConfirmed(Boolean confirmed) { this.confirmed = confirmed; }
    //@ApiModelProperty(value = "User offers", required = true)
    public List<Offer> getUserOffers() {
        return userOffers;
    }

    public void setUserOffers(List<Offer> userOffers) {
        this.userOffers = userOffers;
    }
    //@ApiModelProperty(value = "User comments", required = true)
    public List<Comment> getUserComments() {
        return userComments;
    }

    public void setUserComments(List<Comment> userComments) {
        this.userComments = userComments;
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
    public int hashCode() {
        return login.hashCode();
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
                ", userOffers='" + userOffers +"'" +
                ", userComments='" + userComments + '}';
    }
}