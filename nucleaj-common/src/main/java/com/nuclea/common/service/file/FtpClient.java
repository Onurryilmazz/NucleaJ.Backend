package com.nuclea.common.service.file;

import com.nuclea.common.service.marker.IScopedService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * FTP client service for CDN file operations.
 */
@Service
@Slf4j
public class FtpClient implements IScopedService {

    @Value("${app.ftp.host:}")
    private String ftpHost;

    @Value("${app.ftp.port:21}")
    private int ftpPort;

    @Value("${app.ftp.username:}")
    private String ftpUsername;

    @Value("${app.ftp.password:}")
    private String ftpPassword;

    /**
     * Upload file to FTP server.
     */
    public boolean uploadFile(String localFilePath, String remoteFilePath) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpHost, ftpPort);
            int replyCode = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.error("FTP server connection failed. Reply code: {}", replyCode);
                return false;
            }

            boolean login = ftpClient.login(ftpUsername, ftpPassword);
            if (!login) {
                log.error("FTP login failed");
                return false;
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Create remote directories if needed
            createRemoteDirectories(ftpClient, remoteFilePath);

            try (InputStream inputStream = new FileInputStream(localFilePath)) {
                boolean uploaded = ftpClient.storeFile(remoteFilePath, inputStream);
                if (uploaded) {
                    log.info("File uploaded successfully to FTP: {}", remoteFilePath);
                } else {
                    log.error("File upload failed to FTP: {}", remoteFilePath);
                }
                return uploaded;
            }
        } catch (IOException e) {
            log.error("Error uploading file to FTP: {}", e.getMessage(), e);
            return false;
        } finally {
            disconnectFtp(ftpClient);
        }
    }

    /**
     * Download file from FTP server.
     */
    public boolean downloadFile(String remoteFilePath, String localFilePath) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpHost, ftpPort);
            ftpClient.login(ftpUsername, ftpPassword);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            try (OutputStream outputStream = new FileOutputStream(localFilePath)) {
                boolean downloaded = ftpClient.retrieveFile(remoteFilePath, outputStream);
                if (downloaded) {
                    log.info("File downloaded successfully from FTP: {}", remoteFilePath);
                } else {
                    log.error("File download failed from FTP: {}", remoteFilePath);
                }
                return downloaded;
            }
        } catch (IOException e) {
            log.error("Error downloading file from FTP: {}", e.getMessage(), e);
            return false;
        } finally {
            disconnectFtp(ftpClient);
        }
    }

    /**
     * Delete file from FTP server.
     */
    public boolean deleteFile(String remoteFilePath) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpHost, ftpPort);
            ftpClient.login(ftpUsername, ftpPassword);

            boolean deleted = ftpClient.deleteFile(remoteFilePath);
            if (deleted) {
                log.info("File deleted successfully from FTP: {}", remoteFilePath);
            } else {
                log.error("File deletion failed from FTP: {}", remoteFilePath);
            }
            return deleted;
        } catch (IOException e) {
            log.error("Error deleting file from FTP: {}", e.getMessage(), e);
            return false;
        } finally {
            disconnectFtp(ftpClient);
        }
    }

    /**
     * Create remote directories recursively.
     */
    private void createRemoteDirectories(FTPClient ftpClient, String remotePath) throws IOException {
        String[] directories = remotePath.split("/");
        StringBuilder path = new StringBuilder();

        for (int i = 0; i < directories.length - 1; i++) {
            if (directories[i].isEmpty()) continue;

            path.append("/").append(directories[i]);
            String currentPath = path.toString();

            if (!ftpClient.changeWorkingDirectory(currentPath)) {
                ftpClient.makeDirectory(currentPath);
                ftpClient.changeWorkingDirectory(currentPath);
            }
        }
    }

    /**
     * Disconnect from FTP server.
     */
    private void disconnectFtp(FTPClient ftpClient) {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            log.error("Error disconnecting from FTP: {}", e.getMessage());
        }
    }
}
