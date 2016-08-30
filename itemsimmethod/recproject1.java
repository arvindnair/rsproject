
import java.io.*;
import java.util.*;


/*For Candidate set*/
class candidate{
	int item1val=0;
	double cosineval=0.000;
}

/*For cosine similarity table which stores similarities of all items wrt each other*/
class cosine{
	int item1val=0;
	int item2val=0;
	double cosineval=0.000;
}

public class recproject1 {

	static int users=40163;
	static int items=140000;
	static int ratings=465377;
	static int limit=200;
	static int N=0;
	static int k=0;
	static int userst=40163;
	static int ratingst=99723;
	
	public static void main(String[] args) throws Exception {
		
		/*Timing done by Arvind Nair*/
		 long startTimefull=System.currentTimeMillis();
		
		 /*CSR Input done by Hitendra Rathod*/
		 /*Take input from Training Dataset*/
		Scanner sc=new Scanner(new File("trainingdataepinion.txt"));
		
		int[] arr1=new int[3];
		for(int i=0;i<arr1.length;i++){
        	arr1[i]=0;
        }
		
		int[] row_ptr=new int[users+1];
		int[] col_ind=new int[ratings];
		int[] val=new int[ratings];
		
		
		int rc=0,cc=0,vc=0;
		while(sc.hasNextLine()){
			String countn=sc.nextLine();
			String cntemp[]=countn.split(" ");
		    int[] arr=new int[cntemp.length];
	        
			for(int i=0;i<cntemp.length;i++){
	        	arr[i]=Integer.parseInt(cntemp[i]);
	        }
			
			
			if(arr[0]!=arr1[0]){
				
				
				
				if((arr[0]-arr1[0])>0){
					int x=rc;
				for(int i=x;i<arr[0];i++){
					row_ptr[i]=vc;
					rc++;
				}
				}
				
				else{
				row_ptr[rc]=vc;
                rc++;
                
				}
			}
			
			val[vc]=arr[2];
			col_ind[vc]=arr[1];
			vc++;
			
			arr1=arr.clone();
			
			
		}
		row_ptr[rc]=vc;

		
		/*CSR Transpose done by Arvind Nair*/
		/*Training Dataset Transpose*/
		int[] valT=new int[val.length];
		int[] col_indT=new int[col_ind.length];
		int[] row_ptrT=new int[items+1];//one extra than no. of items
		
		row_ptrT[0]=0;//starts from zero
		int c2=0;
		int c1=0;
		for(int k=0;k<items;k++){//for each item
			
			if(k%500==0){
				System.out.println("k: "+k);
			}
				
			for(int j=0;j<col_ind.length;j++){
				if((k+1)==col_ind[j]){
					valT[c1]=val[j];
					boolean flag1=true;
					int j1=0;
					while(j1<row_ptr.length-1&&flag1==true){
					     int r1=row_ptr[j1];
					     int r2=row_ptr[j1+1];
							
					    	 if((j>=r1)&&(j<r2)){
					    		 col_indT[c1]=j1+1;
					    		 c1++;
					    		 flag1=false;
					    	  }
							j1++;
					    }
					}
			}
			c2++;
			row_ptrT[c2]=c1;
		}
		
		
		/*Input done by Hitendra Rathod*/
		System.out.println("CSR Transpose done!");

		/*Taking K and N input values*/
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
  	     System.out.println("Enter the K value for K most unpurchased similar items: ");
  	     k=Integer.parseInt(br.readLine());
  	     
  	   System.out.println("Enter the N value for N candidate set: ");
	     N=Integer.parseInt(br.readLine());
		
	     /*Cosine table generation done by Hitendra Rathod*/
		/*Cosine table generation*/
	     long startTime=System.currentTimeMillis();
		cosine[][] cosinetable=new cosine[items][limit];
		
		for(int i=0;i<items;i++){
			for(int j=0;j<limit;j++){
			cosinetable[i][j]=new cosine();
			}
		}
		
		for(int i=0;i<row_ptrT.length-1;i++){
			
			cosine[] cosinetemp=new cosine[items];
			for(int j=0;j<items;j++){
				cosinetemp[j]=new cosine();
				}
			if(i%500==0){
				System.out.println("i: "+i);
			}
			boolean itemmissing=false;
			if((row_ptrT[i+1]-row_ptrT[i])==0){
				
				itemmissing=true;
				for(int j=0;j<limit;j++){
					cosinetable[i][j].item1val=i+1;
					cosinetable[i][j].item2val=j+1;
					cosinetable[i][j].cosineval=0;
					
				}
			}//item missing loop
			
			if(itemmissing==false){//item not missing
			int[] a=new int[row_ptrT[i+1]-row_ptrT[i]];
			for(int c3=0,c4=row_ptrT[i];c3<a.length&&c4<row_ptrT[i+1];c3++,c4++){
				
				a[c3]=col_indT[c4];
			}
			
			
			for(int j=0;j<row_ptrT.length-1;j++){
				
				
				
				boolean itemsame=false;
				if(i==j){
					itemsame=true;
					cosinetemp[j].item1val=i+1;
					cosinetemp[j].item2val=j+1;
					cosinetemp[j].cosineval=0;
				}
				
				if(itemsame==false){
					boolean fillafteritems=false;
					if(row_ptrT[j+1]-row_ptrT[j]==0){
						fillafteritems=true;
						cosinetemp[j].item1val=i+1;
						cosinetemp[j].item2val=j+1;
						cosinetemp[j].cosineval=0;
					}
					
					
					if(fillafteritems==false){
					int[] b=new int[row_ptrT[j+1]-row_ptrT[j]];
					
					for(int c3=0,c4=row_ptrT[j];c3<b.length&&c4<row_ptrT[j+1];c3++,c4++){
						
						b[c3]=col_indT[c4];
					}
				    
					double countersim=0;
					for(int c3=0;c3<a.length;c3++){
						
						for(int c4=0;c4<b.length;c4++){
							if(a[c3]==b[c4]){
								countersim++;
								break;
							}
						}
					}
					
					double s = (double)(a.length*b.length);
					cosinetemp[j].item1val=i+1;
					cosinetemp[j].item2val=j+1;
					cosinetemp[j].cosineval=countersim/s;
				
				}
				}
				
			}
		}//item not missing
		
			sort1(cosinetemp);
			for(int c3=0;c3<limit;c3++){
				
				cosinetable[i][c3]=cosinetemp[c3];
			}
			
		}
		
		/*Timing done by Arvind Nair*/
		long endTime=System.currentTimeMillis();
		float sec=0;
		sec=(endTime-startTime)/1.0f;
		System.out.print("The Item Item Similarity Generation Execution took: ");
    	System.out.format("%.2f",sec);
    	System.out.println("ms");
		
	
    	 /*CSR Input done by Hitendra Rathod*/
Scanner sct=new Scanner(new File("testingdataepinion.txt"));
		
		int[] arr1t=new int[3];
		for(int i=0;i<arr1t.length;i++){
        	arr1t[i]=0;
        }
		
		int[] row_ptrt=new int[userst+1];
		int[] col_indt=new int[ratingst];
		int[] valt=new int[ratingst];
		
		
		int rct=0,cct=0,vct=0;
		while(sct.hasNextLine()){
			String countnt=sct.nextLine();
			String cntempt[]=countnt.split(" ");
		    int[] arrt=new int[cntempt.length]; 
	        
			for(int i=0;i<cntempt.length;i++){
	        	arrt[i]=Integer.parseInt(cntempt[i]);
	        }
			
			if(arrt[0]!=arr1t[0]){
				
				if((arrt[0]-arr1t[0])>0){
					int x=rct;
				for(int i=x;i<arrt[0];i++){
					row_ptrt[i]=vct;
					rct++;
				}
				}
				
				else{
				row_ptrt[rct]=vct;
                rct++;
                
				}
			}
			
			valt[vct]=arrt[2];
			col_indt[vct]=arrt[1];
			vct++;
			
			arr1t=arrt.clone();
			
			
		}
		row_ptrt[rct]=vct;
		
		
		double hits=0;
 	     double rank=0.0;
 	     
 	    /*Timing done by Arvind Nair*/
 	    long startTime1=System.currentTimeMillis();
 	     
 	     /*Candidate set generation*/
 	   /*Candidate set generation done by Hitendra Rathod*/
 	     for(int i=0;i<row_ptr.length-1;i++){
				
				if(i%500==0){
					System.out.println("c: "+i);
				}
				if((row_ptr[i+1]-row_ptr[i])!=0&&(row_ptrt[i+1]-row_ptr[i]!=0)){
				candidate[] cand=new candidate[k*(row_ptr[i+1]-row_ptr[i])];
				for(int j=0;j<cand.length;j++){
					cand[j]=new candidate();
					}
				
				cosine[][] cosinetabletemp=new cosine[(row_ptr[i+1]-row_ptr[i])][k];
			
				for(int j=0;j<(row_ptr[i+1]-row_ptr[i]);j++){
					for(int j1=0;j1<k;j1++){
						cosinetabletemp[j][j1]=new cosine();
					}
				}
			
				int[] a1=new int[(row_ptr[i+1]-row_ptr[i])];
				
				int c5=0;
				for(int j=row_ptr[i];j<row_ptr[i+1];j++){
					a1[c5]=col_ind[j];
					c5++;
				}
				int j5=0;
				for(int j=0;j<a1.length;j++){
					int d=a1[j];
					for(int j1=0;j1<limit;j1++){
						
						boolean check1=false;
						for(int j2=0;j2<a1.length;j2++){
							
							if(cosinetable[d-1][j1].item2val==a1[j2]){
								check1=true;
								break;
							}
						}
						
						if(check1==false){
						cosinetabletemp[j][j5].item1val=cosinetable[d-1][j1].item1val;
						cosinetabletemp[j][j5].item2val=cosinetable[d-1][j1].item2val;
						cosinetabletemp[j][j5].cosineval=cosinetable[d-1][j1].cosineval;
					    j5++;
						}
						
						if(j5==k){
					    	j5=0;
							break;
						}
					}
				}
				
				
				int c4=0;
				for(int j=0;j<a1.length;j++){
					
					for(int j1=0;j1<k;j1++){
						int var=cosinetabletemp[j][j1].item2val;
						boolean checkpresent=false;
						for(int j2=0;j2<a1.length&&cand[j2].item1val!=0;j2++){
							if(cand[j2].item1val==var){
								checkpresent=true;
								break;
							}
						}
						if(checkpresent==false){
							
							cand[c4].item1val=var;
							 
							for(int j2=1;j2<(row_ptr[i+1]-row_ptr[i]);j2++){
							
								for(int j3=0;j3<k;j3++){
									if(var==cosinetabletemp[j2][j3].item2val){
										cand[c4].cosineval=cand[c4].cosineval+cosinetabletemp[j2][j3].cosineval;
										break;
									}
								}
							}c4++;
						}
					}
				}
				
				
			
				sort(cand);
				
				/*HR and ARHR calculation done by Hitendra Rathod*/
				int[] testitems=new int[row_ptrt[i+1]-row_ptrt[i]];
				int e2=0;
				for(int e1=row_ptrt[i];e1<row_ptrt[i+1];e1++){
				
				for(int k1=0;k1<N;k1++){
					if(col_indt[e1]==cand[k1].item1val){
					   hits++;
       		       rank=rank+(1/(k1+1));
       		       break;
					}
				}
				}
				
			}
				
			}
			 double hr=(double)hits/users;//typecasting for accuracy
	         double arhr=(double)rank/users;
	         System.out.println("HR is: "+hr);
	         System.out.println("ARHR: "+arhr);	
	         
	         System.out.println("k: "+k+" N: "+N);
	        		
	         /*Timing done by Arvind Nair*/
	         long endTime1=System.currentTimeMillis();
	 		float sec1=0;
	 		sec1=(endTime1-startTime1)/1.0f;
	 		System.out.print("The Candidate Set Generation Generation Execution took: ");
	     	System.out.format("%.2f",sec1);
	     	System.out.println("ms");
	     	
	     	long endTimefull=System.currentTimeMillis();
			float secfull=0;
			secfull=(endTimefull-startTimefull)/1.0f;
			System.out.print("The Complete Program Execution took: ");
	    	System.out.format("%.2f",secfull);
	    	System.out.println("ms");
		
	}

	
	/*Merge sort algorithm template for cosine table elements sorting by Arvind Nair values changed appropriately by Hitendra Rathod*/
	public static void sort1(cosine[] cosinetemp){
        cosine[] tempArray = new cosine[cosinetemp.length];
        for(int i=0;i<cosinetemp.length;i++){
    		tempArray[i]=new cosine();
    	}
		  mergeSort1(tempArray,0,cosinetemp.length-1,cosinetemp);
       return;
       }
		      public static void mergeSort1(cosine[] tempArray,int lowerIndex,int upperIndex,cosine[] cosinetemp){
		          if(lowerIndex == upperIndex){
		              return;
		          }
		          else{
		              int mid = (lowerIndex+upperIndex)/2;
		              mergeSort1(tempArray, lowerIndex, mid, cosinetemp);
		              mergeSort1(tempArray, mid+1, upperIndex, cosinetemp);
		              merge1(tempArray,lowerIndex,mid+1,upperIndex,cosinetemp);
		          }
		      }
		      public static void merge1(cosine[] tempArray,int lowerIndexCursor,int higherIndex,int upperIndex, cosine[] cosinetemp){
		          int tempIndex=0;
		          int lowerIndex = lowerIndexCursor;
		          int midIndex = higherIndex-1;
		          int totalItems = upperIndex-lowerIndex+1;
		          while(lowerIndex <= midIndex && higherIndex <= upperIndex){
		        	  if(cosinetemp[lowerIndex].cosineval > cosinetemp[higherIndex].cosineval){
		            	  tempArray[tempIndex].cosineval = cosinetemp[lowerIndex].cosineval;
		            	  tempArray[tempIndex].item1val = cosinetemp[lowerIndex].item1val;
		            	  tempArray[tempIndex].item2val = cosinetemp[lowerIndex].item2val;
		            	  tempIndex++;
		            	  lowerIndex++;
		                  }
		              else{
		                  tempArray[tempIndex].cosineval = cosinetemp[higherIndex].cosineval;
		                  tempArray[tempIndex].item1val = cosinetemp[higherIndex].item1val;
		                  tempArray[tempIndex].item2val = cosinetemp[higherIndex].item2val;
		                  tempIndex++;
		                  higherIndex++;
		              }
		          }
		          while(lowerIndex <= midIndex){
		              tempArray[tempIndex].cosineval = cosinetemp[lowerIndex].cosineval;
		              tempArray[tempIndex].item1val = cosinetemp[lowerIndex].item1val;
		              tempArray[tempIndex].item2val = cosinetemp[lowerIndex].item2val;
		              tempIndex++;
		              lowerIndex++;
		          }
		          while(higherIndex <= upperIndex){
		        	  tempArray[tempIndex].cosineval = cosinetemp[higherIndex].cosineval;
		        	  tempArray[tempIndex].item1val = cosinetemp[higherIndex].item1val;
		        	  tempArray[tempIndex].item2val = cosinetemp[higherIndex].item2val;
		              tempIndex++;
		              higherIndex++;
		          }
		          for(int i=0;i<totalItems;i++){
		        	  cosinetemp[lowerIndexCursor+i].cosineval = tempArray[i].cosineval;
		        	  cosinetemp[lowerIndexCursor+i].item1val = tempArray[i].item1val;
		        	  cosinetemp[lowerIndexCursor+i].item2val = tempArray[i].item2val;
		          }
		      }
		      
		      /*Merge sort algorithm template for candidate set elements sorting by Arvind Nair values changed appropriately by Hitendra Rathod*/
		      public static void sort(candidate[] cand1){
		          candidate []tempArray = new candidate[cand1.length];
		          for(int k=0;k<cand1.length;k++){
		   		   tempArray[k]=new candidate();
		   	   }
				  mergeSort(tempArray,0,cand1.length-1,cand1);
		         return;
			  }
				      public static void mergeSort(candidate[] tempArray,int lowerIndex,int upperIndex,candidate[] cand1){
				          if(lowerIndex == upperIndex){
				              return;
				          }
				          else{
				              int mid = (lowerIndex+upperIndex)/2;
				              mergeSort(tempArray, lowerIndex, mid, cand1);
				              mergeSort(tempArray, mid+1, upperIndex, cand1);
				              merge(tempArray,lowerIndex,mid+1,upperIndex,cand1);
				              }
				          }
				      public static void merge(candidate[] tempArray,int lowerIndexCursor,int higherIndex,int upperIndex, candidate[] cand1){
				          int tempIndex=0;
				          int lowerIndex = lowerIndexCursor;
				          int midIndex = higherIndex-1;
				          int totalItems = upperIndex-lowerIndex+1;
				          while(lowerIndex <= midIndex && higherIndex <= upperIndex){
				              if(cand1[lowerIndex].cosineval > cand1[higherIndex].cosineval){
				                  tempArray[tempIndex].cosineval = cand1[lowerIndex].cosineval;
				                  tempArray[tempIndex].item1val = cand1[lowerIndex].item1val;
				                  lowerIndex++;
				                  tempIndex++;
		                      }
				              else{
				                  tempArray[tempIndex].cosineval = cand1[higherIndex].cosineval;
				                  tempArray[tempIndex].item1val = cand1[higherIndex].item1val;
				                  higherIndex++;
				                  tempIndex++;
				              }
				          }
				          while(lowerIndex <= midIndex){
				              tempArray[tempIndex].cosineval = cand1[lowerIndex].cosineval;
				              tempArray[tempIndex].item1val = cand1[lowerIndex].item1val;
				              tempIndex++;
				              lowerIndex++;
				          }
				          while(higherIndex <= upperIndex){
				              tempArray[tempIndex].cosineval = cand1[higherIndex].cosineval;
				              tempArray[tempIndex].item1val = cand1[higherIndex].item1val;
				              tempIndex++;
				              higherIndex++;
				          }
				          for(int i=0;i<totalItems;i++){
				              cand1[lowerIndexCursor+i].cosineval = tempArray[i].cosineval;
				              cand1[lowerIndexCursor+i].item1val = tempArray[i].item1val;
				          }
				      }
}

