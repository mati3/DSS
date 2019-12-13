package mio.jersey.primero.dao;

import java.util.HashMap;
import java.util.Map;

import mio.jersey.primero.modelo.Todo;

public enum TodoDao {
	INSTANCE;
	private Map<String, Todo> proveedorContenidos = new HashMap<String, Todo>();
	
	private TodoDao() {
		//Creamos 2 contenidos iniciales
		Todo todo = new Todo("1", "Aprender REST");
		todo.setDescripcion("Leer http://lsi.ugr.es/dsbcs/Documentos/Practica/practica3.html");
		proveedorContenidos.put("1", todo);
		todo = new Todo("2", "Aprender algo sobre DSBCS");
		todo.setDescripcion("Leer todo el material de http://lsi.ugr.es/dsbcs");
		proveedorContenidos.put("2", todo);
	}
	public Map<String, Todo> getModel(){
		return proveedorContenidos;
	
	}
}
