package interfaz;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Todo;

public class TablaProductos extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private List<Todo> lista;
    private String[] columnNames = { "Id", "Resumen", "Descripcion", "Accion" };
    public TablaProductos(List<Todo> productos){
        this.lista = productos;
    }
    @Override
    public int getRowCount() {
        return lista.size();
    }
    @Override
    public int getColumnCount() {
        return 4;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	if (columnIndex == 2) {
			return false;
		}
    	return true;
    }
    //añade un producto
    public void add(Todo producto) {
        lista.add(producto);
        fireTableDataChanged();
    }
    //elimina un producto
    public void remove(Todo producto) {
        if (lista.contains(producto)) {
            lista.remove(producto);
            fireTableDataChanged();
        }
    }
    //elimina por id
    public void removeRow(int rowIndex) {
    	lista.remove(rowIndex);
        fireTableDataChanged();
    }

    public Todo getProductoAt(int rowIndex) {
    	return lista.get(rowIndex);
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Todo producto = lista.get(rowIndex);
        switch (columnIndex){
            case 0:
                return producto.getId();
            case 1:
                return producto.getResumen();
            case 2:
                return producto.getDescripcion();
            case 3: 
            	return "Borrar";
        }
        return "";
    }
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	Todo producto = lista.get(rowIndex);
        switch (columnIndex){
	        case 0:
	            producto.setId((String) value);
				break;
	        case 1:
	            producto.setResumen((String) value);
				break;
	        case 2:
	            producto.setDescripcion((String) value);
				break;
        }
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}
