import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

// FIXME: Add certificate pinning. This is a SCAFFOLD.
// https://docs.oracle.com/javase/8/docs/api/index.html?java/security/cert/X509Certificate.html

public class CustomTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// check the client's cert
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// Check the server's cert
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0]; // Return empty array as a placeholder
	}
}
