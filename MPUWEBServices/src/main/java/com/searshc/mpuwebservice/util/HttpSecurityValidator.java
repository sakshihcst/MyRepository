package com.searshc.mpuwebservice.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class HttpSecurityValidator {

	  public static void trustSelfSignedSSL()
	   {
		try {
			final SSLContext ctx = SSLContext.getInstance("TLS");
			final X509TrustManager tm = new X509TrustManager() {
			public void checkClientTrusted(final X509Certificate[] xcs,
			final String string) throws CertificateException {
			// do nothing
			}
	
		public void checkServerTrusted(final X509Certificate[] xcs,
			final String string) throws CertificateException {
			// do nothing
			}
	
		public X509Certificate[] getAcceptedIssuers() {
		return null;
		}
		};
		ctx.init(null, new TrustManager[] { tm }, null);
		SSLContext.setDefault(ctx);
		} catch (final Exception ex) {
		ex.printStackTrace();
		}
	}

}
