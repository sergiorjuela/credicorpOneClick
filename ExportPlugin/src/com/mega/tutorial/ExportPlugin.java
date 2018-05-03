package com.mega.tutorial;
// To use files functionalities
import java.io.FileWriter;
import java.io.IOException;
// To use MEGA API
import com.mega.modeling.api.*;
// To use MEGA metamodel identifiers generated by the MEGA Wizard
import com.mega.vocabulary.*;

import javax.swing.*;

public class ExportPlugin {
	// full name of the file in which the Business Process will be exported
	private static String filePath = "C:\\Windows\\Temp\\";
	/**
	 * Function called when the command is triggered.
	 * Example : click on a menu.
	 * 
	 * @param sourceObject
	 *            MegaObject on which the command is applied.
	 * @param commandNumber
	 *            Number identifying the command among all the commands
	 *            implemented by this class.
	 *            Each command correspond to the commandNumber used in CmdInit
	 *            function.
	 *            Useful if CmdCount indicates more than one command.
	 */
	public void CmdInvoke(MegaObject sourceObject, Integer commandNumber) {
		

		FileWriter writer = null;
		MegaObject process = null;
		// Source object is a process
		process = sourceObject;
		// Exceptions must be caught with try ... catch statement
		try {
			// Open a FileWriter with file name defined statically in the class
			writer = new FileWriter(ExportPlugin.filePath + process.getProp(BusinessProcess.MA_GenericLocalName));
			// Writes the source process name
			writer.write(process.getProp(BusinessProcess.MA_GenericLocalName) + "\r\n");
			// Get the collection of sub-processes
			// Uses : MegaObject.getCollection function with the identifier
			// of sub-process generated in the com.mega.vocabulary package
			MegaCollection subProcessesCollection = null;
			subProcessesCollection = process.getCollection(BusinessProcess.MAE_Component);
			int test=0;
			test =test+subProcessesCollection.count() + 1;
			// Search for all the sub-processes
			for (int subProcessesIndex = 1; subProcessesIndex < subProcessesCollection.count() + 1; subProcessesIndex++) {
				MegaObject subProcess = null;
				// Get the sub process in the collection at subProcessesIndex
				// position
				subProcess = subProcessesCollection.item(subProcessesIndex);
				// Writes the sub-process name
				writer.write(" " + subProcess.getProp(BusinessProcess.MA_GenericLocalName)
						+ "\r\n");
			}

		} catch (IOException e) {
		}
		// In all cases, the FileWriter is closed if needed.
		finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
				}
		}
	}
	

}
