package com.test.idcard;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class BMPLoader {

	// �ı�ɶ�������  
    public static String[][] getPX(String args) {  
        int[] rgb = new int[3];  
  
        File file = new File(args);  
        BufferedImage bi = null;  
        try {  
            bi = ImageIO.read(file);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        int width = bi.getWidth();  
        int height = bi.getHeight();  
        int minx = bi.getMinX();  
        int miny = bi.getMinY();  
        String[][] list = new String[width][height];  
        for (int i = minx; i < width; i++) {  
            for (int j = miny; j < height; j++) {  
                int pixel = bi.getRGB(i, j);  
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];  
  
            }  
        }  
        return list;  
  
    }  
      
    public static void compareImage(String imgPath1, String imgPath2){  
        String[] images = {imgPath1, imgPath2};  
        if (images.length == 0) {  
            System.out.println("Usage >java BMPLoader ImageFile.bmp");  
            System.exit(0);  
        }  
  
        // ����ͼƬ���ƶ� begin  
        String[][] list1 = getPX(images[0]);  
        String[][] list2 = getPX(images[1]);  
        int xiangsi = 0;  
        int busi = 0;  
        int i = 0, j = 0;  
        for (String[] strings : list1) {  
            if ((i + 1) == list1.length) {  
                continue;  
            }  
            for (int m=0; m<strings.length; m++) {  
                try {  
                    String[] value1 = list1[i][j].toString().split(",");  
                    String[] value2 = list2[i][j].toString().split(",");  
                    int k = 0;  
                    for (int n=0; n<value2.length; n++) {  
                        if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {  
                            xiangsi++;  
                        } else {  
                            busi++;  
                        }  
                    }  
                } catch (RuntimeException e) {  
                    continue;  
                }  
                j++;  
            }  
            i++;  
        }  
  
        list1 = getPX(images[1]);  
        list2 = getPX(images[0]);  
        i = 0;  
        j = 0;  
        for (String[] strings : list1) {  
            if ((i + 1) == list1.length) {  
                continue;  
            }  
            for (int m=0; m<strings.length; m++) {  
                try {  
                    String[] value1 = list1[i][j].toString().split(",");  
                    String[] value2 = list2[i][j].toString().split(",");  
                    int k = 0;  
                    for (int n=0; n<value2.length; n++) {  
                        if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {  
                            xiangsi++;  
                        } else {  
                            busi++;  
                        }  
                    }  
                } catch (RuntimeException e) {  
                    continue;  
                }  
                j++;  
            }  
            i++;  
        }  
        String baifen = "";  
        try {  
            baifen = ((Double.parseDouble(xiangsi + "") / Double.parseDouble((busi + xiangsi) + "")) + "");  
            baifen = baifen.substring(baifen.indexOf(".") + 1, baifen.indexOf(".") + 3);  
        } catch (Exception e) {  
            baifen = "0";  
        }  
        if (baifen.length() <= 0) {  
            baifen = "0";  
        }  
        if(busi == 0){  
            baifen="100";  
        }  
  
        System.out.println("��������������" + xiangsi + " ����������������" + busi + " �����ʣ�" + Integer.parseInt(baifen) + "%");  
  
    }  
	public static void main(String[] args) throws Exception {
		BMPLoader.compareImage("E:\\1.jpg", "E:\\2.jpg");  
		  float percent = compare(getData("E:\\1.jpg"),getData("E:\\2.jpg"));
	        System.out.println("����ͼƬ�����ƶ�Ϊ��"+percent+"%");
	}

	public static int[] getData(String name)throws Exception{
        BufferedImage img = ImageIO.read(new File(name));
        BufferedImage slt = new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
        slt.getGraphics().drawImage(img,0,0,100,100,null);
        //ImageIO.write(slt,"jpeg",new File("slt.jpg"));
        int[] data = new int[256];
        for(int x = 0;x<slt.getWidth();x++){
            for(int y = 0;y<slt.getHeight();y++){
                int rgb = slt.getRGB(x,y);
                Color myColor = new Color(rgb);
                int r = myColor.getRed();
                int g = myColor.getGreen();
                int b = myColor.getBlue();
                data[(r+g+b)/3]++;
            }
        }
        //data ������νͼ��ѧ���е�ֱ��ͼ�ĸ���
        return data;
    }
    public static float compare(int[] s,int[] t){
        float result = 0F;
        for(int i = 0;i<256;i++){
            int abs = Math.abs(s[i]-t[i]);
            int max = Math.max(s[i],t[i]);
            result += (1-((float)abs/(max==0?1:max)));
        }
        return (result/256)*100;
}
}