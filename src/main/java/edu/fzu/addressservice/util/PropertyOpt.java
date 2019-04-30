package edu.fzu.addressservice.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyOpt {
	/** 配置文件路径 */
	public static final String CONFIG_FILE_PATH = "src/main/resources/addressrecognition.properties";

	/** 单例对象 */
	private static PropertyOpt config = null;
	
	private String[] keyword5 ;
	private String[] keyword6 ;
	private String[] keyword7 ;
	private String[] keyword8 ;
	private String[] keyword9 ;
	private String sampleinputpath;
	private String resultoutputpath;
	private String evaluationdatapath;
	private String standardaddressinputpath;
	private boolean isevaluation;
	private int[] recognizationcolumn;
	private String addresselementsplitsign;
	private String multipleresultsplitsign;
	private int addresscolumn;
	private String addressfile;
	private int cachesize;
	private String inputrecordsplitsign;
	private int threadnum;
	private int maxaddressnum;
	private String driver = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private String mappingpath = null;	
	private List<String> baidukeylist ;
	private int concurrencylimit;
	private String relationoutputpath = null;	
	private String cleanoutputpath = null;
	private String tagoutputpath = null;
	private boolean isclean;
	private boolean isrelation;
	private int cleanningWindowSize;
	private boolean iscleanningevaluation;
	private int cleanningMaxSize;
	private String cleandatainputpath;
	private String cleanevaluationinputpath;
	private boolean runisonline;	
	private String mongodburl;
	private int mongodbport;
	private String mongodbdbname;
	private String mongodbuser;
	private String mongodbpwd;
	private int progressnum;
	private int validelements;
	private String baiduresultpath;
	private String baidusearchpath;
	private String cleanningdetailedinfo;
	private String baiduavailable;
	private String baiduunavailable;
	private String baiduprocessed;
		/**
		 * 返回单例对象
		 * 
		 * @return
		 */
		public synchronized static PropertyOpt getInstance() {
			if (config == null) {
				synchronized (PropertyOpt.class){
					config = new PropertyOpt();
					config.loadConfig();
				}
			}
			return config;
		}
		
		public void loadConfig() {
			BufferedReader reader = null;
			Properties prop = null;
			try {
				InputStream confStream = new FileInputStream(CONFIG_FILE_PATH);
				reader = new BufferedReader(new InputStreamReader(confStream,"UTF-8"));
				prop = new Properties();
				prop.load(reader);
				this.setKeyword5(prop.getProperty(SystemConsts.ADDRESS_LEVEL_5).split(","));
				this.setKeyword6(prop.getProperty(SystemConsts.ADDRESS_LEVEL_6).split(","));
				this.setKeyword7(prop.getProperty(SystemConsts.ADDRESS_LEVEL_7).split(","));
				this.setKeyword8(prop.getProperty(SystemConsts.ADDRESS_LEVEL_8).split(","));
				this.setKeyword9(prop.getProperty(SystemConsts.ADDRESS_LEVEL_9).split(","));
				this.setSampleinputpath(prop.getProperty(SystemConsts.SAMPLEINPUTPATH));
				this.setResultoutputpath(prop.getProperty(SystemConsts.RESULTOUTPUTPATH));
				this.setEvaluationdatapath(prop.getProperty(SystemConsts.EVALUATIONDATAPATH));
				this.setStandardaddressinputpath(prop.getProperty(SystemConsts.STANDARDADDRESSINPUTPATH));
				String tempIsEvaluation = prop.getProperty(SystemConsts.ISEVALUATION);
				if(tempIsEvaluation.equals("true")){
					isevaluation=true;
				}else {
					isevaluation=false;
				}
				this.setIsevaluation(isevaluation);
				String cleanIsEvaluation = prop.getProperty(SystemConsts.ISCLEANNINGEVALUATION);
				if(cleanIsEvaluation.equals("true")){
					iscleanningevaluation=true;
				}else {
					iscleanningevaluation=false;
				}
				this.setIsevaluation(iscleanningevaluation);
				String runIsOnline = prop.getProperty(SystemConsts.RUNISONLINE);
				if(runIsOnline.equals("true")){
					runisonline=true;
				}else {
					runisonline=false;
				}
				this.setRunIsOnline(runisonline);
				
				String temp = prop.getProperty(SystemConsts.RECOGNIZATIONCOLUMN).trim();
				String[] temp2 = temp.split(",");
				recognizationcolumn= new int[temp2.length];
				for (int i = 0; i < temp2.length; i++) recognizationcolumn[i] = Integer.parseInt(temp2[i]);
				this.setRecognizationColumn(recognizationcolumn);
				
				this.setMultipleresultsplitsign(prop.getProperty(SystemConsts.MULTIPLERESULTSPLITSIGN));
				this.setAddresselementsplitsign(prop.getProperty(SystemConsts.ADDRESSELEMENTSPLITSIGN));
				this.setAddresscolumn(Integer.parseInt(prop.getProperty(SystemConsts.ADDRESSCOLUMN)));
				this.setAddressfile(prop.getProperty(SystemConsts.ADDRESSFILE));
//该段代码有问题				
//				String temp3 =prop.getProperty(SystemConsts.CACHESIZE);
//				int tempCacheSize = Integer.parseInt(temp3.substring(0,temp3.length()-1));
//				if(temp3.endsWith("M")||temp3.endsWith("m")){
//					cachesize = tempCacheSize*1024*1024;
//				}else if(temp3.endsWith("g")||temp3.endsWith("G")){
//					cachesize = tempCacheSize*1024*1024*1024;
//				}else if(temp3.endsWith("k")||temp3.endsWith("K")){
//					cachesize = tempCacheSize*1024;
//				}else{
//					cachesize=tempCacheSize;
//				}
//				this.setCachesize(cachesize);
				
				String temp3 =prop.getProperty(SystemConsts.CACHESIZE);
				//System.out.println("###" + temp3 + " " + temp3.substring(0,temp3.length()-1));
				int tempCacheSize ;//= Integer.parseInt(temp3.substring(0,temp3.length()-1));
				if(temp3.endsWith("M")||temp3.endsWith("m")){
					tempCacheSize = Integer.parseInt(temp3.substring(0,temp3.length()-1));
					cachesize = tempCacheSize*1024*1024;
				}else if(temp3.endsWith("g")||temp3.endsWith("G")){
					tempCacheSize = Integer.parseInt(temp3.substring(0,temp3.length()-1));
					cachesize = tempCacheSize*1024*1024*1024;
				}else if(temp3.endsWith("k")||temp3.endsWith("K")){
					tempCacheSize = Integer.parseInt(temp3.substring(0,temp3.length()-1));
					cachesize = tempCacheSize*1024;
				}else{
					tempCacheSize = Integer.parseInt(temp3.substring(0,temp3.length()));
					cachesize=tempCacheSize;
				}
				//System.out.println("##" + cachesize);
				this.setCachesize(cachesize);
				
				this.setInputrecordsplitsign(prop.getProperty(SystemConsts.INPUTRECORDSPLITSIGN));
				this.setThreadnum(Integer.parseInt(prop.getProperty(SystemConsts.THREADNUM)));
				this.setMaxaddressnum(Integer.parseInt(prop.getProperty(SystemConsts.ADDRESSNUM)));
				this.setDriver(prop.getProperty(SystemConsts.DATABASEDRIVER));
				this.setUrl(prop.getProperty(SystemConsts.DATABASEURL));
				this.setUsername(prop.getProperty(SystemConsts.DATABASEUSERNAME));
				this.setPassword(prop.getProperty(SystemConsts.DATABASEPASSWORD));
				this.setBaiduKeylist(prop.getProperty(SystemConsts.BAIDUKEYLIST).split(","));
				this.setMappingPath(prop.getProperty(SystemConsts.MAPPINGPATH));
				this.setRelationOutputPath(prop.getProperty(SystemConsts.RELATIONOUTPUTPATH));
				this.setCleanOutputPath(prop.getProperty(SystemConsts.CLEANOUTPUTPATH));
				this.setTagOutputPath(prop.getProperty(SystemConsts.TAGOUTPUTPATH));
				this.setBaiduConcurrencyLimit(Integer.parseInt(prop.getProperty(SystemConsts.BAIDUCONCURRENCYLIMIT)));
				this.setCleanningWindowSize(Integer.parseInt(prop.getProperty(SystemConsts.CLEANNINGWINDOWSIZE)));
				this.setCleanningMaxSize(Integer.parseInt(prop.getProperty(SystemConsts.CLEANNINGMAXSIZE)));
				this.setCleandatainputpath(prop.getProperty(SystemConsts.CLEANNINGDATAINPUTPATH));
				this.setCleanevaluationinputpath(prop.getProperty(SystemConsts.CLEANNINGSTANDARDINPUTPATH));
				this.setMongodbUrl(prop.getProperty(SystemConsts.MONGODBURL));
				this.setMongodbPort(Integer.parseInt(prop.getProperty(SystemConsts.MONGODBPORT)));
				this.setMongodbDBName(prop.getProperty(SystemConsts.MONGODBDBNAME));
				this.setMongodbDBUser(prop.getProperty(SystemConsts.MONGODBUSER));
				this.setMongodbDBPwd(prop.getProperty(SystemConsts.MONGODBPWD));
				this.setBaiduResultPath(prop.getProperty(SystemConsts.BAIDURESULTPATH));
				this.setBaiduSearchPath(prop.getProperty(SystemConsts.BAIDUSEARCHPATH));
				this.setCleanningDetailInfo(prop.getProperty(SystemConsts.CLEANNINGDETAILEDINFO));
				this.setValidelements(Integer.parseInt(prop.getProperty(SystemConsts.VALIDELEMENTSNUM)));
				this.setBaiduAvailable(prop.getProperty(SystemConsts.BAIDUAVAILABLE));
				this.setBaiduUnAvailable(prop.getProperty(SystemConsts.BAIDUUNAVAILABLE));
				this.setBaiduProcessed(prop.getProperty(SystemConsts.BAIDUPROCESSED));
				String tempIsclean = prop.getProperty(SystemConsts.ISCLEAN).trim();
			
				if(tempIsclean.equals("true")){
					isclean=true;
				}else {
					isclean=false;
				}
				this.setIsclean(isclean);
				
				String tempIsrelation = prop.getProperty(SystemConsts.ISRELATION).trim();
				if(tempIsrelation.equals("true")){
					isrelation=true;
				}else {
					isrelation=false;
				}
				this.setIsrelation(isrelation);
				this.setProgressnum(Integer.parseInt(prop.getProperty(SystemConsts.PROGRESSNUM)));
				
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ex) {
					}
				}
			}
		}
		
//保存配置文件
		public void saveConfig(){
			String file = "addressrecognition.properties";
			OutputStreamWriter write = null;
			BufferedWriter writer  = null;
			try {
				
				write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
				writer = new BufferedWriter(write);
				writer.write("#后6级地址识别正则匹配关键词\n");
				
				writer.write(SystemConsts.ADDRESS_LEVEL_5+"="+StringUtil.arrayToString(this.getKeyword5())+"\n");
				writer.write(SystemConsts.ADDRESS_LEVEL_6+"="+StringUtil.arrayToString(this.getKeyword6())+"\n");
				writer.write(SystemConsts.ADDRESS_LEVEL_7+"="+StringUtil.arrayToString(this.getKeyword7())+"\n");
				writer.write(SystemConsts.ADDRESS_LEVEL_8+"="+StringUtil.arrayToString(this.getKeyword8())+"\n");
				writer.write(SystemConsts.ADDRESS_LEVEL_9+"="+StringUtil.arrayToString(this.getKeyword9())+"\n");
				writer.write("\n");
				
				writer.write("#数据库配置\n");
				writer.write(SystemConsts.DATABASEDRIVER+"="+this.getDriver()+"\n");
				writer.write(SystemConsts.DATABASEURL+"="+this.getUrl()+"\n");
				writer.write(SystemConsts.DATABASEUSERNAME+"="+this.getUsername()+"\n");
				writer.write(SystemConsts.DATABASEPASSWORD+"="+this.getPassword()+"\n");
				writer.write("\n");
				
				writer.write("#mongodb配置\n");
				writer.write(SystemConsts.MONGODBURL+"="+this.getMongodbUrl()+"\n");
				writer.write(SystemConsts.MONGODBPORT+"="+this.getMongodbPort()+"\n");
				writer.write(SystemConsts.MONGODBUSER+"="+this.getMongodbDBUser()+"\n");
				writer.write(SystemConsts.MONGODBPWD+"="+this.getMongodbDBPwd()+"\n");
				writer.write(SystemConsts.MONGODBDBNAME+"="+this.getMongodbDBName()+"\n");
				
				writer.write("#识别地址文件配置\n");
				writer.write("#addressrecognization.sampleinputpath=./data/   可一次处理data目录下的所有文件。  ./data/train.txt   指明只处理train.txt文件\n");
				writer.write(SystemConsts.SAMPLEINPUTPATH+"="+this.getSampleinputpath()+"\n");
				writer.write("#addressrecognization.resultoutputpath=./result/  运行结果存放于该目录下，每个文件名与输入的文件名一一对应。 ./result/results.txt  将所有的结果都存于该文件下。\n");
				writer.write(SystemConsts.RESULTOUTPUTPATH+"="+this.getResultoutputpath()+"\n");
				writer.write("#文档中需要识别的地址所在的列号\n");
				writer.write(SystemConsts.RECOGNIZATIONCOLUMN+"="+StringUtil.intArrayToString(this.getRecognizationcolumn())+"\n");
				writer.write("#各级地址要素的分割符，单字符\n");
				writer.write(SystemConsts.ADDRESSELEMENTSPLITSIGN+"="+this.getAddresselementsplitsign()+"\n");
				writer.write("#一条地址的不同结果的分割符，单字符\n");
				writer.write(SystemConsts.MULTIPLERESULTSPLITSIGN+"="+this.getMultipleresultsplitsign()+"\n");
				writer.write("#输入文件中字段分隔符，单字符\n");
				writer.write(SystemConsts.INPUTRECORDSPLITSIGN+"="+this.getInputrecordsplitsign()+"\n");
				writer.write("#线程数\n");
				writer.write(SystemConsts.THREADNUM+"="+this.getThreadnum()+"\n");
				writer.write("#单线程处理的地址条数\n");
				writer.write(SystemConsts.ADDRESSNUM+"="+this.getMaxaddressnum()+"\n");
				writer.write("\n");
				
				writer.write("#文件读取缓冲区大小(B)\n");
				writer.write(SystemConsts.CACHESIZE+"="+this.getCachesize()+"\n");
				writer.write("\n");
				
				writer.write("#评估配置\n");
				writer.write("#是否开启评估，默认false\n");
				writer.write(SystemConsts.ISEVALUATION+"="+this.getIsevaluation()+"\n");
				writer.write("#待评估的文件路径\n");
				writer.write(SystemConsts.ADDRESSFILE+"="+this.getAddressfile()+"\n");
				writer.write("#待评估的地址列\n");
				writer.write(SystemConsts.ADDRESSCOLUMN+"="+this.getAddresscolumn()+"\n");
				writer.write("#evaluation.evaluationdatapath=./evaluation/evaluation1000.txt  用于评估的标准十级地址所在路径\n");
				writer.write(SystemConsts.EVALUATIONDATAPATH+"="+this.getEvaluationdatapath()+"\n");
				writer.write("#是否开启地址去重评估，默认false\n");
				writer.write(SystemConsts.ISCLEANNINGEVALUATION+"="+this.getIscleanningevaluation()+"\n");
				writer.write("#待去重地址数据路径\n");
				writer.write(SystemConsts.CLEANNINGDATAINPUTPATH+"="+this.getCleandatainputpath()+"\n");
				writer.write("#去重评估标准数据路径\n");
				writer.write(SystemConsts.CLEANNINGSTANDARDINPUTPATH+"="+this.getCleanevaluationinputpath()+"\n");
				writer.write("\n");
				
				writer.write("#地址清洗窗口设置\n");
				writer.write(SystemConsts.CLEANNINGWINDOWSIZE+"="+this.getCleanningWindowSize()+"\n");
				writer.write("\n");
				writer.write("#地址清洗最大地址数设置\n");
				writer.write(SystemConsts.CLEANNINGMAXSIZE+"="+this.getCleanningMaxSize()+"\n");
				writer.write("\n");
				writer.write("#有效地址级数\n");
				writer.write(SystemConsts.VALIDELEMENTSNUM+"="+this.getValidelements()+"\n");
				writer.write("\n");
				writer.write("#地址清洗输出路径\n");
				writer.write(SystemConsts.CLEANOUTPUTPATH+"="+this.getCleanOutputPath()+"\n");
				writer.write("\n");
				writer.write("#是否进行地址清洗\n");
				writer.write(SystemConsts.ISCLEAN+"="+this.getIsclean()+"\n");
				writer.write("\n");
				writer.write("#整合清洗地址与经纬度、标签的文件路径\n");
				writer.write(SystemConsts.CLEANNINGDETAILEDINFO+"="+this.getCleanningDetailInfo()+"\n");
				writer.write("\n");
				
				writer.write("#标准库文件配置\n");
				writer.write(SystemConsts.STANDARDADDRESSINPUTPATH+"="+this.getStandardaddressinputpath()+"\n");
				writer.write("\n");
				
				writer.write("#地址标签与关系映射表路径\n");
				writer.write(SystemConsts.MAPPINGPATH+"="+this.getMappingPath()+"\n");
				writer.write("\n");
				writer.write("#关系输出路径\n");
				writer.write(SystemConsts.RELATIONOUTPUTPATH+"="+this.getResultoutputpath()+"\n");
				writer.write("\n");
				writer.write("#是否建立关系\n");
				writer.write(SystemConsts.ISRELATION+"="+this.getIsrelation()+"\n");
				writer.write("\n");
				writer.write("#标签数据输出路径\n");
				writer.write(SystemConsts.TAGOUTPUTPATH+"="+this.getTagOutputPath()+"\n");
				writer.write("\n");
				
//				writer.write("#使用百度地图API-KEY\n");
//				int len = this.getBaiduKeylist().toString().length();
//			//	System.out.println(this.getBaiduKeylist().toString().substring(1,len-1));
//				writer.write(SystemConsts.BAIDUKEYLIST+"="+this.getBaiduKeylist().toString().substring(1,len-1)+"\n");
//				writer.write("\n");
				
				writer.write("#使用百度地图API-KEY\n");
				int len = this.getBaiduKeylist().toString().length();
			//	System.out.println(this.getBaiduKeylist().toString().substring(1,len-1));
			//	writer.write(SystemConsts.BAIDUKEYLIST+"="+this.getBaiduKeylist().toString().substring(1,len-1)+"\n");
				String baidukeylist = "";
				for(int i=0;i<this.getBaiduKeylist().size();i++) {
					baidukeylist = baidukeylist + this.getBaiduKeylist().get(i);
					if(i!=(this.getBaiduKeylist().size()-1)) {
						baidukeylist = baidukeylist + ",";
					}
				}
				writer.write(SystemConsts.BAIDUKEYLIST+"="+baidukeylist);
				writer.write("\n");
				
				writer.write("#使用百度地图API并发限制\n");
				writer.write(SystemConsts.BAIDUCONCURRENCYLIMIT+"="+this.getBaiduConcurrencyLimit()+"\n");
				writer.write("\n");
				writer.write("#百度查询结果文件路径\n");
				writer.write(SystemConsts.BAIDURESULTPATH+"="+this.getBaiduResultPath()+"\n");
				writer.write("\n");
				writer.write("#待百度查询的文件存放路径\n");
				writer.write(SystemConsts.BAIDUSEARCHPATH+"="+this.getBaiduSearchPath()+"\n");
				writer.write("\n");
				
				writer.write("#是否在线运行\n");
				writer.write(SystemConsts.RUNISONLINE+"="+this.getRunIsOnline()+"\n");
				writer.write("\n");
				writer.write("#每处理多少条数据输出一个提示\n");
				writer.write(SystemConsts.PROGRESSNUM+"="+this.getProgressnum()+"\n");
				writer.write("\n");
				
				}catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						writer.close();
						write.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
		public int[] getRecognizationcolumn() {
			return recognizationcolumn;
		}

		public void setRecognizationcolumn(int[] recognizationcolumn) {
			this.recognizationcolumn = recognizationcolumn;
		}

		public int getMaxaddressnum() {
			return maxaddressnum;
		}

		public void setMaxaddressnum(int maxaddressnum) {
			this.maxaddressnum = maxaddressnum;
		}
		public int getThreadnum() {
			return threadnum;
		}

		public void setThreadnum(int threadnum) {
			this.threadnum = threadnum;
		}

		public String getInputrecordsplitsign() {
			return inputrecordsplitsign;
		}

		public void setInputrecordsplitsign(String inputrecordsplitsign) {
			this.inputrecordsplitsign = inputrecordsplitsign;
		}
		public int getCachesize() {
			return cachesize;
		}
		public void setCachesize(int cachesize) {
			this.cachesize = cachesize;
		}
		public PropertyOpt(){
				
			}
		public String getAddressfile() {
			return addressfile;
		}
		public void setAddressfile(String addressfile) {
			this.addressfile = addressfile;
		}
		public String[] getKeyword5() {
			return keyword5;
		}
		public void setKeyword5(String[] keyword) {
			this.keyword5 = keyword;
		}
		
		public String[] getKeyword6() {
			return keyword6;
		}
		public void setKeyword6(String[] keyword) {
			this.keyword6 = keyword;
		}
		
		public String[] getKeyword7() {
			return keyword7;
		}
		public void setKeyword7(String[] keyword) {
			this.keyword7 = keyword;
		}
		
		public String[] getKeyword8() {
			return keyword8;
		}
		public void setKeyword8(String[] keyword) {
			this.keyword8 = keyword;
		}
		
		public String[] getKeyword9() {
			return keyword9;
		}
		public void setKeyword9(String[] keyword) {
			this.keyword9 = keyword;
		}
		public String getSampleinputpath() {
			return sampleinputpath;
		}
		public void setSampleinputpath(String sampleinputpath) {
			this.sampleinputpath = sampleinputpath;
		}
		public String getResultoutputpath() {
			return resultoutputpath;
		}
		public void setResultoutputpath(String resultoutputpath) {
			this.resultoutputpath = resultoutputpath;
		}
		public String getEvaluationdatapath() {
			return evaluationdatapath;
		}
		public void setEvaluationdatapath(String evaluationdatapath) {
			this.evaluationdatapath = evaluationdatapath;
		}
		public String getStandardaddressinputpath() {
			return standardaddressinputpath;
		}
		public void setStandardaddressinputpath(String standardaddressinputpath) {
			this.standardaddressinputpath = standardaddressinputpath;
		}
		public boolean getIsevaluation() {
			return isevaluation;
		}
		public void setIsevaluation(boolean isevaluation) {
			this.isevaluation = isevaluation;
		}
		public int getAddresscolumn() {
			return addresscolumn;
		}
		public void setAddresscolumn(int addresscolumn) {
			this.addresscolumn = addresscolumn;
		}
		public String getAddresselementsplitsign() {
			return addresselementsplitsign;
		}
		public void setAddresselementsplitsign(String addresselementsplitsign) {
			this.addresselementsplitsign = addresselementsplitsign;
		}
		public String getMultipleresultsplitsign() {
			return multipleresultsplitsign;
		}
		public void setMultipleresultsplitsign(String multipleresultsplitsign) {
			this.multipleresultsplitsign = multipleresultsplitsign;
		}
		
		public int[] getRecognizationColumn() {
			return recognizationcolumn;
		}
		public void setRecognizationColumn(int[] recognizationcolumn) {
			this.recognizationcolumn = recognizationcolumn;
		}
		public String getDriver() {
			return driver;
		}

		public void setDriver(String driver) {
			this.driver = driver;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getMappingPath() {
			return mappingpath;
		}
		
		public void setMappingPath(String mappingpath) {
			this.mappingpath = mappingpath;
		}
		
		public List<String> getBaiduKeylist() {
			return baidukeylist;
		}
		
		public String getRelationOutputPath() {
			return relationoutputpath;
		}
		
		public void setRelationOutputPath(String relationoutputpath) {
			this.relationoutputpath = relationoutputpath;
		}
		
		public String getCleanOutputPath() {
			return cleanoutputpath;
		}
		
		public void setCleanOutputPath(String cleanoutputpath) {
			this.cleanoutputpath = cleanoutputpath;
		}
		
		public String getTagOutputPath() {
			return tagoutputpath;
		}
		
		public void setTagOutputPath(String tagoutputpath) {
			this.tagoutputpath = tagoutputpath;
		}
		
		public void setBaiduKeylist(String[] baidukeylist) {
			List<String> keylist = new ArrayList<String>();
			for(String s : baidukeylist) {
				keylist.add(s);
			}
			this.baidukeylist = keylist;
		}
		
		public int getBaiduConcurrencyLimit() {
			return concurrencylimit;
		}
		
		public void setBaiduConcurrencyLimit(int concurrencylimit) {
			this.concurrencylimit = concurrencylimit;
		}
		
		public boolean getIsclean() {
			return isclean;
		}
		public void setIsclean(boolean isclean) {
			this.isclean = isclean;
		}
		
		public boolean getIsrelation() {
			return isrelation;
		}
		public void setIsrelation(boolean isrelation) {
			this.isrelation = isrelation;
		}
		public int getCleanningWindowSize() {
			return cleanningWindowSize;
		}

		public void setCleanningWindowSize(int cleanningWindowSize) {
			this.cleanningWindowSize = cleanningWindowSize;
		}
		public boolean getIscleanningevaluation() {
			return iscleanningevaluation;
		}

		public void setIscleanningevaluation(boolean iscleanningevaluation) {
			this.iscleanningevaluation = iscleanningevaluation;
		}
		public int getCleanningMaxSize() {
			return cleanningMaxSize;
		}

		public void setCleanningMaxSize(int cleanningMaxSize) {
			this.cleanningMaxSize = cleanningMaxSize;
		}
		public String getCleandatainputpath() {
			return cleandatainputpath;
		}

		public void setCleandatainputpath(String cleandatainputpath) {
			this.cleandatainputpath = cleandatainputpath;
		}

		public String getCleanevaluationinputpath() {
			return cleanevaluationinputpath;
		}

		public void setCleanevaluationinputpath(String cleanevaluationinputpath) {
			this.cleanevaluationinputpath = cleanevaluationinputpath;
		}
		public boolean getRunIsOnline() {
			return runisonline;
		}

		public void setRunIsOnline(boolean runisonline) {
			this.runisonline = runisonline;
		}
		
		public String getMongodbUrl() {
			return mongodburl;
		}

		public void setMongodbUrl(String mongodburl) {
			this.mongodburl = mongodburl;
		}
		
		public int getMongodbPort() {
			return mongodbport;
		}

		public void setMongodbPort(int mongodbport) {
			this.mongodbport = mongodbport;
		}
		
		public String getMongodbDBName() {
			return mongodbdbname;
		}

		public void setMongodbDBName(String mongodbdbname) {
			this.mongodbdbname = mongodbdbname;
		}
		
		public String getMongodbDBUser() {
			return mongodbuser;
		}

		public void setMongodbDBUser(String mongodbuser) {
			this.mongodbuser = mongodbuser;
		}
		
		public String getMongodbDBPwd() {
			return mongodbpwd;
		}

		public void setMongodbDBPwd(String mongodbpwd) {
			this.mongodbpwd = mongodbpwd;
		}
		
		public int getProgressnum() {
			return progressnum;
		}
		
		public void setProgressnum(int progressnum) {
			this.progressnum = progressnum;
		}
		public int getValidelements() {
			return validelements;
		}

		public void setValidelements(int validelements) {
			this.validelements = validelements;
		}
		
		public String getBaiduResultPath() {
			return baiduresultpath;
		}

		public void setBaiduResultPath(String baiduresultpath) {
			this.baiduresultpath = baiduresultpath;
		}
		
		public String getBaiduSearchPath() {
			return baidusearchpath;
		}

		public void setBaiduSearchPath(String baidusearchpath) {
			this.baidusearchpath = baidusearchpath;
		}
		
		public String getCleanningDetailInfo() {
			return cleanningdetailedinfo;
		}

		public void setCleanningDetailInfo(String cleanningdetailedinfo) {
			this.cleanningdetailedinfo = cleanningdetailedinfo;
		}
		
		public String getBaiduAvailable() {
			return baiduavailable;
		}

		public void setBaiduAvailable(String baiduavailable) {
			this.baiduavailable = baiduavailable;
		}
		
		public String getBaiduUnAvailable() {
			return baiduunavailable;
		}

		public void setBaiduUnAvailable(String baiduunavailable) {
			this.baiduunavailable = baiduunavailable;
		}
		
		public String getBaiduProcessed() {
			return baiduprocessed;
		}

		public void setBaiduProcessed(String baiduprocessed) {
			this.baiduprocessed = baiduprocessed;
		}

}
