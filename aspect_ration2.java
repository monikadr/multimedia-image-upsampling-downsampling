// second type of retaining aspect ration

 public static BufferedImage specific(int width, int height, BufferedImage img,int new_width, int new_height, byte[] bytes)
   {
	   

	   float old_ar = (float)width / (float)height;
	   float new_ar = (float)new_width / (float)new_height;
	
	   System.out.println(new_width +" "+width+" "+height);
	   
		  float nh = height * ((float)((float)new_width/(float)width));
		 float nw = new_width;
		   System.out.println("inside if: "+nh);
	   
	   if(nh > new_height)
	   {
		   nw = nw * (float)(new_height/nh);
		   nh = new_height;
	   }
	   System.out.println(nw +" "+ nh);
	   BufferedImage img1 = new BufferedImage((int)nw, (int)nh, BufferedImage.TYPE_INT_RGB);
	   Graphics2D g = img1.createGraphics();
		g.drawImage(img, 0, 0, (int)nw, (int)nh, null);
		g.dispose();
	  
// 		for(int y = 0; y < new_height; y++){
// 			
// 			for(int x = 0; x < new_width; x++){
// 				
// 				int pix = img.getRGB(x,y);
// //			      int pix = 0xff000000 | (((int)r & 0xff) << 16) | (((int)g & 0xff) << 8) | ((int)b & 0xff);
// 				img1.setRGB(x,y,pix);
//
// 			}
// 		}



		return img1;
   }
