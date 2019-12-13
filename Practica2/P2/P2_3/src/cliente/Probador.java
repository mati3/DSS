package cliente;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import modelo.Todo;

public class Probador {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientConfig config = new ClientConfig();
		Client cliente = ClientBuilder.newClient(config);
		WebTarget servicio = cliente.target(getBaseURI());
		
		// crearse un tercer "objeto" todo, aparte de los 2 que ya estan creados en TodoDao
		Todo todo = new Todo("3","Este es el resumen del tercer registro");
		Response respuesta = servicio.path("rest").path("todos")
		.path(todo.getId()).request(MediaType.APPLICATION_XML)
		.put(Entity.entity(todo, MediaType.APPLICATION_XML), Response.class);
		System.out.print("Codigo devuelto: ");

		// El codigo que se devuelve deberia ser: 201 == created resource
		System.out.println(respuesta.getStatus());

		// Mostrar el contenido del recurso Todos como texto XML	
		System.out.println("Mostrar como Texto XML Plano");
		System.out.println(servicio.path("rest").path("todos")
		.request().accept(MediaType.TEXT_XML).get(String.class));

		// Ahora nos crearemos un cuarto recurso Todo utilizando un formulario Web
		System.out.println("Creacion de 1 formulario");
		Form form = new Form();
		form.param("id", "6");
		form.param("resumen", "Demostracion de la biblioteca-cliente para formularios");
		respuesta = servicio.path("rest").path("todos")
		.request().post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED), Response.class);
		System.out.println("Respuesta con el formulario " +
		respuesta.getStatus());

		// Mostrar el contenido del recurso Todos, se ha debido crear el elemento con id = 4
		System.out.println("Contenidos del recurso, despues de enviar el elemento id=6");
		System.out.println(servicio.path("rest")
		.path("todos").request().accept(MediaType.APPLICATION_XML).get(String.class));

		// Ahora vamos a eliminar el "objeto" con id=1 del recurso
		servicio.path("rest").path("todos/6").request().delete();
		
		// Mostramos el contenido del recurso Todos, el elemento con id=1 deberia haber sido eliminado
		System.out.println("El elemento con id = 6 del recurso se ha eliminado");
		System.out.println(servicio.path("rest").path("todos")
		.request().accept(MediaType.APPLICATION_XML).get(String.class));
	
	}
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/P2_3").build();
	}
}
