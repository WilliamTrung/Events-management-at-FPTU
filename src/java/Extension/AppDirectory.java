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
//Nhut: APP_DIR = "Documents\\Github\\Events-management-at-FPTU\\web\\images";
//Nam Anh: APP_DIR = "Dropbox\\PC\\Documents\\Github\\Events-management-at-FPTU\\web\\images";

    private static final String T = "EventManagementFPT";
    private static final String DATA_DIR = "web\\images";
    private static final String APP_DIR = "Documents\\Github\\Events-management-at-FPTU";

    public static String getAppDir() {
        String path = System.getProperty("user.home");
        path += "\\" + APP_DIR;
        String t = path + "\\" + T;
        boolean check;
        File file = new File(path);
        if (file.exists()) {
            file = new File(t);
            if (file.exists()) {
                check = mkDir( t +"\\"+"web", "images");
                if (check) {
                    path += "\\"+T+"\\"+DATA_DIR;
                }
            } else {
                path = path+"\\"+"web";
                check = mkDir(path, "images");
                if (!check) {
                    path = "";
                } else {
                    path+="\\"+"images";
                }
            }
        }
        return path;
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

    public static File getFile(String path, String foldername, String filename) {
        File file = new File(path + "\\" + foldername + "\\" + filename);
        return file;
    }
}
