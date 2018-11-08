package com.fight.light.okhttp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLUtils {
    public static class SSLParams
    {
        public SSLSocketFactory sSLSocketFactory;
        public X509TrustManager trustManager;
    }
   public static TrustManager[] createTrustManager(){
       return createTrustManager(getCertificateStream());
   }
    /**
     * 默认证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    public static SSLParams createSSLSocketFactory(InputStream[] certificates,InputStream bksFile, String password) {
        SSLParams sslParams = new SSLParams();
        try {
            SSLContext sc  = SSLContext.getInstance("TLS");
            TrustManager[]  trustManager = SSLUtils.createTrustManager(certificates);
            KeyManager[] keyManagers = prepareKeyManager(bksFile, password);
            X509TrustManager x509TrustManager;
            if (trustManager == null){
                x509TrustManager = new SSLUtils.TrustAllManager();
            }else{
                x509TrustManager = (X509TrustManager) trustManager[0];
            }
            sc.init(keyManagers, new TrustManager[]{x509TrustManager},
                    null);
            sslParams.sSLSocketFactory = sc.getSocketFactory();
            sslParams.trustManager = x509TrustManager;
            return sslParams;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslParams;
    }

    private static KeyManager[] prepareKeyManager(InputStream bksFile, String password)
    {
        try
        {
            if (bksFile == null || password == null) return null;

            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();

        } catch (KeyStoreException e)
        {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e)
        {
            e.printStackTrace();
        } catch (CertificateException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public   static  TrustManager[] createTrustManager(InputStream... certificates){
        if (certificates == null || certificates.length <= 0) return null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
//            char[] password = getPassword();
//         try (FileInputStream fis = new FileInputStream("keyStoreName")) {
//        ks.load(fis, password);
//   }
            int i = 0;
            for (InputStream certificate:certificates) {
                String certificateAlia = Integer.toString(i++);
                Certificate cert = cf.generateCertificate(certificate);
                keyStore.setCertificateEntry(certificateAlia,cert);
                if (certificate!= null)
                    certificate.close();
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            // X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            return trustManagers;

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * 信任所有
     */
    public static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
    private static InputStream getCertificateStream(){
        String certStream ="-----BEGIN CERTIFICATE-----\n" +
                "MIIG6TCCBdGgAwIBAgIQMFs+HdS1ESQ1ZryDtz11lTANBgkqhkiG9w0BAQsFADCB\n" +
                "kDELMAkGA1UEBhMCR0IxGzAZBgNVBAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G\n" +
                "A1UEBxMHU2FsZm9yZDEaMBgGA1UEChMRQ09NT0RPIENBIExpbWl0ZWQxNjA0BgNV\n" +
                "BAMTLUNPTU9ETyBSU0EgRG9tYWluIFZhbGlkYXRpb24gU2VjdXJlIFNlcnZlciBD\n" +
                "QTAeFw0xODA4MDMwMDAwMDBaFw0xOTExMDYyMzU5NTlaMHwxITAfBgNVBAsTGERv\n" +
                "bWFpbiBDb250cm9sIFZhbGlkYXRlZDElMCMGA1UECxMcSG9zdGVkIGJ5IENvbW9k\n" +
                "byBKYXBhbiwgSW5jLjEcMBoGA1UECxMTQ09NT0RPIFNTTCBXaWxkY2FyZDESMBAG\n" +
                "A1UEAwwJKi5hcHBzLnRsMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA\n" +
                "3nPr80vWRZ4nSFlj9aK5INOUkBE0o2ohTIfeSDTnDXuRlxq7E22TgzhoAtXMA7eU\n" +
                "NUzPJzDdddp1hzUEr9fpu9WVUHvjDd7OABpfd5uq8oA8UKL6UgnkfvNujNCuzdkX\n" +
                "6wuWVIz+15JQXyrQBO+l7AwHkD3G/fqhmtOSSglzEeIBSvcPU0LSTP3Ei9Fkw8Y9\n" +
                "pHSCmVkwevW5RhI69vb+qT9wsQzNhptAa7DQfGoI0/z+M4NHM3PPjYoyyiaplRE1\n" +
                "mtufO+izH8RmZK+031coDrTNN2grmfApj3dLeFc/peN9ZhCFNmVcARytDhw0f1no\n" +
                "SF8XW/PsUkgkD1BWkOkK9wIDAQABo4IDUDCCA0wwHwYDVR0jBBgwFoAUkK9qOpRa\n" +
                "C9iQ6hJWc99DtDoo2ucwHQYDVR0OBBYEFOHAVaVNoLqBvurQ/3cc6PMNjZH9MA4G\n" +
                "A1UdDwEB/wQEAwIFoDAMBgNVHRMBAf8EAjAAMB0GA1UdJQQWMBQGCCsGAQUFBwMB\n" +
                "BggrBgEFBQcDAjBPBgNVHSAESDBGMDoGCysGAQQBsjEBAgIHMCswKQYIKwYBBQUH\n" +
                "AgEWHWh0dHBzOi8vc2VjdXJlLmNvbW9kby5jb20vQ1BTMAgGBmeBDAECATBUBgNV\n" +
                "HR8ETTBLMEmgR6BFhkNodHRwOi8vY3JsLmNvbW9kb2NhLmNvbS9DT01PRE9SU0FE\n" +
                "b21haW5WYWxpZGF0aW9uU2VjdXJlU2VydmVyQ0EuY3JsMIGFBggrBgEFBQcBAQR5\n" +
                "MHcwTwYIKwYBBQUHMAKGQ2h0dHA6Ly9jcnQuY29tb2RvY2EuY29tL0NPTU9ET1JT\n" +
                "QURvbWFpblZhbGlkYXRpb25TZWN1cmVTZXJ2ZXJDQS5jcnQwJAYIKwYBBQUHMAGG\n" +
                "GGh0dHA6Ly9vY3NwLmNvbW9kb2NhLmNvbTAdBgNVHREEFjAUggkqLmFwcHMudGyC\n" +
                "B2FwcHMudGwwggF9BgorBgEEAdZ5AgQCBIIBbQSCAWkBZwB2AO5Lvbd1zmC64UJp\n" +
                "H6vhnmajD35fsHLYgwDEe4l6qP3LAAABZP7UdgsAAAQDAEcwRQIhAKmzjPUOg4KM\n" +
                "ctbs9SEynlkJFJdlwg7eKjSgXhJ04AzKAiAJpCpZOPP1S31CRY3n54UtTmktb/t8\n" +
                "fBCCEsAyelTDfAB2AHR+2oMxrTMQkSGcziVPQnDCv/1eQiAIxjc1eeYQe8xWAAAB\n" +
                "ZP7UdlIAAAQDAEcwRQIhAKc+YGp9mcH/GFjspUBY0OU4QMfgXqP5274YJQ1aUPYo\n" +
                "AiBP88iGDUapEFLb6j4BB6M9BnkDjcs10uF/54/PcNolYQB1AFWB1MIWkDYBSuoL\n" +
                "m1c8U/DA5Dh4cCUIFy+jqh0HE9MMAAABZP7Ud6MAAAQDAEYwRAIgFVlSlMy8NHtI\n" +
                "a4MuQ1sFuoKN9oXEv73kOEJM8JRLZbUCIE2N/9qA9D/DpRMQSTcuxt+qQoPqA/gY\n" +
                "xzMkF0MjbAmxMA0GCSqGSIb3DQEBCwUAA4IBAQCGg53pcegoHZarvUUfQx7M8eak\n" +
                "vGmmFu1Gv3HIpB4n+pFOApPNs9wMbKRDquR43aKIotmoO0zpaTf9iQxo4SEbhNAl\n" +
                "zeJLhCwDXjwOqK1b+DQAwzQMq7HiStMlBn6R8FDYywxIae6tnWBdsqfdnXNbQ+RM\n" +
                "aDBcxjLRjtTtKfM4BzPWp2ex1+TUmi8bmsYFl288R8tpRFLVguOPePJXB9hSLzYF\n" +
                "dGoSIbVJnqxflC6hCKkCvYG2WHhhBUrA85e5Na/KXWTkJ1/txMVyuzKKgLie3Gi2\n" +
                "ougXOBxVpDsBJcXATkcCSTaGwbzAUCfScA9IQZUfgPqgVQqLQal0d0YyIA9T\n" +
                "-----END CERTIFICATE-----";
//              return new Buffer()
//                      .writeUtf8(certStream).inputStream();
        try {
            return   new ByteArrayInputStream(certStream.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
