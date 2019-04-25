package edu.fzu.addressservice.addresscleanning;

import java.util.List;

public class SimilarityCount {
	/**
	 * 功能：计算地址得分
	 * @param listOne 地址1
	 * @param listTwo 地址2
	 * @return 地址最后得分
	 */
   public int computeScore(List<String> listOne,List<String> listTwo) {
		 int score =0;
		 boolean flag = false;
		 for (int m=9;m>1;m--) {//遍历list1
//			  System.out.println("1"+listOne.toString());
//			  System.out.println("2"+listTwo.toString());
			  if((m<=3 )&& (flag ==false)){
				  return 0;
			  }
			  //System.out.println(listOne.get(m)+" "+listTwo.get(m)+" m"+m);
			  
			  
			  if(listOne.get(m)==null || "".equals(listOne.get(m)))continue;
			  if( listTwo.get(m)==null || "".equals(listTwo.get(m)))continue;
			  if("null".equals(listOne.get(m).trim()))continue;
			  if("null".equals(listTwo.get(m).trim()))continue;
			  if(listOne.get(m).equals(listTwo.get(m))){
				  flag = true;
				  if(m==6){
					  //如果第七级地址匹配上，则直接加30分，跳出循环
					  score = score +30;
					  break;
				  }else{
					  //对每个匹配上的地址级别赋予分数，一般就按级别给分。例如第8级则8分。
					  score=score+m;
					  //System.out.println(score);
				  }
			  }
		  }
		  return score;
	}
   /**
    * 功能：计算地址相似度。（0-1）0表示完全不同，1表示完全相同。
    * @param address 地址1
    * @param standardAddress 标准地址
    * @return 相似度
    */
   public double sim(List<String> address,List<String> standardAddress){
	   double sim =0;
	   sim =(double)(computeScore(address,standardAddress))/45;
	   return sim;
	   
   }
   

}
