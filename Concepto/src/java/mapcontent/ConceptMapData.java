/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapcontent;

import java.io.File;

/**
 *
 * @author chanakya
 */
public class ConceptMapData {
    private String input_text;
    private String output_text;
    private String chapter_name;
    private String chapter_number;
    private String section_number;
    private String section_name;

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }
    private File uploadedFile;
    private String uploadedFileContentType;
    private String uploadedFileFileName;
    public File getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(File uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getUploadedFileContentType() {
        return uploadedFileContentType;
    }

    public void setUploadedFileContentType(String uploadedFileContentType) {
        this.uploadedFileContentType = uploadedFileContentType;
    }

    public String getUploadedFileFileName() {
        return uploadedFileFileName;
    }

    public void setUploadedFileFileName(String uploadedFileName) {
        this.uploadedFileFileName = uploadedFileName;
    }
    
    public String getInput_text() {
        return input_text;
    }



    public void setInput_text(String input_text) {
        this.input_text = input_text;
    }

    public String getOutput_text() {
        return output_text;
    }

    public void setOutput_text(String output_text) {
        this.output_text = output_text;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getChapter_number() {
        return chapter_number;
    }

    public void setChapter_number(String chapter_number) {
        this.chapter_number = chapter_number;
    }

    public String getSection_number() {
        return section_number;
    }

    public void setSection_number(String section_number) {
        this.section_number = section_number;
    }
    
    
}
