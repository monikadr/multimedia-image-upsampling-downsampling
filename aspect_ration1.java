// aspect ratio maintaining down sampling


public static BufferedImage specific(int width, int height, BufferedImage img,int new_width, int new_height, byte[] bytes)
   {
	   
	   int neww, newh = 0;
	   float rw = (float) width / (float)new_width; // width and height are maximum thumbnail's bounds
	   float rh = (float) height / (float) new_height;
	   System.out.println(rh);

	   if (rw > rh)
	   {
	       newh = Math.round(height / rw);
	       neww = new_width;
	       System.out.println("inside if "+newh+" "+neww);
	   }
	   else
	   {
		   System.out.println(width+" "+rh);
	       neww = Math.round(width / rh);
	       newh = new_height;
	       System.out.println("inside else : "+newh+" "+neww);
	   }
	   System.out.println(newh+" "+neww);
	   BufferedImage img1 = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_RGB);
	   Graphics2D g = img1.createGraphics();
		g.drawImage(img, 0, 0, neww, newh, null);
		g.dispose();
	
// 		for(int y = 0; y < new_height; y++){
// 			
// 			for(int x = 0; x < new_width; x++){
//// 				int a = (int)(Math.random()*256); //alpha
//// 		         int r = (int)(Math.random()*256); //red
//// 		         int g = (int)(Math.random()*256); //green
//// 		         int b = (int)(Math.random()*256); //blue
// 		 
// 			
// 				int pix = img.getRGB(x,y);
//// 			      int pix = 0xff000000 | (((int)r & 0xff) << 16) | (((int)g & 0xff) << 8) | ((int)b & 0xff);
// 				img1.setRGB(x,y,pix);
//
// 			}
// 		}

	   // just to get the size of the original image
	  

		return img1;
   }
