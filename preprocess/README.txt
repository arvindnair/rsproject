README File for running the program of Pre-processing of Epinion Dataset.

1. For the pre-processing of Epinion dataset, just run the rpinput.java file.
2. The program is written in Java so you need a Java Runtime Environment.
3. Place the ratings_data_.txt in the same folder as that of the rpinput file.
4. On command line specify the path of JRE to the folder which contains rpinput.
5. javac is used to compile the code and java is used to run the code.

Commands to type for Running the Program:
1. javac rpinput.java
2. java rpinput

Example:
1. The ratings_data_.txt must be in the same folder(preprocess) as that of the rpinput.java file.
2. Open (command prompt)cmd and set the path in the folder datapreprocess to (location where Java is installed)/Java/jdk(version)/bin and press Enter.
3. Type javac rpinput.java and press Enter.
4. Type java rpinput and press Enter.
5. The program will generate 3 output files i.e., trainingdataepinion.txt, validationdataepinion.txt and testingdataepinion.txt.
6. These 3 files are the training, validation and testing files respectively.

Note:
1. Do not use these files for testing our code as they will be generated different each time as we have used shuffling and randomization.
2. Please use the provided trainingdataepinion.txt, validationdataepinion.txt and testingdataepinion.txt files in the respective folders which are provided to verify the results as all the programs have used the same ones.
3. The program has just been provided for reference which can be run to show how we implemented the pre-processing of Epinion Dataset.
