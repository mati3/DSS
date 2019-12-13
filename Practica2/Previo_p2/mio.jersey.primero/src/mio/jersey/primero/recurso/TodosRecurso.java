package mio.jersey.primero.recurso;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import mio.jersey.primero.dao.TodoDao;
import mio.jersey.primero.modelo.Todo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

@Path("/todos")
public class TodosRecurso {
	// Permite insertar objetos contextuales en la clase,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	// Devolvera la lista de todos lo elementos contenidos en el proveedor al
	//navegador del usuario
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Todo> getTodosBrowser() {
		List<Todo> todos = new ArrayList<Todo>();
		todos.addAll(TodoDao.INSTANCE.getModel().values());
		return todos;
	}
	
	//Devolvera la lista de todos lo elementos contenidos en el proveeedor
	//a las aplicaciones cliente
	//@GET
	//@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	//############...
	//Para obtener el numero total de elementos en el proveedor de contenidos
	
	@GET
	@Path("cont")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int cont = TodoDao.INSTANCE.getModel().size();
		return String.valueOf(cont);
	}
	
	//Para enviar datos al servidor como un formulario Web
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newTodo(@FormParam("id") String id,
	@FormParam("resumen") String resumen,
	@FormParam("descripcion") String descripcion,
	@Context HttpServletResponse servletResponse) throws IOException {
		Todo todo = new Todo(id, resumen);
		if (descripcion != null) {
			todo.setDescripcion(descripcion);
		}
		TodoDao.INSTANCE.getModel().put(id, todo);
		servletResponse.sendRedirect("../crear_todo.html");
	}
	
	//Para poder pasarle argumentos a las operaciones en el servidor
	//Permite por ejemplo escribir http://localhost:8080/com.mio.jersey.todo/rest/todos/1
	@Path("{todo}")
	public TodoRecurso getTodo(@PathParam("todo") String id) {
		//return new TodoRecurso(uriInfo, request, id);
		return new TodoRecurso();
	}
}