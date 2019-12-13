package comunicacion;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import modelo.Todo;
import modelo.BDProducto;

public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
		doPost(peticion, respuesta);
	}
	
	@Override
	protected void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
		String accion = peticion.getParameter("action");
		
		if (accion == null) {
			respuesta.setContentType("text/html");	
			respuesta.setCharacterEncoding("UTF-8");
			
			PrintWriter writer = respuesta.getWriter();
			
			writer.println("<h1>Productos</h1>");
			writer.println("<table>");
			writer.println("<thead>");
			writer.println("<tr>");
			writer.println("<th>Id</th>");
			writer.println("<th>Resumen</th>");
			writer.println("<th>Descripcion</th>");
			writer.println("</tr>");
			writer.println("</thead>");
			writer.println("<tbody>");
			for (Todo producto: BDProducto.listarTodos()) {
				writer.println("<tr>");
				writer.println("<td>" + producto.getId() + "</td>");
				writer.println("<td>" + producto.getResumen() + "</td>");
				writer.println("<td>" + producto.getDescripcion() + "</td>");
				writer.println("</tr>");
			}
			writer.println("</tbody>");
			writer.println("</table>");
		} else {	
			String id = peticion.getParameter("id");
			String resumen = peticion.getParameter("resumen");
			String descripcion = peticion.getParameter("descripcion");
			
			ObjectOutputStream oos = new ObjectOutputStream(respuesta.getOutputStream()); 
			
			switch (accion) {
			case "aniadirProducto":
				if (!BDProducto.existeId(id)) {
					Todo producto = new Todo();
					producto.setId(id);
					producto.setResumen(resumen);
					producto.setDescripcion(descripcion);
					BDProducto.insertar(producto);
					oos.writeInt(0);
					oos.writeObject("Producto aniadido correctamente.");
				} else {
					oos.writeInt(1);
					oos.writeObject("Ya existe un producto con el id " + id + ".");
				}
				break;
			case "actualizarProducto":
				if (BDProducto.existeId(id)) {
					Todo producto = BDProducto.seleccionarTodo(id);
					producto.setResumen(resumen);
					producto.setDescripcion(descripcion);
					BDProducto.actualizar(producto);
					oos.writeInt(0);
					oos.writeObject("Producto actualizado correctamente.");
				} else {
					oos.writeInt(1);
					oos.writeObject("No existe un producto con el id " + id + ".");
				}
				break;
			case "eliminarProducto":
				if (BDProducto.existeId(id)) {
					Todo producto = BDProducto.seleccionarTodo(id);
					BDProducto.eliminar(producto);
					oos.writeInt(0);
					oos.writeObject("Producto eliminado correctamente.");
				} else {
					oos.writeInt(1);
					oos.writeObject("No existe un producto con el id " + id + ".");
				}
				break;
	  		default: // lista productos por defecto
	  			List<Todo> productos = BDProducto.listarTodos();
	  			oos.writeObject(productos);
			}
			
			oos.flush();
			oos.close();
		}		
	}
}
