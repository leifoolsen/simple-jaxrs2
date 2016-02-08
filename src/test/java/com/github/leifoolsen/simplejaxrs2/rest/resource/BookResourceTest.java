package com.github.leifoolsen.simplejaxrs2.rest.resource;

import com.github.leifoolsen.simplejaxrs2.domain.Book;
import com.github.leifoolsen.simplejaxrs2.embeddedjetty.JettyFactory;
import com.github.leifoolsen.simplejaxrs2.repository.BookRepository;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.server.validation.ValidationError;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BookResourceTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BOOK_RESOURCE_PATH = "books";
    private static final String TRAVELLING_TO_INFINITY_ISBN = "9781846883668";
    private static final String FISKEN_ISBN = "9788202148683";
    private static final String ISBN_NOT_IN_REPOSITORY = "9788202148680";

    private static Server server;
    private static WebTarget target;

    @BeforeClass
    public static void setUp() throws Exception {

        // start the server
        server = new JettyFactory().build();
        JettyFactory.start(server);

        assertTrue(server.isStarted());
        assertTrue(server.isRunning());

        // create the client
        Client c = ClientBuilder.newClient();
        target = c.target(server.getURI()).path("api");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        JettyFactory.stop(server);
    }

    @Test
    public void pingShouldReturnPong() {
        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("ping")
                .request(MediaType.TEXT_PLAIN)
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String ping = response.readEntity(String.class);
        assertEquals(ping, "Pong!");
    }

    @Test
    public void pingBoolShouldReturnTrue() {
        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("ping-bool")
                .request(MediaType.TEXT_PLAIN)
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Boolean bool = response.readEntity(Boolean.class);
        assertEquals(bool.booleanValue(), true);
    }

    @Test
    public void createBook() {
        Book book = Book
            .with("9788202289331")
            .title("Kurtby")
            .author("Loe, Erlend")
            .published(new GregorianCalendar(2008, 1, 1).getTime())
            .summary("Kurt og gjengen er på vei til Mummidalen da Kurt sovner ved rattet og trucken havner " +
                    "i en svensk elv. Et langt stykke nedover elva ligger Kurtby - et lite samfunn hvor en " +
                    "dame som heter Kirsti Brud styrer og steller i samråd med Den hellige ånd. Det går " +
                    "ikke bedre enn at Kurt havner på kjøret, nærmere bestemt på Jesus-kjøret. " +
                    "Så blir han pastor og går bananas.")
            .build();

        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        Book bookResponse = response.readEntity(Book.class);
        assertEquals(book, bookResponse);
    }

    @Test
    public void createBookWithValidationFailureShouldReturn_BAD_REQUEST() {
        final String tooShortISBN = "97882021486";
        Book book = Book
                .with(tooShortISBN)
                .title("Foo")
                .author("Bar")
                .published(new GregorianCalendar(1990, 1, 1).getTime())
                .summary("Baz")
                .build();

        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON_TYPE));

        //List<ValidationError> errors = response.readEntity(new GenericType<List<ValidationError>>() {});
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
    

    @Test
    public void createBookShouldReturn_CONFLICT() {
        Book bookAlreadyInRepository = BookRepository.findBook(TRAVELLING_TO_INFINITY_ISBN);
        assertNotNull(bookAlreadyInRepository);

        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(bookAlreadyInRepository, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    public void createBookWithFormParam() {

        Form form = new Form();
        form.param("isbn", "9780857520197")
            .param("title", "Second Life")
            .param("author", "Watson, S. J.")
            .param("published", "2015-02-12")
            .param("translator", null)
            .param("summary", "The sensational new psychological thriller from ... ");

        Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("post-with-formparam")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void createBookWithFormParam2() {
        Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("post-with-formparam")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity("isbn=9780857520198&title=Title&author=Author&published=2015-01-01",
                        MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        // http://localhost:8080/api/books/post-with-formparam
    }

    @Test
    public void createBookWithBeanParam() {
        Form form = new Form();
        form.param("isbn", "9780297871934")
            .param("title", "Accidence Will Happen : The Non-Pedantic Guide to English Usage")
            .param("author", "Kamm, Oliver")
            .param("published", "2015-02-12")
            .param("translator", null)
            .param("summary", "Are standards of English alright - or should that be all right? To knowingly " +
                    "split an infinitive or not to? And what about ending a sentence with preposition, or for " +
                    "that matter beginning one with 'and'? We learn language by instinct, but good English, " +
                    "the pedants tell us, requires rules. Yet, as Oliver Kamm demonstrates, many of the purists' " +
                    "prohibitions are bogus and can be cheerfully disregarded. ACCIDENCE WILL HAPPEN is an " +
                    "authoritative and deeply reassuring guide to grammar, style and the linguistic conundrums " +
                    "we all face.");

        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("post-with-beanparam")
                .request()
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void updateBook() {
        Book bookToUpdate = BookRepository.findBook(TRAVELLING_TO_INFINITY_ISBN);
        assertNotNull(bookToUpdate);

        Book updatedBook = Book.with(bookToUpdate)
                .title("Travelling to Infinity: The True Story behind")
                .build();

        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(updatedBook, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void updateNonExistingBookShouldReturn_NOT_FOUND() {
        Book bookToUpdate = Book.with("1234567890123").author("A").title("T").build();
        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(bookToUpdate, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getBookByIsbnShouldReturn_OK() {
        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("9781846883668")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Book book = response.readEntity(Book.class);
        assertEquals("9781846883668", book.getIsbn());
    }

    @Test
    public void bookNotFoundShouldReturn_NOT_FOUND() throws Exception {
        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("1234567890123")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void invalidIsbnShouldReturn_BAD_REQUEST() throws Exception {
        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("en-tulle-isbn")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void deleteBookShouldReturn_NO_CONTENT() {
        Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path(FISKEN_ISBN)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void deleteBookShouldReturn_NOT_FOUND() {
        Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path(ISBN_NOT_IN_REPOSITORY)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldGetAllBooks() {
        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        final List<Book> result = response.readEntity(new GenericType<List<Book>>() {});
        assertEquals(BookRepository.countBooks(), result.size());
    }

    @Test
    public void shouldPaginateTroughAllBooks() {
        Integer offset = 0;
        int numberOfBooks = 0;
        Response response;
        do {
            response = target
                    .path(BOOK_RESOURCE_PATH)
                    .queryParam("offset", offset)
                    .queryParam("limit", 5)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get();

            if(Response.Status.OK.getStatusCode() == response.getStatus()) {
                final List<Book> result = response.readEntity(new GenericType<List<Book>>() {});
                numberOfBooks += result.size();
                offset += 5;
            }
            else {
                break;
            }
        }
        while (true);

        logger.debug("Number of books in repository: {}", numberOfBooks);
        assertEquals(BookRepository.countBooks(), numberOfBooks);
    }

    @Test
    public void shouldGetBooksByPublisher() {
        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("publisher")
                .path("Vintage")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        final List<Book> result = response.readEntity(new GenericType<List<Book>>() {});
        assertThat(result.size(), greaterThan(0));
    }

    @Test
    public void unhandeledExceptionShouldReturn_INTERNAL_SERVER_ERROR() {
        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("unhandeled-exception")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void entityBeanValidationExceptionShouldReturn_BAD_REQUEST() {
        Book invalidBook = Book
                .with("97882021486xx")
                .build();

        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("entity-bean-validation-exception")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(invalidBook, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        List<ValidationError> errors = response.readEntity(new GenericType<List<ValidationError>>() {});
        assertThat(errors.size(), greaterThan(0));
    }

    @Test
    public void constraintBeanValidationExceptionShouldReturn_BAD_REQUEST() {
        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("constraint-bean-validation-exception")
                .path("an-invalid-isbn")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        List<ValidationError> errors = response.readEntity(new GenericType<List<ValidationError>>() {});
        assertThat(errors.size(), greaterThan(0));
    }

    @Test
    public void nullResultShouldReturn_INTERNAL_SERVER_ERROR() {
        final Response response = target
                .path(BOOK_RESOURCE_PATH)
                .path("null-result")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        List<ValidationError> errors = response.readEntity(new GenericType<List<ValidationError>>() {});
        assertThat(errors.size(), greaterThan(0));
    }
}
