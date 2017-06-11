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
     * ��ѹ���ܵ�ѹ���ļ�
     * @param zipfile Ҫ��ѹ���ļ�
     * @param dest Ҫ���ļ����������λ��
     * @param passwd
     * @throws ZipException
     */
    public void unZip(File zipfile,String dest,String passwd) throws ZipException{
        ZipFile zfile=new ZipFile(zipfile);
//        zfile.setFileNameCharset("GBK");//��GBKϵͳ����Ҫ����
        if(!zfile.isValidZipFile()){
            throw new ZipException("ѹ���ļ����Ϸ��������Ѿ��𻵣�");
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
     * ѹ���ļ��Ҽ���
     * @param src �ļ�·��
     * @param dest ���ܺ���õ�λ�ð����ļ�����
     * @param is
     * @param passwd
     */
    public void zip(String src,String dest,boolean is,String passwd){
        File srcfile=new File(src);
        //����Ŀ���ļ�
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
     * Ŀ���ļ�����
     * @param srcfile
     * @param dest
     * @return
     */
    public String buildDestFileName(File srcfile,String dest){
        if(dest==null){//û�и���Ŀ��·��ʱ
            if(srcfile.isDirectory()){
                dest=srcfile.getParent()+File.separator+srcfile.getName()+".zip";
            }else{
                String filename=srcfile.getName().substring(0,srcfile.getName().lastIndexOf("."));
                dest=srcfile.getParent()+File.separator+filename+".zip";
            }
        }else{
            createPath(dest);//·���Ĵ���
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
     * ·������
     * @param dest
     */
    private void createPath(String dest){
        File destDir=null;
        if(dest.endsWith(File.separator)){
            destDir=new File(dest);//��������·��ʱ
        }else{
            destDir=new File(dest.substring(0,dest.lastIndexOf(File.separator)));
        }
        if(!destDir.exists()){
            destDir.mkdirs();
        }
    }
    
   @org.junit.Test
   public void Test(){
       String src="D:\\��������\\��������1-2.pdf";
       String dest="D:\\��������\\��������1-2.zip";
       zip(src, dest, true, "123456");
       
   }
}

