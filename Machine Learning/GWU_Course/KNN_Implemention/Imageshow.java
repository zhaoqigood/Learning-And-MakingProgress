/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 *
 * @author zhaoq
 */
public class Imageshow {

    /**
     * @param args the command line arguments
     */
    public BufferedImage showImage(int[] array) {
        int width = 28;
        int height = 28;
        BufferedImage buffImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = (Graphics2D)buffImage.getGraphics();
        Color[] color = new Color[256];
        for(int i =0; i<256;i++){
            color[i] = new Color(i,i,i);
        }
        for(int i = 0; i < 784; i++){
            int x = i%width;
            int y = i/width;
            g.setColor(color[array[i]]);
            g.fillRect(x, y, 1, 1);
        }
		return buffImage;
    }
    public static void main(String[] args) throws IOException{
		BufferedImage[][] images = new BufferedImage[10][10];
        Imageshow image = new Imageshow();
        String csvFile = "C:\\Study materials\\Third semester\\machine learning2\\week 2\\draw.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            for(int i = 0;i<10;i++){
				for(int j = 0;j<10;j++){
                    if((line = br.readLine()) != null){
                        String[] test1  = line.split(cvsSplitBy);
                        int[] test = new int[test1.length];
		                for(int k = 0;k<784;k++){
		                    test[k] = Integer.parseInt(test1[k]);
		                }
                        images[i][j] = image.showImage(test);
                    }
				}
			}
		    BufferedImage newImage = new BufferedImage(280,280,BufferedImage.TYPE_BYTE_GRAY);
			for(int i = 0;i<280;i++){
				for(int j = 0; j< 280; j++){
                    int row = i/28;
				    int column = j/28;
					int row1 = i%28;
					int column1 = j%28;
				    int rgb = images[row][column].getRGB(row1,column1);
					newImage.setRGB(i,j,rgb);
				}
            }
            try{
                File file = new File("matrix.jpg");
		        if(!file.exists()){
			        file.createNewFile();
                }
                ImageIO.write(newImage, "jpg", file);
            }catch(IOException e){
			    e.printStackTrace();
		    }
			
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

// TODO code application logic here
    }
    
}
