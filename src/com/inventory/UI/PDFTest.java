/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventory.UI;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author pinal
 */
public class PDFTest {
    
    //psvm and tab for this method
    public static void main(String[] args) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            float fontSize = 10;
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);

            // Add image at the top of the page
            // Specify the image file
            
            //PDImageXObject image = PDImageXObject.createFromFile("bank_logo.png", document);
            PDImageXObject image = PDImageXObject.createFromFileByContent(getFile(), document);
            // Set the perfect height and width (adjust the values as needed)
            float perfectWidth = 450; // in points
            float perfectHeight = 65; // in points

            // Calculate the scaling factors
            float scaleX = perfectWidth / image.getWidth();
            float scaleY = perfectHeight / image.getHeight();
            contentStream.drawImage(image, 50, 750,image.getWidth() * scaleX, image.getHeight() * scaleY); // Adjust the coordinates as needed

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
            addTextWithUnderline(contentStream, "PERPOSE OF TRANSFER:  ", "FLOUR PURCHASE IN.NO          ", 50, 550);
            addTextWithUnderline(contentStream, "AMOUNT IN FIGURES:  ", "                                                       ", 50, 530);
            addTextWithUnderline(contentStream, "AMOUNT IN WORDS:  ", "                                                                                                                         ", 50, 510);
            addTextWithUnderline(contentStream, "                                       ", "                                                                                                                         ", 50, 490);
            addTextWithUnderline(contentStream, "SENDER’S SIGNATURE: ", "                                                                                 ", 50, 470);
            

            // Add sender's details
            // Add text with underline Lest 
            String BENEFICIARYtext = "BENEFICIARY / RECIPIENT’S DETAILS:";
            addLeftTextWithUnderline(contentStream, Sendertext, 50, 410);
            addTextWithUnderline(contentStream, "NAME:                  ", "ANTELOPE WHOLESALE MERCHANTS LTD                          .", 50, 390);
            addTextWithUnderline(contentStream, "BANK:                  ", "BARCLAYS BANK                                             .", 50, 370);
            addTextWithUnderline(contentStream, "BRANCH:             ", "LUANSHYA                                             .", 50, 350);
            addTextWithUnderline(contentStream, "SORT CODE:       ", "020713                                             .", 50, 330);
            addTextWithUnderline(contentStream, "ACCOUNT NUMBER (FULL A/C)::          ", "01308477943                                             .", 50, 310);

            
            String ForBanckUsetext = "FOR BANK USE ONLY:";
            addLeftTextWithUnderline(contentStream, ForBanckUsetext, 50, 250);

            addTextWithUnderline(contentStream, "SIGN:...................CAT A............................................CAT B............................................", "", 50, 220);

            contentStream.close();  // Close the content stream after all operations

            document.save("OUTWORD_DDACC_Application_Form.pdf");
            document.close();

            System.out.println("PDF created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static File getFile()
    {
        return  new File(PDFTest.class.getResource("/com/inventory/UI/Icons/bank_logo.png").getFile());
    }
    
    private void createPdf()
    {
       try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            float fontSize = 10;
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);

            // Add image at the top of the page
            // Specify the image file
            File imageFile = new File(getClass().getResource("/com/inventory/UI/Icons/bank_logo.png").getFile());
            //PDImageXObject image = PDImageXObject.createFromFile("bank_logo.png", document);
            PDImageXObject image = PDImageXObject.createFromFileByContent(imageFile, document);
            // Set the perfect height and width (adjust the values as needed)
            float perfectWidth = 450; // in points
            float perfectHeight = 65; // in points

            // Calculate the scaling factors
            float scaleX = perfectWidth / image.getWidth();
            float scaleY = perfectHeight / image.getHeight();
            contentStream.drawImage(image, 50, 750,image.getWidth() * scaleX, image.getHeight() * scaleY); // Adjust the coordinates as needed

            // Add text with underline center horizontal
            String text = "OUTWORD DDACC APPLICATION FORM";
            
            float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(text) / 1000 * fontSize;

            // Center the text horizontally
            float startX = (page.getMediaBox().getWidth() - textWidth) / 2;
            float startY = 700;
            
            addCenterTextWithUnderline(contentStream, text, startX, startY);
            
            // Add sender's details
            // Add text with underline center horizontal
            String Sendertext = "SENDER’S DETAILS:";
            addLeftTextWithUnderline(contentStream, Sendertext, 50, 670);
            
            
            addTextWithUnderline(contentStream, "DATE:", "                      .", 50, 650);
            addTextWithUnderline(contentStream, "NAME:", "STAR BAKERY LTD                .", 50, 635);
            // Add other sender details using addText and addTextWithUnderline methods with appropriate coordinates

            // Add beneficiary/recipient's details
            addText(contentStream, "BENEFICIARY / RECIPIENT’S DETAILS:", 50, 600);
            addTextWithUnderline(contentStream, "NAME:", "ANTELOPE WHOLESALE MERCHANTS LTD               .", 50, 590);
            //Add other beneficiary details using addText and addTextWithUnderline methods with appropriate coordinates

            contentStream.close();  // Close the content stream after all operations

            document.save("OUTWORD_DDACC_Application_Form.pdf");
            document.close();

            System.out.println("PDF created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
