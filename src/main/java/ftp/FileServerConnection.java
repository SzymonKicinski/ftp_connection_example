package ftp;


import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import lombok.Setter;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Setter
public abstract class FileServerConnection {

    protected String knowHostsFile;
    protected String username;
    protected String password;
    protected String remoteHost;
    protected String type;
    private Integer port;

    public FileServerConnection(String knowHostsFile, String username, String password, String remoteHost, String type,
                                Integer port) {
        this.knowHostsFile = knowHostsFile;
        this.username = username;
        this.password = password;
        this.remoteHost = remoteHost;
        this.type = type;
        this.port = port;
    }

    public FileServerConnection(String username, String password, String remoteHost, Integer port ) {
        this.username = username;
        this.password = password;
        this.remoteHost = remoteHost;
        this.type = "sftp";
        this.port = port;
    }

    public FileServerConnection(String knowHostsFile, String username, String password, String remoteHost, Integer port) {
        this.knowHostsFile = knowHostsFile;
        this.username = username;
        this.password = password;
        this.remoteHost = remoteHost;
        this.port = port;
    }

    protected ChannelSftp connect(
            String knowHostsFile,
            String username,
            String remoteHost,
            String password,
            String type)
            throws JSchException {
        return null;
    }

    protected FTPClient connect(
            FTPClient ftpClient,
            String remoteHost,
            String username,
            String password,
            String type,
            Integer port
    ) throws IOException {
        return null;
    }

    protected Collection<String> getListOfFiles( String path ) throws IOException {
        return null;
    }

    protected Boolean uploadFileToFileServer(File localFile, String path, String remoteFile) {
        return null;
    }

    protected Boolean getFileFromFileServer(String source, String destination) throws IOException {
        return null;
    }

    protected Boolean deleteFileFromFileServer(String path, String fileName) throws IOException {
        return null;
    }

    public Boolean getFileFromFileServer(String remoteDirectory, String remoteFile, String localDirectory) {
        return null;
    }

    protected Boolean uploadFileToFileServer(String localFile, String remoteFile) {
        return null;
    }
}

