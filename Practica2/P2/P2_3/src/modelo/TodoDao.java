package modelo;

import java.util.HashMap;
import java.util.Map;

import modelo.Todo;

import modelo.BDProducto;

public enum TodoDao {
	INSTANCE;
	private Map<String, Todo> proveedorContenidos = new HashMap<String, Todo>();
	
	private TodoDao() {
		//Creamos 4 contenidos iniciales
		Todo todo;
		todo = new Todo("1", "Learning DSBCS");
		todo.setDescripcion("Libro para el aprendizaje de DSBCS");
		proveedorContenidos.put("1", todo);
		BDProducto.insertar(todo);
		todo = new Todo("2", "Mesa");
		todo.setDescripcion("Bonita mesa de estudio cuadrada ");
		proveedorContenidos.put("2", todo);
		BDProducto.insertar(todo);
		todo = new Todo("3", "Silla");
		todo.setDescripcion("Silla Gamin, ergonomica, ideal para el estudio");
		proveedorContenidos.put("1", todo);
		BDProducto.insertar(todo);
		todo = new Todo("4", "Flexo");
		todo.setDescripcion("Lampara de led de bajo consumo");
		proveedorContenidos.put("2", todo);
		BDProducto.insertar(todo);
	}
	public Map<String, Todo> getModel(){
		return proveedorContenidos;
	
	}
}
