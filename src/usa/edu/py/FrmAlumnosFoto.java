package usa.edu.py;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.CheckboxMenuItem;
import java.awt.Choice;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.PrintJob;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.UIManager;

import org.freixas.jcalendar.JCalendar;
import org.freixas.jcalendar.JCalendarCombo;

import py.edu.ucsa.connections.ManejadorBD;
import py.edu.ucsa.connections.opAbm;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.datatransfer.Clipboard;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
import java.awt.im.InputMethodHighlight;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.peer.ButtonPeer;
import java.awt.peer.CanvasPeer;
import java.awt.peer.CheckboxMenuItemPeer;
import java.awt.peer.CheckboxPeer;
import java.awt.peer.ChoicePeer;
import java.awt.peer.DesktopPeer;
import java.awt.peer.DialogPeer;
import java.awt.peer.FileDialogPeer;
import java.awt.peer.FontPeer;
import java.awt.peer.FramePeer;
import java.awt.peer.LabelPeer;
import java.awt.peer.ListPeer;
import java.awt.peer.MenuBarPeer;
import java.awt.peer.MenuItemPeer;
import java.awt.peer.MenuPeer;
import java.awt.peer.PanelPeer;
import java.awt.peer.PopupMenuPeer;
import java.awt.peer.ScrollPanePeer;
import java.awt.peer.ScrollbarPeer;
import java.awt.peer.TextAreaPeer;
import java.awt.peer.TextFieldPeer;
import java.awt.peer.WindowPeer;
import java.awt.FlowLayout;
import java.beans.FeatureDescriptor;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.awt.GridLayout;

public class FrmAlumnosFoto extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JPanel pnlFoto;
	private JPanel pnlAcciones;
	private JTextField txtTxtfoto;
	private JButton btnGuardar;
	private JButton btnAgregar;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnAnterior;
	private JButton btnSiguiente;
	private JButton btnUltimo;
	private JButton btnPrimero;
	private JFileChooser jfcFtoto;
	private FileNameExtensionFilter filtro;
	private JButton btnExaminar;
	private JLabel lblId;
	private JTextField txtID;
	private JLabel lblFechaNac;
	private JCalendarCombo cboFechaNac;
	private PreparedStatement pstm;
	private Statement stm;
	private ManejadorBD manDB = new ManejadorBD();
	private File archivo;
	private ResultSet res;
	private Image imgFoto;
	private JLabel lblFoto;
	private ImageIcon icono;
	private byte[] img;
	private JLabel lblExaminar;
	private opAbm operacionActualizar;
	private FileInputStream foto;
	private int posUno;
	private int posFinal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
					FrmAlumnosFoto frame = new FrmAlumnosFoto();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public FrmAlumnosFoto() throws SQLException {
		super("Alumnos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 554, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel pnlAlumno = new JPanel();
		pnlAlumno.setBorder(new TitledBorder(null, "Alumno", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		jfcFtoto = new JFileChooser();
		filtro = new FileNameExtensionFilter("Podes subir GIF, JPG o PNG", "JPEG", "JPG", "jpg", "jpeg", "GIF", "gif", "PNG", "png");
		jfcFtoto.setFileFilter(filtro);
		
		pnlAcciones = new JPanel();
		pnlAcciones.setBorder(new TitledBorder(null, "Acciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(pnlAcciones, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(pnlAlumno, GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(pnlAlumno, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlAcciones, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
		);
		
		btnPrimero = new JButton("<<");
		btnPrimero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnAnterior = new JButton("<");
		
		btnSiguiente = new JButton(">");
		
		btnUltimo = new JButton(">>");
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activarTexto(true);
				activarBotones(false);
				activarNavegar(false);
				btnExaminar.setEnabled(true);
				btnGuardar.setEnabled(true);
				limpiarTexto();
				operacionActualizar = opAbm.INSERTAR;
			}

			
		});
		
		btnModificar = new JButton("Modificar");
		btnModificar.setEnabled(false);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operacionActualizar = opAbm.MODIFICAR;
				activarTexto(true);
				activarBotones(false);
				btnGuardar.setEnabled(true);
				btnExaminar.setEnabled(true);
			}
		});
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operacionActualizar = opAbm.BORRAR;
				try {
					
						abm(opAbm.BORRAR);
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBorrar.setEnabled(false);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switch (operacionActualizar) {
					case INSERTAR:
						if (validar()) {
							abm(opAbm.INSERTAR);
							limpiarTexto();
							btnAgregar.setEnabled(true);
							activarNavegar(true);
							lblFechaNac.setText(null);
						}else{
							JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
							txtNombre.setFocusable(true);
							}
						break;
					case MODIFICAR:
						abm(opAbm.MODIFICAR);
						break;
					}
					
					System.out.println(operacionActualizar);
					activarBotones(true);
					btnGuardar.setEnabled(false);
					activarTexto(false);
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGuardar.setEnabled(false);
		GroupLayout gl_pnlAcciones = new GroupLayout(pnlAcciones);
		gl_pnlAcciones.setHorizontalGroup(
			gl_pnlAcciones.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlAcciones.createSequentialGroup()
					.addGroup(gl_pnlAcciones.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlAcciones.createSequentialGroup()
							.addGap(46)
							.addComponent(btnAgregar, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnModificar, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBorrar, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnGuardar, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlAcciones.createSequentialGroup()
							.addGap(128)
							.addComponent(btnPrimero)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAnterior, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnSiguiente, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnUltimo)))
					.addContainerGap(65, Short.MAX_VALUE))
		);
		gl_pnlAcciones.setVerticalGroup(
			gl_pnlAcciones.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlAcciones.createSequentialGroup()
					.addGroup(gl_pnlAcciones.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPrimero)
						.addComponent(btnAnterior)
						.addComponent(btnSiguiente)
						.addComponent(btnUltimo))
					.addGap(18)
					.addGroup(gl_pnlAcciones.createParallelGroup(Alignment.LEADING)
						.addComponent(btnBorrar, Alignment.TRAILING)
						.addComponent(btnModificar, Alignment.TRAILING)
						.addComponent(btnAgregar, Alignment.TRAILING)
						.addComponent(btnGuardar, Alignment.TRAILING))
					.addContainerGap(16, Short.MAX_VALUE))
		);
		pnlAcciones.setLayout(gl_pnlAcciones);
		
		JLabel lblNombre = new JLabel("Nombre");
		
		txtNombre = new JTextField();
		txtNombre.setEnabled(false);
		txtNombre.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido");
		
		txtApellido = new JTextField();
		txtApellido.setEnabled(false);
		txtApellido.setColumns(10);
		
		pnlFoto = new JPanel();
		pnlFoto.setBorder(new TitledBorder(null, "Foto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		lblExaminar = new JLabel("Foto");
		
		txtTxtfoto = new JTextField();
		txtTxtfoto.setEnabled(false);
		txtTxtfoto.setColumns(10);
		
		btnExaminar = new JButton("examinar");
		btnExaminar.setEnabled(false);
		
		btnExaminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = jfcFtoto.showDialog(btnExaminar, "Agregá la foto");
				if(valor == JFileChooser.APPROVE_OPTION){
					txtTxtfoto.setText(jfcFtoto.getSelectedFile().getPath());
					archivo = new File(jfcFtoto.getSelectedFile().getAbsolutePath());
				    ImageIcon fot = new ImageIcon(jfcFtoto.getSelectedFile().getAbsolutePath());
				    Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_DEFAULT));
				    lblFoto.setIcon(icono);
				}
			}
		});
		
		lblId = new JLabel("ID");
		
		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setColumns(10);
		
		lblFechaNac = new JLabel("fecha nac");
		cboFechaNac = new JCalendarCombo();
		cboFechaNac.setEnabled(false);
		cboFechaNac.setEditable(true);
		
		GroupLayout gl_pnlAlumno = new GroupLayout(pnlAlumno);
		gl_pnlAlumno.setHorizontalGroup(
			gl_pnlAlumno.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_pnlAlumno.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlAlumno.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFechaNac)
						.addGroup(gl_pnlAlumno.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, gl_pnlAlumno.createSequentialGroup()
								.addGap(76)
								.addComponent(cboFechaNac, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(Alignment.LEADING, gl_pnlAlumno.createSequentialGroup()
								.addGroup(gl_pnlAlumno.createParallelGroup(Alignment.LEADING)
									.addComponent(lblId)
									.addComponent(lblNombre)
									.addComponent(lblApellido, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblExaminar))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_pnlAlumno.createParallelGroup(Alignment.LEADING, false)
									.addComponent(txtID)
									.addComponent(txtNombre)
									.addComponent(txtApellido)
									.addGroup(gl_pnlAlumno.createSequentialGroup()
										.addComponent(txtTxtfoto, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(btnExaminar, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pnlFoto, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
		);
		gl_pnlAlumno.setVerticalGroup(
			gl_pnlAlumno.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlAlumno.createSequentialGroup()
					.addGroup(gl_pnlAlumno.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnlFoto, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
						.addGroup(gl_pnlAlumno.createSequentialGroup()
							.addGap(0, 22, Short.MAX_VALUE)
							.addGroup(gl_pnlAlumno.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblId))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlAlumno.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNombre)
								.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlAlumno.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblApellido)
								.addComponent(txtApellido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlAlumno.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblExaminar)
								.addGroup(gl_pnlAlumno.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtTxtfoto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnExaminar)))
							.addGap(18)
							.addGroup(gl_pnlAlumno.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFechaNac)
								.addComponent(cboFechaNac, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(16))
		);
		pnlFoto.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblFoto = new JLabel("");
		lblFoto.setToolTipText("Foto");
		lblFoto.setSize(200, 200);
		pnlFoto.add(lblFoto);
		pnlAlumno.setLayout(gl_pnlAlumno);
		contentPane.setLayout(gl_contentPane);
		
		String sql = "SELECT id, nombre, apellido, fecha_nac, foto FROM personas order by id";
		stm = ManejadorBD.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		res = stm.executeQuery(sql);
		
		
		//res.next();
				
		accionDeplazar(btnPrimero);
		accionDeplazar(btnAnterior);
		accionDeplazar(btnSiguiente);
		accionDeplazar(btnUltimo);
	}
	
	private enum desplazarBoton{
		PRIMERO, ANTERIOR, SIGUIENTE, ULTIMO;
	}
	
	private void accionDeplazar(final JButton botonMover){
		botonMover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (botonMover.getText().equals("<<")){
						desplazar(desplazarBoton.PRIMERO);
					}else if(botonMover.getText().equals("<")){
						desplazar(desplazarBoton.ANTERIOR);
					}else if(botonMover.getText().equals(">")){
						desplazar(desplazarBoton.SIGUIENTE);
					}else if(botonMover.getText().equals(">>")){
						desplazar(desplazarBoton.ULTIMO);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	private void desplazar(desplazarBoton desplazar) throws SQLException, IOException{
		
		switch (desplazar) {
		case PRIMERO:
			res.first();
			break;
		case ANTERIOR:
			if(!res.isFirst()){
				if(res.previous());
			}
				
			break;
		case SIGUIENTE:
			if(!res.isLast()){
				res.next();
			}
				
			break;
		case ULTIMO:
			res.last();
			break;
		}
		
		verDatos();
		activarBotones(true);
		activarTexto(false);
		btnGuardar.setEnabled(false);
	}
	
	private void verDatos() throws SQLException, IOException{
		String fecha = formatearFecha(res.getDate("fecha_nac"));
		
		txtID.setText(res.getString("id"));
		txtNombre.setText(res.getString("nombre"));
		txtApellido.setText(res.getString("apellido"));
		cboFechaNac.setDate(formatearFecha(fecha));
		img = res.getBytes("foto");
		imgFoto = getImage(img);
		icono = new ImageIcon(imgFoto);
		lblFoto.setText("");
		lblFoto.setIcon(ajustarImagen(icono));
	}
	
	private void activarTexto(boolean si) {
		txtApellido.setEnabled(si);
		txtNombre.setEnabled(si);
		cboFechaNac.setEnabled(si);
		cboFechaNac.setEditable(si);
	}
	
	private void activarBotones(boolean si){
		btnAgregar.setEnabled(si);
		btnGuardar.setEnabled(si);
		btnModificar.setEnabled(si);
		btnBorrar.setEnabled(si);
	} 
	
	private void activarNavegar(boolean si){
		btnPrimero.setEnabled(si);
		btnAnterior.setEnabled(si);
		btnSiguiente.setEnabled(si);
		btnUltimo.setEnabled(si);
	}
	
	private void limpiarTexto(){
		txtID.setText(null);
		txtNombre.setText(null);
		txtApellido.setText(null);
		txtTxtfoto.setText(null);
	}
	
	private boolean validar(){
		
		if(txtApellido.getText().length() > 0 && txtNombre.getText().length() > 0 && 
				txtTxtfoto.getText().length() > 0){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param operacion
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	private void abm(opAbm operacion) throws SQLException, FileNotFoundException{
		switch (operacion) {
		case INSERTAR:
				String sql ="INSERT INTO personas(id, nombre, apellido, fecha_nac, foto) VALUES ((SELECT max(id)+1 FROM personas), ?, ?, '"+formatearFecha(cboFechaNac.getDate())+"', ?)";
				pstm = ManejadorBD.con.prepareStatement(sql);
				foto = new FileInputStream(archivo);
				pstm.setString(1, txtNombre.getText());
				pstm.setString(2, txtApellido.getText());
				pstm.setBinaryStream(3, foto, (int) archivo.length());
				pstm.executeUpdate();
			break;
		case MODIFICAR:
			String fecha = formatearFecha(cboFechaNac.getDate());
			Date fechaDb = java.sql.Date.valueOf(fecha);
				res.updateString("nombre", txtNombre.getText());
				res.updateString("apellido", txtApellido.getText());
				res.updateDate("fecha_nac", (java.sql.Date) fechaDb);
				if(txtTxtfoto.getText().length() > 0){
					archivo = new File(jfcFtoto.getSelectedFile().getAbsolutePath());
					foto = new FileInputStream(archivo);
					res.updateBinaryStream("foto", foto, (int) archivo.length());
				}	
				res.updateRow();
				System.out.println("Entro en modificar");
			break;
		case BORRAR:
			int op = JOptionPane.showConfirmDialog(null, "Realmente desea borrar el registro?");
			if(op == JOptionPane.OK_OPTION){
				res.deleteRow();
				res.updateRow();
				res.last();
			}
				
			
		}
		
	}
	
	private String formatearFecha(Date fecha){
		java.text.SimpleDateFormat dia = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String Fecha = dia.format(fecha);
        return Fecha;
	}
	
	private java.util.Date formatearFecha(String fecha){
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );
        Date formatFecha = null;
        try {
			formatFecha = new Date(formatter.parse( fecha ).getTime( ));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return formatFecha;
	}
	
	private Image getImage(byte[] bytes) throws IOException {

	    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
	    Iterator readers = ImageIO.getImageReadersByFormatName("png");
	    //readers = ImageIO.getImageReadersByFormatName("jpeg");
	    //readers = ImageIO.getImageReadersByFormatName("jpg");
	    //readers = ImageIO.getImageReadersByFormatName("gif");
	    ImageReader reader = (ImageReader) readers.next();
	    Object source = bis; // File or InputStream
	    ImageInputStream iis = ImageIO.createImageInputStream(source);
	    reader.setInput(iis, true);
	    ImageReadParam param = reader.getDefaultReadParam();
	    return reader.read(0, param);

	}
	
	private ImageIcon ajustarImagen(ImageIcon ico){
		ImageIcon tmpIconAux = ico;
	    //Escalar Imagen
	    ImageIcon tmpIcon = new ImageIcon(tmpIconAux.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
	    return tmpIcon;
	}
}
