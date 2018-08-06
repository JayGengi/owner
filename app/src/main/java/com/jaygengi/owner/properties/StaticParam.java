package com.jaygengi.owner.properties;

/**
*  静态参数
*  create by flyingshare 60075
*/
public class StaticParam {

	/**
	 * BaseUrl
	 */
	public static final String BASE_URL = "https://gank.io/api/";
	public static final String BASE_API = "api";

	/**---------------------系统语言_begin-----------------------------------*/
	/** 简体中文 */
	public static final String ZH_CN = "zh_CN";
	/** 繁体中文_香港 */
	public static final String ZH_HK = "zh_HK";
	/** 繁体中文_台湾 */
	public static final String ZH_TW = "zh_TW";
	/** 繁体中文_澳门 */
	public static final String zh_MO = "zh_MO";
	/** 英语 */
	public static final String EN_US = "en_US";

	/**---------------------系统语言_end-----------------------------------*/

	/**---------------------页面操作回调参数_begin-----------------------------------*/
	public static final int RESULT_GET_IP_INFO = 101;
	public static final int RESULT_STAY_TIMETAMP = 102;
	/**---------------------页面操作回调参数_end-----------------------------------*/

	/**---------------------列表设置_begin-------------------------*/
	public static final int ROWS = 10;//一次请求10条数据
	public static final int MAX_ROWS = 100;//一次请求100条数据
	public static int REFRESHPAGE = 1;//刷新请求页码
	public static int LOADMOREPAGE = 1;//加载更多请求页码
	/**---------------------列表设置_end-------------------------*/

	//文件保存目录
	public static String fileSavePath = "";


	/**
	 * 网络请求时长
	 */
	public static final int HTTP_TIME = 5000;
}


