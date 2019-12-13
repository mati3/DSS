package prueba;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javax.faces.bean.ManagedBean; 
import javax.faces.bean.ManagedProperty; 
import javax.faces.bean.RequestScoped;  

@ManagedBean(name = "webmusical", eager = true) 
@RequestScoped 
public class webmusical implements Serializable { 
	  private static final long serialVersionUID = 1L;
	
	  private String fecha;
	  private String ciudad;
	  private String pais;
	  private static final ArrayList<Gira> giras
	     = new ArrayList<Gira>(Arrays.asList(
	     new Gira("17/02/2018", "Santa Cruz", "Argentina"),
	     new Gira("21/02/2018", "Viña del Mar", "Chile"),
	     new Gira("07/03/2018", "Mibnterrey", "México"),
	     new Gira("10/03/2018", "Tijuana", "México"),
	     new Gira("14/03/2018", "Guadalajara", "México"),
	     new Gira("18/03/2018", "Mérida", "México"),
	     new Gira("21/04/2018", "Panama", "Panama"),
	     new Gira("01/05/2018", "San Juan", "Puerto Rico"),
	     new Gira("05/08/2018", "Madrid", "España"),
	     new Gira("12/08/2018", "Granada", "España"),
	     new Gira("18/08/2018", "Barcelona", "España")
	  ));	
	  
	  public ArrayList<Gira> getGiras() {
	      return giras;
	  }

	  public String addGira() {		 
	      Gira Gira = new Gira(fecha,ciudad,pais);
	      giras.add(Gira);
	      return null;
	  }

	  public String deleteGira(Gira Gira) {
	      giras.remove(Gira);		
	      return null;
	  }

	  public String editGira(Gira Gira) {
	      Gira.setCanEdit(true);
	      return null;
	  }

	  public String saveGiras() {		      
	      for (Gira Gira : giras) {
	         Gira.setCanEdit(false);
	      }		
	      return null;
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

	   public String moveToHome() {      
		   return "home";    
	   }  

	   public String moveTobiografia() {      
		   return "biografia";    
	   }  
	   
	   public String moveTodiscografia() {      
		   return "discografia";    
	   }  
	   
	   public String moveToformulario() {      
		   return "formulario";    
	   }  
	   
	   public String moveToGira() {      
		   return "gira";    
	   }  
	   
}
