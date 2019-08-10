package prueba1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class conexion extends JFrame {

	public static final String URL="jdbc:mysql://localhost:3306/Escuela";
	public static final String USERNAME="root";
	public static final String PASSWORD="";
	
	PreparedStatement ps;
	ResultSet rs;
	
	
	private JPanel contentPane;
	private JTextField txtClave;
	private JTextField txtNombre;
	private JTextField txtCorreo;
	private JTextField txtApellido;
	private JTextField txtFecha;
	private JTextField txtId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					conexion frame = new conexion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void limpiarCajas(){
		txtClave.setText(null);
		txtNombre.setText(null);
		txtApellido.setText(null);
		txtCorreo.setText(null);
		txtFecha.setText(null);
		//comboBoxGenero.setSelectedIndex(0);
	}
	/**
	 * Create the frame.
	 */
	public conexion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.setBounds(5, 233, 424, 23);
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					Connection con= null;
					con = getConection();
					PreparedStatement ps;
					ResultSet res;
					
					ps =(PreparedStatement) con.prepareStatement("SELECT * FROM persona");
					res=ps.executeQuery();
					
					if(res.next()){
						JOptionPane.showMessageDialog(null, res.getString("nombre")+" "+res.getString("apellido"));	
					}
					else{
						JOptionPane.showMessageDialog(null, "No");
					}
					con.close();
				}catch(Exception e){
					System.out.println(e);
				}
				
			}
		
		});
		contentPane.setLayout(null);
		contentPane.add(btnConectar);
		
		JLabel lblClave = new JLabel("Clave");
		lblClave.setBounds(23, 11, 46, 14);
		contentPane.add(lblClave);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(23, 47, 46, 14);
		contentPane.add(lblNombre);
		
		JLabel lblCorreo = new JLabel("Correo");
		lblCorreo.setBounds(23, 119, 46, 14);
		contentPane.add(lblCorreo);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(23, 79, 46, 14);
		contentPane.add(lblApellido);
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(23, 161, 46, 14);
		contentPane.add(lblFecha);
		
		txtClave = new JTextField();
		txtClave.setBounds(80, 8, 86, 20);
		contentPane.add(txtClave);
		txtClave.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(79, 44, 86, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtCorreo = new JTextField();
		txtCorreo.setBounds(79, 116, 86, 20);
		contentPane.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(79, 76, 86, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		
		txtFecha = new JTextField();
		txtFecha.setBounds(79, 158, 86, 20);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);
		
		JLabel lblGenero = new JLabel("Genero");
		lblGenero.setBounds(23, 196, 46, 14);
		contentPane.add(lblGenero);
		
		JComboBox comboBoxGenero = new JComboBox();
		comboBoxGenero.setModel(new DefaultComboBoxModel(new String[] {"Selecciona", "Masculino", "Femenino"}));
		comboBoxGenero.setBounds(80, 189, 86, 20);
		contentPane.add(comboBoxGenero);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//guardar datos
				Connection con = null;
				try{
					con = getConection();
					ps = (PreparedStatement) con.prepareStatement("INSERT INTO persona(clave,nombre,apellido,correo,fecha,genero)VALUES(?,?,?,?,?,?)");
					
					//ps.setString(1, txtId.getText());
					ps.setString(1, txtClave.getText());
					ps.setString(2, txtNombre.getText());
					ps.setString(3, txtApellido.getText());
					ps.setString(4, txtCorreo.getText());
					ps.setDate(5, Date.valueOf(txtFecha.getText()));
					ps.setString(6, comboBoxGenero.getSelectedItem().toString());
					int res = ps.executeUpdate();
					
					if(res>0){
						JOptionPane.showMessageDialog(null, "Persona guardada");
						limpiarCajas();
					}else{
						JOptionPane.showMessageDialog(null, "Error");
						limpiarCajas();
						
					}
					con.close();
				
				}catch(Exception e){
					System.err.print(e);
				}
				
			}
			
		});
		
		
		btnGuardar.setBounds(215, 7, 89, 23);
		contentPane.add(btnGuardar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//modificar datos
				Connection con = null;
				try{
					con = getConection();
					ps = (PreparedStatement) con.prepareStatement("UPDATE persona SET clave=?,nombre=?,apellido=?,correo=?,fecha=?,genero=? WHERE idpersona=?");
					
					ps.setString(1, txtClave.getText());
					ps.setString(2, txtNombre.getText());
					ps.setString(3, txtApellido.getText());
					ps.setString(4, txtCorreo.getText());
					ps.setDate(5, Date.valueOf(txtFecha.getText()));
					ps.setString(6, comboBoxGenero.getSelectedItem().toString());
					//ps.setString(7, txtId.getText());
					ps.setInt(7,Integer.parseInt(txtId.getText()));
					int res = ps.executeUpdate();
					
					if(res>0){
						JOptionPane.showMessageDialog(null, "Persona modificada");
						limpiarCajas();
					}else{
						JOptionPane.showMessageDialog(null, "Error");
						limpiarCajas();
						
					}
					con.close();
				
				}catch(Exception e){
					System.err.print(e);
				}
			}
		});
		btnModificar.setBounds(215, 43, 89, 23);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//eliminar datos
				Connection con = null;
				try{
					con = getConection();
					ps = (PreparedStatement) con.prepareStatement("DELETE FROM persona WHERE idpersona=?");
					
					ps.setInt(1,Integer.parseInt(txtId.getText()));
					
					int res = ps.executeUpdate();
					
					if(res>0){
						JOptionPane.showMessageDialog(null, "Persona eliminada");
						limpiarCajas();
					}else{
						JOptionPane.showMessageDialog(null, "Error");
						limpiarCajas();
						
					}
					con.close();
				
				}catch(Exception ee){
					System.err.print(ee);
				}
	
			}
		});
		btnEliminar.setBounds(215, 79, 89, 23);
		contentPane.add(btnEliminar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCajas();
			}
		});
		btnLimpiar.setBounds(215, 119, 89, 23);
		contentPane.add(btnLimpiar);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection con = null;
				try{
					con = getConection();
					ps = (PreparedStatement) con.prepareStatement("SELECT * FROM persona WHERE clave=?");
					ps.setString(1, txtClave.getText());
					
					rs=ps.executeQuery();
					if(rs.next()){
						txtId.setText(rs.getString("idpersona"));
						txtNombre.setText(rs.getString("nombre"));
						txtApellido.setText(rs.getString("apellido"));
						txtCorreo.setText(rs.getString("correo"));
						txtFecha.setText(rs.getString("fecha"));
						comboBoxGenero.setSelectedItem(rs.getString("genero"));
					}else{
						JOptionPane.showMessageDialog(null, "No se encontro registro");
						//limpiarCajas();
					}
				}catch(Exception e){
					System.err.println(e);
				}
					
			}
		});
		btnBuscar.setBounds(215, 161, 89, 23);
		contentPane.add(btnBuscar);
		
		txtId = new JTextField();
		txtId.setBounds(327, 164, 86, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);
	}
	
	public static Connection getConection()
	{
		Connection con= null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con= (Connection) DriverManager.getConnection(URL,USERNAME, PASSWORD);
			JOptionPane.showMessageDialog(null, "Exitosa");
		}catch(Exception e){
			System.out.println(e);
		}
		return con;
	}
	
	
}
