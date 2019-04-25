/**
 * 
 */
package edu.fzu.addressservice.datapreprocess;

import java.io.IOException;
import java.util.List;

/**
 * @author HC
 * @create 2017年7月20日
 * @code  
 */
public interface IDataPreprocess {
	public List<String> addressPreprocess(String inputpath) throws IOException; 
}
