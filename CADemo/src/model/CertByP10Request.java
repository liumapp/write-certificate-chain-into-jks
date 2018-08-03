package model;
/**
 * 封装本地CA证书申请数据
 * @author
 *
 */
public class CertByP10Request extends BaseRequest{
	
				
	private String p10;	//p10,必填
	
	private String certOID;	//证书扩展�?必
	
	private String userName;	//颁发给的�?必填
	
	private int validDate = 1;	//有效期，必填。默�?�?
	
	private String certAlg = "";		//算法标识,必填
	     
	
	private String certOidKey ;

	public String getCertOidKey() {
		return certOidKey;
	}

	public void setCertOidKey(String certOidKey) {
		this.certOidKey = certOidKey;
	}

	public String getCertAlg() {
		return certAlg;
	}

	public void setCertAlg(String certAlg) {
		this.certAlg = certAlg;
	}

	public String getCertOID() {
		return certOID;
	}

	public void setCertOID(String certOID) {
		this.certOID = certOID;
	}

	public String getP10() {
		return p10;
	}

	public void setP10(String p10) {
		this.p10 = p10;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getValidDate() {
		return validDate;
	}

	public void setValidDate(int validDate) {
		this.validDate = validDate;
	}

	
}
