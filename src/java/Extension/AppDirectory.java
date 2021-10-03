/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extension;

import java.io.File;

/**
 *
 * @author WilliamTrung
 */
public class AppDirectory {

    private static final String APP_DIR = "EventManagementData";
    private static final String DATA_DIR = "data";
    private static final String TEMP_DIR = "temp";

    public static String getAppDir(){
        String userHome = System.getProperty("user.home") + "\\" + "Documents";
        //now dir = Documents\
        userHome += "\\" + APP_DIR;
        return userHome;
    }
    public static String getTempDir(){
        String tempDir = getAppDir();
        tempDir += "\\" +TEMP_DIR;
        //dir = Document\EventManagementData\temp
        return tempDir;
    }
    public static String getDataDir(){
        String dataDir = getAppDir();
        dataDir += "\\" +DATA_DIR;
        //dir = Document\EventManagementData\data
        return dataDir;
    }
    public static boolean DataDirChecking() {
        String userHome = System.getProperty("user.home");
        //dir: Documents\
        userHome = userHome + "\\" + "Documents";
        //make dir: Documents\EventManagementData
        boolean check = mkDir(userHome, APP_DIR);
        //dir: Documents\EventManagementData
        userHome += ("\\" + APP_DIR);
        //make dir: Documents\EventManagementData\data
        check = mkDir(userHome, DATA_DIR);
        //make dir: Documents\EventManagementData\temp
        check = mkDir(userHome, TEMP_DIR);
        return check;
    }

    public static boolean mkDir(String path, String foldername) {
        path = path + "\\" + foldername;
        //Instantiate the File class   
        File file = new File(path);
        //Creating a folder using mkdir() method 
        boolean check;
        if (file.exists()) {
            check = true;
        } else {
            check = file.mkdir();
        }
        return check;
    }
    public static File getFile(String path, String foldername,String filename){
        File file = new File(path + "\\" + foldername + "\\" + filename);
        return file;
    }
}
