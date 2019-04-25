package edu.fzu.addressservice.addresscleanning;



import edu.fzu.addressservice.util.PropertyOpt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SNM {
	PropertyOpt prop = PropertyOpt.getInstance();
	//相似度阈值
	private double minSim=0;
	//窗口大小
	private int windowSize=prop.getCleanningWindowSize()+10;
	
	
    /**
     * 功能：地址清洗
     * @param object 待清洗的地址集
     * @return 清洗后的地址集
     */
	public List<TreeMap<Integer, List<String>>> addressCleaning(List<TreeMap<Integer, List<String>>> object){
		List<TreeMap<Integer, List<String>>> result = new ArrayList<TreeMap<Integer, List<String>>>();
		int N=prop.getCleanningMaxSize();
		//列表长度为1，不做处理，直接返回
		int t = 0;
		int k=0;
		int end =0;
		int it=0;
		if (object.size()==1) return object;
		int a[] = new int[N];
		for(int f=0;f<object.size();f++){
			int c=object.get(f).firstKey();
//			System.out.println(c);
			a[c]=0;
		}
		SimilarityCount cw = new SimilarityCount();
		List<TreeMap<Integer, List<String>>> temp = new ArrayList<TreeMap<Integer, List<String>>>();
		Map<Integer, List<String>> tempTreeMap = new TreeMap<Integer, List<String>>();
		int addressNum = object.size();
		while(k<addressNum){
			//根据窗口大小截取数据块
			temp = object.subList(k, end=(windowSize+k<=addressNum?windowSize+k:addressNum));
			if(temp.size()==1) break;
			//记录标准地址下标
			int standardIndex =0;
			//记录标准地址
			List<String> Normative_Address =new ArrayList<String>();
			TreeMap<Integer, List<String>> tempStandard = new TreeMap<Integer, List<String>>();

			int length[] = new int[windowSize];
			int maxThird[]=new int[3];
			int sameSectionNum[] = new int[3];
			int sameIndex[] = new int[3];
			//计算窗口内的地址的长度，存放到数组中
			for (int j = 0; j < temp.size(); j++)  
			{  
				if(a[temp.get(j).firstKey()]==0){
					length[j]=temp.get(j).firstEntry().getValue().toString().replace("null", "").length();
				}else{
					length[j]=0;
				}
				
			}
			//寻找长度最长的三条地址的下标
			maxThird=findThirdMax(length);
//			System.out.println("11"+temp.get(maxThird[2]).firstEntry().getValue());
			//计算长度最长的三条地址，两两地址之间的公共串数目
//			System.out.println(maxThird[2]);
			sameSectionNum[0]=getTheSameSection(temp.get(maxThird[0]).firstEntry().getValue(),temp.get(maxThird[1]).firstEntry().getValue());
			if(temp.size()>=3){
				sameSectionNum[1]=getTheSameSection(temp.get(maxThird[0]).firstEntry().getValue(),temp.get(maxThird[2]).firstEntry().getValue());
				sameSectionNum[2]=getTheSameSection(temp.get(maxThird[1]).firstEntry().getValue(),temp.get(maxThird[2]).firstEntry().getValue());
			}
			
			//计算长度最长的三条地址，两两地址之间的公共串的最大下标
			sameIndex[0]=getTheSameIndex(temp.get(maxThird[0]).firstEntry().getValue(),temp.get(maxThird[1]).firstEntry().getValue());
			if(temp.size()>=3){
				sameIndex[1]=getTheSameIndex(temp.get(maxThird[0]).firstEntry().getValue(),temp.get(maxThird[2]).firstEntry().getValue());
				sameIndex[2]=getTheSameIndex(temp.get(maxThird[1]).firstEntry().getValue(),temp.get(maxThird[2]).firstEntry().getValue());
			}
			//结合地址长度以及公共串数目以及公共串的最大下标，确定标准地址
			int SectionNum=getMax(sameSectionNum);
			if(SectionNum==2&&getMax(sameIndex)==2&&getMax(sameIndex)>=4){
				standardIndex =maxThird[1];
			}else {
				standardIndex =maxThird[0];
			}
			//获取标准地址
			Normative_Address= temp.get(standardIndex).firstEntry().getValue();
//			System.out.println(temp.toString());
//			Normative_Address= temp.get(k+1).firstEntry().getValue();
			a[temp.get(standardIndex).firstKey()]=1;
			//利用剩余的两条地址对标准地址的缺失字段进行补全
			for(int m=3;m<=9;m++){
				String temo=Normative_Address.get(m);
				//System.out.println(Normative_Address);
				if("null".equals(temo)){
					//System.out.println("jieg"+getTheSameIndex(temp.get(maxThird[0]),Normative_Address));
					if(((temp.get(maxThird[0]).firstEntry().getValue().get(m+1)).equals(Normative_Address.get(m+1)))&&(getTheSameIndex(temp.get(maxThird[0]).firstEntry().getValue(),Normative_Address)>=4)&&(!"null".equals(temp.get(maxThird[0]).firstEntry().getValue().get(m).trim()))){
						Normative_Address.set(m, temp.get(maxThird[0]).firstEntry().getValue().get(m));
					}else if(((temp.get(maxThird[1]).firstEntry().getValue().get(m+1)).equals(Normative_Address.get(m+1)))&&(getTheSameIndex(temp.get(maxThird[1]).firstEntry().getValue(),Normative_Address)>=4)&&(!"null".equals(temp.get(maxThird[1]).firstEntry().getValue().get(m).trim()))){
						Normative_Address.set(m, temp.get(maxThird[1]).firstEntry().getValue().get(m));	  
					}else if((temp.size()>=3)&&((temp.get(maxThird[2]).firstEntry().getValue().get(m+1)).equals(Normative_Address.get(m+1)))&&(getTheSameIndex(temp.get(maxThird[2]).firstEntry().getValue(),Normative_Address)>=4)&&(!"null".equals(temp.get(maxThird[2]).firstEntry().getValue().get(m).trim()))){
						Normative_Address.set(m, temp.get(maxThird[2]).firstEntry().getValue().get(m));
					}

				}

			}
			
			tempStandard.put(temp.get(standardIndex).firstKey(),Normative_Address);
			object.set(standardIndex+1*t, tempStandard);
//			 System.out.println("Normative_Address"+Normative_Address);
			 


			/*
			 * 相似度值sim>=0.66则用标准地址替换该地址。
			 */
			for(int i=0;i<temp.size();i++){
				if(a[temp.get(i).firstKey()]==1) continue;
				TreeMap<Integer, List<String>> tempM = new TreeMap<Integer, List<String>>();
				if(cw.sim(temp.get(i).firstEntry().getValue(),Normative_Address)>=0.66){
//					if(Normative_Address.get(9).endsWith("院")){
//						System.out.println(Normative_Address);
//						tempM.put(temp.get(i).firstEntry().getKey(),placeMaxElement(Normative_Address,temp.get(i).firstEntry().getValue(),9));
//					}else{
						tempM.put(temp.get(i).firstEntry().getKey(),placeMaxElement(Normative_Address,temp.get(i).firstEntry().getValue(), 9));
//					}
					
					object.set(i+1*t, tempM);
					a[temp.get(i).firstKey()]=1;
				}else{
					tempM.put(temp.get(i).firstEntry().getKey(),placeElement(Normative_Address,temp.get(i).firstEntry().getValue()));
     				object.set(i+1*t, tempM);
				}
			}
			//滑动窗口长度为3
//			System.out.println(a[temp.get(k).firstKey()]);
			
			if(standardIndex>k&&it<30){
				k=k;
				it++;
			}else{
				k=k+1;
				t++;
				it=0;
			}
			
		}
		return object;

	}

	/**
	 * 取出数组中的最大值的下标
	 * @param arr
	 * @return
	 */
	public static int getMax(int[] arr){
		int max=arr[0];
		int index=0;
		for(int i=1;i<arr.length;i++){
			if(arr[i]>max){
				max=arr[i];
				index =i;
			}
		}
		return index;
	}
    /**
     * 利用标准地址补全待处理地址的缺失字段
     * @param Normative_Address 标准地址
     * @param address 待处理地址
     * @return 处理后的地址
     */
	public static List<String> placeElement(List<String> Normative_Address,List<String> address){
		
		int index=getTheSameIndex(Normative_Address, address);
		//System.out.println( Normative_Address+":"+address+":"+index);
		if(index>=2&&(Normative_Address.get(2).equals(address.get(2)))){
			for(int i=1;i<=index;i++){
				address.set(i, Normative_Address.get(i));
			}
		}

		return address;
	}

	/**
	 * 将待处理地址的前index级用标准地址的前index代替
	 * @param Normative_Address 标准地址
	 * @param address 待处理地址
	 * @param index 替换的最大下标
	 * @return 处理后的地址
	 */
	public static List<String> placeMaxElement(List<String> Normative_Address,List<String> address,int index){

		if(index>2){
			for(int i=1;i<index;i++){
				address.set(i, Normative_Address.get(i));
			}
		}

		return address;
	}
    /**
     * 功能：找出数组中的最大三个数
     * @param a 数组
     * @return
     */
	public static int[] findThirdMax(int a[]){
		int b[] = new int[3];
		int first = 0;
		int second = 1;
		int third =2;
		for(int i=1; i<a.length; i++){
			if(a[i]>a[first] ){
				third = second;
				second = first;
				first = i;
			}else if(a[i] > a[second]){
				third = second;
				second = i;
			}else if(a[i] > a[third]){
				third =i;
			}
		}
		b[0]=first;
		b[1]=second;
		b[2]=third;
		return b;
	}
	/**
	 * 功能：获取两个地址的公共串的最大下标
	 * @param listOne 地址1
	 * @param listTwo 地址2
	 * @return 下标值
	 */
	public static int getTheSameIndex(List<String> listOne,List<String> listTwo){
		int index = 0;
		PropertyOpt prop = PropertyOpt.getInstance();
		String[] key8 = {"学院","阁"};
		String[] key9 = {"中心","店"};
		String[] key10 = {"中心","店","大厦","公司","大学","中学","小学","学校","广场","学院","阁"};
		for(int m=9;m>=2;m--){
			if(listOne.get(m)==null || "".equals(listOne.get(m)))continue;
			if( listTwo.get(m)==null || "".equals(listTwo.get(m)))continue;
			if("null".equals(listOne.get(m).trim())||"null".equals(listTwo.get(m).trim())){
				continue;
			}
			
			if ((listOne.get(m).equals(listTwo.get(m)))){
				if(m==9){
					for(String a:key10){
						listOne.get(m).endsWith(a);
						index = m;
					    break;	
					}
				}
				if(m==8){
					for(String a:key9){
						listOne.get(m).endsWith(a);
						index = m;
					    break;	
					}
				}
				if(m==7){
					for(String a:key8){
						listOne.get(m).endsWith(a);
						index = m;
					    break;	
					}
				}
				if(m<=4||m==6){
					index = m;
				    break;	
				}
				
				if(listOne.get(m-1)==null || "".equals(listOne.get(m-1)))continue;
				//if( listTwo.get(m-2)==null || "".equals(listTwo.get(m-2)))continue;
				if(((listOne.get(m-1).equals(listTwo.get(m-1))))){
					index = m;
					break;
				}
				
			}

		}
		return index;
	}
	
	/**
	 * 功能：计算两个的地址的公共串个数
	 * @param listOne 地址1
	 * @param listTwo 地址2
	 * @return 公共串个数
	 */
	public int getTheSameSection(List<String> listOne,List<String> listTwo) {
		int count =0;
		for (String item : listTwo) {//遍历list1
			if (listOne.contains(item)&&(!"null".equals(item))) {//如果存在这个数
				count ++;
			}
		}
		return count;
	}
	
	public double getMinSim() {
		return minSim;
	}
	public void setMinSim(double minSim) {
		this.minSim = minSim;
	}

	public int getWindowSize() {
		return windowSize;
	}
	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
	//构造函数
	public SNM(double minSim,int windowSize){	
		this.minSim =minSim;
		this.windowSize = windowSize;
	}
	public SNM(){		
	}

}
