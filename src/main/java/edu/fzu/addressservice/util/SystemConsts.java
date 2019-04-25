package edu.fzu.addressservice.util;

/**
 * 系统常量类
 * 
 * @author zwz
 *
 */
public final class SystemConsts {
	/** 系统文本数据统一编码。 */
	public static final String TEXT_FORMAT = "UTF-8";
	
	/** 数据库表第一列列名*/
	public static final String TABLE_COLUMN_1 = "ID";
	
	/** 数据库表第二列列名*/
	public static final String TABLE_COLUMN_2 = "superior_region_code";
	
	/** 数据库表第三列列名*/
	public static final String TABLE_COLUMN_3 = "region_code";
	
	/** 数据库表第四列列名*/
	public static final String TABLE_COLUMN_4 = "region_name";
	
	/**  第五级地址正则匹配特征 */
	public static final String ADDRESS_LEVEL_5 = "addressrecognition5.keyword";
	
	/**  第六级地址正则匹配特征 */
	public static final String ADDRESS_LEVEL_6 = "addressrecognition6.keyword";
	
	/**  第七级地址正则匹配特征 */
	public static final String ADDRESS_LEVEL_7 = "addressrecognition7.keyword";
	
	/**  第八级地址正则匹配特征 */
	public static final String ADDRESS_LEVEL_8 = "addressrecognition8.keyword";
	
	/**  第九级地址正则匹配特征 */
	public static final String ADDRESS_LEVEL_9 = "addressrecognition9.keyword";
	
	/**  待识别地址路径 */
	public static final String SAMPLEINPUTPATH = "addressrecognization.sampleinputpath";
	
	/**  结果地址路径 */
	public static final String RESULTOUTPUTPATH = "addressrecognization.resultoutputpath";
	
	/**  评估文件路径 */
	public static final String EVALUATIONDATAPATH = "evaluation.evaluationdatapath";
	
	/**  标准的前四级地址路径 */
	public static final String STANDARDADDRESSINPUTPATH = "standardaddress.inputpath";
	
	/**  是否进行评估 */
	public static final String ISEVALUATION = "evaluation.isevaluation";
	
	/**  文件中待识别地址列 */
	public static final String RECOGNIZATIONCOLUMN="addressrecognization.recognizationcolumn";
	
	/**  地址元素分割符 */
	public static final String ADDRESSELEMENTSPLITSIGN="addressrecognization.addresselementsplitchar";
	
	/**  多结果地址分隔符 */
	public static final String MULTIPLERESULTSPLITSIGN="addressrecognization.multipleresultsplitchar";
	
	public static final String ADDRESSFILE="evaluation.addressfile";
	
	/**  待评估的地址所在的列*/
	public static final String ADDRESSCOLUMN="evaluation.addresscolumn";
	
	/**  文件读取缓冲区大小*/
	public static final String CACHESIZE="file.cachesize";
	
	/**  输入文件分隔符*/
	public static final String INPUTRECORDSPLITSIGN="addressrecognization.inputrecordsplitchar";
	
	/**  线程数*/
	public static final String THREADNUM="addressrecognization.threadnum";
	
	/** 日志logger的名称 */
	public static final String LOGGER_NAME = "AddressRecognitionLogger";
	
	public static final String ADDRESSNUM="addressrecognization.addressnum";
	
	public static final String DATABASEDRIVER="database.driver";
	
	public static final String DATABASEURL="database.url";
	
	public static final String DATABASEUSERNAME="database.username";
	
	public static final String DATABASEPASSWORD="database.password";
	
	public static final String MAPPINGPATH = "relation.mappingpath";
	
	public static final String RELATIONOUTPUTPATH = "relation.outputpath";
	
	public static final String BAIDUKEYLIST = "baiduAPI.keylist";
	
	public static final String BAIDUCONCURRENCYLIMIT = "buiduAPI.concurrencylimit";
	
	public static final String  CLEANOUTPUTPATH = "cleanning.cleanoutputpath";										
	
	public static final String TAGOUTPUTPATH = "relation.tagoutputpath";
	
	public static final String ISCLEAN = "cleanning.isclean";
	
	public static final String ISRELATION = "relation.isrelation";
	
	public static final String CLEANNINGWINDOWSIZE = "cleanning.windowsize";
	/**  是否进行地址清洗评估 */
	public static final String ISCLEANNINGEVALUATION = "evaluation.cleanningevaluation";
	
	public static final String CLEANNINGMAXSIZE = "cleanning.maxsize";
	
	public static final String CLEANNINGDATAINPUTPATH = "evaluation.cleandatainputpath";
	
	public static final String CLEANNINGSTANDARDINPUTPATH = "evaluation.cleanstandardinputpath";
	
	public static final String RUNISONLINE = "run.isonline";
	
	public static final String MONGODBURL = "mongodb.url";
	
	public static final String MONGODBPORT = "mongodb.port";
	
	public static final String MONGODBDBNAME = "mongodb.dbname";
	
	public static final String MONGODBUSER = "mongodb.user";
	
	public static final String MONGODBPWD = "mongodb.pwd";
	
	public static final String PROGRESSNUM = "run.progressnum";
	
	public static final String VALIDELEMENTSNUM = "cleanning.validelements";
	
	public static final String BAIDURESULTPATH = "buiduAPI.resultpath";
	
	public static final String BAIDUSEARCHPATH = "baiduAPI.searchpath";
	
	public static final String CLEANNINGDETAILEDINFO = "cleanning.detailedinfo";
	
	public static final String BAIDUAVAILABLE = "baiduAPI.available";
	
	public static final String BAIDUUNAVAILABLE = "baiduAPI.unavailable";
	
	public static final String BAIDUPROCESSED = "baiduAPI.processed";
	
	
	
}
