/*
  File: Calculator.java
  Author: Casey Froke
  Date: 2/6/2016
  
  Description: The calculator.java class is a stand-alone calculator to allow the user to perform simple on the spot calculations.
 */


package net.sf.memoranda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sf.memoranda.util.Local;


/**
Class: Calculator

Description: Main class for the calculator
*/
public class Calculator extends JFrame implements ActionListener {
	/**
	* Serialized 
	*/
	private static final long serialVersionUID = 1L;
	// Variables
	final int MAX_INPUT_LENGTH = 20;
	final int INPUT_MODE = 0;
	final int RESULT_MODE = 1;
	final int ERROR_MODE = 2;
	int displayMode;
	boolean clearOnNextDigit, percent;
	double lastNumber;
	String lastOperator;
	private JLabel jlOutput;
	private JButton jbButtons[];
	private JPanel jpMaster, jpControl;

	// Constructor
	public Calculator() {

		// Layout Manager
		setBackground(Color.gray);
		jpMaster = new JPanel();
		jlOutput = new JLabel("0");
		jlOutput.setHorizontalTextPosition(JLabel.RIGHT);
		jlOutput.setBackground(Color.white);
		jlOutput.setOpaque(true);

		// Frame components
		getContentPane().add(jlOutput, BorderLayout.NORTH);
		jbButtons = new JButton[27];

		// Container for JButton
		JPanel jpButtons = new JPanel();

		// Numeric Buttons
		for (int i = 0; i <= 9; i++) {
			// Label each button by index
			jbButtons[i] = new JButton(String.valueOf(i));
		}

		// Operator Buttons
		jbButtons[10] = new JButton("±");
		jbButtons[11] = new JButton(".");
		jbButtons[12] = new JButton("=");
		jbButtons[13] = new JButton("/");
		jbButtons[14] = new JButton("*");
		jbButtons[15] = new JButton("-");
		jbButtons[16] = new JButton("+");
		jbButtons[17] = new JButton("√");
		jbButtons[18] = new JButton("%");
		jbButtons[19] = new JButton("1/x");

		// Function Buttons
		jbButtons[20] = new JButton("CM");
		jbButtons[21] = new JButton("RM");
		jbButtons[22] = new JButton("MS");
		jbButtons[23] = new JButton("M+");
		jbButtons[24] = new JButton(Local.getString("Backspace"));
		jbButtons[25] = new JButton("CE");
		jbButtons[26] = new JButton("C");

		// Calculator Layout
		jpControl = new JPanel();
		jpControl.setLayout(new GridLayout(1, 3, 4, 4));
		jpControl.add(jbButtons[24]);
		jpControl.add(jbButtons[25]);
		jpControl.add(jbButtons[26]);

		// Text color
		for (int i = 0; i < jbButtons.length; i++) {
			if (i < 10){
				jbButtons[i].setForeground(Color.blue);
			}else
				jbButtons[i].setForeground(Color.red);
		}

		// 4 by 6 grid
		jpButtons.setLayout(new GridLayout(4, 5, 4, 4));

		// First Row
		//jpButtons.add(jbButtons[20]); // MC button
		for (int i = 7; i <= 9; i++) // 7, 8, 9 buttons
		{
			jpButtons.add(jbButtons[i]);
		}
		jpButtons.add(jbButtons[13]); // Division button
		jpButtons.add(jbButtons[17]); // Square Root button

		// Second Row
		//jpButtons.add(jbButtons[21]); // MR button
		for (int i = 4; i <= 6; i++) {
			jpButtons.add(jbButtons[i]); // 4, 5, 6 buttons
		}
		jpButtons.add(jbButtons[14]); // Multiplication button
		jpButtons.add(jbButtons[18]); // Percentage button

		// Third Row
		//jpButtons.add(jbButtons[22]); // MS button
		for (int i = 1; i <= 3; i++) {
			jpButtons.add(jbButtons[i]); // 1, 2, 3 buttons
		}
		jpButtons.add(jbButtons[15]); // Subtraction button
		jpButtons.add(jbButtons[19]); // Reciprocal button

		// Fourth Row
		//jpButtons.add(jbButtons[23]); // M+ button
		jpButtons.add(jbButtons[0]); // 0 button
		jpButtons.add(jbButtons[10]); // Positive/Negative button
		jpButtons.add(jbButtons[11]); // Decimal button
		jpButtons.add(jbButtons[16]); // Addition button
		jpButtons.add(jbButtons[12]); // Equals button

		jpMaster.setLayout(new BorderLayout());
		jpMaster.add(jpControl, BorderLayout.EAST);
		jpMaster.add(jpButtons, BorderLayout.SOUTH);

		// Components to Frame
		getContentPane().add(jpMaster, BorderLayout.SOUTH);
		requestFocus();

		// Activate ActionListener
		for (int i = 0; i < jbButtons.length; i++) {
			jbButtons[i].addActionListener(this);
		}

		clearAll();
		
	}
	
	void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

	// Actions
	/*
	Method: actionPerformed
	@param: functions on the calculator
	@return: double
	*/
	public void actionPerformed(ActionEvent e) {
		double result = 0;
		String buttonText = ((JButton) e.getSource()).getText();
		int numberButton = 11;
		
		try {
		  numberButton = Integer.valueOf(buttonText);
		} catch (NumberFormatException ex) {
		  //Non-number caught, do nothing
		}
		
		if (numberButton < 10){
		  addDigitToDisplay(Integer.valueOf(buttonText));
		  return;
		} else if (buttonText.equals("±")) {
		  processSignChange();
		} else if (buttonText.equals(".")) {
		  addDecimalPoint();
		} else if (buttonText.equals("=")) {
		  processEquals();
    } else if (buttonText.equals("√")) {
      if (displayMode != ERROR_MODE) {
        try {
          if (getDisplayString().indexOf("-") == 0)
            displayError("Invalid input for function.");

          result = Math.sqrt(getNumberInDisplay());
          displayResult(result);
        } catch (Exception ex) {
          displayError("Invalid input for function.");
          displayMode = ERROR_MODE;
        }
      }
    } else if (buttonText.equals("%")) {
      if (displayMode != ERROR_MODE) {
        try {
          result = getNumberInDisplay() / 100;
          displayResult(result);
        } catch (Exception ex) {
          displayError("Invalid input for function.");
          displayMode = ERROR_MODE;
        }
      }
    } else if (buttonText.equals("1/x")) {
      if (displayMode != ERROR_MODE) {
        try {
          if (getNumberInDisplay() == 0)
            displayError("Cannot divide by zero.");
          result = 1 / getNumberInDisplay();
          displayResult(result);
        } catch (Exception ex) {
          displayError("Cannot divide by zero.");
          displayMode = ERROR_MODE;
        }
      }
    } /*else if (buttonText.equals("CM")) {
      clearMemory();
    } else if (buttonText.equals("RM")) {
      recallMemory();
    } else if (buttonText.equals("MS")) {
      storeInMemory();
    } else if (buttonText.equals("M+")) {
      addToMemory();
    }*/ else if (buttonText.equals(Local.getString("Backspace"))) {
      if (displayMode != ERROR_MODE) {
        setDisplayString(getDisplayString().substring(0, getDisplayString().length() - 1));
        if (getDisplayString().length() < 1)
          setDisplayString("0");
      }
    } else if (buttonText.equals("CE")) {
      clearExisting();
    } else if (buttonText.equals("C")) {
      clearAll();
    } else {
      processOperator(buttonText);
    }
	}
// All memory functions will be added in a future release
	private void addToMemory() {
		// need to figure out how to code

	}

	private void storeInMemory() {
		// need to figure out how to code
	}

	private void recallMemory() {
		// need to figure out how to code
	}

	private void clearMemory() {
		// need to figure out how to code
	}

	void setDisplayString(String s) {
		jlOutput.setText(s);
	}

	String getDisplayString() {
		return jlOutput.getText();
	}

	void addDigitToDisplay(int digit) {
		if (clearOnNextDigit) {
			setDisplayString("");
		}
		String inputString = getDisplayString();
		if (inputString.indexOf("0") == 0) {
			inputString = inputString.substring(1);
		}
		if ((!inputString.equals("0") || digit > 0) && inputString.length() < MAX_INPUT_LENGTH) {
			setDisplayString(inputString + digit);
		}
		displayMode = INPUT_MODE;
		clearOnNextDigit = false;
	}

	void addDecimalPoint() {
		displayMode = INPUT_MODE;
		if (clearOnNextDigit) {
			setDisplayString("");
		}
		String inputString = getDisplayString();
		if (inputString.indexOf(".") < 0) {
			setDisplayString(inputString + ".");
		}
	}

	void processSignChange() {
		if (displayMode == INPUT_MODE) {
			String input = getDisplayString();
			if (input.length() > 0 && !input.equals("0")) {
				if (input.indexOf("-") == 0) {
					setDisplayString(input.substring(1));
				} else {
					setDisplayString("-" + input);
				}
			}
		}
	}

	void clearAll() {
		setDisplayString("0");
		lastOperator = "0";
		lastNumber = 0;
		displayMode = INPUT_MODE;
		clearOnNextDigit = true;
	}

	void clearExisting() {
		setDisplayString("0");
		clearOnNextDigit = true;
		displayMode = INPUT_MODE;
	}

	double getNumberInDisplay() {
		String input = jlOutput.getText();
		return Double.parseDouble(input);
	}

	void processOperator(String op) {
		if (displayMode != ERROR_MODE) {
			double numberInDisplay = getNumberInDisplay();
			if (!lastOperator.equals("0")) {
				try {
					double result = processLastOperator();
					displayResult(result);
					lastNumber = result;
				} catch (DivideByZeroException e) {
					displayError("Cannot divide by sero.");
				}
			} else {
				lastNumber = numberInDisplay;
			}
			clearOnNextDigit = true;
			lastOperator = op;
		}
	}

	void processEquals() {
		double result = 0;
		if (displayMode != ERROR_MODE) {
			try {
				result = processLastOperator();
				displayResult(result);
			} catch (DivideByZeroException e) {
				displayError("Cannot divide by sero.");
			}
			lastOperator = "0";
		}
	}

	double processLastOperator() throws DivideByZeroException {
		double result = 0;
		double numberInDisplay = getNumberInDisplay();
		if (lastOperator.equals("/")) {
			if (numberInDisplay == 0) {
				throw (new DivideByZeroException());
			}
			result = lastNumber / numberInDisplay;
		}
		if (lastOperator.equals("*")) {
			result = lastNumber * numberInDisplay;
		}
		if (lastOperator.equals("-")) {
			result = lastNumber - numberInDisplay;
		}
		if (lastOperator.equals("+")) {
			result = lastNumber + numberInDisplay;
		}
		return result;
	}

	void displayResult(double result) {
		setDisplayString(Double.toString(result));
		lastNumber = result;
		displayMode = RESULT_MODE;
		clearOnNextDigit = true;
	}

	void displayError(String errorMessage) {
		setDisplayString(errorMessage);
		lastNumber = 0;
		displayMode = ERROR_MODE;
		clearOnNextDigit = true;
	}

	public static void openCalc() {
		Calculator calc = new Calculator();
		calc.setTitle(Local.getString("Calculator"));
		calc.setSize(440, 400);
		calc.pack();
		calc.setLocation(400, 300);
		calc.setVisible(true);
		calc.setResizable(false);
	}
}

class DivideByZeroException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DivideByZeroException() {
		super();
	}

	public DivideByZeroException(String s) {
		super(s);
	}
}
