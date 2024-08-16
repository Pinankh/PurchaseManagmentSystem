/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventory.DTO;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author pinal
 */
public class SupplierComboboxModel extends AbstractListModel implements ComboBoxModel {

        public SupplierDTO[] supplierDTOs;

        SupplierDTO selection = null;

    @Override
    public String toString() {
        return selection.getFullName(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
        
        
        
        @Override
        public Object getElementAt(int index) {
            return supplierDTOs[index];
        }

        @Override
        public int getSize() {
            return supplierDTOs.length;
        }

        @Override
        public void setSelectedItem(Object anItem) {
            selection = (SupplierDTO)anItem;
        }

        @Override
        public Object getSelectedItem() {
            return selection;
        }
    }