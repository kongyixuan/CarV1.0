package com.test.zip;
import java.io.File;

import java.util.ArrayList;
import java.util.Collections;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
public class CompressUtils {
	/**
     * 解压加密的压缩文件
     * @param zipfile 要解压的文件
     * @param dest 要将文件放在那里的位置
     * @param passwd
     * @throws ZipException
     */
    public void unZip(File zipfile,String dest,String passwd) throws ZipException{
        ZipFile zfile=new ZipFile(zipfile);
//        zfile.setFileNameCharset("GBK");//在GBK系统中需要设置
        if(!zfile.isValidZipFile()){
            throw new ZipException("压缩文件不合法，可能已经损坏！");
        }
        File file=new File(dest);
        if(file.isDirectory() && !file.exists()){
            file.mkdirs();
        }
        if(zfile.isEncrypted()){
            zfile.setPassword(passwd);
        }
        zfile.extractAll(dest);
    }
    /**
     * 压缩文件且加密
     * @param src 文件路径
     * @param dest 加密后放置的位置包含文件名字
     * @param is
     * @param passwd
     */
    public void zip(String src,String dest,boolean is,String passwd){
        File srcfile=new File(src);
        //创建目标文件
        String destname = buildDestFileName(srcfile, dest);
        ZipParameters par=new ZipParameters();
        par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        if(passwd!=null){
            par.setEncryptFiles(true);
            par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            par.setPassword(passwd);
            
        }
        try {
            ZipFile zipfile=new ZipFile(destname);
            if(srcfile.isDirectory()){
                if(!is){
                    File[] listFiles = srcfile.listFiles();
                    ArrayList<File> temp=new ArrayList<File>();
                    Collections.addAll(temp, listFiles);
                    zipfile.addFiles(temp, par);
                }
                zipfile.addFolder(srcfile, par);
            }else{
                zipfile.addFile(srcfile, par);
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }
        
        
    }
    /**
     * 目标文件名称
     * @param srcfile
     * @param dest
     * @return
     */
    public String buildDestFileName(File srcfile,String dest){
        if(dest==null){//没有给出目标路径时
            if(srcfile.isDirectory()){
                dest=srcfile.getParent()+File.separator+srcfile.getName()+".zip";
            }else{
                String filename=srcfile.getName().substring(0,srcfile.getName().lastIndexOf("."));
                dest=srcfile.getParent()+File.separator+filename+".zip";
            }
        }else{
            createPath(dest);//路径的创建
            if(dest.endsWith(File.separator)){
                String filename="";
                if(srcfile.isDirectory()){
                    filename=srcfile.getName();
                }else{
                    filename=srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                }
                dest+=filename+".zip";
            }
        }
        return dest;
    }
    /**
     * 路径创建
     * @param dest
     */
    private void createPath(String dest){
        File destDir=null;
        if(dest.endsWith(File.separator)){
            destDir=new File(dest);//给出的是路径时
        }else{
            destDir=new File(dest.substring(0,dest.lastIndexOf(File.separator)));
        }
        if(!destDir.exists()){
            destDir.mkdirs();
        }
    }
    
   @org.junit.Test
   public void Test(){
       String src="D:\\个人资料\\背景资料1-2.pdf";
       String dest="D:\\个人资料\\背景资料1-2.zip";
       zip(src, dest, true, "123456");
       
   }
}

