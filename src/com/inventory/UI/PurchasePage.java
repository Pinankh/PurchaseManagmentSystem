/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.UI;

import com.inventory.DAO.ProductDAO;
import com.inventory.DAO.SupplierDAO;
import com.inventory.DTO.ProductDTO;
import com.inventory.DTO.SupplierDTO;
import com.inventory.UI.datepicker.DateChooser;
import com.inventory.UI.datepicker.EventDateChooser;
import com.inventory.UI.datepicker.SelectedAction;
import com.inventory.UI.datepicker.SelectedDate;
import com.inventory.Utill.DecimalToWordsConverter;
import com.inventory.Utill.InputStreamToByteArray;
import com.inventory.Utill.Supporter;
import com.inventory.cell.TableActionCellEditor;
import com.inventory.cell.TableActionCellRender;
import com.inventory.cell.TableActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;


import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;

import javax.swing.text.NumberFormatter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;


public class PurchasePage extends javax.swing.JPanel {

    ProductDTO productDTO;
    String username = null;
    String supplier = null;
    Dashboard dashboard;
    int quantity;
    String prodCode = null;
    double selectedVAT = 0.0;
    String SELECTED_VAT_NO = "";
    TableActionEvent event;
    SupplierDTO SELECTED_SUPPLIER=null;
//    private final DateChooser dateChooser;
    
    /**
     * Creates new form PurchasePage
     * @param dashboard
     */
    // Custom cell renderer for double values
    static class DoubleValueCellRenderer extends DefaultTableCellRenderer {
        private final DecimalFormat decimalFormat;

        public DoubleValueCellRenderer() {
            decimalFormat = new DecimalFormat("#0.00");
            setHorizontalAlignment(SwingConstants.RIGHT);
        }

        @Override
        protected void setValue(Object value) {
            // Format the double value before displaying
            if (value instanceof Double) {
                setText(decimalFormat.format(value));
            } else {
                super.setValue(value);
            }
        }
    }
    public PurchasePage(Dashboard dashboard) {
        initComponents();
        this.dashboard = dashboard;
        
       setNumberFormate();
        
//        dateChooser = new DateChooser();
//        dateChooser.setTextRefernce(date_jTextField);
        
        dateChooser1.addEventDateChooser((SelectedAction action, SelectedDate date) -> {
            System.out.println(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
            if (action.getAction() == SelectedAction.DAY_SELECTED) {
                dateChooser1.hidePopup();
            }
        });
        event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
            }
            
            @Override
            public void onDelete(int row) {
                if (purchaseTable.isEditing()) {
                    purchaseTable.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) purchaseTable.getModel();
                //model.removeRow(row);
                String supplierID = String.valueOf(model.getValueAt(row, 1));
                String commodities = String.valueOf(model.getValueAt(row, 8));
                String amount = String.valueOf(model.getValueAt(row, 6));
                
                SupplierDAO supplierDAO = new SupplierDAO();
                SupplierDTO dTO = supplierDAO.getSupplierBankDetails(Integer.parseInt(supplierID));
                System.out.println("commodities " + commodities);
                System.out.println("amount " + amount);
                
                createPDFData(commodities,amount,dTO);
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
            }
        };
        
        //AutoCompleteDecorator.decorate(suppCombo);
        suppCombo.setRenderer(new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList jlist, Object e, int i, boolean bln, boolean bln1) {
                String text = e == null ? "" : e.toString();
                JLabel label = new JLabel(text);
                label.setFont(suppCombo.getFont());
                if (i >= 0) {
                    label.setBorder(new EmptyBorder(5, 8, 5, 8));
                } else {
                    label.setBorder(null);
                }
                if (bln) {
                    label.setOpaque(true);
                    label.setBackground(new Color(240, 240, 240));
                    label.setForeground(new Color(17, 155, 215));
                }
                return label;
            }
        });
        
        // Set the selection mode to single selection
        purchaseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        loadComboBox();
        loadDataSet();
                
        
        
        SELECTED_SUPPLIER = (SupplierDTO) suppCombo.getSelectedItem();
        codeText.setEditable(true);
        codeText.setText(SELECTED_SUPPLIER==null?"":SELECTED_SUPPLIER.getTpin_no());
        codeText.setEditable(false);
        selectedVAT = SELECTED_SUPPLIER==null?0.0:SELECTED_SUPPLIER.getVat_percent();
        SELECTED_VAT_NO = SELECTED_SUPPLIER==null?"":SELECTED_SUPPLIER.getVat_no();
        selectedVatText.setText("VAT No :- " +SELECTED_VAT_NO);
        
        supplierVAT.setValue(selectedVAT);
        if(SELECTED_VAT_NO.equals("25"))
        {
            vatValueTF.setEnabled(true);
            vatValueTF.setEditable(true);
        }
        else
        {
            vatValueTF.setEnabled(false);
            vatValueTF.setEditable(false);
        }
        suppCombo.addItemListener((ItemEvent e) -> {
            // Get the selected item
            clearButton.doClick();
            SELECTED_SUPPLIER = (SupplierDTO) e.getItem();
            codeText.setEditable(true);
            codeText.setText(SELECTED_SUPPLIER.getTpin_no());
            codeText.setEditable(false);
            selectedVAT = SELECTED_SUPPLIER.getVat_percent();
            SELECTED_VAT_NO = SELECTED_SUPPLIER.getVat_no();
            selectedVatText.setText("VAT No :- " +SELECTED_VAT_NO);
            supplierVAT.setValue(selectedVAT);
            //commented due to as per Chetanbhai they enter vat exclsive value and vat value
            if(SELECTED_VAT_NO.equals("25"))
            {
                vatValueTF.setEnabled(true);
                vatValueTF.setEditable(true);
            }
            else
            {
                vatValueTF.setEnabled(false);
                vatValueTF.setEditable(false);
            }
            
            
        });
        
        
        
        jDateChooser1.setForeground(Color.WHITE);
        
        
                        

    }

    private void setNumberFormate()
    {
         // Create a NumberFormatter with a DecimalFormat that allows decimal numbers
        NumberFormatter formatter = new NumberFormatter(new DecimalFormat("#0.00"));
        formatter.setValueClass(Double.class); // Set the value class to Double
        formatter.setMinimum(0.0); // Set minimum value (optional, adjust as needed)
        //formatter.setMaximum(100.0); // Set maximum value (optional, adjust as needed)
        formatter.setAllowsInvalid(false);
        formatter.setMaximum(Double.MAX_VALUE);
        
                
        // Set the formatter factory for the JFormattedTextField
        vatExclusiveValueTF.setFormatterFactory(new DefaultFormatterFactory(formatter));
        vatExclusiveValueTF.setColumns(10);
        
        // Set the formatter factory for the JFormattedTextField
        totalValueTF.setFormatterFactory(new DefaultFormatterFactory(formatter));
        totalValueTF.setColumns(10);
        
        // Set the formatter factory for the JFormattedTextField
        vatValueTF.setFormatterFactory(new DefaultFormatterFactory(formatter));
        vatValueTF.setColumns(10);
        
        // Set the formatter factory for the JFormattedTextField
        supplierVAT.setFormatterFactory(new DefaultFormatterFactory(formatter));
        supplierVAT.setColumns(10);
        
        
        
        clearButton.doClick();
    }
     private void createPDFData(String commodities,String amount,SupplierDTO supplierDTO) {
        try {
             
            String amountInWord = DecimalToWordsConverter.convertToWords(Double.parseDouble(amount));
            String amountInWord1 = "";
            String amountInWord2 = "";
            if (amountInWord.length() > 45) {
            int splitIndex = Supporter.findNearestWhitespace(amountInWord, 45);

            if (splitIndex != -1) {
                amountInWord1 = amountInWord.substring(0, splitIndex).trim();
                amountInWord2 = amountInWord.substring(splitIndex).trim();

                System.out.println("String is longer than 45 characters. Splitting into two parts:");
                System.out.println("Part 1: " + amountInWord1);
                System.out.println("Part 2: " + amountInWord2);
            } else {
                System.out.println("Couldn't find a suitable whitespace to split the string.");
            }
        } else {
            System.out.println("String is not longer than 45 characters.");
            amountInWord1 = amountInWord;
        }
            
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            float fontSize = 10;
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);

//            // Add image at the top of the page
//            // Specify the image file
//            
//            //PDImageXObject image = PDImageXObject.createFromFile("bank_logo.png", document);
//            //PDImageXObject image = PDImageXObject.createFromFileByContent(getFile(), document);
//            URL resourceAbsolutePath = PurchasePage.class.getResource("/com/inventory/UI/Icons/bank_logo.png");
//            System.out.println("Image URI = "+resourceAbsolutePath);
//            String path = PurchasePage.class.getResource("/com/inventory/UI/Icons/bank_logo.png").getPath();
//            System.out.println("Image Path = "+path);
//            System.out.println("Image URI Path = "+resourceAbsolutePath.getFile());
            
            // Get the class loader
        ClassLoader classLoader = getClass().getClassLoader();

//        // Specify the path to the image resource (relative to the classpath)
//        String imagePath = "/com/inventory/UI/Icons/bank_logo.png";
//
//        // Load the image using getResource
//        URL imageURL = classLoader.getResource(imagePath);
//        File file = null;
//        // Check if the image was found
//        if (imageURL != null) {
//            try {
//                file = new File(imageURL.toURI());
//            } catch (URISyntaxException ex) {
//                Logger.getLogger(PurchasePage.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
            
        // Specify the path to the image resource (relative to the classpath)
        String imagePath2 = "com/inventory/UI/Icons/bank_logo.png";

        // Load the image using getResourceAsStream
        try {
                InputStream inputStream = classLoader.getResourceAsStream(imagePath2);
            
            // Check if the image was found
            if (inputStream != null) {
                // Create an ImageIcon with the loaded image
                
            PDImageXObject image = PDImageXObject.createFromByteArray(document,InputStreamToByteArray.convert(inputStream) , "image");
//            PDImageXObject image = PDImageXObject.createFromFileByContent(file, document);
           
            // Set the perfect height and width (adjust the values as needed)
            float perfectWidth = 450; // in points
            float perfectHeight = 65; // in points

            // Calculate the scaling factors
            float scaleX = perfectWidth / image.getWidth();
            float scaleY = perfectHeight / image.getHeight();
            contentStream.drawImage(image, 50, 750,image.getWidth() * scaleX, image.getHeight() * scaleY); // Adjust the coordinates as needed

                
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        
        
//            try {
//                file = new File(resourceAbsolutePath.toURI());
//            } catch (URISyntaxException ex) {
//                Logger.getLogger(PurchasePage.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            PDImageXObject image = PDImageXObject.createFromFile(path, document);
//
//           
//            // Set the perfect height and width (adjust the values as needed)
//            float perfectWidth = 450; // in points
//            float perfectHeight = 65; // in points
//
//            // Calculate the scaling factors
//            float scaleX = perfectWidth / image.getWidth();
//            float scaleY = perfectHeight / image.getHeight();
//            contentStream.drawImage(image, 50, 750,image.getWidth() * scaleX, image.getHeight() * scaleY); // Adjust the coordinates as needed

            // Add text with underline center horizontal
            String text = "OUTWORD DDACC APPLICATION FORM";
            
            float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(text) / 1000 * fontSize;

            // Center the text horizontally
            float startX = (page.getMediaBox().getWidth() - textWidth) / 2;
            float startY = 700;
            
            addCenterTextWithUnderline(contentStream, text, startX, startY);
            
            // Add sender's details
            // Add text with underline Lest 
            String Sendertext = "SENDER’S DETAILS:";
            addLeftTextWithUnderline(contentStream, Sendertext, 50, 670);
            
            
            addTextWithUnderline(contentStream, "DATE: ", "                      .", 50, 650);
            addTextWithUnderline(contentStream, "NAME: ", "STAR BAKERY LTD                .", 50, 630);
            addTextWithUnderline(contentStream, "ADDRESS: ", "H-7, ARUSHA STREET, NDOLA                .", 50, 610);
            addTextWithUnderline(contentStream, "NRC:................................... CONTACT/ PHONE NO. ", "0955822722       ", 50, 590);
            addTextWithUnderline(contentStream, "ACCOUNT NUMBER (13 DIGIT): ", "0073020000026          ", 50, 570);
            addTextWithUnderline(contentStream, "PERPOSE OF TRANSFER:  ", commodities+"          ", 50, 550);
            addTextWithUnderline(contentStream, "AMOUNT IN FIGURES:  ", amount+"                                               ", 50, 530);
            addTextWithUnderline(contentStream, "AMOUNT IN WORDS:  ", amountInWord1+"                            ", 50, 510);
            if("".equals(amountInWord2))
                addTextWithUnderline(contentStream, "                                       ", "                                                                                                                         ", 50, 490);
            else
                addTextWithUnderline(contentStream, "                                       ", amountInWord2+"                                                   ", 50, 490);
            addTextWithUnderline(contentStream, "SENDER’S SIGNATURE: ", "                                                                                 ", 50, 470);
            

            // Add sender's details
            // Add text with underline Lest 
            String BENEFICIARYtext = "BENEFICIARY / RECIPIENT’S DETAILS:";
            addLeftTextWithUnderline(contentStream, Sendertext, 50, 410);
            addTextWithUnderline(contentStream, "NAME:                  ", supplierDTO.getAccount_name()+"                          .", 50, 390);
            addTextWithUnderline(contentStream, "BANK:                  ", supplierDTO.getBank_name()+"                                             .", 50, 370);
            addTextWithUnderline(contentStream, "BRANCH:             ", supplierDTO.getBranch_name()+"                                            .", 50, 350);
            addTextWithUnderline(contentStream, "SORT CODE:       ", supplierDTO.getShort_code()+"                                           .", 50, 330);
            addTextWithUnderline(contentStream, "ACCOUNT NUMBER (FULL A/C):", supplierDTO.getAccount_number()+"                                             .", 50, 310);

            
            String ForBanckUsetext = "FOR BANK USE ONLY:";
            addLeftTextWithUnderline(contentStream, ForBanckUsetext, 50, 250);

            addTextWithUnderline(contentStream, "SIGN:...................CAT A............................................CAT B............................................", "", 50, 220);

            contentStream.close();  // Close the content stream after all operations

            document.save("F:\\JavaProject\\InventoryManagementSystem-master\\dist\\OUTWORD_DDACC_Application_Form.pdf");
            document.close();
            System.out.println("PDF created successfully.");
            try
            {
                File pdffile = new File("F:\\JavaProject\\InventoryManagementSystem-master\\dist\\OUTWORD_DDACC_Application_Form.pdf");
                if(pdffile.exists())
                {
                    if(Desktop.isDesktopSupported())
                    {
                        Desktop.getDesktop().open(pdffile);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "File Not Exist!");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "File Not Exist!");
                }
            }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
           
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static File getFile()
    {
        String path = PurchasePage.class.getResource("/com/inventory/UI/Icons/bank_logo.png").getPath();
        System.out.println("Image Path = "+path);
        return  new File(path);
    }
    
   
    
    private static void addText(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }
    private static void addTextWithUnderline(PDPageContentStream contentStream, String lable, String value, float x, float y) throws IOException {
        
        String text = lable +" "+ value;
        float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(text) / 1000 * 10;
        float lableWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(lable) / 1000 * 10;
        float startX = x+lableWidth;
        float endX = x + textWidth;

        // Draw a dotted line
        contentStream.setLineDashPattern(new float[]{1, 2}, 0);
        contentStream.moveTo(startX, y - 3); // Move to the starting point below the text
        contentStream.lineTo(endX, y - 3);   // Draw a line below the text
        contentStream.stroke();
        contentStream.setLineDashPattern(new float[]{}, 0); // Reset to solid line
        
        addText(contentStream, text, x, y);
    }
    
    private static void addCenterTextWithUnderline(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        
        addText(contentStream, text, x, y);
        float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(text) / 1000 * 10;
        
        float startX = x;
        float endX = x + textWidth;

        // Draw the underline
            float underlineStartX = startX;
            float underlineEndX = startX + textWidth;
            float underlineY = y - 3; // Adjust the distance from the text as needed

            contentStream.setLineWidth(1f); // Set the line width as needed
            contentStream.moveTo(underlineStartX, underlineY);
            contentStream.lineTo(underlineEndX, underlineY);
            contentStream.stroke(); // Reset to solid line
        
        
    }
    
    private static void addLeftTextWithUnderline(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        
        addText(contentStream, text, x, y);
        float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(text) / 1000 * 10;
        
        float startX = x;
        float endX = x + textWidth;

        // Draw the underline
            float underlineStartX = startX;
            float underlineEndX = startX + textWidth;
            float underlineY = y - 3; // Adjust the distance from the text as needed

            contentStream.setLineWidth(1f); // Set the line width as needed
            contentStream.moveTo(underlineStartX, underlineY);
            contentStream.lineTo(underlineEndX, underlineY);
            contentStream.stroke(); // Reset to solid line
        
        
    }
   
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.inventory.UI.datepicker.DateChooser();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        suppCombo = new javax.swing.JComboBox<>();
        addSuppButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        codeText = new javax.swing.JTextField();
        nameText = new javax.swing.JTextField();
        sellText = new javax.swing.JTextField();
        brandText = new javax.swing.JTextField();
        purchaseButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        selectedVatText = new javax.swing.JLabel();
        vatExclusiveValueTF = new javax.swing.JFormattedTextField();
        jDateChooser1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        totalValueTF = new javax.swing.JFormattedTextField();
        vatValueTF = new javax.swing.JFormattedTextField();
        supplierVAT = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        purchaseTable = new javax.swing.JTable();
        refreshButton = new javax.swing.JButton();
        searchText = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        dateChooser1.setForeground(new java.awt.Color(51, 51, 51));
        dateChooser1.setDateFormat("yyyy-MM-dd");
        dateChooser1.setTextRefernce(jDateChooser1);

        jLabel1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel1.setText("PURCHASE");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Purchase Product"));

        jLabel2.setText("Select Supplier:");

        addSuppButton.setText("Click to add a new Supplier");
        addSuppButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSuppButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("TPIN No:");

        jLabel4.setText("InvoiceNo:");

        jLabel5.setText("Invoice Date:");

        jLabel6.setText("Vat Exclusive Value:");

        jLabel7.setText("Supplir VAT %");

        jLabel8.setText("Total Value:");

        jLabel9.setText("Commodities:");

        codeText.setEnabled(false);
        codeText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codeTextKeyReleased(evt);
            }
        });

        sellText.setEnabled(false);
        sellText.setFocusable(false);

        purchaseButton.setText("Purchase");
        purchaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchaseButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        clearButton.setText("CLEAR");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        selectedVatText.setText("0.00");

        vatExclusiveValueTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        vatExclusiveValueTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        vatExclusiveValueTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                vatExclusiveValueTFFocusLost(evt);
            }
        });
        vatExclusiveValueTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                vatExclusiveValueTFKeyReleased(evt);
            }
        });

        jLabel11.setText("Vat Value:");

        totalValueTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        totalValueTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalValueTF.setEnabled(false);
        totalValueTF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        vatValueTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        vatValueTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        vatValueTF.setToolTipText("");
        vatValueTF.setEnabled(false);
        vatValueTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                vatValueTFFocusLost(evt);
            }
        });

        supplierVAT.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        supplierVAT.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        supplierVAT.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(suppCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addSuppButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(vatValueTF))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(sellText, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalValueTF))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(brandText))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(purchaseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(selectedVatText)
                        .addGap(36, 36, 36))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(codeText)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameText)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(vatExclusiveValueTF, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(supplierVAT, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(selectedVatText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(suppCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addSuppButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vatExclusiveValueTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(supplierVAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vatValueTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sellText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalValueTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(brandText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(purchaseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton))
        );

        purchaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        purchaseTable.setRowHeight(30);
        purchaseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                purchaseTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(purchaseTable);

        refreshButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        refreshButton.setText("REFRESH");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        searchText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextKeyReleased(evt);
            }
        });

        jLabel10.setText("Search:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton)
                    .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(0, 10, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        loadDataSet();
        loadComboBox();
        clearButtonActionPerformed(evt); 
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void searchTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextKeyReleased
        loadSearchData(searchText.getText());
    }//GEN-LAST:event_searchTextKeyReleased

    private void purchaseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchaseTableMouseClicked
        int row = purchaseTable.getSelectedRow();
        int col = purchaseTable.getColumnCount();

        // commented by pinalkumar
//        Object[] data = new Object[col];
//        for (int i=0; i<col; i++)
//            data[i] = purchaseTable.getValueAt(row, i);
//        try
//        {
//            //Commented by pinalkumar
//            quantity = Integer.parseInt(data[3].toString());
//            prodCode = data[1].toString();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        
    }//GEN-LAST:event_purchaseTableMouseClicked

    private void vatExclusiveValueTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vatExclusiveValueTFKeyReleased
        // TODO add your handling code here:
//        try {
//                    
//       vatExclusiveValueTF.commitEdit();
//      // Object value = vatExclusiveValueTF.getText();
//
//        double d = (Double) vatExclusiveValueTF.getValue();
//        System.out.println("User input: " + d);
//
//        double vat_percent = Double.parseDouble(supplierVAT.getText());
//        double percentvalue = vat_percent / 100;
//        System.out.println("percentvalue: " + percentvalue);
//
//        double exact = percentvalue * d;
//        
//        System.out.println("VatValue: " + exact);
//
//        double total = d + exact;
//        System.out.println("Total: " + total);
//
//        vatValueTF.setEditable(true);
//        vatValueTF.setValue(BigDecimal.valueOf(exact).setScale(2, RoundingMode.HALF_UP));
//        vatValueTF.setEditable(false);
//
//        totalValueTF.setEditable(true);
//        totalValueTF.setValue(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP));
//        totalValueTF.setEditable(false);
//        if(SELECTED_VAT_NO.equals("25"))
//            {
//                vatValueTF.setEnabled(true);
//                vatValueTF.setEditable(true);
//            }
//            else
//            {
//                vatValueTF.setEnabled(false);
//                vatValueTF.setEditable(false);
//            }
//        
//        
//    } catch (Exception ex) {
//        // Handle the parse exception if needed
//        ex.printStackTrace();
//    }
   
        
    }//GEN-LAST:event_vatExclusiveValueTFKeyReleased

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        //codeText.setText("");
        nameText.setText("");
        jDateChooser1.setText("");
        vatExclusiveValueTF.setValue(0.00);
        vatValueTF.setValue(0.00);
        sellText.setText("");
        brandText.setText("");
        searchText.setText("");
        totalValueTF.setValue(0.00);

    }//GEN-LAST:event_clearButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (purchaseTable.getSelectedRow()<0)
        JOptionPane.showMessageDialog(null, "Please select a transaction from the table.");
        else {
            int opt = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this purchase?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if(opt==JOptionPane.YES_OPTION) {

                new ProductDAO().deletePurchaseDAO_new((int) purchaseTable.getValueAt(purchaseTable.getSelectedRow(),0));
                //Commented by pinalkumar
                //new ProductDAO().editPurchaseStock(prodCode, quantity);

                loadDataSet();

            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void purchaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchaseButtonActionPerformed
        productDTO = new ProductDTO();
        if (codeText.getText().equals("") || jDateChooser1.getText()==null
            || jDateChooser1.getText().equals("") || vatExclusiveValueTF.getText().equals("") || brandText.getText().equals(""))
        JOptionPane.showMessageDialog(null, "Please enter all the required details.");
        else {

            productDTO.setInvoiceNO(nameText.getText());
            productDTO.setDate(jDateChooser1.getText());
            productDTO.setVatExclusiveValue(Double.valueOf(vatExclusiveValueTF.getText()));
            productDTO.setSuppCode(new ProductDAO().getSuppCode(suppCombo.getSelectedItem().toString()));
            productDTO.setSupplierID(new ProductDAO().getSuppID(suppCombo.getSelectedItem().toString()));
            productDTO.setVatValue(Double.valueOf(vatValueTF.getText()));
            productDTO.setBrand("");
            productDTO.setTotalCost(Double.valueOf(totalValueTF.getText()));
            productDTO.setProdCode("101");
            productDTO.setQuantity(0);

            productDTO.setCommodities(brandText.getText());

            new ProductDAO().addPurchaseDAO2(productDTO);

            loadDataSet();
            clearButtonActionPerformed(evt);
            //            productDTO.setSuppCode(new ProductDAO().getSuppCode(suppCombo.getSelectedItem().toString()));
            //            productDTO.setProdCode(codeText.getText());
            //            try {
                //                ResultSet resultSet = new ProductDAO().getProdName(codeText.getText());
                //                if (resultSet.next()) {
                    //                    //productDTO.setProdName(nameText.getText());
                    //                    productDTO.setDate(jDateChooser1.getDate().toString());
                    //                    productDTO.setQuantity(Integer.parseInt(quantityText.getText()));
                    //                    //productDTO.setCostPrice(Double.parseDouble(costText.getText()));
                    //                    //productDTO.setSellPrice(Double.parseDouble(sellText.getText()));
                    //                    //productDTO.setBrand(brandText.getText());
                    //                    Double costPrice = Double.parseDouble(costText.getText());
                    //                    Double totalCost = costPrice * Integer.parseInt(quantityText.getText());
                    //                    productDTO.setTotalCost(totalCost);
                    //
                    //                    new ProductDAO().addPurchaseDAO(productDTO);
                    //                    loadDataSet();
                    //                } else
                //                    JOptionPane.showMessageDialog(null, "This seems to be a new product" +
                    //                            " that hasn't been added yet.\nPlease add this product in the \"Products\" section before proceeding.");
                //            } catch (SQLException e) {
                //                e.printStackTrace();
                //            }

        }
    }//GEN-LAST:event_purchaseButtonActionPerformed

    private void codeTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codeTextKeyReleased
        try {
            ResultSet resultSet = new ProductDAO().getProdFromCode(codeText.getText());
            if (resultSet.next()) {
                nameText.setText(resultSet.getString("productname"));
                vatValueTF.setValue(String.valueOf(resultSet.getDouble("costprice")));
                sellText.setText(String.valueOf(resultSet.getDouble("sellprice")));
                brandText.setText(resultSet.getString("brand"));
            } else {
                nameText.setText("");
                vatValueTF.setValue("");
                sellText.setText("");
                brandText.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_codeTextKeyReleased

    private void addSuppButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSuppButtonActionPerformed
        dashboard.addSuppPage();
    }//GEN-LAST:event_addSuppButtonActionPerformed

    private void vatExclusiveValueTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vatExclusiveValueTFFocusLost
        // TODO add your handling code here:
         try {
                    
       vatExclusiveValueTF.commitEdit();
      // Object value = vatExclusiveValueTF.getText();

        double d = (Double) vatExclusiveValueTF.getValue();
        System.out.println("User input: " + d);

        double vat_percent = Double.parseDouble(supplierVAT.getText());
        double percentvalue = vat_percent / 100;
        System.out.println("percentvalue: " + percentvalue);

        double exact = percentvalue * d;
        
        System.out.println("VatValue: " + exact);

        double total = d + exact;
        System.out.println("Total: " + total);

        //vatValueTF.setEditable(true);
        vatValueTF.setValue(BigDecimal.valueOf(exact).setScale(2, RoundingMode.HALF_UP).doubleValue());
        //vatValueTF.setEditable(false);

        //totalValueTF.setEditable(true);
        totalValueTF.setValue(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue());
        //totalValueTF.setEditable(false);
        if(SELECTED_VAT_NO.equals("25"))
            {
                vatValueTF.setEnabled(true);
                vatValueTF.setEditable(true);
            }
            else
            {
                vatValueTF.setEnabled(false);
                vatValueTF.setEditable(false);
            }
        
        
    } catch (Exception ex) {
        // Handle the parse exception if needed
        ex.printStackTrace();
    }
    }//GEN-LAST:event_vatExclusiveValueTFFocusLost

    private void vatValueTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vatValueTFFocusLost
        // TODO add your handling code here:
        if(SELECTED_VAT_NO.equals("25"))
        {
            try
            {
                double d = (Double) vatExclusiveValueTF.getValue();
                double d2 = Double.parseDouble(vatValueTF.getText());
                
                double total = d + d2;
                totalValueTF.setValue(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP));
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_vatValueTFFocusLost

    // Method to load and update combo box containing supplier names
    private void loadComboBox() {
        try {
            SupplierDAO supplierDAO = new SupplierDAO();
            SupplierDAO supplierDAO2 = new SupplierDAO();
            suppCombo.setModel(supplierDAO.setComboItemsCustome(supplierDAO.getQueryResult(),supplierDAO2.getQueryCountResult()));
            //suppCombo.setSelectedIndex(0);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   

    // Method to load data into table
    private void loadDataSet() {
        try {
            ProductDAO productDAO = new ProductDAO();
            purchaseTable.setModel(productDAO.buildTableModelPurchase(productDAO.getPurchaseInfo2()));
            for(int i=0;i<purchaseTable.getColumnCount();i++)
            {
                if(i==0 || i==1)
                {
                     //purchaseTable.getColumnModel().getColumn(i).setPreferredWidth(-10);
                     
                     
                     purchaseTable.getColumnModel().getColumn(i).setMinWidth(0);
                     purchaseTable.getColumnModel().getColumn(i).setMaxWidth(0);
                     purchaseTable.getColumnModel().getColumn(i).setResizable(false);
                }
                else if(i==2 || i==4)
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_left);
                }
                else if(i==purchaseTable.getColumnCount()-2 || i==purchaseTable.getColumnCount()-3 || i==purchaseTable.getColumnCount()-4)
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_right_bold);
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(new DoubleValueCellRenderer());
                }
//                else if(i==purchaseTable.getColumnCount()-1)
//                {
//                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(new TableActionCellRender());
//                    purchaseTable.getColumnModel().getColumn(i).setCellEditor(new TableActionCellEditor(event));    
//                }
                else
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_center);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Method to display search result in table
    public void loadSearchData(String text) {
        try {
            ProductDAO productDAO = new ProductDAO();
            purchaseTable.setModel(productDAO.buildTableModelPurchase(productDAO.getPurchaseSearch2(text)));
             for(int i=0;i<purchaseTable.getColumnCount();i++)
            {
                if(i==0 || i==1)
                {
                     //purchaseTable.getColumnModel().getColumn(i).setPreferredWidth(-10);
                     
                     
                     purchaseTable.getColumnModel().getColumn(i).setMinWidth(0);
                     purchaseTable.getColumnModel().getColumn(i).setMaxWidth(0);
                     purchaseTable.getColumnModel().getColumn(i).setResizable(false);
                }
                else if(i==2)
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_left);
                }
                else if(i==purchaseTable.getColumnCount()-2 || i==purchaseTable.getColumnCount()-3 || i==purchaseTable.getColumnCount()-4)
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_right_bold);
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(new DoubleValueCellRenderer());
                }
//                else if(i==purchaseTable.getColumnCount()-1)
//                {
//                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(new TableActionCellRender());
//                    purchaseTable.getColumnModel().getColumn(i).setCellEditor(new TableActionCellEditor(event));    
//                }
                else
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_center);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSuppButton;
    private javax.swing.JTextField brandText;
    private javax.swing.JButton clearButton;
    private javax.swing.JTextField codeText;
    private com.inventory.UI.datepicker.DateChooser dateChooser1;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField nameText;
    private javax.swing.JButton purchaseButton;
    private javax.swing.JTable purchaseTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField searchText;
    private javax.swing.JLabel selectedVatText;
    private javax.swing.JTextField sellText;
    private javax.swing.JComboBox<String> suppCombo;
    private javax.swing.JFormattedTextField supplierVAT;
    private javax.swing.JFormattedTextField totalValueTF;
    private javax.swing.JFormattedTextField vatExclusiveValueTF;
    private javax.swing.JFormattedTextField vatValueTF;
    // End of variables declaration//GEN-END:variables
}
