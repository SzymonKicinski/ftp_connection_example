package ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class FTPConnection extends FileServerConnection {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String knowHostsFile;
    private String username;
    private String password;
    private String remoteHost;
    private String type;
    private Integer port;
    private FTPClient ftp;
    private FileOutputStream out;
    InputStream inputStream;
    OutputStream outputStream;

    public FTPConnection(
            String knowHostsFile,
            String username,
            String password,
            String remoteHost,
            String type,
            Integer port) {
        super(knowHostsFile, username, password, remoteHost, type, port);
        this.knowHostsFile = knowHostsFile;
        this.username = username;
        this.password = password;
        this.remoteHost = remoteHost;
        this.type = type;
        this.port = port;
    }

    @Override
    protected FTPClient connect(FTPClient ftp, String remoteHost, String username, String password, String type,
                                Integer port)
            throws IOException {
        ftp.connect(remoteHost, port);
        ftp.login(username, password);
        ftp.enterLocalPassiveMode();
        System.out.println(ftp.getStatus());
        System.out.println(ftp.isConnected());
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        return this.ftp = ftp;
    }

    protected void disconnect() {
        try {
            if (ftp.isConnected()) {
                ftp.logout();
                ftp.disconnect();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected Collection<String> getListOfFiles(String path) throws IOException {
            FTPFile[] files = ftp.listFiles(path);
            return Arrays.stream(files)
                    .map(FTPFile::getName)
                    .collect(Collectors.toList());
        }

    @Override
    protected Boolean uploadFileToFileServer(File localFile, String path, String remoteFile) {
        try {
            localFile = new File(path);
            inputStream = new FileInputStream(localFile);
            if (ftp.storeFile(remoteFile, inputStream)) {
                inputStream.close();
                return true;
            }
            inputStream.close();
        } catch (IOException ex) {
            log.error("Connection of FTP server accrued some problem with login ");
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean getFileFromFileServer(String source, String destination) throws IOException {
         out = new FileOutputStream(destination);
         return ftp.retrieveFile(source, out);
    }

    @Override
    public Boolean deleteFileFromFileServer(String path, String fileName) throws IOException {
        if (ftp.deleteFile(path + "/" + fileName)) {
            return true;
        } else {
            return false;
        }
    }

    public void closeFileOutputStream() throws IOException {
        out.close();
    }

    public Boolean uploadFileFTPTwo(File localFile, String path, String remoteFileName) throws IOException {
        try {
            localFile = new File(path);
            inputStream = new FileInputStream(localFile);

            outputStream = ftp.storeFileStream(remoteFileName);
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();
            if (ftp.completePendingCommand()) {
                return true;
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return false;
    }

}

