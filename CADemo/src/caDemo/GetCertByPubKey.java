package caDemo;

import java.io.UnsupportedEncodingException;


import model.CertByPubKeyRequest;
import utils.Base64Utils;
import utils.SendHttpUtils;
/**
 * 根据公钥申请云证书
 * @author LlENOVO
 *
 */
public class GetCertByPubKey {
	public static void main(String[] args) throws UnsupportedEncodingException {
		//证书请求地址
		String url = "https://139.196.95.196:8443/CA/v1.1/getCertByPubKey";
		//创建请求对象
		CertByPubKeyRequest certByPubKeyRequest = new CertByPubKeyRequest();
		//设置公钥，必填
		certByPubKeyRequest.setPubKey("MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEncyHNFEHU2fAF1nfonx8JBrJH6yCjhTulSU9yDULuWBK/1B9IdVO23S5cOQG76Ah2OoO2DEg147iwMx9yU0tdQ==");
		//设置证书扩展项，可不填；如传必须为Base64编码
		certByPubKeyRequest.setCertOID("5rWL6K+V5pyN5Yqh5LiT55So");
		//设置商户号appKey，必填
		certByPubKeyRequest.setAppKey("LgNLxgd2t6nDkxSl");
		//设置证书有效期(天)，必填
		certByPubKeyRequest.setValidDate(1);
		//设置证书算法标识，RSA或者SM2,必填
		certByPubKeyRequest.setCertAlg("SM2");
		//设置证书dn项，必填
		certByPubKeyRequest.setDn(Base64Utils.encodeToString("c=cn,o=测试,ou=测试,cn=张雷".getBytes("UTF-8")));
		//发送请求
		String certResult = SendHttpUtils.sendRequest(url, certByPubKeyRequest,3,5000);
	    //打印请求结果
		if (certResult != null) {
			System.out.println(certResult);
		}else {
			System.out.println("请求异常!!!");
		}
		
	}
}
