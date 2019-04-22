package org.jhipster.thibaut.thelibrary.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "language")
    private String language;

    @Column(name = "isbn")
    private Integer isbn;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    @OneToMany(mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Loan> loans = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "book_author",
               joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private Set<Author> authors = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "book_category",
               joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "book_library",
               joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "library_id", referencedColumnName = "id"))
    private Set<Library> libraries = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("books")
    private Editor editor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public Book language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public Book isbn(Integer isbn) {
        this.isbn = isbn;
        return this;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public Book publicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public Book numberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
        return this;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Set<Loan> getLoans() {
        return loans;
    }

    public Book loans(Set<Loan> loans) {
        this.loans = loans;
        return this;
    }

    public Book addLoan(Loan loan) {
        this.loans.add(loan);
        loan.setBook(this);
        return this;
    }

    public Book removeLoan(Loan loan) {
        this.loans.remove(loan);
        loan.setBook(null);
        return this;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public Book authors(Set<Author> authors) {
        this.authors = authors;
        return this;
    }

    public Book addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
        return this;
    }

    public Book removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
        return this;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Book categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Book addCategory(Category category) {
        this.categories.add(category);
        category.getBooks().add(this);
        return this;
    }

    public Book removeCategory(Category category) {
        this.categories.remove(category);
        category.getBooks().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Library> getLibraries() {
        return libraries;
    }

    public Book libraries(Set<Library> libraries) {
        this.libraries = libraries;
        return this;
    }

    public Book addLibrary(Library library) {
        this.libraries.add(library);
        library.getBooks().add(this);
        return this;
    }

    public Book removeLibrary(Library library) {
        this.libraries.remove(library);
        library.getBooks().remove(this);
        return this;
    }

    public void setLibraries(Set<Library> libraries) {
        this.libraries = libraries;
    }

    public Editor getEditor() {
        return editor;
    }

    public Book editor(Editor editor) {
        this.editor = editor;
        return this;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
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
        Book book = (Book) o;
        if (book.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", language='" + getLanguage() + "'" +
            ", isbn=" + getIsbn() +
            ", publicationDate='" + getPublicationDate() + "'" +
            ", numberOfPages=" + getNumberOfPages() +
            "}";
    }
}
