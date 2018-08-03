package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/*
 * MD5 算法
 */
public class MD5SignUtil {


	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getMD5Code(String SourceString) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(SourceString.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getMD5CodeLess(String SourceString) {
		return getMD5Code(SourceString).substring(8, 24);
	}

	private static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	/**
	 * map转成TreeMap
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private static TreeMap<String, String> toTreeMap(Map<String, String> map) {
		if(map == null){
			return null;
		}
		TreeMap<String, String> newTreeMap = new TreeMap<String, String>();
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> next = iterator.next();
			newTreeMap.put(next.getKey(), next.getValue());
		}
		return newTreeMap;
	}
	/**
	 * map参数计算签名：按照字典顺序对value拼装字符串，计算签名
	 * @param retMap 返回Map
	 * @param bizKey 应用系统secretKey
	 * @return
	 */
	public static String getMd5Sign(Map<String, String> retMap ,String bizKey) {
		TreeMap<String, String> treeMap = toTreeMap(retMap);
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> entrySet = treeMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			if (entry.getValue() != null && ! "sign".equals(entry.getKey())) {
				sb.append(entry.getValue());
			}
		}
		String sign = getMD5Code(sb.append(bizKey).toString()).toUpperCase();
		return sign;
	}

}