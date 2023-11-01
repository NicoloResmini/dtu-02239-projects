package src;
import java.io.IOException;
import java.rmi.server.RMIServerSocketFactory;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class RMISSLServerSocketFactory implements RMIServerSocketFactory {
    private final ServerSocketFactory delegate;
    private final String[] protocols;
    private final String[] enabledCipherSuites;
    public RMISSLServerSocketFactory() {
        this.delegate = SSLServerSocketFactory.getDefault();
        this.enabledCipherSuites = new String[]{"TLS_AES_128_GCM_SHA256"};
        this.protocols = new String[]{"TLSv1.3"};
    }
    @Override
    public SSLServerSocket createServerSocket(int port) throws IOException {
        SSLServerSocket serverSocket = (SSLServerSocket) delegate.createServerSocket(port);
        
        serverSocket.setEnabledProtocols(this.protocols);
        serverSocket.setEnabledCipherSuites(this.enabledCipherSuites);   

        return serverSocket;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RMISSLServerSocketFactory;
    }
}
