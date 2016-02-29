package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.CurrentNote;
import net.sf.memoranda.NoteListener;
import net.sf.memoranda.EventNotificationListener;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.History;
import net.sf.memoranda.HistoryItem;
import net.sf.memoranda.HistoryListener;
import net.sf.memoranda.Note;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.ClockObservable;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;


/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/* $Id: DailyItemsPanel.java,v 1.22 2005/02/13 03:06:10 rawsushi Exp $ */
@SuppressWarnings("serial")
public class DailyItemsPanel extends JPanel implements Observer {
  ClockObservable clockObservable = new ClockObservable();

  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  BorderLayout dailyItemsLayout = new BorderLayout();
  JSplitPane splitPane = new JSplitPane();
  JSplitPane splitPaneVert = new JSplitPane();
  JPanel controlPanel = new JPanel(); /* Contains the calendar */
  JPanel mainPanel = new JPanel();
  BorderLayout controlPanelLayout = new BorderLayout();
  JPanel statusPanel = new JPanel(new GridBagLayout());
  BorderLayout mainPanelLayout = new BorderLayout();
  JPanel editorsPanel = new JPanel();
  CardLayout cardLayout1 = new CardLayout();
  public EditorPanel editorPanel = new EditorPanel(this);
  JLabel currentDateLabel = new JLabel();
  JLabel clockLabel = new JLabel();
  JLabel eventCountdownLabel = new JLabel();
  GridBagConstraints statusPanelGBC;
  
  public TaskPanel tasksPanel = new TaskPanel(this);
  public EventsPanel eventsPanel = new EventsPanel(this);
  AgendaPanel agendaPanel = new AgendaPanel(this);
  ResourcesPanel filesPanel = new ResourcesPanel();
  ContactsPanel contactPanel = new ContactsPanel(this);
  
  ImageIcon expIcon = new ImageIcon(
      net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/exp_right.png"));
  ImageIcon collIcon = new ImageIcon(
      net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/exp_left.png"));
  ImageIcon bookmarkIcon = new ImageIcon(
      net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/star8.png"));
  boolean expanded = true;

  Note currentNote;
  CalendarDate currentDate;

  boolean calendarIgnoreChange = false;
  boolean dateChangedByCalendar = false;
  boolean changedByHistory = false;
  JPanel cmainPanel = new JPanel();
  public JNCalendarPanel calendar = new JNCalendarPanel();
  JToolBar toggleToolBar = new JToolBar();
  BorderLayout borderLayout5 = new BorderLayout();
  Border border1;
  JButton toggleButton = new JButton();
  public WorkPanel parentPanel = null;

  boolean addedToHistory = false;
  JPanel indicatorsPanel = new JPanel();
  JButton alarmB = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton taskB = new JButton();
  JPanel mainTabsPanel = new JPanel();
  NotesControlPanel notesControlPane = new NotesControlPanel();
  CardLayout cardLayout2 = new CardLayout();

  JTabbedPane tasksTabbedPane = new JTabbedPane();
  JTabbedPane eventsTabbedPane = new JTabbedPane();
  JTabbedPane agendaTabbedPane = new JTabbedPane();
  JTabbedPane resourcesTabbedPane = new JTabbedPane();
  JTabbedPane contactsTabbedPane = new JTabbedPane();

  Border border2;

  String currentPanel;

  Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);

  public DailyItemsPanel(final WorkPanel _parentPanel) {
    try {
      clockObservable.addObserver(this);
      parentPanel = _parentPanel;
      jbInit();
    } catch (Exception ex) {
      new ExceptionDialog(ex);
    }
  }

  final void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white, Color.gray);
    border2 = BorderFactory.createEtchedBorder(Color.white, new Color(161, 161, 161));
    this.setLayout(dailyItemsLayout);
    splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setBorder(null);
    splitPane.setDividerSize(5);
    splitPaneVert.setOrientation(JSplitPane.VERTICAL_SPLIT);
    splitPaneVert.setBorder(null);
    splitPaneVert.setDividerSize(5);
    controlPanel.setLayout(controlPanelLayout);
    // calendar.setMinimumSize(new Dimension(200, 170));
    mainPanel.setLayout(mainPanelLayout);
    mainPanel.setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
    editorsPanel.setLayout(cardLayout1);
    statusPanel.setBackground(Color.black);
    statusPanel.setForeground(Color.white);
    statusPanel.setMinimumSize(new Dimension(14, 48));
    statusPanel.setPreferredSize(new Dimension(14, 48));
    currentDateLabel.setFont(new java.awt.Font("Dialog", 0, 16));
    currentDateLabel.setForeground(Color.white);
    currentDateLabel.setText(CurrentDate.get().getFullDateString());

    clockLabel.setFont(new java.awt.Font("Dialog", 0, 16));
    clockLabel.setForeground(Color.white);
    clockLabel.setText(clockObservable.getTime());

    eventCountdownLabel.setFont(new java.awt.Font("Dialog", 0, 16));
    eventCountdownLabel.setForeground(Color.white);
    eventCountdownLabel.setText(clockObservable.getTime());

    controlPanel.setBackground(new Color(230, 230, 230));
    controlPanel.setBorder(border2);
    controlPanel.setMinimumSize(new Dimension(20, 170));
    controlPanel.setPreferredSize(new Dimension(screenSize.width / 2, 200));
    // controlPanel.setMaximumSize(new Dimension(206, 170));
    // controlPanel.setSize(controlPanel.getMaximumSize());
    calendar.setFont(new java.awt.Font("Dialog", 0, 11));
    calendar.setMinimumSize(new Dimension(0, 168));
    toggleToolBar.setBackground(new Color(215, 225, 250));
    toggleToolBar.setRequestFocusEnabled(false);
    toggleToolBar.setFloatable(false);
    cmainPanel.setLayout(borderLayout5);
    cmainPanel.setBackground(SystemColor.desktop);
    cmainPanel.setMinimumSize(new Dimension(0, 0));
    cmainPanel.setOpaque(false);
    toggleButton.setMaximumSize(new Dimension(32767, 32767));
    toggleButton.setMinimumSize(new Dimension(16, 16));
    toggleButton.setOpaque(false);
    toggleButton.setPreferredSize(new Dimension(16, 16));
    toggleButton.setBorderPainted(false);
    toggleButton.setContentAreaFilled(false);
    toggleButton.setFocusPainted(false);
    toggleButton.setMargin(new Insets(0, 0, 0, 0));
    toggleButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        toggleButton_actionPerformed(e);
      }
    });
    toggleButton.setIcon(collIcon);
    indicatorsPanel.setOpaque(false);
    indicatorsPanel.setLayout(flowLayout1);
    alarmB.setMaximumSize(new Dimension(24, 24));
    alarmB.setOpaque(false);
    alarmB.setPreferredSize(new Dimension(24, 24));
    alarmB.setToolTipText(Local.getString("Active events"));
    alarmB.setBorderPainted(false);
    alarmB.setMargin(new Insets(0, 0, 0, 0));
    alarmB.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        alarmB_actionPerformed(e);
      }
    });
    alarmB.setIcon(
        new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/alarm.png")));
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setVgap(0);
    taskB.setMargin(new Insets(0, 0, 0, 0));
    taskB.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        taskB_actionPerformed(e);
      }
    });
    taskB.setPreferredSize(new Dimension(24, 24));
    taskB.setToolTipText(Local.getString("Active to-do tasks"));
    taskB.setBorderPainted(false);
    taskB.setMaximumSize(new Dimension(24, 24));
    taskB.setOpaque(false);
    taskB.setIcon(
        new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/task.png")));

    /*
     * These are currently empty, find a use for the space? tasksTabbedPane.setSize(new
     * Dimension(0,0)); eventsTabbedPane.setSize(new Dimension(0,0)); agendaTabbedPane.setSize(new
     * Dimension(0,0)); resourcesTabbedPane.setSize(new Dimension(0,0));
     */

    notesControlPane.setFont(new java.awt.Font("Dialog", 1, 10));
    mainTabsPanel.setLayout(cardLayout2);
    this.add(splitPane, BorderLayout.CENTER);

    /* THE FOLLOWING IS THE LAYOUT OF THE VARIOUS PANELS */

    // mainPanel.add(cmainPanel, BorderLayout.SOUTH);
    mainPanel.add(statusPanel, BorderLayout.NORTH);
    mainPanel.add(editorsPanel, BorderLayout.CENTER);

    // Control Panel only contains the calendar
    controlPanel.add(calendar, BorderLayout.CENTER);
    // Toggle button allows the calendar to be minimized to the left
    controlPanel.add(toggleToolBar, BorderLayout.SOUTH);
    toggleToolBar.add(toggleButton, null);

    statusPanelGBC = new GridBagConstraints();
    statusPanelGBC.gridx = 0;
    statusPanelGBC.gridy = 0;
    statusPanelGBC.weightx = 0.5;
    statusPanelGBC.anchor = GridBagConstraints.FIRST_LINE_START;
    statusPanel.add(currentDateLabel, statusPanelGBC);
    statusPanelGBC = new GridBagConstraints();
    statusPanelGBC.gridx = 1;
    statusPanelGBC.gridy = 0;
    statusPanelGBC.weightx = 0.5;
    statusPanelGBC.insets = new Insets(0, 0, 0, 27);
    statusPanelGBC.anchor = GridBagConstraints.FIRST_LINE_END;
    statusPanel.add(indicatorsPanel, statusPanelGBC);
    statusPanelGBC = new GridBagConstraints();
    statusPanelGBC.gridx = 0;
    statusPanelGBC.gridy = 1;
    statusPanelGBC.weightx = 0.5;
    statusPanelGBC.anchor = GridBagConstraints.LAST_LINE_START;
    statusPanel.add(clockLabel, statusPanelGBC);
    statusPanelGBC = new GridBagConstraints();
    statusPanelGBC.gridx = 1;
    statusPanelGBC.gridy = 1;
    statusPanelGBC.weightx = 0.5;
    statusPanelGBC.insets = new Insets(0, 0, 0, 25);
    statusPanelGBC.anchor = GridBagConstraints.LAST_LINE_END;
    statusPanel.add(eventCountdownLabel, statusPanelGBC);

    editorsPanel.add(agendaPanel, "AGENDA");
    editorsPanel.add(eventsPanel, "EVENTS");
    editorsPanel.add(tasksPanel, "TASKS");
    editorsPanel.add(editorPanel, "NOTES");
    editorsPanel.add(filesPanel, "FILES");
    editorsPanel.add(contactPanel, "CONTACTS");

    // function on the right, control (calendar) panel to the left.
    splitPane.add(splitPaneVert, JSplitPane.RIGHT);
    splitPane.add(controlPanel, JSplitPane.LEFT);

    splitPaneVert.add(mainPanel, JSplitPane.TOP);
    splitPaneVert.add(cmainPanel, JSplitPane.BOTTOM);

    splitPane.setDividerLocation((int) controlPanel.getPreferredSize().getWidth());
    splitPaneVert.setDividerLocation((int) mainPanel.getPreferredSize().getHeight());
    // splitPane.setResizeWeight(0.0);

    CurrentDate.addDateListener(new DateListener() {
      @Override
      public void dateChange(final CalendarDate d) {
        currentDateChanged(d);
      }
    });

    CurrentProject.addProjectListener(new ProjectListener() {
      @Override
      public void projectChange(final Project p, final NoteList nl, final TaskList tl,
          final ResourcesList rl) {
        // Util.debug("DailyItemsPanel Project Listener: Project is going to be changed!");
        // Util.debug("current project is " + CurrentProject.get().getTitle());

        currentProjectChanged(p, nl, tl, rl);
      }

      @Override
      public void projectWasChanged() {
        // Util.debug("DailyItemsPanel Project Listener: Project has been changed!");
        // Util.debug("current project is " + CurrentProject.get().getTitle());

        // cannot save note here, changing to new project
        currentNote = CurrentProject.getNoteList().getNoteForDate(CurrentDate.get());
        CurrentNote.set(currentNote, false);
        editorPanel.setDocument(currentNote);

        // // DEBUG
        // if (currentNote != null) {
        // Util.debug("currentNote has been set to " + currentNote.getTitle());
        // }
        // else {
        // Util.debug("currentNote has been set to null");
        // }
        // // DEBUG
      }
    });

    CurrentNote.addNoteListener(new NoteListener() {
      @Override
      public void noteChange(final Note note, final boolean toSaveCurrentNote) {
        currentNoteChanged(note, toSaveCurrentNote);
      }
    });

    calendar.addSelectionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        if (calendarIgnoreChange) {
          return;
        }
        dateChangedByCalendar = true;
        CurrentDate.set(calendar.get());
        dateChangedByCalendar = false;
      }
    });

    AppFrame.addExitListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (editorPanel.isDocumentChanged()) {
          saveNote();
          CurrentStorage.get().storeNoteList(CurrentProject.getNoteList(), CurrentProject.get());
        }
      }
    });

    History.addHistoryListener(new HistoryListener() {
      @Override
      public void historyWasRolledTo(HistoryItem hi) {
        historyChanged(hi);
      }
    });

    EventsScheduler.addListener(new EventNotificationListener() {
      @Override
      public void eventIsOccured(net.sf.memoranda.Event ev) {
        /* DEBUG */
        System.out.println(ev.getTimeString() + " " + ev.getText());
        updateIndicators();
      }

      @Override
      public void eventsChanged() {
        updateIndicators();
      }
    });

    currentDate = CurrentDate.get();
    currentNote = CurrentProject.getNoteList().getNoteForDate(CurrentDate.get());
    CurrentNote.set(currentNote, true);
    editorPanel.setDocument(currentNote);
    History.add(new HistoryItem(CurrentDate.get(), CurrentProject.get()));
    cmainPanel.add(mainTabsPanel, BorderLayout.CENTER);
    mainTabsPanel.add(eventsTabbedPane, "EVENTSTAB");
    mainTabsPanel.add(tasksTabbedPane, "TASKSTAB");
    mainTabsPanel.add(notesControlPane, "NOTESTAB");
    mainTabsPanel.add(agendaTabbedPane, "AGENDATAB");
    mainTabsPanel.add(resourcesTabbedPane, "AGENDATAB");
    mainTabsPanel.add(contactsTabbedPane, "CONTACTSTAB");
    updateIndicators(CurrentDate.get(), CurrentProject.getTaskList());
    mainPanel.setBorder(null);
  }

  void currentDateChanged(CalendarDate newdate) {
    Cursor cur = App.getFrame().getCursor();
    App.getFrame().setCursor(waitCursor);
    if (!changedByHistory) {
      History.add(new HistoryItem(newdate, CurrentProject.get()));
    }
    if (!dateChangedByCalendar) {
      calendarIgnoreChange = true;
      calendar.set(newdate);
      calendarIgnoreChange = false;
    }

    /*
     * if ((currentNote != null) && !changedByHistory && !addedToHistory) History.add(new
     * HistoryItem(currentNote));
     */
    currentNoteChanged(currentNote, true);
    currentNote = CurrentProject.getNoteList().getNoteForDate(newdate);
    CurrentNote.set(currentNote, true);
    currentDate = CurrentDate.get();

    /*
     * addedToHistory = false; if (!changedByHistory) { if (currentNote != null) { History.add(new
     * HistoryItem(currentNote)); addedToHistory = true; } }
     */

    currentDateLabel.setText(newdate.getFullDateString());
    if ((currentNote != null) && (currentNote.isMarked())) {
      currentDateLabel.setIcon(bookmarkIcon);
      currentDateLabel.setHorizontalTextPosition(SwingConstants.LEFT);
    } else {
      currentDateLabel.setIcon(null);
    }

    updateIndicators(newdate, CurrentProject.getTaskList());
    App.getFrame().setCursor(cur);
  }

  void currentNoteChanged(Note note, boolean toSaveCurrentNote) {
    // Util.debug("currentNoteChanged");

    if (editorPanel.isDocumentChanged()) {
      if (toSaveCurrentNote) {
        saveNote();
      }
      notesControlPane.refresh();
    }
    currentNote = note;
    editorPanel.setDocument(currentNote);
    calendar.set(CurrentDate.get());
    editorPanel.editor.requestFocus();
  }

  void currentProjectChanged(Project newprj, NoteList nl, TaskList tl, ResourcesList rl) {
    // Util.debug("currentProjectChanged");

    Cursor cur = App.getFrame().getCursor();
    App.getFrame().setCursor(waitCursor);
    if (!changedByHistory) {
      History.add(new HistoryItem(CurrentDate.get(), newprj));
    }
    if (editorPanel.isDocumentChanged()) {
      saveNote();
    }
    /*
     * if ((currentNote != null) && !changedByHistory && !addedToHistory) History.add(new
     * HistoryItem(currentNote));
     */
    CurrentProject.save();

    /*
     * addedToHistory = false; if (!changedByHistory) { if (currentNote != null) { History.add(new
     * HistoryItem(currentNote)); addedToHistory = true; } }
     */

    updateIndicators(CurrentDate.get(), tl);
    App.getFrame().setCursor(cur);
  }

  void historyChanged(HistoryItem hi) {
    changedByHistory = true;
    CurrentProject.set(hi.getProject());
    CurrentDate.set(hi.getDate());
    changedByHistory = false;
  }

  public void saveNote() {
    if (currentNote == null) {
      currentNote = CurrentProject.getNoteList().createNoteForDate(currentDate);
    }
    currentNote.setTitle(editorPanel.titleField.getText());
    currentNote.setId(Util.generateId());
    CurrentStorage.get().storeNote(currentNote, editorPanel.getDocument());
    /* DEBUG* System.out.println("Save"); */
  }

  void toggleButton_actionPerformed(ActionEvent e) {
    if (expanded) {
      expanded = false;
      toggleButton.setIcon(expIcon);
      controlPanel.remove(toggleToolBar);
      controlPanel.add(toggleToolBar, BorderLayout.EAST);
      splitPane.setDividerLocation((int) controlPanel.getMinimumSize().getWidth());

    } else {
      expanded = true;
      toggleButton.setIcon(collIcon);
      controlPanel.remove(toggleToolBar);
      controlPanel.add(toggleToolBar, BorderLayout.SOUTH);
      splitPane.setDividerLocation((int) controlPanel.getPreferredSize().getWidth());
    }
  }

  public void updateIndicators(CalendarDate date, TaskList tl) {
    indicatorsPanel.removeAll();
    // if (date.equals(CalendarDate.today())) {
    if (tl.getActiveSubTasks(null, date).size() > 0) {
      indicatorsPanel.add(taskB, null);
    }
    if (EventsScheduler.isEventScheduled()) {
      /*
       * String evlist = ""; for (Iterator it = EventsScheduler .getScheduledEvents().iterator();
       * it.hasNext();) { net.sf.memoranda.Event ev = (net.sf.memoranda.Event)it.next(); evlist +=
       * ev.getTimeString()+" - "+ev.getText()+"\n"; }
       */
      net.sf.memoranda.Event ev = EventsScheduler.getFirstScheduledEvent();
      alarmB.setToolTipText(ev.getTimeString() + " - " + ev.getText());
      indicatorsPanel.add(alarmB, null);
    }
    // }
    indicatorsPanel.updateUI();
  }

  public void updateIndicators() {
    updateIndicators(CurrentDate.get(), CurrentProject.getTaskList());
  }

  public void selectPanel(String pan) {
    if (calendar.jnCalendar.renderer.getTask() != null) {
      calendar.jnCalendar.renderer.setTask(null);
      // calendar.jnCalendar.updateUI();
    }
    if (pan.equals("TASKS") && (tasksPanel.taskTable.getSelectedRow() > -1)) {
      Task t = CurrentProject.getTaskList().getTask(tasksPanel.taskTable.getModel()
          .getValueAt(tasksPanel.taskTable.getSelectedRow(), TaskTable.TASK_ID).toString());
      calendar.jnCalendar.renderer.setTask(t);
      // calendar.jnCalendar.updateUI();
    }
    boolean isAg = pan.equals("AGENDA");
    agendaPanel.setActive(isAg);
    if (isAg) {
      agendaPanel.refresh(CurrentDate.get());
    }
    cardLayout1.show(editorsPanel, pan);
    cardLayout2.show(mainTabsPanel, pan + "TAB");
    calendar.jnCalendar.updateUI();
    currentPanel = pan;
  }

  public String getCurrentPanel() {
    return currentPanel;
  }

  void taskB_actionPerformed(ActionEvent e) {
    parentPanel.tasksB_actionPerformed(null);
  }

  void alarmB_actionPerformed(ActionEvent e) {
    parentPanel.eventsB_actionPerformed(null);
  }

  public void updateLanguage() {
    taskB.setToolTipText(Local.getString("Active to-do tasks"));
    alarmB.setToolTipText(Local.getString("Active events"));

    tasksPanel.updateLanguage();
    calendar.updateLanguage();
    eventsPanel.updateLanguage();
    editorPanel.updateLanguage();
    notesControlPane.updateLanguage();
    notesControlPane.searchPanel.updateLanguage();

    filesPanel.updateLanguage(); // branches complete

    this.repaint();
  }

  @Override
  public void update(Observable o, Object arg) {
    if (clockObservable == o) {
      try {
        eventCountdownLabel.setText(getCountdownLabelText((long) arg));
      } catch (ClassCastException e) {
        clockLabel.setText(Local.getString("Current time is") + ": " + (String) arg);
      }
    }
  }

  public String getCountdownLabelText(long arg) {
    String result = ""; 
    if (arg == -2) {
      return Local.getString("Update events for current Memoranda version");
    }
    
    if (arg == -1) {
      return Local.getString("No Future Events Scheduled Within One Month");
    }
    
    long secondsRemaining = arg;
	  
	  int days = (int) (secondsRemaining / (24 * 3600));
	  secondsRemaining -= days * 24 * 3600;
	  
	  int hours = (int) (secondsRemaining / 3600);
    secondsRemaining -= hours * 3600;
    
    int minutes = (int) (secondsRemaining / 60);
    secondsRemaining -= minutes * 60;

    result += Local.getString("Next event in") + " ";
	  result += days + "d:";
	  result += hours + "h:";
	  result += minutes +"m:";
	  result += secondsRemaining + "s";
    
    return result;
	}
}