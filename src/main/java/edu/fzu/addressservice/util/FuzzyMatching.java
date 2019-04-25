package edu.fzu.addressservice.util;


/**
 * @Author CH
 * @Description //TODO 
 * @Date 16:47 2019/4/22
 **/
public class FuzzyMatching {
	/**
	 * @description 模糊匹配
	 * @param standardaddress 标准地址
	 * @param realaddress  待匹配的真实地址
	 * @param state 0：直接进行模糊匹配，1：对匹配地址删除镇等关键词后再模糊匹配
	 * @return 模糊匹配成功返回true，否则返回false
	 */
	public boolean fuzzyMatch(String standardaddress, String realaddress,int state) {
		boolean flag = false;
		int lcs=0;
		String[] str={"镇","街道","乡","地区办事处","街道办事处","街办事处","办事处","区","县","市"};
		
		if((!"null".equals(standardaddress))&&(!"null".equals(realaddress))
				&& standardaddress != null && realaddress != null){
			if(standardaddress.equals(realaddress)) {
				flag =true;
				return flag;
			}
			char[] a =standardaddress.toCharArray();
	        char[] b =realaddress.toCharArray();
//	        int an=a.length;
//	        int bn=b.length;
	        
	        /*如果输入字符串长度小于2，直接返回false*/
	        if(b.length<2){
	        	if(standardaddress.equals(realaddress)) flag =true;
	        	return flag;
	        }
	        lcs =LCS(a,b);
	       // lcs=minDistance(standardaddress,realaddress );
	        if(state ==0){
		       //调用最长公共子序列模糊匹配算法
		        flag = matching(a,b,lcs);
		        if(flag==false){
		        	flag = isAutonomousRegion(standardaddress,realaddress,lcs);	
		        }
		        
	        }else if(state ==1){
	        	if((!(flag = matching(a,b,lcs)))&&(!(flag = isAutonomousRegion(standardaddress,realaddress,lcs)))){
		        	String temp = null;
		        	boolean standardaddressflag=false;
	        		boolean realaddressflag=false;
		        	for(int i=0;i<str.length;i++){
		        		temp = str[i];
		        		int templength = temp.length();
		        		if(standardaddress.endsWith(temp)){
		        			if(!standardaddressflag){
		        				//切除特定结尾词
			        			standardaddress=standardaddress.substring(0, (standardaddress.length()-templength));
			        			standardaddressflag=true;
		        			}
		        			
		        		}
		        		//判断是否以字符串数组中Str的特定词为结尾
		        		if(realaddress.endsWith(temp)){
		        			if(!realaddressflag){
		        				//切除特定结尾词
			        			realaddress=realaddress.substring(0, (realaddress.length()-templength));
			        			realaddressflag=true;
		        			}
		        			
		        		}
		        		if(standardaddress.equals(realaddress)){
		        			flag=true;
		        		}
		        	}
	        	}
	        }
	        
		}
		//返回flag
        return flag;
    
	}
	/**
	 * 
	 * @param a 字符串a
	 * @param b	字符串b
	 * @return 最长公共子序列长度
	 */
	public int LCS(char[] a, char[]b) {
		boolean flag = false;
        int an=a.length;
        int bn=b.length;  
        /*
         * 最长公共子序列
         * 
         * */
        int[][] dp=new int[an+1][bn+1];
        for(int i=0;i<=an;i++){
            dp[i][bn]=0;
        }
        for(int i=0;i<=bn;i++){
            dp[an][i]=0;
        }
        for(int i=an-1;i>=0;i--){
            for(int j=bn-1;j>=0;j--){
                if(a[i]==b[j]){
                    dp[i][j]=dp[i+1][j+1]+1;
                }else{
                    if(dp[i][j+1]>dp[i+1][j])
                        dp[i][j]=dp[i][j+1];
                    else
                        dp[i][j]=dp[i+1][j];
                }
            }
        }
        
       
    //返回最长公共子序列长度
	return dp[0][0];
	}
	/**
	 * 
	 * @param word1 字符串1
	 * @param word2 字符串2
	 * @return 最小编辑距离
	 */
	public int minDistance(String word1, String word2) {
        //若两个字符串都为空时，编辑距离为0
        if((word1==null||word1.length()==0)&&(word2==null||word2.length()==0)){
            return 0;
        }
        //若其中一个字符串为空时，编辑距离为另一个字符串的长度
        if(word1==null||word1.length()==0){
            return word2.length();
        }
        if(word2==null||word2.length()==0){
            return word1.length();
        }
        
       //建立二维数组来保存所有的edit(i,j)
        int[][] edit_distance = new int[word1.length()+1][word2.length()+1];
        
        for(int i=0;i<word1.length()+1;i++){
            edit_distance[i][0] = i;
        }
        for(int j=0;j<word2.length()+1;j++){
            edit_distance[0][j] = j;
        }
        
        //套入动态规划公式
        for(int i=1;i<word1.length()+1;i++){
            for(int j=1;j<word2.length()+1;j++){
                int d1 = edit_distance[i-1][j]+1;
                int d2 = edit_distance[i][j-1]+1;
                int d3 = edit_distance[i-1][j-1]+(word1.charAt(i-1)==word2.charAt(j-1)?0:1);
                edit_distance[i][j] = Math.min(d1,Math.min(d2,d3));
            }
        }
        
        return edit_distance[word1.length()][word2.length()];
 }
	
	/**
	 * 
	 * @param a 
	 * @param b
	 * @return
	 */
	public boolean matching(char[] a, char[]b ,int lcs){
		boolean flag =false;
		if( lcs == b.length){
        	int k =0;
        	if((b.length<2)&&(a[k]==b[k])) {
        		flag = true;
        		//
        	}else if((a[k]==b[k])&&(a[k+1]==b[k+1])&&(lcs>=(0.66*a.length))){
        		flag = true;
        	}		
		}
		return flag;
	}
	
	
	public boolean isAutonomousRegion(String standardaddress, String realaddress,int lcs){
		boolean flag =false;
		char[] a =standardaddress.toCharArray();
        char[] b =realaddress.toCharArray();
		
		if(standardaddress.endsWith("自治区")||standardaddress.endsWith("自治州")||standardaddress.endsWith("自治县")){
			 if( lcs ==b.length){
		        	int k =0;
		        	if((b.length<2)&&(a[k]==b[k])) {
		        		flag = true;
		        	}else if((a[k]==b[k])&&(a[k+1]==b[k+1])){
		        		flag = true;
		        	}		
				}
    	}   
        return flag;
   }
}
