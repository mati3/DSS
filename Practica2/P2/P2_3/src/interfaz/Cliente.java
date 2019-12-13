package interfaz;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

import modelo.Todo;

public class Cliente extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private JPanel panelContenido;
	private JTable table;
	private JScrollPane scroll;
	
	private JToolBar toolBar;
	private JButton btnToolbarAniadirProducto;

	private JPanel panelAniadir;
	private JLabel lblId;
	private JTextField tfId;
	private JLabel lblResumen;
	private JTextField tfResumen;
	private JLabel lblDescripcion;
	private JTextField tfDescripcion;
	private JPanel botonera;
	private JButton btnAniadir;
	private JButton btnCancelar;

	private static final String EXPR_REG_ID = "^[0-9]+$"; 
	final Pattern pattern = Pattern.compile(EXPR_REG_ID);
	
	public static String urlString = "http://localhost:8080/P2_3/servlet";

	private TablaProductos modeloTablaProductos;
	
	//lanza la aplicacion
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{ System.err.println("Unable to load System look and feel"); }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//la ventana
	public Cliente() {
		setTitle("DSS P2");
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 300);
		setLocationRelativeTo(null);
		panelContenido = new JPanel();
		panelContenido.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelContenido);
		panelContenido.setLayout(new BorderLayout(0, 0));
		List<Todo> listaProductos = obtenerListaProductos();
		modeloTablaProductos = new TablaProductos(listaProductos);
		table = new JTable(modeloTablaProductos) {
			private static final long serialVersionUID = 1L;
			@Override
			public void editingStopped(ChangeEvent e) {
		        TableCellEditor editor = getCellEditor();
		        if (editor != null) {
		            Object value = editor.getCellEditorValue();
		            Todo producto = new Todo(modeloTablaProductos.getProductoAt(editingRow));
		            if (editingColumn < 2) {
			            switch (editingColumn) {
				            case 0:
				                producto.setId((String) value);
								break;
				            case 1:
				                producto.setResumen((String) value);
								break;
							default:
								break;
						}
						try {
							Map<String,String> parametros = new HashMap<String, String>();
							parametros.put("action", "actualizarProducto");
							parametros.put("id", producto.getId());
							parametros.put("resumen", producto.getResumen());
							parametros.put("descripcion", producto.getDescripcion());
							ObjectInputStream respuesta = new ObjectInputStream(realizarPeticionPost(urlString, parametros));
							int codigo = respuesta.readInt();
							String mensaje = (String) respuesta.readObject();
							switch (codigo) {
								case 0:
						            setValueAt(value, editingRow, editingColumn);
									break;
								default:
									JOptionPane.showMessageDialog(Cliente.this,
										    mensaje,
										    "Error",
										    JOptionPane.ERROR_MESSAGE);
									break;
							}							
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
		            removeEditor();
		        }
		    }
		};
		Action borrarFila = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        Todo producto = modeloTablaProductos.getProductoAt(modelRow);
		        
		        int resultadoDialogo = JOptionPane.showConfirmDialog(
		        		Cliente.this, 
		        		"¿Quieres eliminar el producto <"+producto.getId()+">?",
		        		"Eliminar producto",
		        		JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		        if (resultadoDialogo == JOptionPane.YES_OPTION) {
					try {
						Map<String,String> parametros = new HashMap<String, String>();
						parametros.put("action", "eliminarProducto");
						parametros.put("id", producto.getId());
						ObjectInputStream respuesta = new ObjectInputStream(realizarPeticionPost(urlString, parametros));
						int codigo = respuesta.readInt();
						String mensaje = (String) respuesta.readObject();
						switch (codigo) {
							case 0:
						        modeloTablaProductos.removeRow(modelRow);
								break;
							default:
								JOptionPane.showMessageDialog(Cliente.this,
									    mensaje,
									    "Error",
									    JOptionPane.ERROR_MESSAGE);
								break;
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			    }
		    }
		};
		//borra fila
		new ButtonColumn(table, borrarFila, 3);
		table.putClientProperty("terminateEditOnFocusLost", true);
		scroll = new JScrollPane(table);
		panelContenido.add(scroll, BorderLayout.CENTER);		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panelContenido.add(toolBar, BorderLayout.NORTH);
		btnToolbarAniadirProducto = new JButton("Aniadir Producto");
		btnToolbarAniadirProducto.setActionCommand("ADDPRODUCTO");
		btnToolbarAniadirProducto.addActionListener(this);
		toolBar.add(btnToolbarAniadirProducto);
		panelAniadir = new JPanel();
		panelAniadir.setVisible(false);
		panelContenido.add(panelAniadir, BorderLayout.EAST);
		GridBagLayout gbl_panelAniadir = new GridBagLayout();
		gbl_panelAniadir.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelAniadir.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelAniadir.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelAniadir.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelAniadir.setLayout(gbl_panelAniadir);
		lblId = new JLabel("Id: ");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.anchor = GridBagConstraints.EAST;
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		panelAniadir.add(lblId, gbc_lblId);
		tfId = new JTextField();
		GridBagConstraints gbc_tfId = new GridBagConstraints();
		gbc_tfId.insets = new Insets(0, 0, 5, 5);
		gbc_tfId.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfId.gridx = 1;
		gbc_tfId.gridy = 0;
		panelAniadir.add(tfId, gbc_tfId);
		tfId.setColumns(10);
		lblResumen = new JLabel("Resumen: ");
		lblResumen.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblResumen = new GridBagConstraints();
		gbc_lblResumen.anchor = GridBagConstraints.EAST;
		gbc_lblResumen.insets = new Insets(0, 0, 5, 5);
		gbc_lblResumen.gridx = 0;
		gbc_lblResumen.gridy = 1;
		panelAniadir.add(lblResumen, gbc_lblResumen);
		tfResumen = new JTextField();
		GridBagConstraints gbc_tfResumen = new GridBagConstraints();
		gbc_tfResumen.insets = new Insets(0, 0, 5, 5);
		gbc_tfResumen.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfResumen.gridx = 1;
		gbc_tfResumen.gridy = 1;
		panelAniadir.add(tfResumen, gbc_tfResumen);
		tfResumen.setColumns(10);
		lblDescripcion = new JLabel("Descripcion: ");
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 2;
		panelAniadir.add(lblDescripcion, gbc_lblDescripcion);
		tfDescripcion = new JTextField();
		GridBagConstraints gbc_tfDescripcion = new GridBagConstraints();
		gbc_tfDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_tfDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfDescripcion.gridx = 1;
		gbc_tfDescripcion.gridy = 2;
		panelAniadir.add(tfDescripcion, gbc_tfDescripcion);
		tfDescripcion.setColumns(10);
		tfDescripcion.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		       btnAniadir.doClick();
		    }
		});
		botonera = new JPanel();
		GridBagConstraints gbc_botonera = new GridBagConstraints();
		gbc_botonera.gridwidth = 2;
		gbc_botonera.insets = new Insets(0, 0, 0, 5);
		gbc_botonera.fill = GridBagConstraints.BOTH;
		gbc_botonera.gridx = 0;
		gbc_botonera.gridy = 7;
		panelAniadir.add(botonera, gbc_botonera);
		botonera.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		btnAniadir = new JButton("A\u00F1adir");
		btnAniadir.setActionCommand("EXEC_ANIADIR");
		btnAniadir.addActionListener(this);
		botonera.add(btnAniadir);
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand("CANCELAR");
		btnCancelar.addActionListener(this);
		botonera.add(btnCancelar);
	}//Cliente
	
	//obtener la lista de Productos desde el Servlet
	@SuppressWarnings("unchecked")
	private List<Todo> obtenerListaProductos() {
		try {
			Map<String,String> parametros = new HashMap<String, String>();
			parametros.put("action", "listarProductos");
			ObjectInputStream respuesta = new ObjectInputStream(realizarPeticionPost(urlString, parametros));
			List<Todo> listaProductos = (List<Todo>) respuesta.readObject();	
			return listaProductos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//ejecuta la accion asociada al evento de pulsar un boton
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ADDPRODUCTO")) {
			panelAniadir.setVisible(true);
			tfId.requestFocusInWindow();
		} 
		else if (e.getActionCommand().equals("EXEC_ANIADIR")) {
			Matcher matcher = pattern.matcher(tfId.getText());
			String id   = tfId.getText(),
				   resumen = tfResumen.getText(),
				   descripcion    = tfDescripcion.getText();
			String fraseError = "";
			boolean error = false;
			if (id.equals("")) {
				error = true;
				fraseError += "\n · Debe introducir un id.";
			}
			if (resumen.equals("")) {
				error = true;
				fraseError += "\n · Debe introducir un resumen.";
			}
			if (!matcher.matches()) {
				error = true;
				fraseError += "\n · Id no valido.";
			}		
			if (!error) {
				try {
					Map<String,String> parametros = new HashMap<String, String>();
					parametros.put("action", "aniadirProducto");
					parametros.put("id", id);
					parametros.put("resumen", resumen);
					parametros.put("descripcion", descripcion);		
					ObjectInputStream respuesta = new ObjectInputStream(realizarPeticionPost(urlString, parametros));
					int codigo = respuesta.readInt();
					String mensaje = (String) respuesta.readObject();					
					switch (codigo) {
						case 0:
							Todo Producto = new Todo();
							Producto.setId(tfId.getText());
							Producto.setResumen(tfResumen.getText());
							Producto.setDescripcion(tfDescripcion.getText());
							modeloTablaProductos.add(Producto);
							tfId.setText("");
							tfResumen.setText("");
							tfDescripcion.setText("");
							break;
						default:
							JOptionPane.showMessageDialog(Cliente.this,
								    mensaje,
								    "Error",
								    JOptionPane.ERROR_MESSAGE);
							break;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(Cliente.this,
					    fraseError,
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
		} 
		else if (e.getActionCommand().equals("CANCELAR")) {
			tfId.setText("");
			tfResumen.setText("");
			tfDescripcion.setText("");
			panelAniadir.setVisible(false);
		}
	}
	//POST a una URL con parametros 
	@SuppressWarnings("deprecation")
	public InputStream realizarPeticionPost(String urlString, Map<String,String> parametros) {
		String cadenaParametros = "";
		boolean primerPar = true;
		for (Map.Entry<String, String> entry : parametros.entrySet()) {
			if (!primerPar) {
				cadenaParametros += "&";
			} else {
				primerPar = false;
			}
		    String parDeParametro = String.format("%s=%s", 
					URLEncoder.encode(entry.getKey()), 
					URLEncoder.encode(entry.getValue()));
		    cadenaParametros += parDeParametro;
		}
		try {
			URL url = new URL(urlString);
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setUseCaches(false);
			conexion.setRequestMethod("POST");
			conexion.setDoOutput(true);
			OutputStream output = conexion.getOutputStream();
			output.write(cadenaParametros.getBytes());
			output.flush();
			output.close();
			return conexion.getInputStream();
		} catch (MalformedURLException | ProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
