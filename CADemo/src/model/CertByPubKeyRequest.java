package model;
/**
 * 封装本地CA证书申请数据
 * @author
 *
 */
public class CertByPubKeyRequest extends BaseRequest{
	
				
	private String certOID;	//证书扩展�?必填
	
	private int validDate = 1;	//有效期，必填。默�?�?
	
	private String certAlg = "";		//算法标识,必填
	
	private String pubKey;
	
	private String dn;

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

	public int getValidDate() {
		return validDate;
	}

	public void setValidDate(int validDate) {
		this.validDate = validDate;
	}

	public String getPubKey() {
		return pubKey;
	}

	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

}
