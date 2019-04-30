/**
 * @Description TODO
 * @Author zytshijack
 * @Date 2019-04-29 11:07
 * @Version 1.0
 */
package edu.fzu.addressservice.addressmatching;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.fzu.addressservice.util.PropertyOpt;
import edu.fzu.addressservice.util.SystemConsts;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author zytshijack
 * @Date 2019-04-29 11:07
 * @Version 1.0
 */
public class BaiduApi {
    private static Logger addressRecognitionLogger = Logger.getLogger(SystemConsts.LOGGER_NAME);
    private static PropertyOpt prop = PropertyOpt.getInstance();
    private static List<String> BAIDU_APPS = prop.getBaiduKeylist();
    private static int BAIDU_CONCURREANCY_LIMIT = prop.getBaiduConcurrencyLimit(); private static String BAIDU_APP_KEY;
    private static Integer keyNum;


//    public String search(){
//        return null;
//    }

    /**
     * 获取百度地图中地址的uuid
     * @param examples
     * @return
     */
    public static ArrayList<String> getBaiduUUID(ArrayList<String> examples) {
        BaiduApi baiduApi = new BaiduApi();
        BaiduApi tr = new BaiduApi();
        ArrayList<String> resultUid = new ArrayList<String>();
        List<String> keylist = tr.getKeyList();
//		Integer it = tr.getKeyNum();
        keyNum = 0;
        tr.setKey(keylist.get(keyNum));
        try {
            String address1 = examples.get(2);
            String address2 = examples.get(1);
//				address1 = URLEncoder.encode(address1, "UTF-8");
//				address2 = URLEncoder.encode(address2, "UTF-8");
//				System.out.println(address1+"   "+address2);
            URL resjson = new URL("http://api.map.baidu.com/place/v2/search?query="
                    + address1 + "&region=" + address2 + "&city_limit=true&output=json&ak=" + tr.getKey());
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    resjson.openStream()));
            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            in.close();
            String str = sb.toString();

            System.out.println("return json:" + str);
//				System.out.println("原先key:"+" "+tr.getKey());
            JSONObject json0_test = JSON.parseObject(str);
            if(json0_test.containsKey("status")&&json0_test.getString("status").equals("401")) {
                addressRecognitionLogger.info("the concurrency containsKey exceeded the limited the quota and limit access");
//					System.out.println("当前并发量已经超过约定并发配额，限制访问");
                Thread.sleep(BAIDU_CONCURREANCY_LIMIT);
                resultUid = getBaiduUUID(examples);
            }

            if(json0_test.containsKey("status")&&json0_test.getString("status").equals("302")) {
                addressRecognitionLogger.info("the time of the service containsKey exceeded and exchange baidukey");
//					System.out.println("服务当日调用次数已超限,更换key");
                if(keyNum<keylist.size()-1) {
                    keyNum++;
                    tr.setKeyNum(keyNum);
//						tr.setKey(keylist.get(it));
                    tr.setKey(keylist.get(tr.getKeyNum()));

                    resultUid = getBaiduUUID(examples);
//						System.out.println("替换key:"+" "+tr.getKey());
                }
                else {
                    addressRecognitionLogger.info("baidukeys have been run out and the program stops");
//						System.out.println("key用完，已用完所有配额，程序停止");
                    ArrayList<String> end = new ArrayList<String>();
                    end.add("over");
                    return end;
                }

            }
            else {
                if(json0_test.containsKey("results")) {
                    JSONArray tt = json0_test.getJSONArray("results");
                    if(tt.size()!=0) {
                        for(int i = 0; i < tt.size() ; i++) {
                            if(tt.getJSONObject(i).containsKey("uid")){
                                resultUid.add(tt.getJSONObject(i).getString("uid"));
                                //								break;
                            }
                            else {
                                //								resultUid.add("");
                                continue;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultUid;
    }

    //通过uuid获取地址的标签

    /**
     * 通过uuid获取POI详情
     * @param uids
     * @return
     */
    public static List<String> getTag(String uids) {
        BaiduApi tr = new BaiduApi();
        List<String> tag = new ArrayList<String>();
        List<String> keylist = tr.getKeyList();
        Integer it = tr.getKeyNum();
        tr.setKey(keylist.get(it));
        try {

            URL resjson = new URL("http://api.map.baidu.com/place/v2/detail?uid="
                    + uids + "&output=json&scope=2&ak=" + tr.getKey());
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    resjson.openStream()));
            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            in.close();
            String str = sb.toString();

            System.out.println("return json:" + str);
//				System.out.println("原先key:"+" "+tr.getKey());
            JSONObject json0_test = JSON.parseObject(str);
            if(json0_test.containsKey("status")&&json0_test.getString("status").equals("401")) {

                addressRecognitionLogger.info("the concurrency containsKey exceeded the limited the quota and limit access");
//					System.out.println("当前并发量已经超过约定并发配额，限制访问");
                Thread.sleep(BAIDU_CONCURREANCY_LIMIT);
                tag = getTag(uids);
            }
            if(json0_test.containsKey("status")&&json0_test.getString("status").equals("302")) {
//					System.out.println("服务当日调用次数已超限,更换key");
                addressRecognitionLogger.info("the time of the service containsKey exceeded and exchange baidukey");
                if(it < keylist.size()-1) {
                    it++;
                    tr.setKeyNum(it);
                    tr.setKey(keylist.get(tr.getKeyNum()));

                    BAIDU_APP_KEY = BAIDU_APPS.get(it);
                    tag = getTag(uids);
//						System.out.println("替换key:"+" "+tr.getKey());
                }
                else {
                    addressRecognitionLogger.info("baidukeys have been run out and the program stops");
//						System.out.println("key用完，已用完所有配额，程序停止");
                    List<String> end = new ArrayList<String>();
                    end.add("over");
                    return end;
                }

            }
            else {

                if(json0_test.containsKey("result")) {
                    JSONObject tt = json0_test.getJSONObject("result");
                    if(tt.containsKey("address")) {
                        tag.add(tt.getString("address"));
                    }
                    else {
                        tag.add("null");
                    }
                    if(tt.containsKey("location")) {
                        if(tt.getJSONObject("location").containsKey("lng")&&tt.getJSONObject("location").containsKey("lat")) {
                            tag.add(tt.getJSONObject("location").getString("lng"));
                            tag.add(tt.getJSONObject("location").getString("lat"));
                        }
                        else {
                            tag.add("null");
                            tag.add("null");
                        }
                    }
                    else {
                        tag.add("null");
                        tag.add("null");
                    }
                    if(tt.containsKey("detail_info")) {
                        if(tt.getJSONObject("detail_info").containsKey("tag")) {
                            tag.add(tt.getJSONObject("detail_info").getString("tag"));
                        }
                        else {
                            tag.add("null");
                        }
                    }
                    else {
                        tag.add("null");
                    }
                }
                else {
                    tag.add("null");
                    tag.add("null");
                    tag.add("null");
                    tag.add("null");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }

    public static void main(String[] args) {
        //Test1 getBaiduUUID
        ArrayList<String> list = new ArrayList<String>();
        list.add("福建省");
        list.add("福州");
        list.add("福州大学");
        ArrayList<String> uuid = BaiduApi.getBaiduUUID(list);
//        for(String s:uuid){
//            System.out.println(s);
//        }
        //end Test1

        //Test2 getBaiduUUID
        for(String s:uuid){
            List<String> tag = BaiduApi.getTag(s);
            for (String s1 : tag) {
                System.out.println(s1);
            }
        }
        //end Test2
    }

    public List<String> getKeyList() {
        return BAIDU_APPS;
    }
    public void setKeyList(List<String> BAIDU_APPS) {
        this.BAIDU_APPS = BAIDU_APPS;
    }
    public String getKey() {
        return BAIDU_APP_KEY;
    }
    public void setKey(String BAIDU_APP_KEY) {
        this.BAIDU_APP_KEY = BAIDU_APP_KEY;
    }
    public int getKeyNum() {
        return keyNum;
    }
    public void setKeyNum(int keyNum) {
        this.keyNum = keyNum;
    }
}
