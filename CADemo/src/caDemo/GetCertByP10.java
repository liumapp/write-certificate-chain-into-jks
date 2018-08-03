package caDemo;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import model.CertByP10Request;
import utils.Base64Utils;
import utils.SendHttpUtils;
/**
 * 根据P10生云证书
 * @author LlENOVO
 *
 */
public class GetCertByP10 {
	public static void main(String[] args) throws UnsupportedEncodingException {
		//证书请求地址--注意
		String url = "https://139.196.95.196:8443/CA/v1.1/getCert";
		//创建请求对象  
		CertByP10Request certByP10Request = new CertByP10Request();
		//设置商户号appKey，必填 
		certByP10Request.setAppKey("LgNLxgd2t6nDkxSl");
		//设置P10，必填
		//rsa:MIIBbzCB2wIBADAeMQ8wDQYDVQQDDAblvKDnj4oxCzAJBgNVBAYTAmNuMIG1MA0GCSqGSIb3DQEBAQUAA4GjADCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAqkp61FXa9Nqi65D18qL5+Qn3BKICHggY6J2saj5P6Od8h6f4CkCTE3wLLmauCBuOYFF75oNN3AbAIZa7CGcS/4JnS+PxDDqPJOks3WATL9lKDznjDuvyaGlWMXR4GeRaGWYDHkNcCCcYTV5/XHi1GrPwgiH1w5ShnBRTCM9S5p0CAwEAATALBgkqhkiG9w0BAQUDgYEAobiDmAISo8teQ4a+r/RM76QpCqgpu+e+w+TzYW7WPCDJnY+gjoNz7IuxgFpHoPxH7jQpIPBHrHE9VtzWnyUnBhhP/lNHyIqptx/XD0xtl5/3Ynf/q3FcuLS1N2NSa2Y+l06NUbdxN7RO6mkFmAtoRz7lmIE3LTvkCfo+OFs254g=
		//sm2:MIHXMH4CAQAwHjEPMA0GA1UEAwwG5byg54+KMQswCQYDVQQGEwJjbjBZMBMGByqGSM49AgEGCCqBHM9VAYItA0IABE1BxgB8uyjzOkHdXUxC1d/hMy0469syE3a83fwGnbIcqmXq2XIoTArz/0Qw8lfc6B6aRnmFf3HN2yZcpYuTeK4wCgYIKoEcz1UBg3UDSQAwRgIhAIvJZJFU0/j8azScvKYOhyiRLlNYOPG0FuReEiDbTp3TAiEAuVLP1Lq7cx9JKXBP6JXWKc+qY+mcSSpbAWNua0IA2w4=
		certByP10Request.setP10("MIHXMH4CAQAwHjEPMA0GA1UEAwwG5byg54+KMQswCQYDVQQGEwJjbjBZMBMGByqGSM49AgEGCCqBHM9VAYItA0IABE1BxgB8uyjzOkHdXUxC1d/hMy0469syE3a83fwGnbIcqmXq2XIoTArz/0Qw8lfc6B6aRnmFf3HN2yZcpYuTeK4wCgYIKoEcz1UBg3UDSQAwRgIhAIvJZJFU0/j8azScvKYOhyiRLlNYOPG0FuReEiDbTp3TAiEAuVLP1Lq7cx9JKXBP6JXWKc+qY+mcSSpbAWNua0IA2w4=");
		//设置证书有效期(天)，必填 
		certByP10Request.setValidDate(1);
		//设置证书算法2.148.1.1标识，RSA或者SM2,必填
		certByP10Request.setCertAlg("SM2");
		//设置证书经办人姓名，必填
		certByP10Request.setUserName(Base64Utils.encodeToString("张珊".getBytes("UTF-8")));
		//设置证书扩展项，可不填；如传必须为Base64编码
		certByP10Request.setCertOID("MTIzNDU2Nzg5");
		//设置证书扩展项key,可不传,但如果传入证书扩展项,则必遵循以下要求
		//RSA证书
		//1.2.86.11.7.1--身份证号
		//1.2.86.11.7.3--工商登记证
		//1.2.86.11.7.4--统一社会信用代码
		//1.2.86.11.7.5--纳税人识别号
		//SM2证书
		//1.2.156.10260.4.1.1--身份证号
		//1.2.156.10260.4.1.3--工商登记证
		//1.2.156.10260.4.1.4--统一社会信用代码
		//1.2.156.10260.4.1.5--纳税人识别号
		certByP10Request.setCertOidKey("1.2.156.10260.4.1.1");
		//发送请求
		
		String result = SendHttpUtils.sendRequest(url, certByP10Request ,3 ,5000);
		
		//打印请求结果
		if (result != null ) {
			System.out.println(result);
			
		}else {
			System.out.println("请求异常!!!");
		}
		
	}
}
