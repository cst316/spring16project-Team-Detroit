package net.sf.memoranda.ui;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.Local;

/**
Class:	ContactsTable.java
Author: masperbe
Description:  Defines layout of Contacts table in work panel
*/
@SuppressWarnings("serial")
public class ContactsTable extends JTable {

    public static final int CONTACT = 100;
    public static final int CONTACT_ID = 101;

    Vector<?> contacts = new Vector<Object>();
    /**
     * Constructor for ContactsTable.
     */
    public ContactsTable() {
        super();
        setModel(new ContactsTableModel());
        initTable();
        this.setShowGrid(false);
    }

    public void initTable() {
        contacts = (Vector<?>)ContactList.getContacts();
        getColumnModel().getColumn(0).setPreferredWidth(300);
        getColumnModel().getColumn(0).setMaxWidth(300);
        clearSelection();
        updateUI();
    }

    public void refresh() {
        //initTable(CurrentDate.get());
    }

     public TableCellRenderer getCellRenderer(int row, int column) {
        return new javax.swing.table.DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
                Component comp;
                comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                comp.setForeground(java.awt.Color.gray);
                
                return comp;
            }
        };

    }

    class ContactsTableModel extends AbstractTableModel {

        String[] columnNames = {
            //Local.getString("Task name"),
            Local.getString("Name"),
            Local.getString("Email"),
            Local.getString("Phone"),
            Local.getString("Note")
        };

        ContactsTableModel() {
            super();
        }

        public int getColumnCount() {
            return 4;
        }

        public int getRowCount() {
			int i;
			try {
				i = contacts.size();
			}
			catch(NullPointerException e) {
				i = 1;
			}
			return i;
        }

      @Override
      public Object getValueAt(int row, int col) {
         EmailContact c = (EmailContact)contacts.get(row);
         if (col == 0)
              return c.getName();
         else if (col == 1)
              return c.getEmail();
         else return c;
      }

        public String getColumnName(int col) {
        	columnNames[0] = Local.getString("Name");
        	columnNames[1] = Local.getString("Email");
        	columnNames[2] = Local.getString("Phone");
        	columnNames[3] = Local.getString("Note");

            return columnNames[col];
        }

    }


    public void updateLanguage() {
    	setModel(new ContactsTableModel());
        getColumnModel().getColumn(0).setPreferredWidth(60);
        getColumnModel().getColumn(0).setMaxWidth(60);
    	this.repaint();
    }

}
