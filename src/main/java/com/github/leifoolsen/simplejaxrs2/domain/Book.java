package com.github.leifoolsen.simplejaxrs2.domain;

import com.google.common.base.MoreObjects;

import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Book {
    @NotNull
    @Pattern(regexp = "[0-9]+", message = "The ISBN must be a numeric value")
    @Size(min=13, max=13, message = "ISBN must be a numeric with excact 13 digits")
    private String isbn;
    
    @NotNull
    private String title;
    
    @NotNull
    private String author;

    private Date published;
    private String translator;
    private String summary;

    private Book() {}
    
    private Book(Builder builder) {
        this.isbn = builder.isbn;
        this.title = builder.title;
        this.author = builder.author;
        this.published = builder.published;
        this.translator = builder.translator;
        this.summary = builder.summary;
    }

    public static Builder with(final String isbn) { return new Builder(isbn); }

    public static Builder with(final Book source) {
        return new Builder(source.isbn)
            .title(source.title)
            .author(source.author)
            .published(source.published)
            .summary(source.summary);
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public Date getPublished() { return published; }
    public String getTranslator() { return translator; }
    public String getSummary() { return summary; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!isbn.equals(book.isbn)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    public static void validate(final Book book) {

        if(book == null) {
            throw new ConstraintViolationException("Book may not be null", new HashSet<ConstraintViolation<?>>());
        }

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book);
        if(!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed",
                    new HashSet<ConstraintViolation<?>>(constraintViolations));
        }

    }

    public static class Builder {
        private String isbn;
        private String title;
        private String author;
        private Date   published;
        private String translator;
        private String summary;

        private static String blankToNull(final String value) {
            String s = MoreObjects.firstNonNull(value, "").trim();
            return s.length() > 0 ? s : null;
        }

        private Builder(final String isbn) {
            this.isbn = blankToNull(isbn);
        }

        public Builder title(final String title) {
            this.title = blankToNull(title);
            return this;
        }

        public Builder author(final String author) {
            this.author = blankToNull(author);
            return this;
        }

        public Builder published(final Date published) {
            this.published = published;
            return this;
        }

        public Builder translator(final String translator) {
            this.translator = blankToNull(translator);
            return this;
        }

        public Builder summary(final String summary) {
            this.summary = blankToNull(summary);
            return this;
        }

        public Book build() { return new Book(this); }
    }

}
