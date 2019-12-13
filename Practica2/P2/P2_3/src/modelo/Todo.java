package modelo;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@XmlRootElement 
@Entity
public class Todo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String id;
	private String resumen;
	private String descripcion;
	
	public Todo(){
	}

	public Todo(Todo todo){
		id = todo.getId();
		resumen = todo.getResumen();
		descripcion = todo.getDescripcion();
	}
	
	public Todo (String id, String resumen){
		this.id = id;
		this.resumen = resumen;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getResumen() {
		return resumen;
	}
	
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString() {
		return "Producto [id=" + id + ", resumen=" + resumen + ", descripcion=" + descripcion + "]";
	}
}
