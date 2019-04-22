package org.jhipster.thibaut.thelibrary.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "editor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "editor")
public class Editor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "editor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Book> books = new HashSet<>();
    @OneToOne(mappedBy = "editor")
    @JsonIgnore
    private Coordinates coordinates;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Editor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public Editor books(Set<Book> books) {
        this.books = books;
        return this;
    }

    public Editor addBook(Book book) {
        this.books.add(book);
        book.setEditor(this);
        return this;
    }

    public Editor removeBook(Book book) {
        this.books.remove(book);
        book.setEditor(null);
        return this;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Editor coordinates(Coordinates coordinates) {
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
        Editor editor = (Editor) o;
        if (editor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), editor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Editor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
