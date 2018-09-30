package com.betsapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


@Configuration
@ConfigurationProperties(prefix = "betsconf")
public class BetsAppProperties {

    private String fileToProcess;

    public Resource getFileToProcessAsResource() {
        return new FileSystemResource(getFileToProcess());
    }

    public String getFileToProcess() {
        return fileToProcess;
    }

    public void setFileToProcess(String fileToProcess) {
        this.fileToProcess = fileToProcess;
    }

}
