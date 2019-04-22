package org.jhipster.thibaut.thelibrary.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Users.
 */
@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Loan> loans = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "users_role",
               joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "users")
    @JsonIgnore
    private Coordinates coordinates;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public Users password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public Users firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Users lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public Users userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Users registrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<Loan> getLoans() {
        return loans;
    }

    public Users loans(Set<Loan> loans) {
        this.loans = loans;
        return this;
    }

    public Users addLoan(Loan loan) {
        this.loans.add(loan);
        loan.setUsers(this);
        return this;
    }

    public Users removeLoan(Loan loan) {
        this.loans.remove(loan);
        loan.setUsers(null);
        return this;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Users roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Users addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
        return this;
    }

    public Users removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Users coordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Users users = (Users) o;
        if (users.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), users.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Users{" +
            "id=" + getId() +
            ", password='" + getPassword() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", userName='" + getUserName() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            "}";
    }
}
