# multimedia-image-upsampling-downsampling
Image upsampling using nearest neighbor and bilinear interpolation. Downsampling using specific pixel and Gaussian smoothing

-----------------------------------------------------
Instructions for compiling and running the program

Program Name:  imageReader

To compile the program type “javac imageReader.java” in command prompt 

To run the program type “java imageReader in_img_name in_img_width in_img_height resampling_method out_size_img”
--------------------------------
Here: 

	in_img_name is the name of the input image file

	in_img_width and in_img_height are the width (4000/400) and height (3000/300) of the input image 

	resampling method is the type of sampling method and it takes values 1 or 2; 

For downsampling an image: resampling methods are –
1.	Specific sampling.
2.	Gaussian smoothing.

For upsampling an image: resampling methods are –
1.	Nearest neighbor sampling.
2.	Bilinear interpolation.

	out_img_size is the size of the output image.  Sizes are given by O1/O2/O3
O1: 1920 x 1080
O2: 1280 x 720
O3: 640 x 480 

For example: To downsample an input image (hw_1_high_res.rgb) with input size 4000 x 3000 to 1920 x 1080 use the following command: 

java imageReader hw_1_high_res 4000 3000 1 O1 
