package recurso;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/helloword")
public class TodoProducto {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTexHello() {
       return "Hello World()";
    }
}
