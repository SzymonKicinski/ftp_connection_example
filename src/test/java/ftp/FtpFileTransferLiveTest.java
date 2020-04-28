package ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.*;


import java.io.File;
import java.io.IOException;
import java.util.Collection;


public class FtpFileTransferLiveTest {
    private String username = "3319690";
    private String password = "";
    private String remoteHost = "f18-preview.royalwebhosting.net";
    private Integer port = 21;
    private String remoteFile = "test.txt";
    private String localFileName = "test.txt";
    private String localDirectory = "src\\test\\resource";
    private String pathToResources = "D:\\OR\\JavaT\\esb\\";
    private File localFile;
    private String knownHostsFileLock = "/DELIVERY/known_hosts";
    private String type = "ftp";
    private FTPConnection ftpConnection;
    private FTPClient ftpClient;
    private String trash;

    @Before
    public void init() throws IOException {
        System.out.println("Init FtpFileTransferLiveTest");
        ftpConnection = new FTPConnection(
                knownHostsFileLock,
                username,
                password,
                remoteHost,
                type,
                port
        );
        ftpClient = new FTPClient();
        ftpConnection.connect(ftpClient, remoteHost, username, password, type, port);
    }

    @After
    public void cleanup(){
        System.out.println("Cleanup FtpFileTransferLiveTest");
        System.out.println("Cleanup FtpFileTransferLiveTest");
        System.out.println("Cleanup FtpFileTransferLiveTest");
        System.out.println("Cleanup FtpFileTransferLiveTest");
        ftpConnection.disconnect();
    }

    @Test
    public void uploadToServerFTPNegative() {
        System.out.println("Launching uploadToServerPositive");
        System.out.println("Launching uploadToServerPositive");
        System.out.println("Launching uploadToServerPositive");
        System.out.println("Launching uploadToServerPositive");
        System.out.println("Launching uploadToServerPositive");
        localFile = new File(localDirectory + "\\" + localFileName);
        Boolean itIsWorks = ftpConnection.uploadFileToFileServer(
                localFile,
                pathToResources + localDirectory + "\\" + localFileName + "spora",
                remoteFile);
        Assert.assertFalse(itIsWorks);
    }

    @Test
    public void getFileFromFileServerPositive() throws IOException {
        ftpConnection.getFileFromFileServer(  remoteFile,
                pathToResources + localDirectory + "\\" + "test.txt");

        Assert.assertTrue(new File(pathToResources + localDirectory + "\\" + "test.txt").exists());
        File file = new File(pathToResources + localDirectory + "\\" + "test.txt");
        file.createNewFile();
    }

    @Test
    public void uploadToServerFTPPositiveSecondApproach() throws IOException {
        System.out.println("Launching uploadToServerPositive");
        localFile = new File(localDirectory + "\\" + localFileName);
        Boolean itIsWorks = ftpConnection.uploadFileFTPTwo(
                localFile,
                pathToResources + localDirectory + "\\" + localFileName,
                "folder\\" + remoteFile);
        Assert.assertTrue(itIsWorks);
    }

    @Test
    public void uploadToServerFTPPositive() {
        System.out.println("Launching uploadToServerPositive");
        localFile = new File(localDirectory + "\\" + localFileName);
        Boolean itIsWorks = ftpConnection.uploadFileToFileServer(
                localFile,
                pathToResources + localDirectory + "\\" + localFileName,
                remoteFile);
        Assert.assertTrue(itIsWorks);
    }

    @Test
    public void givenRemoteFile_whenListingRemoteFiles_thenItIsContainedInListNegative() throws IOException {
        Collection<String> files = ftpConnection.getListOfFiles("");
        Assert.assertTrue(files.contains("test.txt"));
    }



    @Test
    public void deleteFileFromFileServerPositive() throws IOException {
        Assert.assertTrue(ftpConnection.deleteFileFromFileServer("", "test.txt"));
    }
}
