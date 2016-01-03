package pl.edu.amu.rest.entity;

import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.amu.rest.model.Comment;
import pl.edu.amu.rest.model.Offer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "users.findAll", query = "SELECT u FROM UserEntity u"),
        @NamedQuery(name = "users.findByLogin", query = "SELECT u FROM UserEntity u WHERE u.login=:login")
})
public class UserEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "HASH_PASSWORD")
    private String hashPassword;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "PERMISSIONS")
    private Boolean permissions;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "CITY")
    private String city;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    @Column(name = "CONFIRMED")
    private Boolean confirmed;


    // auto-generated

    /*public void setId(int id) {
        this.id = id;
    }
    */


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
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

    public Boolean getPermissions() {
        return permissions;
    }

    public void setPermissions(Boolean permissions) {
        this.permissions = permissions;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }


    //fields can be renamed
    //fields can be indexed for better performance


    //Lifecycle methods -- Pre/PostLoad, Pre/PostPersist...
    @PostLoad
    private void postLoad() {
        LOGGER.info("postLoad: {}", toString());
    }

    public UserEntity() {
    }

    public UserEntity(String login, String hashPassword, String firstName, String lastName, String email, String city, String address, String phone, String zipCode, Timestamp createdAt) {
        this.login = login;
        this.hashPassword = hashPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.zipCode = zipCode;
        this.createdAt = createdAt;
        this.permissions = false;
        this.confirmed = false;
    }

    public UserEntity(String login, String hashPassword, String firstName, String lastName, Boolean permissions, String email, String city, String address, String phone, String zipCode, Timestamp createdAt, Boolean confirmed, List<Offer> userOffers, List<Comment> userComments) {
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

    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", permissions=" + permissions +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", createdAt=" + createdAt +
                ", confirmed=" + confirmed +
                '}';
    }
}
