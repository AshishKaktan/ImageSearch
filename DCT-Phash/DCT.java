import java.awt.Graphics2D;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.*;
import javax.imageio.ImageIO;

public class DCT {
 
        public int ImageSize = 64;
        public int size = 16;
       

        double[] c;
        void init() {
            c = new double[ImageSize];
               
            for (int i=1;i<size;i++) {
                c[i]=1;
            }
            c[0]=1/Math.sqrt(2.0);
        }
       
        public double[][] applyDCT(double[][] f) {
                int N = ImageSize;
               
                double[][] F = new double[N][N];
                for (int u=0;u<N;u++) {
                    for (int v=0;v<N;v++) {
                        double sum = 0.0;
                        for (int i=0;i<N;i++) {
                            for (int j=0;j<N;j++) {
                                sum+=Math.cos(((2*i+1)/(2.0*N))*u*Math.PI)*Math.cos(((2*j+1)/(2.0*N))*v*Math.PI)*(f[i][j]);
                            }
                        }
                        sum*=((c[u]*c[v])/4.0);
                        F[u][v] = sum;
                    }
                }
                return F;
        } 

        static BufferedImage resize(String inputImagePath, int scaledWidth, int scaledHeight) throws IOException {
    
            File inputFile = new File(inputImagePath);
            BufferedImage inputImage = ImageIO.read(inputFile);
 
            BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();
 
            return outputImage;
        }

        static BufferedImage togray(BufferedImage img){

            for(int i=0;i<32;i++){
                for(int j=0;j<32;j++){
                    int pixel = img.getRGB(j,i);
                    Color c = new Color(pixel);
                    int r = (int)(c.getRed()*0.299);
                    int b = (int)(c.getBlue()*0.587);
                    int g = (int)(c.getGreen()*0.114);
                    Color cg = new Color(r+b+g,g+r+b,b+r+g);
                    img.setRGB(j,i,cg.getRGB());
                }
            }
            return img;
        }

        public int distance(String s1, String s2) {
                int counter = 0;
                for (int k = 0; k < s1.length();k++) {
                        if(s1.charAt(k) != s2.charAt(k)) {
                                counter++;
                        }
                }
                return counter;
        }
       
        public String getHash(String input) throws Exception {        // return Binary String
                
                File file = new File(input);
                BufferedImage img = ImageIO.read(file);
        
                img = resize(input, ImageSize, ImageSize);
                img = togray(img);
            
                double[][] color_array = new double[ImageSize][ImageSize];
               
                for(int i=0;i<32;i++){
                    for(int j=0;j<32;j++){
                        int pixel = img.getRGB(j,i);
                        Color c = new Color(pixel);
                        int r = (int)c.getRed();
                        int g = (int)c.getGreen();
                        int b = (int)c.getBlue();
                        //System.out.print ("("+r+" "+g+" "+b+")");
                        color_array[i][j]=r;
                    }
                }
                
                init();
                double[][] dct_array = applyDCT(color_array);
                double total = 0;
               
                for (int x = 0; x < size; x++) {
                        for (int y = 0; y < size; y++) {
                                total += dct_array[x][y];
                        }
                }
                total -= dct_array[0][0];
               
                double avg = total / (double) ((size * size) - 1);
                String hash = "";
               
                for (int x = 0; x < size; x++) {
                        for (int y = 0; y < size; y++) {
                                if (x != 0 && y != 0) {
                                        hash += (dct_array[x][y] > avg?"1":"0");
                                }
                        }
                }
               
                return hash;
        }
}