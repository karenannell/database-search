/*
*
* DATABSE SEARCH (MSGF, XTandem, Comet)
*
*/

GUI DATABASE = new GUI();
new DATABASE.setVisible(true);

class DATABASE{
import java.util.*;
import java.util.Scanner;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.BufferedWriter;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

	private class GetInput extends JPanel {
	private static final long serialVersionUID = -6211781418045572320L;
	private static final String APP_TITLE = "Get Input";
	private static final int APP_WIDTH = 1;
	private static final int APP_HEIGHT = 1;
	private static JFrame frame;

/* How the buttons should be laid out (number of buttons on each row and column) */
	private JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
	private JPanel buttonLayer1 = new JPanel(new GridLayout(1,5));
	private JPanel buttonLayer2 = new JPanel(new GridLayout(1,2));


/* Create the main labels */
	private JLabel databaseSearchLabel_Text = new JLabel("              DATABASE SEARCH");

/* An array containing the labels above */
	private JLabel[] mainLabels =
				{  databaseSearchLabel_Text};

/* 
 * Initialization of the JTextFields. Sets the default text that appears at the start of the
 * program in the whitespace 
 */
private JTextField msgfplusJarDir_Text = new JTextField("C:\\Users\\jmeyer\\Downloads\\" +
		                                                   "MSGFPlus.20160629\\msgfplus.jar");
private JTextField fastaDir_Text = new JTextField("EX:      C:\\...\\" + 
		                                   "20150427_mouse_sprot.cc.fasta");
private JTextField ppm_Text = new JTextField("15ppm");
private JTextField enzyme_Text = new JTextField("1");
private JTextField modsDir_Text = new JTextField("EX:      C:\\...\\Mods_acetyl.txt");
private JTextField ntt_Text = new JTextField("2");
private JTextField ti_Text = new JTextField("0,0");
private JTextField tandemDir_Text = new JTextField("C:\\Inetpub\\tpp-bin\\tandem.exe");
private JTextField tandem2xmlDir_Text = new JTextField("C:\\Inetpub\\tpp-bin\\Tandem2XML.exe");
private JTextField paramsXMLDir_Text = new JTextField("C:\\Users\\Sushanth\\" + 
		                                                "xTandem_MAIN_params.xml");
private JTextField taxonomyPath_Text = new JTextField("C:\\Users\\Sushanth\\taxonomy.xml");
private JTextField cometDir_Text = new JTextField("C:\\Inetpub\\tpp-bin\\comet.exe");
private JTextField cometParamsDir_Text = new JTextField("EX:      C:\\...\\comet.Kac.params");
private JTextField cometFastaDir_Text = new JTextField("EX:      C:\\...\\" + 
		                                        "2015.mouse.cc.iRT_DECOY.fasta");

/* Array containing all textfields declared above */
private JTextField[] textFields = 
				{ 
				  msgfplusJarDir_Text, fastaDir_Text, ppm_Text, enzyme_Text,
				  modsDir_Text, ntt_Text, ti_Text, paramsXMLDir_Text, tandemDir_Text, tandem2xmlDir_Text, 
				  taxonomyPath_Text, cometDir_Text, cometParamsDir_Text, cometFastaDir_Text
				};

/* Width and height for each JTextField */
private int textFieldWidth = 250;
private int textFieldHeight = 20;
	
/* An array containing all the JCheckBoxes declared above */
private JCheckBox[] checkBoxFields = 
					{ 
					 MSGFSearch_Checkbox, XTandem_Checkbox,
					  CometSearch_Checkbox
					};

/* Initialization of boolean values for each checkbox above */
private boolean runMSGFSearch = false;
private boolean runXTandem = false;
private boolean runCometSearch = false;

/* To prevent multiple save/load user parameters and mapDIA input from opening */
private boolean saveParamsClosed = true;
private boolean loadParamsClosed = true;
private boolean mapDIAClosed = true;

/* Options for checkIfFilesExist() method */
private int msgfSearchOpt = 1;
private int xTandemSearchOpt = 2;
private int cometSearchOpt = 3;

private int runOnce = 0;

private JButton clearButton = new JButton("Clear");
private JButton defaultButton = new JButton("Default");
private JButton doneButton = new JButton("Finished");
private JButton exitButton = new JButton("Exit");
private JButton browseButton = new JButton("Browse");
private JButton loadButton = new JButton("Load");
private JButton saveButton = new JButton("Save");

/* A string buffer to hold all the data in the JTextFields */
private StringBuffer fieldData = new StringBuffer();

/* A string buffer that contains all the errors that were found to report to the user */
private StringBuffer errors = new StringBuffer();

/* A string buffer that will contain the data in the taxonomy file */
private StringBuffer dataInTaxonomyFile = new StringBuffer();

/* A string buffer that will contain the checkbox matrix as a list of 1's and 0's */
private StringBuffer checkboxStringBuf = new StringBuffer();

/* A StringBuffer that will contain user specified parameters to load */
private StringBuffer parametersStringBuf = new StringBuffer();


/* the appWidth and appHeight */
private int appWidth;
private int appHeight;

/* A component Map that will contain all the elements being added to the panel */
private Map<String, GridItem> componentMap;

/* Using a GridBagLayout */
private GridBagLayout layout;
private GridBagConstraints constraints;


/*
 *
 *
 */
private static class GridItem {
    private JComponent component;
    private boolean isExportable;
    private int xPos;
    private int yPos;
    private int colSpan;
    private int rowSpan;



	/*
	 *
	 *
	 */
    public GridItem(JComponent component, boolean isExportable, int xPos, int yPos) {
        this(component, isExportable, xPos, yPos, 1, 1);
    }

	/*
	 *
	 *
	 */
    public GridItem(JComponent component, boolean isExportable, int xPos, int yPos,
        				int colSpan, int rowSpan) {
        this.component = component;
        this.isExportable = isExportable;
        this.xPos = xPos;
        this.yPos = yPos;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
        }
		
    }

/*
 *
 *
 */
public GetInput(int width, int height) {
    super();

    this.appWidth = width;
    this.appHeight = height;

    this.init();
    this.createChildren();
    }



/*
 *
 *
 */
protected void init() {
    /* Set the default information for the applet */
    this.constraints = new GridBagConstraints();
    this.layout = new GridBagLayout();
    this.componentMap = new LinkedHashMap<String, GridItem>();
    this.setLayout(this.layout);
    this.constraints.ipadx = 5;
    this.constraints.ipady = 5;
    this.constraints.insets = new Insets(1, 4, 1, 4);
    this.constraints.anchor = GridBagConstraints.LAST_LINE_START;
    this.constraints.fill = GridBagConstraints.HORIZONTAL;


    /* Create and add all the components to the frame */
        addAllItems();



	/*
	 *
	 *
	 */
    (doneButton).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            /* Create a boolean to check if any errors occur when cheching each input */
            int justReturn = 0;
			int errorOccurred = 1;
			int returnVal;

			returnVal = checkSelectedSections();

			/* Just return. Errors were already displayed in checkSelectedSections() */
			if( returnVal == justReturn ) {
				return;
			}
				
			/* Display the errors in a pop-up message dialog if any were encountered */
			if( returnVal == errorOccurred ) {
				JOptionPane.showMessageDialog(null, errors.toString());
			}
			/* 
			 * Else, display the message below if the Prophet or XTandem checkboxes were
			 * checked, grab the field data, write it to the input.parameters file,
			 * and close the program
			 */
			else {
				if(runProphet || runXTandem) {
					JOptionPane.showMessageDialog(null, "Errors may occur if incorrect " +
							                       "modification masses\nor cleavage sites " +
							                              "were set in the _params.xml file");
					}

				grabFieldData();
				writeToFile();
				System.exit(0);
			}
				
			/* End of ActionPerformed method */
            }
        });;

    /*
	 * Sets the information below into the JTextBoxes when the Default JButton is clicked
	 *
	 */
    (defaultButton).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            /* 
             * An array containing the name of the JTextBox, two colons, and the information
             * to set when the Default JButton is clicked
             */
            String[] lines = {
					"fastaDirText::EX:      C:\\...\\20150427_mouse_sprot.cc.fasta",
					"ppmText::15ppm",
					"enzymeText::1",
					"nttText::2",
					"tiText::0,0",
					//"mapDIALabelsText::EX:    2wk_chow_ctl, 2wk_chow_fruc, 2wf_chow_gluc",
					"modsDirText::EX:      C:\\...\\Mods_acetyl.txt",
					"paramsXMLDirText::C:\\Users\\Sushanth\\xTandem_MAIN_params.xml",
					"taxonomyPathText::C:\\Users\\Sushanth\\taxonomy.xml",
					"tandemDirText::C:\\Inetpub\\tpp-bin\\tandem.exe",
					"tandem2xmlDirText::C:\\Inetpub\\tpp-bin\\Tandem2XML.exe",
					//"xInteractDirText::C:\\Inetpub\\tpp-bin\\xinteract.exe",
					//"InterProphetParserDirText::C:\\Inetpub\\tpp-bin\\InterProphetParser.exe",
					"msgfplusJarDirText::C:\\Users\\jmeyer\\Downloads\\MSGFPlus.20160629\\" +
					                                                          "msgfplus.jar",
					"cometDirText::C:\\Inetpub\\tpp-bin\\comet.exe",
					"cometFastaDirText::EX:      C:\\...\\2015.mouse.cc.iRT_DECOY.fasta",
					"cometParamsDirText::EX:      C:\\...\\comet.Kac.params",
					"mzxmlToMgfParamsText::C:\\DIA-Umpire_v2_0\\Kac.se_params",
					//"skylineRunnerExePathText::C:\\addadefaultpath",
					//"skylineReportNameText::2016_0710_Nate_mapDIA",
					//"skylineFastaDirText::C:\\addadefaultpath",
					//"skylineTemplateDocText::EX:    C:\\default_empty.sky",
					//"skylineXMLFileText::EX:    C:\\ptmProphet-output-file.ptm.pep.xml",
					//"ptmProphetExeText::C:\\Inetpub\\tpp-bin\\PTMProphetParser.exe",
					//"ptmProphetParserMassesText::K:42.010565,M:15.9949,nQ:-17.026549",
					//"ptmProphetParserMZTOLText::0.055",
					//"ptmProphetParserMINPROBText::0.9"
                };


				/* Calls setFieldData to put the default information in the JTextFields */
                setFieldData(lines);

            /* End of ActionPerformed method */
            }
        });;


    	/*
		 *
		 *
		 */
        (clearButton).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	/* Calls clearFieldData to clear all the field data */
                clearFieldData();

            }
    });;

        /*
         *
         *
         */
        (browseButton).addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		//System.out.println(lastTextFieldSelected);
        		JFileChooser fileChooser = new JFileChooser("Computer");

        		// For Directory
        		//fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
 
        		// For File
        		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        		fileChooser.setAcceptAllFileFilterUsed(false);
        		int rVal = fileChooser.showOpenDialog(null);

        		if (rVal == JFileChooser.APPROVE_OPTION) {
        			lastTextFieldSelected.setText(fileChooser.getSelectedFile().toString());
        		}
        	}
        });

		/*
         *
         *
         */
        (loadButton).addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if(loadParamsClosed) {
        			createLoadUserParams();
        		}
        	}
        });



        /*
         *
         *
         */
        (saveButton).addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if(saveParamsClosed) {
        			createSaveUserParams();
        		}
        	}
        });



        /*
		 *
		 *
		 */
		(exitButton).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	/* Set all the JCheckBox booleans to false */
				runDiaUmpire = false;
				runMSGFSearch = false;
				runXTandem = false;
				runProphet = false;
				runCometSearch = false;
				runSkyline = false;
				runPTMProphetParser = false;
				runMapDia = false;

				/* Grab the data in the JTextFields */
				grabFieldData();

				/* Write the information grabbed to the input.parameters file */
				writeToFile();

				/* Exit the program */
                System.exit(0);
            }
        });;


		/*
		 *
		 *
		 */
		((JCheckBox) componentMap.get("MSGFSearchCheckbox").component).addItemListener(
		                                                           new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            	/* 
            	 * If the MSGFSearchCheckbox is checked, set the respective boolean to true and
            	 * enable the respective JTextFields
            	 */
                if(e.getStateChange() == ItemEvent.SELECTED) {
					runMSGFSearch = true;
					msgfplusJarDir_Text.setEnabled(true);
					fastaDir_Text.setEnabled(true);
					ppm_Text.setEnabled(true);
					enzyme_Text.setEnabled(true);
					modsDir_Text.setEnabled(true);
					ntt_Text.setEnabled(true);
					ti_Text.setEnabled(true);
				}

				/* 
				 * If the MSGFSearchCheckbox is unchecked, set the respective boolean to false and
				 * disable the respective JTextFields
				 */
				else {
					runMSGFSearch = false;
					msgfplusJarDir_Text.setEnabled(false);
					fastaDir_Text.setEnabled(false);
					ppm_Text.setEnabled(false);
					enzyme_Text.setEnabled(false);
					modsDir_Text.setEnabled(false);
					ntt_Text.setEnabled(false);
					ti_Text.setEnabled(false);
				}

			/* End of itemStateChanged method */
            }
        });;
		


		/*
		 *
		 *
		 */
		((JCheckBox) componentMap.get("XTandemCheckbox").component).addItemListener(
		                                                        new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            	/* 
            	 * If the XTandemCheckbox is checked, set the respective boolean to true and
            	 * enable the respective JTextFields
            	 */
                if(e.getStateChange() == ItemEvent.SELECTED) {
					runXTandem = true;
					tandemDir_Text.setEnabled(true);
					tandem2xmlDir_Text.setEnabled(true);
					paramsXMLDir_Text.setEnabled(true);
					taxonomyPath_Text.setEnabled(true);
				}

				/* 
				 * If the XTandemCheckbox is unchecked, set the respective boolean to false and
				 * disable the respective JTextFields
				 */
				else {
					runXTandem = false;
					tandemDir_Text.setEnabled(false);
					tandem2xmlDir_Text.setEnabled(false);
					paramsXMLDir_Text.setEnabled(false);
					taxonomyPath_Text.setEnabled(false);
				}

			/* End of itemStateChanged method */
            }
        });;
		

		/*
		 *
		 *
		 */
		((JCheckBox) componentMap.get("CometSearchCheckbox").component).addItemListener(
			                                                        new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            	/* 
            	 * If the CometSearchCheckbox is checked, set the respective boolean to true and
            	 * enable the respective JTextFields
            	 */
                if(e.getStateChange() == ItemEvent.SELECTED) {
					runCometSearch = true;
					cometDir_Text.setEnabled(true);
					cometParamsDir_Text.setEnabled(true);
					cometFastaDir_Text.setEnabled(true);
				}

				/* 
				 * If the CometSearchCheckbox is unchecked, set the respective boolean to false and
				 * disable the respective JTextFields
				 */
				else {
					runCometSearch = false;
					cometDir_Text.setEnabled(false);
					cometParamsDir_Text.setEnabled(false);
					cometFastaDir_Text.setEnabled(false);
				}

			/* End of itemStateChanged method */
            }
        });;
		

		/*
		 * If the user closes the program by pressing the 'X' on the top right, set the JCheckBox
		 * booleans to false, grab the data in the whitespaces, and write it to a input.parameters
		 * file
		 */
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				runDiaUmpire = false;
				runMSGFSearch = false;
				runXTandem = false;
				runProphet = false;
				runCometSearch = false;
				grabFieldData();
				writeToFile();
			}
		});
	
	/* End of init() method */
    }



	/*
     * Creates and adds all the components to the frame
     *
     */
    public void addAllItems() {
    	int verticalPosition = 0;
        int horizontalPosition = 0;
		
		/* 
		 * Make all the JTextFields the same size and disable them. Add a FocusListener to all the
		 * JTextField to keep track of which one was the last one to be clicked on
		 */
		for( JTextField element : textFields ) {
			element.setPreferredSize( new Dimension(textFieldWidth, textFieldHeight) );
			element.setEnabled(false);

			(element).addFocusListener(new FocusListener() {
            	@Override
            	public void focusGained(FocusEvent e) {
            		//System.out.println(e.getComponent().getClass().getName() + " focus gained");
            		lastTextFieldSelected = (JTextField) e.getComponent();
            	}

            	@Override
            	public void focusLost(FocusEvent e) {
            		//System.out.println("inputDir_Text focus lost");
            	}
        	});;
		}
		
		/* Place the text on the left side of the JCheckBoxes */
		for( JCheckBox element : checkBoxFields ) {
			element.setHorizontalTextPosition(SwingConstants.LEFT);
		}
		
		/* Make all the main labels of the font ROMAN_BASELINE, bold, and size 24 */
		for( JLabel element : mainLabels ) {
			element.setFont(new Font("ROMAN_BASELINE", Font.BOLD, 24));
		}

/* Place the labels in their appropriate locations */
		componentMap.put("databaseSearchLabel", new GridItem(databaseSearchLabel_Text, false,
			                            3, 0, 2, 1));

/* Create the MSGF search info section */
		verticalPosition = 1;
		horizontalPosition = 3;
		componentMap.put("MSGFSearch_input", new GridItem(new JLabel("MSGFSearch Info"), false,
			                                    horizontalPosition+1, verticalPosition, 1, 1));
		componentMap.put("msgfplusJarDir", new GridItem(new JLabel("<html>Path to " +
			           "msgfplus.jar</html>"), false, horizontalPosition, ++verticalPosition));
        componentMap.put("msgfplusJarDirText", new GridItem(msgfplusJarDir_Text, true,
        	                           horizontalPosition+1, verticalPosition, 1, 1));
        componentMap.put("fastaDir", new GridItem(new JLabel("<html>Path to " +
        	          ".fasta file</html>"), false, horizontalPosition, ++verticalPosition));
        componentMap.put("fastaDirText", new GridItem(fastaDir_Text, true,
        	               horizontalPosition+1, verticalPosition, 1, 1));
		componentMap.put("ppm", new GridItem(new JLabel("Enter ##ppm"), false,
			                       horizontalPosition, ++verticalPosition));
        componentMap.put("ppmText", new GridItem(ppm_Text, true,
        	     horizontalPosition+1, verticalPosition, 1, 1));
		componentMap.put("enzyme", new GridItem(new JLabel("Enter enzyme (-e)"), false,
			                                  horizontalPosition, ++verticalPosition));
        componentMap.put("enzymeText", new GridItem(enzyme_Text, true,
        	           horizontalPosition+1, verticalPosition, 1, 1));
		componentMap.put("modsDir", new GridItem(new JLabel("<html>Path to " +
			      "mods .txt file</html>"), false, horizontalPosition, ++verticalPosition));
        componentMap.put("modsDirText", new GridItem(modsDir_Text, true,
        	             horizontalPosition+1, verticalPosition, 1, 1));
        componentMap.put("ntt", new GridItem(new JLabel("<html>Enter ntt (-ntt)</html>"), false,
        											   horizontalPosition, ++verticalPosition));
        componentMap.put("nttText", new GridItem(ntt_Text, true, horizontalPosition+1,
        													 verticalPosition, 1, 1));
        componentMap.put("ti", new GridItem(new JLabel("<html>Enter ti (-ti)</html>"), false,
        											   horizontalPosition, ++verticalPosition));
        componentMap.put("tiText", new GridItem(ti_Text, true, horizontalPosition+1,
        													 verticalPosition, 1, 1));

/* Create the XTandem search info section */
		verticalPosition = 9;
		horizontalPosition = 3;
		componentMap.put("XTandem_input", new GridItem(new JLabel("XTandem Info"), false,
			                              horizontalPosition+1, verticalPosition, 1, 1));
        componentMap.put("tandemDir", new GridItem(new JLabel("<html>Path to " + 
        	                "tandem.exe</html>"), false, horizontalPosition, ++verticalPosition));
        componentMap.put("tandemDirText", new GridItem(tandemDir_Text, true,
        	                 horizontalPosition+1, verticalPosition, 1, 1));
        componentMap.put("tandem2xmlDir", new GridItem(new JLabel("<html>Path to " +
        	        "tandem2xml.exe</html>"), false, horizontalPosition, ++verticalPosition));
        componentMap.put("tandem2xmlDirText", new GridItem(tandem2xmlDir_Text, true,
        	                         horizontalPosition+1, verticalPosition, 1, 1));
        componentMap.put("paramsXMLDir", new GridItem(new JLabel("<html>Path to " +
        	                                           "_params.xml file</html>"), false,
                                                        horizontalPosition, ++verticalPosition));
        componentMap.put("paramsXMLDirText", new GridItem(paramsXMLDir_Text, true,
        	                       horizontalPosition+1, verticalPosition, 1, 1));
        componentMap.put("taxonomyPath", new GridItem(new JLabel("<html>Path to " + 
                                                    "the taxonomy.txt<br>(or .xml)</html>"), false,
                                                        horizontalPosition, ++verticalPosition));
        componentMap.put("taxonomyPathText", new GridItem(taxonomyPath_Text, true,
        	                       horizontalPosition+1, verticalPosition, 1, 1));


		/* Create the Comet search info section */
		verticalPosition = 14;
		horizontalPosition = 3;
		componentMap.put("Comet_input", new GridItem(new JLabel("Comet Search Info"), false,
			                                 horizontalPosition+1, verticalPosition, 1, 1));
		componentMap.put("cometFastaDir", new GridItem(new JLabel("<html>Path to " +
			                                           ".fasta file</html>"), false,
		                                                 horizontalPosition, ++verticalPosition));
        componentMap.put("cometFastaDirText", new GridItem(cometFastaDir_Text, true,
        	                         horizontalPosition+1, verticalPosition, 1, 1));
		componentMap.put("cometDir", new GridItem(new JLabel("<html>Path to " +
			                "comet.exe</html>"), false, horizontalPosition, ++verticalPosition));
        componentMap.put("cometDirText", new GridItem(cometDir_Text, true,
        	               horizontalPosition+1, verticalPosition, 1, 1));
		componentMap.put("cometParamsDir", new GridItem(new JLabel("<html>Path to " +
			                                           ".params file</html>"), false,
		                                                  horizontalPosition, ++verticalPosition));
        componentMap.put("cometParamsDirText", new GridItem(cometParamsDir_Text, true,
        	                           horizontalPosition+1, verticalPosition, 1, 1));

        /* Check boxes for each section */
		componentMap.put("MSGFSearchCheckbox", new GridItem(MSGFSearch_Checkbox, false, 3, 1));
		componentMap.put("XTandemCheckbox", new GridItem(XTandem_Checkbox, false, 3, 9));
		componentMap.put("CometSearchCheckbox", new GridItem(CometSearch_Checkbox, false, 3, 14));

		buttonLayer1.add(clearButton);
		buttonLayer1.add(defaultButton);
		buttonLayer1.add(browseButton);
		buttonLayer1.add(loadButton);
		buttonLayer1.add(saveButton);
		buttonLayer2.add(exitButton);
		buttonLayer2.add(doneButton);

		buttonPanel.add(buttonLayer1);
		buttonPanel.add(buttonLayer2);

		//buttonPanel.add(runMapDiaButton);

		componentMap.put("button_Panel", new GridItem(buttonPanel, false, 2, 19, 4, 1));

    }


	/*
     *
     *
     */
    public int checkSelectedSections() {
            	/* Create a boolean to check if any errors occur when cheching each input */
				boolean errorOccurred = false;

				/* Clear the errors and fieldData string buffers*/
				errors.delete(0, errors.length());
				fieldData.delete(0, fieldData.length());
				
		/* If the MSGF Search checkbox was checked */
			if(runMSGFSearch) {

				/* Checking if the MSGF Search Info inputs are filled out */
				if(ntt_Text.getText().equals("") || modsDir_Text.getText().equals("") ||
					ppm_Text.getText().equals("") || enzyme_Text.getText().equals("") ||
				                                msgfplusJarDir_Text.getText().equals("") ||
				        fastaDir_Text.getText().equals("") || ti_Text.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "         Fill in all the fields");
						return 0;
				}

				/* Checking if the MSGF Search Info inputs have been edited */
				if(fastaDir_Text.getText().contains("EX:") ||
						modsDir_Text.getText().contains("EX:")) {
						JOptionPane.showMessageDialog(null, "     Fill in all fields correctly");
						return 0;
				}

				/* Checking if the MSGF Search Info files exist */
				if(!checkIfFilesExist(msgfSearchOpt)) {
						errorOccurred = true;
				}
			}

		/* If the XTandem Search checkbox was checked */
			if(runXTandem) {

				/* Checking if the XTandem Search Info inputs are filled out */
				if(tandem2xmlDir_Text.getText().equals("") ||
						taxonomyPath_Text.getText().equals("") ||
				        paramsXMLDir_Text.getText().equals("") ||
				           tandemDir_Text.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "         Fill in all the fields");
						return 0;
				}

				/* Checking if the XTandem Search Info inputs have been edited */
				if(paramsXMLDir_Text.getText().contains("EX:") ||
					   taxonomyPath_Text.getText().contains("EX:")) {
						JOptionPane.showMessageDialog(null, "     Fill in all fields correctly");
						return 0;
				}

				/* Checking if the XTandem Search Info Info files exist */
				if(!checkIfFilesExist(xTandemSearchOpt)) {
						errorOccurred = true;
					}
				}


				/* If the Comet Search checkbox was checked */
				if(runCometSearch) {

					/* Checking if the Comet Search Info inputs are filled out */
					if(cometParamsDir_Text.getText().equals("") ||
						cometFastaDir_Text.getText().equals("") ||
						     cometDir_Text.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "         Fill in all the fields");
						return 0;
					}

					/* Checking if the Comet Search Info inputs have been edited */
					if(cometParamsDir_Text.getText().contains("EX:") ||
						cometFastaDir_Text.getText().contains("EX:")) {
						JOptionPane.showMessageDialog(null, "     Fill in all fields correctly");
						return 0;
					}

					/* Checking if the Comet Search Info files exist */
					if(!checkIfFilesExist(cometSearchOpt)) {
						errorOccurred = true;
					}
				}
	}

	/*
     *
     *
     */
    public int numOfWiffsContainingLabel(String mapDIA_wiffFiles_Dir, String label) {
       	int numOfOccurances = 0;

    	try {
    		File wiffDir = new File(mapDIA_wiffFiles_Dir);
    		File[] files = wiffDir.listFiles();

    		for( File file : files ) {
				if( !file.isDirectory() && ((file.getAbsolutePath().endsWith(".wiff"))||(file.getAbsolutePath().endsWith(".raw"))) &&
					(file.getAbsolutePath().toLowerCase().contains(label.toLowerCase())) ) {
					numOfOccurances++;
				}
			}

    	} catch (Exception e) {
    		numOfOccurances = 0;
    	} finally {
    		return numOfOccurances;
    	}

    }

	/*
     *
     *
     */
    public void createLoadUserParams() {
    	JButton user1 = new JButton("Load 1");
    	JButton user2 = new JButton("Load 2");
    	JButton user3 = new JButton("Load 3");
    	JButton user4 = new JButton("Load 4");

    	loadParamsClosed = false;

        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
            	JFrame frame = new JFrame("Load User Params");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel pane = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();

                c.ipadx = 10;
        		c.ipady = 10;
        		c.insets = new Insets(10, 4, 10, 4);

        		c.gridx = 0;
				c.gridy = 0;
				pane.add(user1, c);

				c.gridx = 0;
				c.gridy = 1;
				pane.add(user2, c);

				c.gridx = 0;
				c.gridy = 2;
				pane.add(user3, c);

				c.gridx = 0;
				c.gridy = 3;
				pane.add(user4, c);

				(user1).addActionListener(new ActionListener() {
        	    	@Override
        	    	public void actionPerformed(ActionEvent e) {
        	    		File user1Settings = new File(System.getProperty("user.dir") + "//userParams//user1.parameters");
        	    		readFile(user1Settings);
        	    		loadFile();
        	    		frame.dispose();
        	    		loadParamsClosed = true;
            		}
        		});;

        		(user2).addActionListener(new ActionListener() {
        	    	@Override
        	    	public void actionPerformed(ActionEvent e) {
        	    		File user2Settings = new File(System.getProperty("user.dir") + "//userParams//user2.parameters");
        	    		readFile(user2Settings);
        	    		loadFile();
        	    		frame.dispose();
        	    		loadParamsClosed = true;
            		}
        		});;

        		(user3).addActionListener(new ActionListener() {
        	    	@Override
        	    	public void actionPerformed(ActionEvent e) {
        	    		File user3Settings = new File(System.getProperty("user.dir") + "//userParams//user3.parameters");
        	    		readFile(user3Settings);
        	    		loadFile();
        	    		frame.dispose();
        	    		loadParamsClosed = true;
            		}
        		});;

        		(user4).addActionListener(new ActionListener() {
        	    	@Override
        	    	public void actionPerformed(ActionEvent e) {
        	    		File user4Settings = new File(System.getProperty("user.dir") + "//userParams//user4.parameters");
        	    		readFile(user4Settings);
        	    		loadFile();
        	    		frame.dispose();
        	    		loadParamsClosed = true;
            		}
        		});;

                frame.getContentPane().add(BorderLayout.CENTER, pane);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);

                frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						loadParamsClosed = true;
					}
				});
            }
        });
    }



	/*
     *
     *
     */
    public boolean readFile( File file ) {
    	parametersStringBuf.delete(0, parametersStringBuf.length());

		Scanner fileToRead = null;
		try {
			fileToRead = new Scanner( file );
			String line;
			while( fileToRead.hasNextLine() && (line = fileToRead.nextLine()) != null ) {
				parametersStringBuf.append(line);
				parametersStringBuf.append("\r\n");
			}
		} catch (FileNotFoundException e) {
            //e.printStackTrace();
            return false;
        } finally {
            fileToRead.close();
            return true;
        }
	}



	/*
	 *
	 *
	 */
	public void loadFile() {
		ArrayList<String> parametersList = new ArrayList<String>();

        for(String retval : parametersStringBuf.toString().split("\r\n")) {
       		parametersList.add(retval);
		}

		String [] parametersArray = new String[parametersList.size()];

		for(int index = 0; index < parametersList.size() ; index++) {
			parametersArray[index] = parametersList.get(index);
		}

		setFieldData(parametersArray);
	}



	/*
     *
     *
     */
    public void createSaveUserParams() {
    	JButton user1 = new JButton("Save 1");
    	JButton user2 = new JButton("Save 2");
    	JButton user3 = new JButton("Save 3");
    	JButton user4 = new JButton("Save 4");

    	saveParamsClosed = false;

        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
            	JFrame frame = new JFrame("Save User Parameters");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel pane = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();

                c.ipadx = 10;
        		c.ipady = 10;
        		c.insets = new Insets(10, 4, 10, 4);

        		c.gridx = 0;
				c.gridy = 0;
				pane.add(user1, c);

				c.gridx = 0;
				c.gridy = 1;
				pane.add(user2, c);

				c.gridx = 0;
				c.gridy = 2;
				pane.add(user3, c);

				c.gridx = 0;
				c.gridy = 3;
				pane.add(user4, c);

				(user1).addActionListener(new ActionListener() {
        	    	@Override
        	    	public void actionPerformed(ActionEvent e) {
        	    		File user1Settings = new File(System.getProperty("user.dir") + "//userParams//user1.parameters");
        	    		grabFieldData();
        	    		saveFile(user1Settings, fieldData);
        	    		frame.dispose();
        	    		saveParamsClosed = true;
            		}
        		});;

        		(user2).addActionListener(new ActionListener() {
        	    	@Override
        	    	public void actionPerformed(ActionEvent e) {
        	    		File user2Settings = new File(System.getProperty("user.dir") + "//userParams//user2.parameters");
        	    		grabFieldData();
        	    		saveFile(user2Settings, fieldData);
        	    		frame.dispose();
        	    		saveParamsClosed = true;
            		}
        		});;

        		(user3).addActionListener(new ActionListener() {
        	    	@Override
        	    	public void actionPerformed(ActionEvent e) {
        	    		File user3Settings = new File(System.getProperty("user.dir") + "//userParams//user3.parameters");
        	    		grabFieldData();
        	    		saveFile(user3Settings, fieldData);
        	    		frame.dispose();
        	    		saveParamsClosed = true;
            		}
        		});;

        		(user4).addActionListener(new ActionListener() {
        	    	@Override
        	    	public void actionPerformed(ActionEvent e) {
        	    		File user4Settings = new File(System.getProperty("user.dir") + "//userParams//user4.parameters");
        	    		grabFieldData();
        	    		saveFile(user4Settings, fieldData);
        	    		frame.dispose();
        	    		saveParamsClosed = true;
            		}
        		});;

                frame.getContentPane().add(BorderLayout.CENTER, pane);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);

                frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						saveParamsClosed = true;
					}
				});
            }
        });
    }


	/*
	 *
	 *
	 */
	public void saveFile(File saveLocation, StringBuffer dataToSave) {
		ArrayList<String> parametersList = new ArrayList<String>();
		StringBuffer saveBuffer = new StringBuffer();
		int index = 1;
		int justReturn = 0;
		int errorOccurred = 1;
		int returnVal;

		saveBuffer.append(dataToSave.toString());

		returnVal = checkSelectedSections();

		if( returnVal == justReturn ) {
			return;
		}
				
		/* Display the errors in a pop-up message dialog if any were encountered */
		if( returnVal == errorOccurred ) {
			JOptionPane.showMessageDialog(null, errors.toString());
			return;
		}

		for(String retval : saveBuffer.toString().split("\r\n")) {
       		parametersList.add(retval);
		}

		saveBuffer.delete(0, saveBuffer.length());

		//saveBuffer.append("inputDirText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("outputDirText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("numOfThreadsText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("amountOfRamText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("AB_SCIEX_MS_ConverterDirText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("msconvertDirText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("indexmzXMLDirText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("DIA_Umpire_SE_Jar_DirText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("mzxmlToMgfParamsText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("msgfplusJarDirText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("fastaDirText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("ppmText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("enzymeText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("modsDirText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("nttText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("tiText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("tandemDirText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("tandem2xmlDirText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("paramsXMLDirText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("taxonomyPathText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("cometDirText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("cometParamsDirText::" + parametersList.get(index++)).append("\r\n");
		saveBuffer.append("cometFastaDirText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("xInteractDirText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("InterProphetParserDirText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("skylineRunnerExePathText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("skylineReportNameText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("skylineFastaDirText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("skylineTemplateDocText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("skylineXMLFileText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("ptmProphetExeText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("ptmProphetParserMassesText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("ptmProphetParserMZTOLText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("ptmProphetParserMINPROBText::" + parametersList.get(index++)).append("\r\n");
		//saveBuffer.append("mapDIALabelsText::" + parametersList.get(index++).replaceAll(" ", ", ")).append("\r\n");


		writeToFile(saveLocation, saveBuffer);
	}



	/*
	 * Adds the components to the map
	 *
	 */
    protected void createChildren() {
        Iterator<Map.Entry<String, GridItem>> it;

        for (it = componentMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, GridItem> item = it.next();
            GridItem gridItem = item.getValue();

            this.constraints.gridx = gridItem.xPos;
            this.constraints.gridy = gridItem.yPos;
            this.constraints.gridwidth = gridItem.colSpan;
            this.constraints.gridheight = gridItem.rowSpan;

            this.add(gridItem.component, this.constraints);
        }
    }


    /*
	 * Checks if the files exist. Only checks the JTextFields
	 * 
	 */
	private boolean checkIfFilesExist(int option) {

		/* Will be set to false if any file paths entered do not exist (false=errors encountered) */
		boolean filesExist = true;

		/* A path variable to temporarily hold each path entered */
		Path tempPath;

		try{

			/* Checking the MSGFSearch Info JTextFields */
			if(option == msgfSearchOpt) {

				/* Checking if msgfplus.jar exists */
				tempPath = Paths.get( msgfplusJarDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					errors.append("msgfplus.jar does not exist\r\n");
					filesExist = false;
				}
				
				/* Checking if the .fasta file exists */
				tempPath = Paths.get( fastaDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					errors.append(".fasta file does not exist\r\n");
					filesExist = false;
				}
				
				/* Checking if the mods .txt file exists */
				tempPath = Paths.get( modsDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					errors.append("mods .txt file does not exist\r\n");
					filesExist = false;
				}
			}
			

			/* Checking the XTandem Info JTextFields */
			if(option == xTandemSearchOpt) {

				/* Checking if tandem.exe exists */
				tempPath = Paths.get( tandemDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					errors.append("tandem.exe does not exist\r\n");
					filesExist = false;
				}
				
				/* Checking if tandem2xml.exe exists */
				tempPath = Paths.get( tandem2xmlDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					errors.append("tandem2xml.exe does not exist\r\n");
					filesExist = false;
				}
				
				/* Checking if the _params.xml file exists */
				tempPath = Paths.get( paramsXMLDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					errors.append("_params.xml file does not exist\r\n");
					filesExist = false;
				}
				
				/* Checking if the taxonomy.xml file exists */
				tempPath = Paths.get( taxonomyPath_Text.getText() );
				if( !Files.exists(tempPath) ) {
					errors.append("taxonomy.txt file does not exist\r\n");
					filesExist = false;
				}

				/* If the taxonomy.xml file exists, check if the "url" exists */
				else {
					filesExist = checkTaxonomyURL(tempPath);
					return filesExist;
				}
			}
			

			/* Checking the Comet Search Info JTextFields */
			if(option == cometSearchOpt) {

				/* Checking if comet.exe exists */
				tempPath = Paths.get( cometDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					errors.append("comet.exe does not exist\r\n");
					filesExist = false;
				}
				
				/* Checking if the .fasta file exists */
				tempPath = Paths.get( cometFastaDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					errors.append("The comet .fasta file does not exist\r\n");
					filesExist = false;
				}
				
				/* Checking if the .params file exists */
				tempPath = Paths.get( cometParamsDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					errors.append("The comet .params file does not exist\r\n");
					filesExist = false;
				}
			}
		}

		/* 
		 * If any of the paths were entered incorrectly (illegal characters, etc.), delete all the
		 * errors that were found and display the message below
		 */
		catch( Exception e ) {
			errors.delete(0, errors.length());
			errors.append("Some file paths have been entered incorrectly.\r\n");
			errors.append("Make sure the paths are in this format:\r\n");
			errors.append("          C:\\Inetpub\\tpp-bin\\sed.exe\r\n");
			errors.append("Also check the URL in the taxonomy file");
			filesExist = false;
		}
		
		/*
		 * Returns true or false indicating if any errors were encountered
		 * (false = errors encountered)
		 */
		return filesExist;
	}
			

	/*
	 *
	 *
	 */
	private boolean checkTaxonomyURL(Path taxonomyFilePath) {

		/* Read in the entire taxonomy.xml file */
		readTaxonomyFile(taxonomyFilePath);

		/* The location of the first and last characters of the path described in the URL */
		int urlBeginning = 0;
		int urlEnd = 0;

		/* Find the beginning of the URL path */
		urlBeginning = dataInTaxonomyFile.lastIndexOf("URL=");

		/* 
		 * If the "URL" string exists in the taxonomy file, find the end of the path by looking for
		 * the ending quotation mark
		 */
		if( urlBeginning != -1 ) {
			urlBeginning += 5;
			urlEnd = urlBeginning;

			/* Find the closing quotation mark */
			while(dataInTaxonomyFile.charAt(urlEnd) != '\"' &&
				      urlEnd != dataInTaxonomyFile.length()) {
				urlEnd++;
			}

			/* If the closing quotation mark does not exist, display the error and return false */
			if( urlEnd == dataInTaxonomyFile.length() ) {
				errors.delete(0, errors.length());
				errors.append("The \"URL\" file in the taxonomy file been entered " +
					                                             "incorrectly.\r\n");
				errors.append("Make sure the path is in this format:\r\n");
				errors.append("<file format=\"peptide\" URL=\"c:/Inetpub/wwwroot/ISB/data" +
					        "/DIAonly/20160625_DIA/20150810.mouse.cc.iRT_DECOY.fasta\" />");
				return false;
			}

			/* 
			   JOptionPane.showMessageDialog(null, "The URL is: " +
			   dataInTaxonomyFile.substring(urlBeginning, urlEnd));
			*/

			/* Check if the path specified in the URL exists */
			try {
				Path tempPath = Paths.get(dataInTaxonomyFile.substring(urlBeginning, urlEnd));
				if( !Files.exists(tempPath) ) {
					errors.delete(0, errors.length());
					errors.append("The \"URL\" file in the taxonomy file does not exist\r\n");
					return false;
				}
				else {
					return true;
				}
			}

			/* If the path contains illegal characters display this message */
			catch( Exception e ) {
				errors.delete(0, errors.length());
				errors.append("The \"URL\" file in the taxonomy file been entered " +
					                                             "incorrectly.\r\n");
				errors.append("Make sure the path is in this format:\r\n");
				errors.append("<file format=\"peptide\" URL=\"c:/Inetpub/wwwroot/ISB/data" +
					        "/DIAonly/20160625_DIA/20150810.mouse.cc.iRT_DECOY.fasta\" />");
				return false;
			}
		}

		/* If there was no "URL" substring in the taxonomy file, display this message */
		else {
			errors.delete(0, errors.length());
			errors.append("There was no URL in the taxonomy file");
			errors.append("\r\n");
			errors.append("Add something like this in the taxonomy file:");
			errors.append("\r\n");
			errors.append("<file format=\"peptide\" URL=\"c:/Inetpub/wwwroot/ISB/data/DIAonly/" +
			                          "20160625_DIA/20150810.mouse.cc.iRT_DECOY.fasta\" />");
			errors.append("\r\n");
			return false;
		}
	}
	


	/*
	 *
	 *
	 */
	public boolean readTaxonomyFile( Path taxonomyFilePath ) {
		dataInTaxonomyFile.delete(0, dataInTaxonomyFile.length());
		Scanner fileToRead = null;
		try {
			fileToRead = new Scanner( taxonomyFilePath );
			String line;
			while( fileToRead.hasNextLine() && (line = fileToRead.nextLine()) != null ) {
				dataInTaxonomyFile.append(line);
				dataInTaxonomyFile.append("\r\n");
			}
		} catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            fileToRead.close();
            return true;
        }
	}


	/*
	 *
	 *
	 */
    private void grabFieldData() {

    	/* Use the information entered if selected, else fill the StringBuffer with default info */
		if(runMSGFSearch) {
			fieldData.append(msgfplusJarDir_Text.getText()).append("\r\n");
			fieldData.append(fastaDir_Text.getText()).append("\r\n");
			fieldData.append(ppm_Text.getText()).append("\r\n");
			fieldData.append(enzyme_Text.getText()).append("\r\n");
			fieldData.append(modsDir_Text.getText()).append("\r\n");
			fieldData.append(ntt_Text.getText()).append("\r\n");
			fieldData.append(ti_Text.getText()).append("\r\n");
		}
		else {
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("25ppm").append("\r\n");
			fieldData.append("5").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("ntt").append("\r\n");
			fieldData.append("ti").append("\r\n");
		}

		/* Use the information entered if selected, else fill the StringBuffer with default info */
		if(runXTandem) {
			fieldData.append(tandemDir_Text.getText()).append("\r\n");
			fieldData.append(tandem2xmlDir_Text.getText()).append("\r\n");
			fieldData.append(paramsXMLDir_Text.getText()).append("\r\n");
			fieldData.append(taxonomyPath_Text.getText()).append("\r\n");
		}
		else {
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
		}

		/* Use the information entered if selected, else fill the StringBuffer with default info */
		if(runCometSearch) {
			fieldData.append(cometDir_Text.getText()).append("\r\n");
			fieldData.append(cometParamsDir_Text.getText()).append("\r\n");
			fieldData.append(cometFastaDir_Text.getText()).append("\r\n");
		}
		else {
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
		}

		/* Add the JCheckBox booleans to the StringBuffer */
		//fieldData.append(runDiaUmpire).append("\r\n");
		fieldData.append(runMSGFSearch).append("\r\n");
		fieldData.append(runXTandem).append("\r\n");
		fieldData.append(runCometSearch).append("\r\n");
		//fieldData.append(runProphet).append("\r\n");
		//fieldData.append(runSkyline).append("\r\n");
		//fieldData.append(runPTMProphetParser).append("\r\n");
		//fieldData.append(runMapDia).append("\r\n");
    }
	

	/*
	 * ***PUT GENERAL INFO STUFF (input, output) IN ALL MODULES?
	 *
	 */
	private void writeToFile() {

		/* 
		 * Write the contents of the fieldData StringBuffer to the input.parameters file located in
		 * the current directory
		 */

		String inputPathString = Paths.get(".").toAbsolutePath().normalize().toString();
		inputPathString += "\\input.parameters";
		
		try {
			BufferedWriter bufwriter = new BufferedWriter( new FileWriter(inputPathString) );
			bufwriter.write(fieldData.toString());
			bufwriter.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
		
	}



	/*
	 *
	 *
	 */
	private  void writeToFile( File file, StringBuffer buffer ) {
		try {
			BufferedWriter bufwriter = new BufferedWriter( new FileWriter(file.toString()) );
			bufwriter.write(buffer.toString());
			bufwriter.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}



	/*
	 *
	 *
	 */
	private  void writeToFile( String outFile, StringBuffer buffer ) {
		String inputPathString = Paths.get(".").toAbsolutePath().normalize().toString();
		inputPathString += "\\";
		inputPathString += outFile;
		try {
			BufferedWriter bufwriter = new BufferedWriter( new FileWriter(outFile) );
			bufwriter.write(buffer.toString());
			bufwriter.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	


	/*
	 *
	 *
	 */
    private void clearFieldData() {
		/* Clears all the JTextFields */
		for( JTextField field : textFields ) {
			field.setText("");
		}
		
		/* Resets the checkboxes and disables the appropriate JTextFields */
		resetTextAndCheckboxes();
	}



	/*
	 *
	 *
	 */
    private void setFieldData(String[] textLines) {
        clearFieldData();

        for (String line : textLines) {
            String[] values = line.split("::");

            if (values.length == 2) {
                GridItem gridItem = componentMap.get(values[0]);

                if (gridItem.isExportable && gridItem.component instanceof JTextComponent) {
                    JTextComponent field = ((JTextComponent) gridItem.component);
                    field.setText(values[1]);
                    field.setCaretPosition(0);
                }
            }
        }
    }
	
	


	/*
	 *
	 *
	 */
	private void resetTextAndCheckboxes() {
		/* Set all the textbox booleans to false */
		//runDiaUmpire = false;
		runMSGFSearch = false;
		runXTandem = false;
		//runProphet = false;
		runCometSearch = false;
		//runSkyline = false;
		//runPTMProphetParser = false;
		//runMapDia = false;


		/* Disable all the JTextFields */
		for( JTextField element : textFields ) {
			element.setEnabled(false);
		}
		
		/* Uncheck all the JCheckBoxes */
		for( JCheckBox element : checkBoxFields ) {
			element.setSelected(false);
		}
		
		
	}


	/*
	 *
	 *
	 */
    public static void main(String[] args) {
		
		/* Sets the frame and runs the application */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame(APP_TITLE);
                frame.setContentPane(new GetInput(APP_WIDTH, APP_HEIGHT));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
				frame.setResizable(false);
            }
        });
    }
}