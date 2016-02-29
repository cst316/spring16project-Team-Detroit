package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.ui.ContactDialog;
import net.sf.memoranda.util.Configuration;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

/**
Class:	ContactsPanel.java
Author: masperbe
Description:  Defines UI for Contacts work panel
*/
@SuppressWarnings("serial")
public class ContactsPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar contactsToolBar = new JToolBar();
    JButton newContactB = new JButton();
    JButton editContactB = new JButton();
    JButton removeContactB = new JButton();   
    
    JScrollPane scrollPane = new JScrollPane();
    ContactsTable contactsTable = new ContactsTable();
    JPopupMenu contactPPMenu = new JPopupMenu();
    JMenuItem ppEditContact = new JMenuItem();
    JMenuItem ppRemoveContact = new JMenuItem();
    JMenuItem ppNewContact = new JMenuItem();
    DailyItemsPanel parentPanel = null;
    
    boolean isActive = false;

    public ContactsPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    void jbInit() throws Exception {
        contactsToolBar.setFloatable(false);

        newContactB.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/contact_new.png")));
        newContactB.setEnabled(true);
        newContactB.setMaximumSize(new Dimension(24, 24));
        newContactB.setMinimumSize(new Dimension(24, 24));
        newContactB.setToolTipText(Local.getString("New contact"));
        newContactB.setRequestFocusEnabled(false);
        newContactB.setPreferredSize(new Dimension(24, 24));
        newContactB.setFocusable(false);
        newContactB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newContactB_actionPerformed(e);
            }
        });
        newContactB.setBorderPainted(false);

        editContactB.setBorderPainted(false);
        editContactB.setFocusable(false);
        editContactB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editContactB_actionPerformed(e);
            }
        });
        editContactB.setPreferredSize(new Dimension(24, 24));
        editContactB.setRequestFocusEnabled(false);
        editContactB.setToolTipText(Local.getString("Edit contact"));
        editContactB.setMinimumSize(new Dimension(24, 24));
        editContactB.setMaximumSize(new Dimension(24, 24));
        editContactB.setEnabled(true);
        editContactB.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/contact_edit.png")));

        removeContactB.setBorderPainted(false);
        removeContactB.setFocusable(false);
        removeContactB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeContactB_actionPerformed(e);
            }
        });
        removeContactB.setPreferredSize(new Dimension(24, 24));
        removeContactB.setRequestFocusEnabled(false);
        removeContactB.setToolTipText(Local.getString("Remove contact"));
        removeContactB.setMinimumSize(new Dimension(24, 24));
        removeContactB.setMaximumSize(new Dimension(24, 24));
        removeContactB.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/contact_remove.png")));

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        contactsTable.setMaximumSize(new Dimension(32767, 32767));
        contactsTable.setRowHeight(24);
        contactPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        ppEditContact.setFont(new java.awt.Font("Dialog", 1, 11));
        ppEditContact.setText(Local.getString("Edit contact") + "...");
        ppEditContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //ppEditEvent_actionPerformed(e);
            }
        });
        ppEditContact.setEnabled(false);
        ppEditContact.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/contact_edit.png")));
        ppRemoveContact.setFont(new java.awt.Font("Dialog", 1, 11));
        ppRemoveContact.setText(Local.getString("Remove contact"));
        ppRemoveContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //ppRemoveContact_actionPerformed(e);
            }
        });
        ppRemoveContact.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/contact_remove.png")));
        ppRemoveContact.setEnabled(false);
        ppNewContact.setFont(new java.awt.Font("Dialog", 1, 11));
        ppNewContact.setText(Local.getString("New contact") + "...");
        ppNewContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //ppNewContact_actionPerformed(e);
            }
        });
        ppNewContact.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/contact_new.png")));
        scrollPane.getViewport().add(contactsTable, null);
        this.add(scrollPane, BorderLayout.CENTER);

        contactsToolBar.add(newContactB, null);
        contactsToolBar.add(removeContactB, null);
        contactsToolBar.add(editContactB, null);

        this.add(contactsToolBar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        contactsTable.addMouseListener(ppListener);

        contactsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = contactsTable.getSelectedRow() > -1;
                editContactB.setEnabled(enbl);
                ppEditContact.setEnabled(enbl);
                removeContactB.setEnabled(enbl);
                ppRemoveContact.setEnabled(enbl);
            }
        });
        editContactB.setEnabled(false);
        removeContactB.setEnabled(false);
        contactPPMenu.add(ppEditContact);
        contactPPMenu.addSeparator();
        contactPPMenu.add(ppNewContact);
        contactPPMenu.add(ppRemoveContact);
		
		// remove events using the DEL key
		contactsTable.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				if(contactsTable.getSelectedRows().length>0 
					&& e.getKeyCode()==KeyEvent.VK_DELETE)
					ppRemoveContact_actionPerformed(null);
			}
			public void	keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){} 
		});
    }
    
	public void setActive(boolean isa) {
		isActive = isa;
	}

    void editContactB_actionPerformed(ActionEvent e) {
        ContactDialog dlg = new ContactDialog(App.getFrame());
        EmailContact c = (EmailContact) contactsTable.getModel().getValueAt(
                contactsTable.getSelectedRow(), ContactsTable.CONTACT);
    }
    
    void newContactB_actionPerformed(ActionEvent e) {
    	ContactDialog dlg = new ContactDialog(App.getFrame());
    }

    void removeContactB_actionPerformed(ActionEvent e) {
		String msg;
		EmailContact c;

		if(contactsTable.getSelectedRows().length > 1) 
			msg = Local.getString("Remove") + " " + contactsTable.getSelectedRows().length 
				+ " " + Local.getString("events") + "\n" + Local.getString("Are you sure?");
		else {
			c = (EmailContact) contactsTable.getModel().getValueAt(
                contactsTable.getSelectedRow(),
                EventsTable.EVENT);
			msg = Local.getString("Remove contact") + "\n'" 
				+ c.getName() + "'\n" + Local.getString("Are you sure?");
		}

        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                msg,
                Local.getString("Remove contact"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION) return;

        for(int i=0; i< contactsTable.getSelectedRows().length;i++) {
			c = (EmailContact) contactsTable.getModel().getValueAt(
                  contactsTable.getSelectedRows()[i], EventsTable.EVENT);
        ContactList.removeContact(c);
		}
        contactsTable.getSelectionModel().clearSelection(); 
  }

    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if ((e.getClickCount() == 2) && (contactsTable.getSelectedRow() > -1))
                editContactB_actionPerformed(null);
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                contactPPMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    }
    void ppEditContactt_actionPerformed(ActionEvent e) {
        editContactB_actionPerformed(e);
    }
    void ppRemoveContact_actionPerformed(ActionEvent e) {
        removeContactB_actionPerformed(e);
    }
    void ppNewContact_actionPerformed(ActionEvent e) {
        newContactB_actionPerformed(e);
    }
    
    public void updateLanguage() {
    	newContactB.setToolTipText(Local.getString("New event"));
    	removeContactB.setToolTipText(Local.getString("Remove event"));
    	editContactB.setToolTipText(Local.getString("Edit event"));
    	
    	ppEditContact.setText(Local.getString("Edit event") + "...");
        ppRemoveContact.setText(Local.getString("Remove event"));
        ppNewContact.setText(Local.getString("New event") + "...");
    	
    	contactsTable.updateLanguage();
    }
    
}