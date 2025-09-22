**Overview of the project:**
For the AIML project of Y2S1, we decided to create a wildfire prediction AI. With the rapid increase of global warming, wildfires are more common than ever. In fact, February of 2025 a massive fire broke out in ella causing 600 acres of forest to be burnt to the ground. By the time the fire was noticed by the locals, atleast 10 acres were burnt. 
Hence because of how unreliant it is to completely rely on humans eyes to catch fire, we turned to satellites, commercial satellites to be exact. As of now, there's about 12,000 commercial satellites in our orbit. They fly closer to the earth's surface, hence has a high resolution. Using them, a wildfire the size of a parking lot can be detected. Therefore, we decided to build an AI that could differentiate between wildfires and non using commercial satellite imagery.

**Dataset details**
link: https://www.kaggle.com/datasets/abdelghaniaaba/wildfire-prediction-dataset
This dataset was obtained using kaggle. It provides 22710 satellite images with a wildfire and  20140 satellite images with no wildfire. The dataset is divided in 70% training data, 15% test data and 15% validate data. All images are 350px by 350px in height and width.

**group member roles**
IT24103971 - Aruniya N. was in charge of making sure there were no duplicates because during data transfer due to slip ups the dataset may contain the same file with the same name multiple times. This caused the other pre-processing techniques to fail when trying to load the image because there were multiple files with the same path. Hence, we got rid of them beforehand.
IT24103947 - Walimuniarachchi W.A.K.S 's role was to getting rid of corrupt files so that it will not cause the other pre processing techniques to halt due to the corrupt file.
IT24103863 - Weerakoon N.B took care of resizing the images to a smaller size (250px X 250px) so that the model would work better with lower quality images, and would need to store smaller values during training making it faster.
IT24103874 - Rathnayake O.M augmented the images with respect to brightness and contrast to make the dataset more diverse so that the model can still identify the wildfire when there is less light (brightness) and lower quality images (contrast).
IT24103988 - Wickramarathna W.G.S.A.S carried out the normalizing of the images, turning the numbers between 0 to 255 of a pixel to a number between 0 to 1.
IT24103878 - Amarakoon M.B.V.B.A finally encoded the images, 0 for no wildfire and 1 for wildfire.

**How to run code**
Step 1: Make sure all the necessary libraries are installed, including os, cv2, shutil, numpy, matplotlib.pyplot, seaborn, warnings, Image, random, and collections.
Step 2: Download the data file and store it within your disk.
Step 3: Replace the [] in each preprocessing technique with the path to your data file. example: "[]/train" => "users/IT12345678/Documents/AIML/data/train"
Step 4: Run the code in the group_pipeline.ipnyb file from top to bottom.
