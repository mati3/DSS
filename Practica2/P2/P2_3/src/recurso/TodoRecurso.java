package recurso;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import modelo.BDProducto;
import modelo.Todo;
import modelo.TodoDao;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;


public class TodoRecurso {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	
	public TodoRecurso(UriInfo uriInfo, javax.ws.rs.core.Request request2, String id) {
		this.uriInfo = uriInfo;
		this.request = (Request) request2;
		this.id = id;
	}
	
	// Este metodo se llamara si existe una peticion XML desde el cliente
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Todo getProductoXML() {
		Todo todo = TodoDao.INSTANCE.getModel().get(id);
		if (todo == null) {
			throw new RuntimeException("Get: Producto con " + id + " no encontrado");
		}
		return todo;
	}
	
	//Lo que sigue se puede utilizar para comprobar la integracion con el navegador que utilicemos
	@GET
	@Produces({ MediaType.TEXT_XML })
	public Todo getProductoHTML() {
		Todo todo = TodoDao.INSTANCE.getModel().get(id);
		if (todo == null) {
			throw new RuntimeException("Get: Producto con " + id + " no encontrado");
		}
		return todo;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putTodo(JAXBElement<Todo> todo) {
		Todo p = todo.getValue();
		return putAndGetResponse(p);
	}
	
	//Para enviar datos al servidor como un formulario Web
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response putTodo(
			@FormParam("id") String id,
			@FormParam("resumen") String resumen,
			@FormParam("descripcion") String descripcion,
			@Context HttpServletResponse servletResponse) throws IOException {
		Todo todo = new Todo(id, resumen);
		if (descripcion != null) {
			todo.setDescripcion(descripcion);
		}
		return putAndGetResponse(todo);
	}
	
	@DELETE
	public void deleteProducto() {
		Todo p = BDProducto.seleccionarTodo(id);
		if (BDProducto.existeId(id) == false) {
			throw new RuntimeException("Delete: Producto con " + id + " no encontrado");
		}
		BDProducto.eliminar(p);
	}
	
	private Response putAndGetResponse(Todo todo) {
		Response response;
		if (TodoDao.INSTANCE.getModel().containsKey(todo.getId())) {
			response = Response.noContent().build();
		} else {
			response = Response.created(uriInfo.getAbsolutePath()).build();
		}
		TodoDao.INSTANCE.getModel().put(todo.getId(), todo);
		return response;
	}

}