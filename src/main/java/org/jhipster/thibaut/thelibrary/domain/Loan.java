package org.jhipster.thibaut.thelibrary.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Loan.
 */
@Entity
@Table(name = "loan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "loan")
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "returned")
    private Boolean returned;

    @Column(name = "initial_end_date")
    private LocalDate initialEndDate;

    @Column(name = "extended_end_date")
    private LocalDate extendedEndDate;

    @ManyToOne
    @JsonIgnoreProperties("loans")
    private Book book;

    @ManyToOne
    @JsonIgnoreProperties("loans")
    private Users users;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Loan startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Boolean isReturned() {
        return returned;
    }

    public Loan returned(Boolean returned) {
        this.returned = returned;
        return this;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public LocalDate getInitialEndDate() {
        return initialEndDate;
    }

    public Loan initialEndDate(LocalDate initialEndDate) {
        this.initialEndDate = initialEndDate;
        return this;
    }

    public void setInitialEndDate(LocalDate initialEndDate) {
        this.initialEndDate = initialEndDate;
    }

    public LocalDate getExtendedEndDate() {
        return extendedEndDate;
    }

    public Loan extendedEndDate(LocalDate extendedEndDate) {
        this.extendedEndDate = extendedEndDate;
        return this;
    }

    public void setExtendedEndDate(LocalDate extendedEndDate) {
        this.extendedEndDate = extendedEndDate;
    }

    public Book getBook() {
        return book;
    }

    public Loan book(Book book) {
        this.book = book;
        return this;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Users getUsers() {
        return users;
    }

    public Loan users(Users users) {
        this.users = users;
        return this;
    }

    public void setUsers(Users users) {
        this.users = users;
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
        Loan loan = (Loan) o;
        if (loan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Loan{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", returned='" + isReturned() + "'" +
            ", initialEndDate='" + getInitialEndDate() + "'" +
            ", extendedEndDate='" + getExtendedEndDate() + "'" +
            "}";
    }
}
