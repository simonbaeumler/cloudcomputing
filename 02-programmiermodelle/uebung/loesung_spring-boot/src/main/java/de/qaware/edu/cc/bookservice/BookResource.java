package de.qaware.edu.cc.bookservice;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import edu.qaware.cc.reactiveZwitscher.actors.MessageCollectorActor;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.concurrent.Await;
import scala.concurrent.Future;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static akka.japi.Util.classTag;
import static akka.pattern.Patterns.ask;

/**
 * The REST resource for the books.
 */
@Component
@Path("/books")
@Api(value = "/books", description = "Operations about books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    public BookResource() {
        ActorSystem actorSystem = ActorSystem.create("Zwitscher");
        this.messageCollector =
                actorSystem.actorOf(Props.create(MessageCollectorActor.class), "Message-Collector");
    }

    @Autowired
    private Bookshelf bookshelf;

    ActorRef messageCollector;

    @GET
    @ApiOperation(value = "Find books", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found all books")
    })
    public Response books(@ApiParam(value = "title to search")
                          @QueryParam("title") String title) {
        Collection<Book> books = bookshelf.findByTitle(title);
        return Response.ok(books).build();
    }

    @GET
    @ApiOperation(value = "Find search items", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found items"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @Path("/search")
    public Response searchItems(@ApiParam(value = "Search string")
                          @QueryParam("search") String searchString) {

        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
        Future resultFuture = ask(messageCollector, searchString, timeout)
                .mapTo(classTag(List.class));

        List<String> result;
        try {
            result = (List<String>) Await.result(resultFuture, timeout.duration());
        } catch (Exception e) {
            return Response.serverError().build();
        }

        return Response.ok(result).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create book")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created the book"),
            @ApiResponse(code = 409, message = "Book already exists")
    })
    public Response create(Book book) {
        boolean created = bookshelf.create(book);
        if (created) {
            return Response.created(URI.create("/api/books/" + book.getIsbn())).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @ApiOperation(value = "Find book by ISBN", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the book"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @Path("/{isbn}")
    public Response byIsbn(@ApiParam(value = "ISBN to search", required = true)
                           @PathParam("isbn") String isbn) {
        Book book = bookshelf.findByIsbn(isbn);
        return Response.ok(book).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update book by ISBN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated the book")
    })
    @Path("/{isbn}")
    public Response update(@ApiParam(value = "ISBN to search", required = true)
                           @PathParam("isbn") String isbn, Book book) {
        bookshelf.update(isbn, book);
        return Response.ok().build();
    }

    @DELETE
    @ApiOperation(value = "Delete book by ISBN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book deleted")
    })
    @Path("/{isbn}")
    public Response delete(@ApiParam(value = "ISBN to delete", required = true)
                           @PathParam("isbn") String isbn) {
        bookshelf.delete(isbn);
        return Response.ok().build();
    }
}
