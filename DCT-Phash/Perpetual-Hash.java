import java.util.*;
import java.awt.Graphics;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class Phash{

	public static void main(String [] ar){
		try{
            DCT dct = new DCT();
            
            String img1 = dct.getHash("TestImage/aa.jpg");
            String img2 = dct.getHash("TestImage/test1.jpg");
            
            int similar = dct.distance(img1,img2);
            System.out.println("Number of bits not matched : "+similar);
          	if(similar<10){
          		System.out.println("Images are similar");
          	}
          	else
          		System.out.println("Images are not similar");
        }catch(Exception e){
            System.out.println(e);
            }
	}
};
