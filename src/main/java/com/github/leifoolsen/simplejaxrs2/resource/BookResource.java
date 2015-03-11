package com.github.leifoolsen.simplejaxrs2.resource;

import com.github.leifoolsen.simplejaxrs2.domain.Book;
import com.github.leifoolsen.simplejaxrs2.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Singleton
@Path("books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UriInfo uriInfo; // actual uri info provided by parent resource (threadsafe)

    public BookResource(@Context UriInfo uriInfo) {
        this.uriInfo = uriInfo;
        logger.debug("Resource created");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("ping")
    public String ping() {
        return "Pong!"; // --> Response.Status.OK
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(final Book book) {

        Book.validate(book); // --> Response.Status.BAD_REQUEST if validation fails

        if(BookRepository.findBook(book.getIsbn()) != null) {
            logger.debug("Can not create book. ISBN: '{}' already in repository", book.getIsbn());
            throw new WebApplicationException(
                Response.status(Response.Status.CONFLICT)
                        .location(uriInfo.getAbsolutePath())
                        .build()
            );
        }
        BookRepository.addBook(book);
        logger.debug("Book with isbn: '{}' created", book.getIsbn());
        return Response.created(uriInfo.getAbsolutePathBuilder().clone().path(book.getIsbn()).build())
                .entity(book)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("post-with-formparam")
    public Response postWithFormParam(
            @FormParam(value = "isbn") String isbn,
            @FormParam(value = "title") String title,
            @FormParam(value = "author") String author,
            @FormParam(value = "published") DateAdapter published,
            @FormParam(value = "translator") String translator,
            @FormParam(value = "summary") String summary) {

        logger.debug("@POST with @FormParam");

        Book book =  Book
            .with(isbn)
            .title(title)
            .author(author)
            .published(published != null ? published.getDate() : null)
            .translator(translator)
            .summary(summary)
            .build();

        return create(book);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("post-with-beanparam")
    public Response postWithBeanParam(@BeanParam final BookParams params) {
        logger.debug("@POST with @BeanParam");
        Book book = Book.with(params.isbn)
                .title(params.title)
                .author(params.author)
                .published(params.published != null ? params.published.getDate() : null)
                .translator(params.translator)
                .summary(params.summary)
                .build();
        return create(book);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Book update(final Book book) {

        Book.validate(book);  // ==> Response.Status.BAD_REQUEST if validation fails

        if(BookRepository.findBook(book.getIsbn()) == null) {
            logger.debug("Could not update book with isbn: '{}'. Not such book in repository", book.getIsbn());
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .location(uriInfo.getAbsolutePath())
                            .build()
            );
        }
        BookRepository.updateBook(book);
        logger.debug("Book with isbn: '{}' updated", book.getIsbn());
        return book;  // ==> Response.Status.OK
    }

    @DELETE
    @Path("{isbn}")
    public void delete(@PathParam("isbn") final String isbn) {
        
        if(BookRepository.findBook(isbn) != null) {
            boolean deleted = BookRepository.removeBook(isbn);
            logger.debug((deleted ? "Book with isbn: '{}' deleted" : "Nothing to delete @ isbn: '{}'"), isbn);
        }
        else {
            logger.debug(("Book with isbn: '{}' not found"), isbn);
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .location(uriInfo.getAbsolutePath())
                            .entity("Book with isbn: '" + isbn + "' not found")
                            .type(MediaType.TEXT_PLAIN)
                            .build()
            );
        }
        return; // ==> Response.Status.NO_CONTENT
    }

    @GET
    @Path("{isbn}")
    public Book byIsbn(
            @NotNull
            @Size(min = 13, max = 13)
            @Pattern(regexp = "\\d+", message = "ISBN must be a valid number")
            @PathParam("isbn") final String isbn) {

        Book result = BookRepository.findBook(isbn);
        if (result == null) {
            logger.debug(("Book with isbn: '{}' not found"), isbn);
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .location(uriInfo.getAbsolutePath())
                            .build()
            );
        }
        return result; // ==>  Response.Status.OK
        // return Response.Status.BAD_REQUEST if Bean validation fails
    }

    @GET
    public Response allBooks(@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().clone();
        if(offset != null) {
            uriBuilder.queryParam("offset", offset);
        }
        if(limit != null) {
            uriBuilder.queryParam("limit", limit);
        }

        List<Book> books = BookRepository.getAllBooks(offset, limit);
        if(books.size()< 1) {
            return Response
                    .noContent()
                    .location(uriBuilder.build())
                    .build();
        }

        GenericEntity<List<Book>> entities = new GenericEntity<List<Book>>(books){};
        UriBuilder linkBuilder = uriInfo.getRequestUriBuilder().clone();
        return Response
            .ok(entities)
            .location(uriBuilder.build())
            //.link(linkBuilder.queryParam("offset", 10).queryParam("limit", limit).build(), "prev")
            //.link(linkBuilder.queryParam("offset", 20).queryParam("limit", limit).build(), "next")
            .build();
    }

    @GET
    @Path("publisher/{name}")
    public Response booksByPublisher(@PathParam("name") final String name) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().clone();

        List<Book> books = BookRepository.getBooksByPublisher(name);
        if(books.size()< 1) {
            return Response
                    .noContent()
                    .location(uriBuilder.build())
                    .build();
        }
        GenericEntity<List<Book>> entities = new GenericEntity<List<Book>>(books){};
        return Response
                .ok(entities)
                .location(uriBuilder.build())
                        // .link("http://foo", "prev") // TODO
                .build();
    }

    // Unhandeled exception
    @GET
    @Path("unhandeled-exception")
    public Response unhandledExceptionWillReturn_INTERNAL_SERVER_ERROR() {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().clone();
        logger.debug("Throwing IllegalStateException @: {}", uriBuilder.build());
        throw new IllegalStateException("Illegal state exception thrown");
    }
    
    
    // Entity bean validation examples
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("entity-bean-validation-exception")
    public Response entityBeanValidationExceptionWillReturn_BAD_REQUEST(@Valid final Book book) {
        return Response
                .created(uriInfo.getAbsolutePathBuilder().clone().path(book.getIsbn()).build())
                .entity(book)
                .build();
    }

    // Constraint bean validation example
    @GET
    @Path("constraint-bean-validation-exception/{isbn}")
    public Book constraintBeanValidationExceptionWillReturn_BAD_REQUEST (
            @PathParam("isbn")
            @NotNull
            @Size(min = 13, max = 13)
            @Pattern(regexp = "\\d+", message = "The ISBN must be a valid number")
            final String isbn) {
        
        return BookRepository.findBook(isbn);
    }

    @GET
    @Path("null-result")
    @NotNull(message="Return NULL not allowed")
    public Book nullResultWillReturn_INTERNAL_SERVER_ERROR() {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().clone();
        return null;
    }



    public static class DateAdapter {
        private Date date;

        public DateAdapter(String date){
            this.date = getDateFromString(date);
        }

        public Date getDate(){
            return this.date;
        }

        public static Date getDateFromString(String dateString) {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date = df.parse(dateString);
                return date;
            } catch (ParseException e) {
                try {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = df.parse(dateString);
                    return date;
                } catch (ParseException e2) {
                    //TODO: throw WebApplicationException ...
                    return null;
                }
            }
        }
    }

    public static class BookParams {
        @FormParam("isbn")
        String isbn;

        @FormParam("title")
        String title;

        @FormParam("author")
        String author;

        @FormParam("published")
        DateAdapter published;

        @FormParam("translator")
        String translator;

        @FormParam("summary")
        String summary;
    }
}
