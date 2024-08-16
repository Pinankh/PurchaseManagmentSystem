/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.UI;

import com.inventory.DAO.PaymentsDAO;
import com.inventory.DAO.ProductDAO;
import com.inventory.DAO.SupplierDAO;
import com.inventory.DTO.ProductDTO;
import com.inventory.DTO.SupplierDTO;
import com.inventory.Utill.DecimalToWordsConverter;
import com.inventory.Utill.InputStreamToByteArray;
import com.inventory.Utill.Supporter;
import com.inventory.cell.TableActionCellEditor;
import com.inventory.cell.TableActionCellRender;
import com.inventory.cell.TableActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;

import java.awt.event.ItemEvent;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;


import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;

import javax.swing.text.NumberFormatter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;


public class PaymentPage extends javax.swing.JPanel {

    ProductDTO productDTO;
    String username = null;
    String supplier = null;
    Dashboard dashboard;
    int quantity;
    String prodCode = null;
    double selectedVAT = 0.0;
    TableActionEvent event;
    SupplierDTO SELECTED_SUPPLIER=null;
    private int SELECTED_SUPPLIER_ID;
    
    /**
     * Creates new form PurchasePage
     * @param dashboard
     */
    
    public PaymentPage(Dashboard dashboard) {
        initComponents();
        this.dashboard = dashboard;
        loadComboBox();
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
        
       SELECTED_SUPPLIER_ID = suppCombo.getSelectedItem()==null?0:((SupplierDTO) suppCombo.getSelectedItem()).getSuppID();
       
       
        suppCombo.addItemListener((ItemEvent e) -> {
            // Get the selected item
            SELECTED_SUPPLIER_ID = ((SupplierDTO) e.getItem()).getSuppID();
            loadDataSet();
            
        });
        loadDataSet();
                
        purchaseTable.getSelectionModel().addListSelectionListener((e) -> {
            if(e.getValueIsAdjusting())
                    return;
            System.out.println("Selecstion");
            if(purchaseTable.getSelectedRowCount()==0)
            {
                printButton.setEnabled(false);
            }
            else
            {
                printButton.setEnabled(true);
            }
        });
        
        
                        

    }



    private void createPDFDataForRTGS(String commodities,String amount,SupplierDTO supplierDTO) {
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

           
            // Get the class loader
            ClassLoader classLoader = getClass().getClassLoader();


            
            // Specify the path to the image resource (relative to the classpath)
            String imagePath2 = "com/inventory/UI/Icons/bank_logo.png";

            // Load the image using getResourceAsStream
            try {
                InputStream inputStream = classLoader.getResourceAsStream(imagePath2);
            
                // Check if the image was found
                if (inputStream != null) {
                // Create an ImageIcon with the loaded image
                
                PDImageXObject image = PDImageXObject.createFromByteArray(document,InputStreamToByteArray.convert(inputStream) , "image");
//              PDImageXObject image = PDImageXObject.createFromFileByContent(file, document);
           
                // Set the perfect height and width (adjust the values as needed)
                float perfectWidth = 470; // in points
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
        
        

            // Add text with underline center horizontal
            String text = "RTGS APPLICATION FORM";
            
            float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(text) / 1000 * fontSize;

            // Center the text horizontally
            float startX = (page.getMediaBox().getWidth() - textWidth) - 50;
            float startY = 730;
            
            
            addTextRight(contentStream, text, startX, startY);
            
            
            // Add text with underline center horizontal
            String textcenter = "(For remittance of funds through RTGS system)";
            
            float textcenterWidth = PDType1Font.HELVETICA.getStringWidth(textcenter) / 1000 * fontSize;

            // Center the text horizontally
            float centerstartX = (page.getMediaBox().getWidth() - textcenterWidth) / 2;
            float centerstartY = 710;
            
            
            addText(contentStream, textcenter, centerstartX, centerstartY);
            
            contentStream.setStrokingColor(Color.gray);
            contentStream.setLineWidth(0.3f);
            
            int initX = 50;
            int initY = 690;
            int cellHieght = 20;
            int cellWidth = 100;
            int colCount = 5;
            int rowCount = 10;
            
            drawTwoColumn(contentStream, initX, initY, cellHieght, "Name of the Remmiter:", "Star Bakery LTD");
//            contentStream.addRect(initX, initY, 170, -cellHieght);
//            contentStream.beginText();
//            contentStream.newLineAtOffset(initX+7, initY-cellHieght+7);
//            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
//            contentStream.showText("Name of the Remmiter:");
//            contentStream.endText();
//            
//            contentStream.addRect(initX+170, initY, 330, -cellHieght);
//            contentStream.beginText();
//            contentStream.newLineAtOffset(initX+177, initY-cellHieght+7);
//            contentStream.setFont(PDType1Font.TIMES_BOLD, 10);
//            contentStream.showText("Star Bakery LTD");
//            contentStream.endText();
            drawMultipleColumn(contentStream, initX, initY, cellHieght, 16, "Remmiter's IZB Account NO:", "0073020000026");
            
            initY -= (cellHieght*2);
            initY -= 5;
            drawTwoColumn(contentStream, initX, initY, cellHieght, "Beneficiery Account Name:", supplierDTO.getAccount_name());
            drawMultipleColumn(contentStream, initX, initY, cellHieght, 16,"Beneficiery Account NO:", supplierDTO.getAccount_number());
            initY -= (cellHieght*2);
            drawTwoColumn(contentStream, initX, initY, cellHieght, "Beneficiery Bank & Branch:", supplierDTO.getBank_name()+"/"+supplierDTO.getBranch_name());
            initY -= cellHieght;
            drawTwoLineColumn(contentStream, initX, initY, cellHieght+10, "Reason for remmitence, if any to ","be mentioned:", "");
            initY -= cellHieght-10;
            drawMultipleColumn(contentStream, initX, initY, cellHieght, 11,"", "");
            initY -= cellHieght;
            drawMultipleColumn(contentStream, initX, initY, cellHieght, 11,"Amount to be remmited (K)", "");
            initY -= cellHieght;
            drawMultipleColumn(contentStream, initX, initY, cellHieght, 11,"RTGS Commision (K)", "");
            initY -= cellHieght-10;
            drawTwoMultipleColumn(contentStream, initX, initY, cellHieght+10, 11,"Total Amount to be debited to ","Sender's account (k)", "");
            initY -= (cellHieght*3);
            drawTwoColumn(contentStream, initX, initY, cellHieght, "Tel No/Mobile No:", supplierDTO.getPhone());
            initY -= cellHieght;
            drawTwoColumn(contentStream, initX, initY, cellHieght, "Tel No/Mobile No:", supplierDTO.getPhone());
//            contentStream.addRect(initX, initY-cellHieght, 170, -cellHieght);
//            int textY = initY-cellHieght;
//            contentStream.beginText();
//            contentStream.newLineAtOffset(initX+7, textY-cellHieght+7);
//            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
//            contentStream.showText("Remmiter's IZB Account NO:");
//            contentStream.endText();
//            
//            float newCLMWidth = 330f / 16f;
//            float newX = 50F+170f;
//            int newY = initY-cellHieght;
//            String accountNo = "0073020000026";
//            for(int i=0;i<16;i++)
//            {
//                contentStream.addRect(newX, newY, newCLMWidth, -cellHieght);
//                if(i < accountNo.length())
//                {
//                    contentStream.beginText();
//                    contentStream.newLineAtOffset(newX+7, newY-cellHieght+5);
//                    contentStream.setFont(PDType1Font.TIMES_BOLD, 10);
//                    contentStream.showText(String.valueOf(accountNo.charAt(i)));
//                    contentStream.endText();
//                }
//                newX += newCLMWidth;
//            }
            
//            for(int i=1;i<=rowCount;i++)
//            {
//                for(int j=1;j<=colCount;j++)
//                {
//                    contentStream.addRect(initX, initY, cellWidth, -cellHieght);
//                    contentStream.beginText();
//                    contentStream.newLineAtOffset(initX+7, initY-cellHieght+5);
//                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
//                    contentStream.showText("Test");
//                    contentStream.endText();
//                    initX += cellWidth;
//                }
//                initX = 50;
//                initY -= cellHieght;
//            }
            
            contentStream.stroke();
            
//           
            contentStream.close();  // Close the content stream after all operations

            document.save("F:\\JavaProject\\InventoryManagementSystem-master\\dist\\RTGS_Application_Form.pdf");
            document.close();
            System.out.println("PDF created successfully.");
            try
            {
                File pdffile = new File("F:\\JavaProject\\InventoryManagementSystem-master\\dist\\RTGS_Application_Form.pdf");
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
    
    
    private void drawTwoColumn(PDPageContentStream contentStream,int initX,int initY,int cellHieght,String text1,String text2)
    {
        try
        {
            contentStream.addRect(initX, initY, 170, -cellHieght);
            contentStream.beginText();
            contentStream.newLineAtOffset(initX+7, initY-cellHieght+7);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.showText(text1);
            contentStream.endText();
            
            contentStream.addRect(initX+170, initY, 330, -cellHieght);
            contentStream.beginText();
            contentStream.newLineAtOffset(initX+177, initY-cellHieght+7);
            contentStream.setFont(PDType1Font.TIMES_BOLD, 10);
            contentStream.showText(text2);
            contentStream.endText();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void drawTwoLineColumn(PDPageContentStream contentStream,int initX,int initY,int cellHieght,String text1,String text1_1,String text2)
    {
        try
        {
            contentStream.addRect(initX, initY, 170, -cellHieght);
            contentStream.beginText();
            contentStream.newLineAtOffset(initX+7, initY-cellHieght+18);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.showText(text1);
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.newLineAtOffset(initX+7, initY-cellHieght+6);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.showText(text1_1);
            contentStream.endText();
            
            contentStream.addRect(initX+170, initY, 330, -cellHieght);
            contentStream.beginText();
            contentStream.newLineAtOffset(initX+177, initY-cellHieght+7);
            contentStream.setFont(PDType1Font.TIMES_BOLD, 10);
            contentStream.showText(text2);
            contentStream.endText();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void drawMultipleColumn(PDPageContentStream contentStream,int initX,int initY,int cellHieght,int  columns2,String text1,String text2)
    {
        try
        {
            contentStream.addRect(initX, initY-cellHieght, 170, -cellHieght);
            int textY = initY-cellHieght;
            contentStream.beginText();
            contentStream.newLineAtOffset(initX+7, textY-cellHieght+7);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.showText(text1);
            contentStream.endText();
            
            float totalColumns = Float.parseFloat(String.valueOf(columns2));
            float newCLMWidth = 330f / totalColumns;
            float newX = 50F+170f;
            int newY = initY-cellHieght;
            
            for(int i=0;i<columns2;i++)
            {
                contentStream.addRect(newX, newY, newCLMWidth, -cellHieght);
                if(i < text2.length())
                {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(newX+7, newY-cellHieght+5);
                    contentStream.setFont(PDType1Font.TIMES_BOLD, 10);
                    contentStream.showText(String.valueOf(text2.charAt(i)));
                    contentStream.endText();
                }
                newX += newCLMWidth;
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    private void drawTwoMultipleColumn(PDPageContentStream contentStream,int initX,int initY,int cellHieght,int  columns2,String text1,String text1_1,String text2)
    {
        try
        {
            contentStream.addRect(initX, initY-cellHieght, 170, -cellHieght);
            int textY = initY-cellHieght;
            contentStream.beginText();
            contentStream.newLineAtOffset(initX+7, textY-cellHieght+18);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.showText(text1);
            contentStream.endText();
            
             contentStream.beginText();
            contentStream.newLineAtOffset(initX+7, textY-cellHieght+7);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.showText(text1_1);
            contentStream.endText();
            
            float totalColumns = Float.parseFloat(String.valueOf(columns2));
            float newCLMWidth = 330f / totalColumns;
            float newX = 50F+170f;
            int newY = initY-cellHieght;
            
            for(int i=0;i<columns2;i++)
            {
                contentStream.addRect(newX, newY, newCLMWidth, -cellHieght);
                if(i < text2.length())
                {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(newX+7, newY-cellHieght+5);
                    contentStream.setFont(PDType1Font.TIMES_BOLD, 10);
                    contentStream.showText(String.valueOf(text2.charAt(i)));
                    contentStream.endText();
                }
                newX += newCLMWidth;
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
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

           
            // Get the class loader
            ClassLoader classLoader = getClass().getClassLoader();


            
            // Specify the path to the image resource (relative to the classpath)
            String imagePath2 = "com/inventory/UI/Icons/bank_logo.png";

            // Load the image using getResourceAsStream
            try {
                InputStream inputStream = classLoader.getResourceAsStream(imagePath2);
            
                // Check if the image was found
                if (inputStream != null) {
                // Create an ImageIcon with the loaded image
                
                PDImageXObject image = PDImageXObject.createFromByteArray(document,InputStreamToByteArray.convert(inputStream) , "image");
//              PDImageXObject image = PDImageXObject.createFromFileByContent(file, document);
           
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
            addLeftTextWithUnderline(contentStream, BENEFICIARYtext, 50, 410);
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
        String path = PaymentPage.class.getResource("/com/inventory/UI/Icons/bank_logo.png").getPath();
        System.out.println("Image Path = "+path);
        return  new File(path);
    }
    
   
    
    private static void addText(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }
    
    private static void addTextRight(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
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

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        purchaseTable = new javax.swing.JTable();
        suppCombo = new javax.swing.JComboBox<>();
        printButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel1.setText("DUE PAYMENT");

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

        printButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/inventory/cell/print.png"))); // NOI18N
        printButton.setEnabled(false);
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Select Supplier");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(suppCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(suppCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

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

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        // TODO add your handling code here:
        if(purchaseTable.getSelectedRowCount()>0)
        {
            int opt = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to pay selected supplier, And make a reciept?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if(opt==JOptionPane.YES_OPTION) {
               int selectedRows[] = purchaseTable.getSelectedRows();
                DefaultTableModel model = (DefaultTableModel) purchaseTable.getModel();  
                
                String amount = String.valueOf(model.getValueAt(selectedRows[0], 7));
                String commoditties = "";
                SupplierDAO supplierDAO = new SupplierDAO();
                SupplierDTO dTO = supplierDAO.getSupplierBankDetails(SELECTED_SUPPLIER_ID);
               
                System.out.println("amount " + amount);
                double total=0.0;
                String PurchaseIDS = "";
                String InvoiceNOS = "";
                int count = selectedRows.length;
               for(int i=0;i<count;i++)
               {
                   total += Double.parseDouble(String.valueOf(model.getValueAt(selectedRows[i], 7)));
                   String resValue = String.valueOf(model.getValueAt(selectedRows[i], 9));
                   if(!commoditties.contains(resValue))
                   {
                       commoditties += " "+resValue;
                   }
                   String invoiceNO = String.valueOf(model.getValueAt(selectedRows[i], 4));
                   
                   
                   int pID = Integer.parseInt(model.getValueAt(selectedRows[i], 0).toString());
                   if(i==count-1)
                   {
                       PurchaseIDS += String.valueOf(pID);
                       InvoiceNOS += invoiceNO;
                   }
                   else
                   {
                       PurchaseIDS += String.valueOf(pID)+",";
                       InvoiceNOS += invoiceNO+",";
                   }
               }
                BigDecimal GrandTotal = BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP);
                System.out.println("TotalAmount: " + GrandTotal.toString());
                        
                LocalDate dateObj = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String Currentdate = dateObj.format(formatter);
                System.out.println(Currentdate);
                
                if(dTO.getBank_name().equalsIgnoreCase("INDO ZAMBIA BANK"))
                {
                    String amountInWord = DecimalToWordsConverter.convertToWords(Double.parseDouble(GrandTotal.toString()));
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

                                    try
                                     {
                                         //Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
                                         String reportPath = "D:\\StarBakery\\reports\\internal_transfer.jrxml";
                                         JasperReport jr = JasperCompileManager.compileReport(reportPath);
                                         Map<String,Object> para = new HashMap<>();
                                         para.put("purpose_of_transfer", commoditties);
                                         para.put("amount_figure", GrandTotal.toString());
                                         para.put("amount_word1", amountInWord1);
                                         para.put("amount_word2", amountInWord2);
                                         para.put("bf_name", dTO.getAccount_name());
                                         para.put("bf_branch", dTO.getBranch_name());
                                         para.put("bf_account_no", dTO.getAccount_number());
                                         para.put("payment_date", Currentdate);

                                         //$X{NOTIN, purchaseinfo.purchaseID, not_into}
                                         JasperPrint jp = JasperFillManager.fillReport(jr, para);
                                         JasperViewer.viewReport(jp, false);
                                         //conn.close();
                                         PaymentsDAO paymentsDAO = new PaymentsDAO();
                                         paymentsDAO.addPaymentsData(SELECTED_SUPPLIER_ID, GrandTotal.doubleValue(), PurchaseIDS);
                                         System.out.println("Payment Successfully done!");
                                         loadDataSet();
                                     }catch(Exception e)
                                     {

                                         JOptionPane.showMessageDialog(null, e);

                                     }
                }
                else
                {
                            if(total>499999)
                            {
                                try
                                     {
                                         String tAmount = "";
                                         if(!GrandTotal.toString().contains("."))
                                         {
                                             tAmount = GrandTotal.toString()+".00";

                                         }
                                         else
                                         {
                                             String[] splitvalue = GrandTotal.toString().split("\\.");
                                             if(splitvalue[1].length()==1)
                                             {
                                                 tAmount = GrandTotal.toString()+"0";
                                             }
                                             else
                                             {
                                                 tAmount = GrandTotal.toString();
                                             }
                                         }
                                         //Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
                                         String reportPath = "D:\\StarBakery\\reports\\RTGSForm.jrxml";
                                         JasperReport jr = JasperCompileManager.compileReport(reportPath);
                                         Map<String,Object> para = new HashMap<>();
                                         para.put("reason", commoditties);
                                         para.put("amount-to_be_paid", tAmount);
                                         para.put("bf_name", dTO.getAccount_name());
                                         para.put("bf_bank_branch", dTO.getBank_name()+","+dTO.getBranch_name());
                                         para.put("bf_account_no", dTO.getAccount_number());
                                         para.put("payment_date", Currentdate);
                                         para.put("invoice_no", InvoiceNOS);

                                         //$X{NOTIN, purchaseinfo.purchaseID, not_into}
                                         JasperPrint jp = JasperFillManager.fillReport(jr, para);
                                         JasperViewer.viewReport(jp, false);
                                         //conn.close();
                                           PaymentsDAO paymentsDAO = new PaymentsDAO();
                                         paymentsDAO.addPaymentsData(SELECTED_SUPPLIER_ID, GrandTotal.doubleValue(), PurchaseIDS);
                                         System.out.println("Payment Successfully done!");
                                         loadDataSet();
                                     }catch(Exception e)
                                     {

                                         JOptionPane.showMessageDialog(null, e);

                                     }
                            }
                            else
                            {

                     //               createPDFData("",GrandTotal.toString(),dTO);
                                    //createPDFDataForRTGS("",GrandTotal.toString(),dTO);

                                     String amountInWord = DecimalToWordsConverter.convertToWords(Double.parseDouble(GrandTotal.toString()));
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

                                    try
                                     {
                                         //Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
                                         String reportPath = "D:\\StarBakery\\reports\\outwordform.jrxml";
                                         JasperReport jr = JasperCompileManager.compileReport(reportPath);
                                         Map<String,Object> para = new HashMap<>();
                                         para.put("purpose_of_transfer", commoditties);
                                         para.put("amount_figure", GrandTotal.toString());
                                         para.put("amount_word1", amountInWord1);
                                         para.put("amount_word2", amountInWord2);
                                         para.put("bf_name", dTO.getAccount_name());
                                         para.put("bf_bank_name", dTO.getBank_name());
                                         para.put("bf_branch", dTO.getBranch_name());
                                         para.put("bf_short_code", dTO.getShort_code());
                                         para.put("bf_account_no", dTO.getAccount_number());
                                         para.put("payment_date", Currentdate);

                                         //$X{NOTIN, purchaseinfo.purchaseID, not_into}
                                         JasperPrint jp = JasperFillManager.fillReport(jr, para);
                                        
                                         
                                         JasperViewer.viewReport(jp, false);
                                         //conn.close();
                                         PaymentsDAO paymentsDAO = new PaymentsDAO();
                                         paymentsDAO.addPaymentsData(SELECTED_SUPPLIER_ID, GrandTotal.doubleValue(), PurchaseIDS);
                                           System.out.println("Payment Successfully done!");
                                           loadDataSet();
                                     }catch(Exception e)
                                     {

                                         JOptionPane.showMessageDialog(null, e);

                                     }
                            }

                         }
                
            }
        }
        else
        {
            
        }
    }//GEN-LAST:event_printButtonActionPerformed

   
    
  

    // Method to load data into table
    private void loadDataSet() {
        try {
            ProductDAO productDAO = new ProductDAO();
            purchaseTable.setModel(productDAO.buildTableModelPayment(productDAO.getPurchaseInfoFor(SELECTED_SUPPLIER_ID)));
            for(int i=0;i<purchaseTable.getColumnCount();i++)
            {
                if(i==0)
                {
                     //purchaseTable.getColumnModel().getColumn(i).setPreferredWidth(-10);
                     
                     
                     purchaseTable.getColumnModel().getColumn(i).setMinWidth(0);
                     purchaseTable.getColumnModel().getColumn(i).setMaxWidth(0);
                     purchaseTable.getColumnModel().getColumn(i).setResizable(false);
                }
                else if(i==7)
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_right_bold);
                }
                else if(i==5 || i==6)
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_right);
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
            purchaseTable.setModel(productDAO.buildTableModelPayment(productDAO.getPurchaseSearch2(text)));
             for(int i=0;i<purchaseTable.getColumnCount();i++)
            {
                if(i==2)
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_left);
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton printButton;
    private javax.swing.JTable purchaseTable;
    private javax.swing.JComboBox<String> suppCombo;
    // End of variables declaration//GEN-END:variables
}
