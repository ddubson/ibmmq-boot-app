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

        KeyStore keyStore = loadKeyStore(keystorePath, keystorePassword);
        KeyStore trustStore = loadTrustStore(truststorePath, truststorePassword);

        KeyManagerFactory keyManagerFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

        trustManagerFactory.init(trustStore);
        keyManagerFactory.init(keyStore, keystorePassword.toCharArray());

        SSLContext sslContext = SSLContext.getInstance(cryptoProtocol);
        System.out.println("SSLContext provider: " + sslContext.getProvider().toString());

        // Initialise our SSL context from the key/trust managers
        sslContext.init(keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(), null);

        return sslContext.getSocketFactory();
    }

    private KeyStore loadKeyStore(String keystorePath, String keystorePassword)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance(keystoreType);
        keyStore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());
        System.out.println("Number of keys in keystore: " + keyStore.size());
        return keyStore;
    }

    private KeyStore loadTrustStore(String truststorePath, String truststorePassword)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore trustStore = KeyStore.getInstance(keystoreType);
        trustStore.load(new FileInputStream(truststorePath), truststorePassword.toCharArray());
        System.out.println("Number of keys in truststore: " + trustStore.size());
        return trustStore;
    }
}
