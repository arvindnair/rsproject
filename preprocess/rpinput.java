
/*Entire code written by Arvind Nair*/
import java.io.*;
import java.util.*;
public class rpinput {

	public static void main(String[] args) throws Exception {/*main method starts*/
		
		int st;
		int end;
		int[] arr=new int[1000];
		
		int c=0;
		int ce=0;
		int c1=0;
		int c2=0;
		int c3=0;
		int[] train=new int[465377];
		int[] valid=new int[99724];
		int[] test=new int[99723];
		
		/*Shuffling the elements upto 664000*/
		for(int i=0;i<664000;i++){
		
			st=i+1;
			arr[c]=st;
			c++;
			if(st%1000==0){
				  c=0;
		
				 Random rnd = new Random();
				    for (int j = arr.length - 1; j >= 0; j--)
				    {
				      int index = rnd.nextInt(j + 1);
				      
				      int a = arr[index];
				      arr[index] = arr[j];
				      arr[j] = a;
				    }
			
				    
				    for(int j=0;j<1000;j++){
				    	
				    	if(j<700){
				    	train[c1]=arr[j];
				    	c1++;
				    	}		
				    	else if(j>=700&&j<850){
				    		valid[c2]=arr[j];
				    		c2++;
				    	}
				    	else if(j>=850&&j<1000){
				    		test[c3]=arr[j];
				    		c3++;
				    	}
				    }
				  
			}
			
		}
		
		/*Shuffling the last elements*/
		int[] arr1=new int[823];
		int icount=0;
		for(int i=0;i<824;i++){
			st=i+664000;
			arr1[c]=st;
			c++;
			icount++;
			if(icount==823){
				c=0;
				
				
				 
				        
					 Random rnd = new Random();
					    for (int j = arr1.length - 1; j >= 0; j--)
					    {
					      int index = rnd.nextInt(j + 1);
					      int a = arr1[index];
					      arr1[index] = arr1[j];
					      arr1[j] = a;
					    }
				    
				    
                    for(int j=0;j<823;j++){
				    	
				    	if(j<577){
				    	train[c1]=arr1[j];
				    	c1++;
				    	}		
				    	else if(j>=577&&j<700){
				    		valid[c2]=arr1[j];
				    		c2++;
				    	}
				    	else if(j>=700&&j<823){
				    		test[c3]=arr1[j];
				    		c3++;
				    	}
				    }
			}
		}
		
		/*Sorting the arrays*/
		Arrays.sort(train);
		Arrays.sort(valid);
		Arrays.sort(test);
	
		
		int rcount=0;
		int[][] ratingfile=new int[664824][3];
		Scanner sc=new Scanner(new File("ratings_data_.txt"));
		while(sc.hasNextLine()){
			String countn=sc.nextLine();
			String cntemp[]=countn.split(" ");
			
			int[] cntemp1=new int[cntemp.length];
		       for(int i=0;i<cntemp.length;i++){
		       	  cntemp1[i]=Integer.parseInt(cntemp[i]);
		        }
		   
			ratingfile[rcount][0]=cntemp1[0];
			ratingfile[rcount][1]=cntemp1[1];
			ratingfile[rcount][2]=cntemp1[2];
		    rcount++;
		}
		
		/*Output to train file*/
		File outFile = new File ("trainingdataepinion.txt");
	    FileWriter fWriter = new FileWriter (outFile);
	    PrintWriter pWriter = new PrintWriter (fWriter);
		for(int i=0;i<train.length;i++){
			
		    
		   
		    pWriter.print(ratingfile[train[i]][0]);
		    pWriter.print(" ");
		    pWriter.print(ratingfile[train[i]][1]);
		    pWriter.print(" ");
		    pWriter.print(ratingfile[train[i]][2]);
		    pWriter.println(" ");
		}
		 pWriter.close();
		
		
		 /*Output to validation file*/
		File outFile1 = new File ("validationdataepinion.txt");
	    FileWriter fWriter1 = new FileWriter (outFile1);
	    PrintWriter pWriter1 = new PrintWriter (fWriter1);
for(int i=0;i<valid.length;i++){
			
	pWriter1.print(ratingfile[valid[i]][0]);
	pWriter1.print(" ");
	pWriter1.print(ratingfile[valid[i]][1]);
	pWriter1.print(" ");
	pWriter1.print(ratingfile[valid[i]][2]);
	pWriter1.println(" ");
		}

pWriter1.close();

/*Output to test file*/
File outFile2 = new File ("testingdataepinion.txt");
FileWriter fWriter2 = new FileWriter (outFile2);
PrintWriter pWriter2 = new PrintWriter (fWriter2);

         for(int i=0;i<test.length;i++){
 			
        	 pWriter2.print(ratingfile[test[i]][0]);
        	 pWriter2.print(" ");
        	 pWriter2.print(ratingfile[test[i]][1]);
        	 pWriter2.print(" ");
        	 pWriter2.print(ratingfile[test[i]][2]);
        	 pWriter2.println(" ");
 		}
         
         pWriter2.close();
	}/*end of main method*/

}

