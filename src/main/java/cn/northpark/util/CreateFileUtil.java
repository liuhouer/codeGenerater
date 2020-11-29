package cn.northpark.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class CreateFileUtil {

    public static boolean CreateFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            file.delete();
//    	System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标不能是目录！");
            return false;
        }
        if (!file.getParentFile().exists()) {
            System.out.println("目标文件所在路径不存在，准备创建。。。");
            if (!file.getParentFile().mkdirs()) {
                System.out.println("创建目录文件所在的目录失败！");
                return false;
            }
        }

        // 创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！");
            return false;
        }
    }


    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
            return false;
        }
        if (!destDirName.endsWith(File.separator))
            destDirName = destDirName + File.separator;
        // 创建单个目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "成功！");
            return false;
        }
    }


    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        try {
            if (dirName == null) {
                // 在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                return tempFile.getCanonicalPath();
            } else {
                File dir = new File(dirName);
                // 如果临时文件所在目录不存在，首先创建
                if (!dir.exists()) {
                    if (!CreateFileUtil.createDir(dirName)) {
                        System.out.println("创建临时文件失败，不能创建临时文件所在目录！");
                        return null;
                    }
                }
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建临时文件失败" + e.getMessage());
            return null;
        }
    }

    /**
     * 删除某个文件夹下的所有文件夹和文件
     *
     * @param delpath String
     * @return boolean
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static boolean deletefile(String delpath) throws FileNotFoundException,
            IOException {
        try {

            File file = new File(delpath);
            if (!file.isDirectory()) {
                System.out.println("1");
                file.delete();
            } else if (file.isDirectory()) {
                System.out.println("2");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "/" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        System.out.println("path=" + delfile.getPath());
                        System.out.println("absolutepath=" + delfile.getAbsolutePath());
                        System.out.println("name=" + delfile.getName());
                        delfile.delete();
                        System.out.println("删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + "" + filelist[i]);
                    }
                }
                file.delete();

            }

        } catch (FileNotFoundException e) {
            System.out.println("deletefile() Exception:" + e.getMessage());
        }
        return true;
    }

    /**
     * 读取某个文件夹下的所有文件夹和文件, 返回所有文件名
     *
     * @param filepath String
     * @return Map<Integer, String> pathMap
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Map<Integer, String> readfile(String filepath, Map<Integer, String> pathMap) throws Exception {
        if (pathMap == null) {
            pathMap = new HashMap<Integer, String>();
        }
        File file = new File(filepath);
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (os.startsWith("win") || os.startsWith("Win")) {// windows操作系统
            // 文件
            if (!file.isDirectory()) {
                pathMap.put(pathMap.size(), file.getPath());

            } else if (file.isDirectory()) { // 如果是目录， 遍历所有子目录取出所有文件名
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    if (!filelist[i].endsWith(".java.vm")) {
                        continue;
                    }
                    File readfile = new File(filepath + "/" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        pathMap.put(pathMap.size(), readfile.getPath());

                    } else if (readfile.isDirectory()) { // 子目录的目录
                        readfile(filepath + "/" + filelist[i], pathMap);
                    }
                }
            }
        } else {//linux || OS X
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                if (!filelist[i].endsWith(".java.vm")) {
                    continue;
                }
                File readfile = new File(filepath + "/" + filelist[i]);
                if (!readfile.isDirectory()) {
                    pathMap.put(pathMap.size(), readfile.getPath());

                } else if (readfile.isDirectory()) { // 子目录的目录
                    readfile(filepath + "/" + filelist[i], pathMap);
                }
            }
        }

        return pathMap;
    }


    static String readtxt(String file) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));

        String str = "";

        String r = br.readLine();

        while (r != null) {

            str += r;

            r = br.readLine();

        }

        return str;

    }

    public static void write(String file, String context) {
        try {


            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(context);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void delAll(String path) throws Exception {
        File f = new File(path);
        if (f.isDirectory()) {
            String[] children = f.list();
            for (String folder : children) {
                String newPath = path + "/" + folder;
                delAll(newPath);
            }
        }
        f.delete();
    }

    public static void main(String[] args) {
        try {
            delAll("d:\\test\\com");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}