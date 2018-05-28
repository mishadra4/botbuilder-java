package com.microsoft.bot.connector.sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.microsoft.bot.connector.SimpleMessageHandler;
import com.microsoft.bot.connector.customizations.CredentialProvider;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

public class BotChannelHttpsServer {
    private HttpsServer server;
    private static String protocol = "TLS";
    CredentialProvider credentialProvider;
    private String certificateAddress;

    public BotChannelHttpsServer(CredentialProvider credentialProvider, String certificateAddress){
        this.credentialProvider = credentialProvider;
        this.certificateAddress = certificateAddress;
    }

    public void Start(int port) {
        try {
            // load certificate
            String keystoreFilename = certificateAddress;
            char[] storepass = "mypassword".toCharArray();
            char[] keypass = "mypassword".toCharArray();
            String alias = "alias";
            FileInputStream fIn = new FileInputStream(keystoreFilename);
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(fIn, storepass);
            // display certificate
//			Certificate cert = keystore.getCertificate(alias);
//			System.out.println(cert);

            // setup the key manager factory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keystore, keypass);

            // setup the trust manager factory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(keystore);

            // create https server
            server = HttpsServer.create(new InetSocketAddress(port), 0);
            // create ssl context
            SSLContext sslContext = SSLContext.getInstance(protocol);
            // setup the HTTPS context and parameters
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            server.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        // initialise the SSL context
                        SSLContext c = SSLContext.getDefault();
                        SSLEngine engine = c.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        // get the default parameters
                        SSLParameters defaultSSLParameters = c.getDefaultSSLParameters();
                        params.setSSLParameters(defaultSSLParameters);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("Failed to create HTTPS server");
                    }
                }
            });

            System.out.println("server started at " + port);
            server.createContext("/api/messages", new SimpleMessageHandler(credentialProvider));
            server.setExecutor(null);
            server.start();
            System.out.println("Server started...");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(3979),0);
            httpServer.createContext("/api/messages", new SimpleMessageHandler(credentialProvider));
            httpServer.setExecutor(null);
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getPath() {
        return /*this.getClass().getClassLoader().getResource("").getPath() + "com/microsoft/bot/connector/sample/"*/"";
    }

    public void Stop() {
        server.stop(0);
        System.out.println("server stopped");
    }
}
