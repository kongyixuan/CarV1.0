package com.test.picshuiyin;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageMarkLogoByIcon {
	  public static void main(String[] args) {   
	        String srcImgPath = "d:/timg.jpg";   
	        String iconPath = "d:/test/shuiyin.png";   
	        String targerPath = "d:/test/img_mark_icon.jpg";   
	        String targerPath2 = "d:/test/img_mark_icon_rotate.jpg";   
	        // ��ͼƬ���ˮӡ   
	        ImageMarkLogoByIcon.markImageByIcon(iconPath, srcImgPath, targerPath);   
	        // ��ͼƬ���ˮӡ,ˮӡ��ת-45   
	        ImageMarkLogoByIcon.markImageByIcon(iconPath, srcImgPath, targerPath2,   
	                -45);   
	  
	    }   
	  
	    /**  
	     * ��ͼƬ���ˮӡ  
	     * @param iconPath ˮӡͼƬ·��  
	     * @param srcImgPath ԴͼƬ·��  
	     * @param targerPath Ŀ��ͼƬ·��  
	     */  
	    public static void markImageByIcon(String iconPath, String srcImgPath,   
	            String targerPath) {   
	        markImageByIcon(iconPath, srcImgPath, targerPath, null);   
	    }   
	  
	    /**  
	     * ��ͼƬ���ˮӡ��������ˮӡͼƬ��ת�Ƕ�  
	     * @param iconPath ˮӡͼƬ·��  
	     * @param srcImgPath ԴͼƬ·��  
	     * @param targerPath Ŀ��ͼƬ·��  
	     * @param degree ˮӡͼƬ��ת�Ƕ�  
	     */  
	    public static void markImageByIcon(String iconPath, String srcImgPath,   
	            String targerPath, Integer degree) {   
	        OutputStream os = null;   
	        try {   
	            Image srcImg = ImageIO.read(new File(srcImgPath));   
	  
	            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),   
	                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);   
	  
	            // �õ����ʶ���   
	            // Graphics g= buffImg.getGraphics();   
	            Graphics2D g = buffImg.createGraphics();   
	  
	            // ���ö��߶εľ��״��Ե����   
	            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   
	                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
	  
	            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg   
	                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);   
	  
	            if (null != degree) {   
	                // ����ˮӡ��ת   
	                g.rotate(Math.toRadians(degree),   
	                        (double) buffImg.getWidth() / 2, (double) buffImg   
	                                .getHeight() / 2);   
	            }   
	  
	            // ˮӡͼ���·�� ˮӡһ��Ϊgif����png�ģ�����������͸����   
	            ImageIcon imgIcon = new ImageIcon(iconPath);   
	  
	            // �õ�Image����   
	            Image img = imgIcon.getImage();   
	  
	            float alpha = 0.5f; // ͸����   
	            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,   
	                    alpha));   
	  
	            // ��ʾˮӡͼƬ��λ��   
	            g.drawImage(img, 150, 300, null);   
	  
	            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));   
	  
	            g.dispose();   
	  
	            os = new FileOutputStream(targerPath);   
	  
	            // ����ͼƬ   
	            ImageIO.write(buffImg, "JPG", os);   
	  
	            System.out.println("ͼƬ������Iconӡ�¡�����������");   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        } finally {   
	            try {   
	                if (null != os)   
	                    os.close();   
	            } catch (Exception e) {   
	                e.printStackTrace();   
	            }   
	        }   
	    }   
	}  

