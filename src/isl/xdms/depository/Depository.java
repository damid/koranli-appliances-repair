/*
Copyright 2015 Institute of Computer Science,
Foundation for Research and Technology - Hellas

Licensed under the EUPL, Version 1.1 or - as soon they will be approved
by the European Commission - subsequent versions of the EUPL (the "Licence");
You may not use this work except in compliance with the Licence.
You may obtain a copy of the Licence at:

http://ec.europa.eu/idabc/eupl

Unless required by applicable law or agreed to in writing, software distributed
under the Licence is distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the Licence for the specific language governing permissions and limitations
under the Licence.

Contact:  POBox 1385, Heraklio Crete, GR-700 13 GREECE
Tel:+30-2810-391632
Fax: +30-2810-391638
E-mail: isl@ics.forth.gr
http://www.ics.forth.gr/isl

Author : Rousidis Ioannis
This file is part of the Depository project.
*/

package	isl.xdms.depository;

import java.io.OutputStream;


/**
 * The Depository class.
 *
 * The <code>Depository</code> object is an interface that is
 * implemented by classes that handle the connectivity
 * with remote depositories.
 *
 * @see DatabaseDepository
 * @see DiskDepository
 * 
 * @author Rousidis Ioannis
 * @version %I%, %G%
 * @since 1.0
 */
public interface Depository{
	
	/**
	 * Sets the directory where the binary files are placed.
	 * <p>
	 * <DD><DL>
	 * <DT><B>Precondition:</B><DD>a valid path into the database</DL></DD>
	 * @param newUploadDirectory the directory path that includes files
	 */
	public void setUploadDirectory(String newUploadDirectory);

	/**
	 * Returns the path of the directory where the binary files are placed.
	 * <p>
	 * <DD><DL>
	 * <DT><B>Postcondition:</B><DD>the directory path that has been set or the default one</DL></DD>
	 */
	public String getUploadDirectory();

	/**
	 * Stores a Binary document.
	 * <p>
	 * <DD><DL>
	 * <DT><B>Precondition:</B><DD>resource path should be a valid Binary document path</DL></DD>
	 * @param filename the name of the file.
	 * @param bytes array of <code>byte (byte[])</code> that construct the uploaded file.
	 */
	public void uploadBinDocument(String filename, byte[] bytes) throws Exception;
	
        /**
	 * Fetches a Binary document.
	 * <p>
	 * <DD><DL>
	 * <DT><B>Precondition:</B><DD>resource path should be a valid Binary document path</DL></DD>
	 * @param filename the name of the file.
	 * @param .
	 */
	public boolean fetchBinDocument(String filename, OutputStream out) throws Exception;
        
        /**
	 * Fetches an XML document.
	 * <p>
	 * <DD><DL>
	 * <DT><B>Precondition:</B><DD>resource path should be a valid XML document path</DL></DD>
	 * @param filename the name of the file.
	 * @param .
	 */
	public StringBuffer fetchXMLDocument(String filename) throws Exception;
        
	/**
	 * Stores an XML document.
	 * <p>
	 * <DD><DL>
	 * <DT><B>Precondition:</B><DD>resource path should be a valid XML document path</DL></DD>
	 * @param filename the name of the file.
	 * @param xml the <code>String</code> that construct the uploaded file.
	 */
	public void uploadXMLDocument(String filename, String xml) throws Exception;
	
	/**
	 * Removes a document.
	 * <p>
	 * <DD><DL>
	 * <DT><B>Precondition:</B><DD>resource path should be a valid document path</DL></DD>
	 * @param filename the name of the file.
	 */
	public void removeDocument(String filename) throws Exception;
}
