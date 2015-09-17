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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


/**
 * The DiskDepository class.
 *
 * Interface Invariant: Once created and until destroyed, this
 * instance contains a valid <code>DiskDepository</code> object. The <code>DiskDepository</code>
 * class contains functions that handle connectivity to a <code>server</code> such as Apache Tomcat.
 * @author Rousidis Ioannis
 * @version %I%, %G%
 * @since 1.0
 */
public class DiskDepository implements Depository {
    
    /** the file separator used by this system */
    private final static String fs = System.getProperty("file.separator");
    
    /**
     * The directory where the binary files are stored.
     * The directory is described with full path.
     * Even in case of a database, again full path to directory
     * must be specified.
     */
    private String uploadDirectory = null;
    
    /**
     * Constructs a <code>DiskDepository</code> object
     */
    public DiskDepository(String UploadDirectory) {
        this.uploadDirectory = UploadDirectory;
    }
    
    /**
     * Sets the directory where the binary files repository is placed in the database.
     * <p>
     * <DD><DL>
     * <DT><B>Precondition:</B><DD>a valid path into the database</DL></DD>
     * @param newUploadDirectory the directory path that includes the files
     */
    public void setUploadDirectory(String newUploadDirectory){
        this.uploadDirectory = newUploadDirectory;
    }
    
    /**
     * Returns the path of the directory where the binary files repository is placed in the database.
     * <p>
     * <DD><DL>
     * <DT><B>Postcondition:</B><DD>the directory path that has been set or the default one</DL></DD>
     */
    public String getUploadDirectory() {
        return uploadDirectory;
    }
    
    /**
     * Stores a Binary document into the disk.
     * <p>
     * <DD><DL>
     * <DT><B>Precondition:</B><DD>resource path should be a valid Binary document path</DL></DD>
     * @param filename the name of the file.
     * @param bytes bytes array that construct the uploaded file.
     */
    public void uploadBinDocument(String filename, byte[] bytes) throws Exception {
        // Check if uploadDirectory exists. If not create it.
        // could throw Exception...
        File directory = new File(this.uploadDirectory);
        if(directory.isDirectory() == false)
            directory.mkdirs();
        
        // create a new File in the new file path. Need for full path.
        FileOutputStream file = new FileOutputStream(this.uploadDirectory+fs+filename);
        // fill in with given bytes and close
        file.write(bytes);
        file.close();
    }
    
    /**
     * Fetches a Binary document from the disk.
     * <p>
     * <DD><DL>
     * <DT><B>Precondition:</B><DD>resource path should be a valid Binary document path</DL></DD>
     * @param filename the name of the file.
     * @param .
     */
    public boolean fetchBinDocument(String filename, OutputStream out) throws Exception {
        
        try{
            // Open the file stream
            FileInputStream in = new FileInputStream(filename);
            
            // Copy the contents of the file to the output stream
            byte[] buf = new byte[1024];
            int count = 0;
            
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            in.close();
            return true;
            
        }catch(Exception e){
            //System.out.println("ERROR occured in UpDownFiles.prepareToDown");
            e.printStackTrace(System.out);
            return false;
        }
    }
    
    /**
     * Fetches an XML document from the Disk (Not implemented)
     * <p>
     * <DD><DL>
     * <DT><B>Precondition:</B><DD>resource path should be a valid Binary document path</DL></DD>
     * @param filename the name of the file.
     * @param .
     */
    public StringBuffer fetchXMLDocument(String filename) throws Exception {
        return null;
    }
    
    /**
     * Stores an XML document into the disk.
     * <p>
     * <DD><DL>
     * <DT><B>Precondition:</B><DD>resource path should be a valid XML document path</DL></DD>
     * @param filename the name of the file.
     * @param bytes bytes array that construct the uploaded file.
     */
    public void uploadXMLDocument(String filename, String xml) throws Exception {
        // Check if binDirectory in exists. If not create it.
        // could throw Exception...
        File directory = new File(this.uploadDirectory);
        if(directory.isDirectory() == false)
            directory.mkdirs();
        
        // create a new File in the new file path. Need for full path.
        FileOutputStream file = new FileOutputStream(this.uploadDirectory+fs+filename);
        //BufferedWriter outWriter = new BufferedWriter(
        OutputStreamWriter outWriter = new OutputStreamWriter(file, "UTF-8");
        // fill in with given bytes and close
        outWriter.write(xml);
        outWriter.close();
    }
    
    /**
     * Removes a document from the disk.
     * <p>
     * <DD><DL>
     * <DT><B>Precondition:</B><DD>resource path should be a valid document path</DL></DD>
     * @param filename the name of the file.
     */
    public void removeDocument(String filename) throws Exception {
        File file = new File(this.uploadDirectory);
        file.delete();
    }
}
