/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventory.Utill;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author pinal
 */
public class Supporter {
    
    public static DefaultTableCellRenderer renderer_left = new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable arg0,Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
         Component tableCellRendererComponent = super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
         int align = DefaultTableCellRenderer.LEFT;
         
        ((DefaultTableCellRenderer)tableCellRendererComponent).setHorizontalAlignment(align);
         return tableCellRendererComponent;
    }
};
    
     public static DefaultTableCellRenderer renderer_right = new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable arg0,Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
         Component tableCellRendererComponent = super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
         int align = DefaultTableCellRenderer.RIGHT;
         
        ((DefaultTableCellRenderer)tableCellRendererComponent).setHorizontalAlignment(align);
         return tableCellRendererComponent;
    }
};
     
     public static DefaultTableCellRenderer renderer_right_bold = new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable arg0,Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
         Component tableCellRendererComponent = super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
         int align = DefaultTableCellRenderer.RIGHT;
         
        ((DefaultTableCellRenderer)tableCellRendererComponent).setHorizontalAlignment(align);
        tableCellRendererComponent.setFont(new Font(tableCellRendererComponent.getFont().getName(), Font.BOLD,
                    tableCellRendererComponent.getFont().getSize()));
         return tableCellRendererComponent;
    }
};
     
     public static DefaultTableCellRenderer renderer_center = new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable arg0,Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
         Component tableCellRendererComponent = super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
         int align = DefaultTableCellRenderer.CENTER;
         
        ((DefaultTableCellRenderer)tableCellRendererComponent).setHorizontalAlignment(align);
         return tableCellRendererComponent;
    }
};
     
     
     public static int findNearestWhitespace(String input, int index) {
        int leftIndex = index;
        int rightIndex = index;

        // Move left to find whitespace
        while (leftIndex > 0 && !Character.isWhitespace(input.charAt(leftIndex))) {
            leftIndex--;
        }

        // Move right to find whitespace
        while (rightIndex < input.length() && !Character.isWhitespace(input.charAt(rightIndex))) {
            rightIndex++;
        }

        // Choose the nearest whitespace
        if (leftIndex > 0 && rightIndex < input.length()) {
            return (index - leftIndex) < (rightIndex - index) ? leftIndex : rightIndex;
        } else if (leftIndex > 0) {
            return leftIndex;
        } else if (rightIndex < input.length()) {
            return rightIndex;
        }

        return -1; // No whitespace found
    }
}
