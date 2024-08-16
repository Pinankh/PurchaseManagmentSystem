/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventory.UI;

/**
 *
 * @author pinal
 */
import java.awt.EventQueue;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;

@SuppressWarnings("serial")
public class FrameComboListener extends JFrame 
{

	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox cbPesawat;
	private JLabel lblNamaPesawat;
	String pesawat[] = {"Garuda","Lion Air","Lufthansa Air","Batavia Air","Bali Air"};
	String dataPesawat[][] = {{"Jakarta - Bali","15.00","17.30","Rp. 250.000"},{"Jakarta - Yogyakarta","08.00","09.00","Rp. 150.000"},{"Jakarta - Singapura","13.00","15.45","Rp. 500.000"}};
	@SuppressWarnings("rawtypes")
	Vector vectorPesawat = new Vector();
	private JLabel lblRute;
	private JLabel lblJamBerangkat;
	private JLabel lblJamTiba;
	private JLabel lblHarga;
	private JButton btnRefresh;
	private JButton btnTampilkan;
	private JLabel lblIsiRute;
	private JLabel lblIsiBerangkat;
	private JLabel lblIsiTiba;
	private JLabel lblIsiHarga;
	private JLabel lblBg;
	private JLabel lblIco;
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FrameComboListener() 
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 621, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNamaPesawat = new JLabel("Nama Pesawat : ");
		lblNamaPesawat.setForeground(new Color(255, 255, 255));
		lblNamaPesawat.setBounds(12, 60, 127, 15);
		contentPane.add(lblNamaPesawat);
		
		cbPesawat = new JComboBox();
		cbPesawat.setModel(new DefaultComboBoxModel(vectorPesawat));
		cbPesawat.setSelectedIndex(-1);
		cbPesawat.setEditable(true);
		JTextField text = (JTextField)cbPesawat.getEditor().getEditorComponent();
		text.setFocusable(true);
		text.setText("");
		text.addKeyListener(new ComboListener(cbPesawat,vectorPesawat));
		cbPesawat.setBounds(144, 56, 165, 24);
		contentPane.add(cbPesawat);
		
		lblRute = new JLabel("Rute : ");
		lblRute.setForeground(new Color(255, 255, 255));
		lblRute.setBounds(12, 214, 70, 15);
		contentPane.add(lblRute);
		
		lblJamBerangkat = new JLabel("Jam Berangkat :  ");
		lblJamBerangkat.setForeground(new Color(255, 255, 255));
		lblJamBerangkat.setBounds(12, 241, 127, 15);
		contentPane.add(lblJamBerangkat);
		
		lblJamTiba = new JLabel("Jam Tiba : ");
		lblJamTiba.setForeground(new Color(255, 255, 255));
		lblJamTiba.setBounds(12, 268, 100, 15);
		contentPane.add(lblJamTiba);
		
		JLabel lblInformasiPesawat = new JLabel("Informasi Pesawat");
		lblInformasiPesawat.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInformasiPesawat.setBounds(222, 12, 185, 15);
		contentPane.add(lblInformasiPesawat);
		
		lblHarga = new JLabel("Harga : ");
		lblHarga.setForeground(new Color(255, 255, 255));
		lblHarga.setBounds(12, 295, 70, 15);
		contentPane.add(lblHarga);
		
		btnTampilkan = new JButton("Tampilkan Informasi");
		btnTampilkan.setIcon(new ImageIcon("/home/resa/Aplikasi Java/SwingComponents/src/Gambar/tampilData.png"));
		btnTampilkan.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent act) 
			{
				if(cbPesawat.getSelectedItem().equals("Batavia Air"))
				{
					lblIsiRute.setText(dataPesawat[0][0]);
					lblIsiBerangkat.setText(dataPesawat[0][1]);
					lblIsiTiba.setText(dataPesawat[0][2]);
					lblIsiHarga.setText(dataPesawat[0][3]);
				}
				else if(cbPesawat.getSelectedItem().equals("Bali Air"))
				{
					lblIsiRute.setText(dataPesawat[0][0]);
					lblIsiBerangkat.setText(dataPesawat[0][1]);
					lblIsiTiba.setText(dataPesawat[0][2]);
					lblIsiHarga.setText(dataPesawat[2][3]);
				}
				else if(cbPesawat.getSelectedItem().equals("Lufthansa Air"))
				{
					lblIsiRute.setText(dataPesawat[0][0]);
					lblIsiBerangkat.setText(dataPesawat[0][1]);
					lblIsiTiba.setText(dataPesawat[0][2]);
					lblIsiHarga.setText(dataPesawat[2][3]);
				}
				else if(cbPesawat.getSelectedItem().equals("Lion Air"))
				{
					lblIsiRute.setText(dataPesawat[1][0]);
					lblIsiBerangkat.setText(dataPesawat[1][1]);
					lblIsiTiba.setText(dataPesawat[1][2]);
					lblIsiHarga.setText(dataPesawat[1][3]);
				}
				else if(cbPesawat.getSelectedItem().equals("Garuda"))
				{
					lblIsiRute.setText(dataPesawat[2][0]);
					lblIsiBerangkat.setText(dataPesawat[2][1]);
					lblIsiTiba.setText(dataPesawat[2][2]);
					lblIsiHarga.setText(dataPesawat[2][3]);
				}
			}
		});
		btnTampilkan.setBounds(12, 150, 185, 43);
		contentPane.add(btnTampilkan);
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent act)
			{
				cbPesawat.setSelectedItem("");
				lblIsiRute.setText("");
				lblIsiBerangkat.setText("");
				lblIsiTiba.setText("");
				lblIsiHarga.setText("");
				cbPesawat.requestFocus();
			}
		});
		btnRefresh.setIcon(new ImageIcon("/home/resa/Aplikasi Java/SwingComponents/src/Gambar/Refresh.png"));
		btnRefresh.setBounds(222, 150, 127, 43);
		contentPane.add(btnRefresh);
		
		lblIsiRute = new JLabel("");
		lblIsiRute.setForeground(new Color(204, 255, 255));
		lblIsiRute.setBounds(144, 214, 205, 15);
		contentPane.add(lblIsiRute);
		
		lblIsiBerangkat = new JLabel("");
		lblIsiBerangkat.setForeground(new Color(51, 0, 255));
		lblIsiBerangkat.setBounds(144, 241, 177, 15);
		contentPane.add(lblIsiBerangkat);
		
		lblIsiTiba = new JLabel("");
		lblIsiTiba.setForeground(new Color(51, 0, 255));
		lblIsiTiba.setBounds(144, 268, 177, 15);
		contentPane.add(lblIsiTiba);
		
		lblIsiHarga = new JLabel("");
		lblIsiHarga.setFont(new Font("Dialog", Font.BOLD, 15));
		lblIsiHarga.setForeground(new Color(255, 153, 0));
		lblIsiHarga.setBounds(144, 295, 239, 24);
		contentPane.add(lblIsiHarga);
		
		lblIco = new JLabel("");
		lblIco.setIcon(new ImageIcon("/home/resa/Aplikasi Java/SwingComponents/src/Gambar/Pesawat.png"));
		lblIco.setBounds(388, 104, 196, 166);
		contentPane.add(lblIco);
		
		lblBg = new JLabel("");
		lblBg.setIcon(new ImageIcon("/home/resa/Aplikasi Java/SwingComponents/src/Gambar/AirWallpaper.png"));
		lblBg.setBounds(0, 0, 624, 373);
		contentPane.add(lblBg);
		setLocationRelativeTo(null);
		setVectorPesawat();
	}
	
	@SuppressWarnings("unchecked")
	public void setVectorPesawat()
	{
		int a;
		for(a=0;a<pesawat.length;a++)
		{
			vectorPesawat.add(pesawat[a]);
		}
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					FrameComboListener frame = new FrameComboListener();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				/*
				try 
				{
					 com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme("Large-Font", "Java Swing", "");
			         UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
					FrameComboListener frame = new FrameComboListener();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}*/
			}
		});
	}
}