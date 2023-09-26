package org.Hamm.minisockets;

import java.io.Serializable;

public class FileToBeTransfered implements Serializable{
    private static final long serialVersionUID = 1L;

    private String filename;
    private int fileSize;
    public byte[] fileData;
    private String status;

    public FileToBeTransfered(String fileName,int fileSize) {
        this.filename=fileName;
        this.fileSize=fileSize;
        this.status="";
        this.fileData=new byte[fileSize];
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

