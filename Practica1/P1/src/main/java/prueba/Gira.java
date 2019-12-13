package prueba;

public class Gira {
	   private String fecha;
	   private String ciudad;
	   private String pais;
	   private boolean canEdit;
	   
	   public Gira (String fecha, String ciudad, String pais) {
	      this.fecha = fecha;
	      this.ciudad = ciudad;
	      this.pais = pais;
	      canEdit = false;
	  }   
	  public String getfecha() {
	      return fecha;
	  }

	  public void setfecha(String fecha) {
	      this.fecha = fecha;
	  }	
	  
	  public String getciudad() {
	      return ciudad;
	  }

	  public void setciudad(String ciudad) {
	      this.ciudad = ciudad;
	  } 
	  
	  public String getpais() {
	      return pais;
	  }

	  public void setpais(String pais) {
	      this.pais = pais;
	  } 
	  
	  public boolean isCanEdit() {
	      return canEdit;
	  }

	  public void setCanEdit(boolean canEdit) {
	      this.canEdit = canEdit;
	  }
}
