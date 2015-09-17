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

import isl.dbms.DBFile;
import isl.dbms.DBCollection;
import java.io.OutputStream;


/**
 * The DatabaseDepository class.
 *
 * Interface Invariant: Once created and until destroyed, this
 * instance contains a valid <code>DatabaseDepository</code> object. The <code>DatabaseDepository</code>
 * class contains functions that handle connectivity to a Database.
 * @author Rousidis Ioannis
 * @version %I%, %G%
 * @since 1.0
 */
public class DatabaseDepository implements Depository {
    
    /**
     * The URI where the database client is connected.
     */
    private String DB_URI;
    /**
     * The user and password for connecting to the database.
     */
    private String user, password;
    /**
     * The collection in the database where the binary files are stored.
     */
    private String uploadDirectory;
    
    /**
     * Constructs an <code>DatabaseDepository</code> object.
     * <p>
     * <DD><DL>
     * <DT><B>Postcondition:</B><DD>is a valid instance of DatabaseDepository object</DL></DD>
     */
    public DatabaseDepository(String db, String collection, String user, String password) throws Exception {
        this.DB_URI = db;
        this.uploadDirectory = collection;
        this.user = user;
        this.password = password;
    }
    
    /**
     * Sets the directory where the binary files repository is placed in the database.
     * <p>
     * <DD><DL>
     * <DT><B>Precondition:</B><DD>a valid path into the database</DL></DD>
     * @param newUploadCollection the collection path that includes the files
     */
    public void setUploadDirectory(String newUploadDirectory){
        uploadDirectory = newUploadDirectory;
    }
    
    /**
     * Returns the path of the directory where the binary files repository is placed in the database.
     * <p>
     * <DD><DL>
     * <DT><B>Postcondition:</B><DD>the directory path that has been set or the default one</DL><DD>
     */
    public String getUploadDirectory() {
        return uploadDirectory;
    }
    
    /**
     * Stores a Binary document into the database. If the specified
     * file does not exist, it creates a new file.
     * <p>
     * <DD><DL>
     * <DD><B>Precondition:</B><DD>resource path should be a valid Binary document path</DL></DD>
     * @param filename the name of the file in the database.
     * @param bytes bytes array that construct the uploaded file.
     */
    public void uploadBinDocument(String filename, byte[] bytes) throws Exception {
        String type = "BinaryDBFile";
        DBCollection col = new DBCollection(this.DB_URI, this.uploadDirectory, this.user, this.password);
        DBFile file = col.getFile(filename);
        // create a new DBFile for storing the bytes
        if (file == null)
            file = col.createFile(filename, type);
        file.setBinary(bytes);
        file.store();
    }
    
    
    /**
     * Fetches an XML document from the DB
     * <p>
     * <DD><DL>
     * <DT><B>Precondition:</B><DD>resource path should be a valid Binary document path</DL></DD>
     * @param filename the name of the file.
     * @param .
     */
    public StringBuffer fetchXMLDocument(String filename) throws Exception {
        String type = "XMLDBFile";
        String parentCol = filename.substring(0,filename.lastIndexOf("/"));
        String actualName = filename.substring(filename.lastIndexOf("/"));
        DBCollection col = new DBCollection(this.DB_URI, parentCol, this.user, this.password);
        DBFile file = col.getFile(actualName);
        
        StringBuffer fileContent = new StringBuffer();
        
        if (file != null) {
            
            //out.println(file.getXMLAsString());
            //out.write(file.getBinary());
            fileContent.append(file.getXMLAsString());
            return fileContent;
        } else {
            return null;
        }
    }
    
    /**
     * Fetches a Binary document from the DB (Not implemented!)
     * <p>
     * <DD><DL>
     * <DT><B>Precondition:</B><DD>resource path should be a valid Binary document path</DL></DD>
     * @param filename the name of the file.
     * @param .
     */
    public boolean fetchBinDocument(String filename, OutputStream out) throws Exception {
        
//        try{
//            // Open the file stream
//            FileInputStream in = new FileInputStream(filename);
//
//            // Copy the contents of the file to the output stream
//            byte[] buf = new byte[1024];
//            int count = 0;
//
//            while ((count = in.read(buf)) >= 0) {
//                out.write(buf, 0, count);
//            }
//            in.close();
//            return true;
//
//        }catch(Exception e){
//            //System.out.println("ERROR occured in UpDownFiles.prepareToDown");
//            e.printStackTrace(System.out);
//            return false;
//        }
        return false;
    }
    
    /**
     * Stores an XML document into the database. If the specified
     * file does not exist, it creates a new file.
     * <p>
     * <DD><DL>
     * <DD><B>Precondition:</B><DD>resource path should be a valid XML document path</DL></DD>
     * @param filename the name of the file in the database.
     * @param xml an XML <code>String</code> that construct the uploaded file.
     */
    public void uploadXMLDocument(String filename, String xml) throws Exception {
        String type = "XMLDBFile";
        DBCollection col = new DBCollection(this.DB_URI, this.uploadDirectory, this.user, this.password);
        DBFile file = col.getFile(filename);
        // create a new DBFile for storing the xml
        if (file == null)
            file = col.createFile(filename, type);
        file.setXMLAsString(xml);
        file.store();
    }
    
    /**
     * Removes a document from the database.
     * <p>
     * <DD><DL>
     * <DT><B>Precondition:</B><DD>resource path should be a valid document path</DL></DD>
     * @param filename the name of the file.
     */
    public void removeDocument(String filename) throws Exception {
        DBFile file = new DBFile(this.DB_URI, this.uploadDirectory, this.user, this.password, filename);
        file.remove();
    }
}