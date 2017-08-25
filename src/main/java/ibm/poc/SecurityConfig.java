package ibm.poc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Configuration
public class SecurityConfig {
    private final String keystoreType = "JKS";
    private final String cryptoProtocol = "TLSv1.2";

    @Bean
    public SSLSocketFactory sslSocketFactory(@Value("${ssl.keystore}") String keystorePath,
                                             @Value("${ssl.keystore.password}") String keystorePassword,
                                             @Value("${ssl.truststore}") String truststorePath,
                                             @Value("${ssl.truststore.password}") String truststorePassword) throws Exception {
        System.out.println("Creating SSLSocketFactory...");
        Class.forName("com.sun.net.ssl.internal.ssl.Provider");
        System.out.println("JSSE is installed correctly!");
        System.out.println(System.getProperty("com.ibm.mq.cfg.useIBMCipherMappings"));

        KeyStore keyStore = loadKeyStore(keystorePath, keystorePassword);
        KeyStore trustStore = loadTrustStore(truststorePath, truststorePassword);

        // Create a default trust and key manager
        KeyManagerFactory keyManagerFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

        // Initialise the managers
        trustManagerFactory.init(trustStore);
        keyManagerFactory.init(keyStore, keystorePassword.toCharArray());

        // Get an SSL context.
        // Note: not all providers support all CipherSuites. But the
        // "SSL_RSA_WITH_3DES_EDE_CBC_SHA" CipherSuite is supported on both SunJSSE
        // and IBMJSSE2 providers

        // Accessing available algorithm/protocol in the SunJSSE provider
        // see http://java.sun.com/javase/6/docs/technotes/guides/security/SunProviders.html
        SSLContext sslContext = SSLContext.getInstance(cryptoProtocol);

        // Acessing available algorithm/protocol in the IBMJSSE2 provider
        // see http://www.ibm.com/developerworks/java/jdk/security/142/secguides/jsse2docs/JSSE2RefGuide.html
        // SSLContext sslContext = SSLContext.getInstance("SSL_TLS");
        System.out.println("SSLContext provider: " + sslContext.getProvider().toString());

        // Initialise our SSL context from the key/trust managers
        sslContext.init(keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(), null);

        // Get an SSLSocketFactory to pass to WMQ
        return sslContext.getSocketFactory();
    }

    private KeyStore loadKeyStore(String keystorePath, String keystorePassword)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        // instantiate a KeyStore with type JKS
        KeyStore keyStore = KeyStore.getInstance(keystoreType);
        // load the contents of the KeyStore
        keyStore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());
        System.out.println("Number of keys in keystore: " + keyStore.size());
        return keyStore;
    }

    private KeyStore loadTrustStore(String truststorePath, String truststorePassword)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        // Create a keystore object for the truststore
        KeyStore trustStore = KeyStore.getInstance(keystoreType);
        trustStore.load(new FileInputStream(truststorePath), truststorePassword.toCharArray());
        System.out.println("Number of keys in truststore: " + trustStore.size());
        return trustStore;
    }
}
