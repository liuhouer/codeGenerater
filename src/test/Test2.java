package test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Test2
{
	
	public static void main(String aa[])
	{

	
	
		 String s = "the instruction set of the Java virtual machine distinguishes its operand types using instructions intended to operate on values of specific types";
	        String[] word = s.split(" ");
	        Map<String,Integer> m = new HashMap<String,Integer>();
	        //用word初使化m，m中包含了所有不重复的单词
	        for(int j=0;j<word.length;j++){
	            m.put(word[j],0);
	        }
	         
	        Set<String> set = m.keySet(); 
	        //用word中的每个单词与m中的单词比较，发现相同的就统计一次    
	        for(int i=0;i<word.length;i++){
	        Iterator<String> it = set.iterator();
	         while(it.hasNext()){
	              String k = it.next();
	              if(word[i].equals(k)){
	                    int c = m.get(k);                  
	                    c++;
	                    m.put(word[i],c);
	                }
	            }                          
	        }
	        System.out.println(m);
	    }
	
	
	
	
	
	
	

}