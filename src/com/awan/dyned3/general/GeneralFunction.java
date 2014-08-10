package com.awan.dyned3.general;
import java.io.IOException;
public class GeneralFunction {
    public static boolean checkIsFileExists(String path){
    	boolean finalReturn = false;

    	try {
			FileConnection fileConnection = (FileConnection)Connector.open(path);
			finalReturn = fileConnection.exists();
		} catch (IOException e) {
    	return finalReturn;
    }
    public static boolean createDirectory(final String newDirectory) {
        // First step make sure temp directory is there
        FileConnection fconn = null;
        boolean returnFlag = false;
        try {
            fconn = (FileConnection) Connector.open(newDirectory, Connector.READ_WRITE);
            if ( !fconn.exists() ) {
                fconn.mkdir();  // create the folder/file if it doesn't exist
            }
            fconn.close();
            fconn = null;
            returnFlag = true;
        } catch (Exception e) {
            String errorMessage = "Error creating directory: " + newDirectory + '\n' + e.toString();
        } finally {
            try {
                if ( fconn != null ) {
                    fconn.close();
                    fconn = null;
                }
            } catch (Exception e) {
            }
        }
        return returnFlag;
    }
}