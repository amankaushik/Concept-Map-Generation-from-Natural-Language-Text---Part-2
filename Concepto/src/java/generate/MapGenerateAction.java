/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generate;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import mapcontent.ConceptMapData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author chanakya
 */
public class MapGenerateAction extends ActionSupport implements ModelDriven { //,ServletRequestAware

    private ConceptMapData concept_map = new ConceptMapData();

    public ConceptMapData getConcept_map() {
        return concept_map;
    }

    public void setConcept_map(ConceptMapData concept_map) {
        this.concept_map = concept_map;
    }

    //private HttpServletRequest servletRequest;
    public void validate() {
        if (StringUtils.isEmpty(concept_map.getChapter_name())) {
            addFieldError("chapter_name", "Chapter Name Cannot be blank");
        }
        if (StringUtils.isEmpty(concept_map.getChapter_number())) {
            addFieldError("chapter_number", "Chapter Number Cannot be blank");
        }
        if (StringUtils.isEmpty(concept_map.getSection_number())) {
            addFieldError("section_number", "Section Number Cannot be blank");
        }
        if (StringUtils.isEmpty(concept_map.getInput_text()) && FileUtils.sizeOf(concept_map.getUploadedFile()) == 0) {
            if (StringUtils.isEmpty(concept_map.getInput_text())) {
                addFieldError("input_text", "Input Text Cannot be blank");
            } else {
                addFieldError("uploaded_file", "Uploaded Text Cannot be blank");
            }
        }
    }

    public String execute() throws IOException, InterruptedException, ClassNotFoundException, SQLException {

        String file_path = "/home/chanakya/NetBeansProjects/Concepto/UploadedFiles";
        File fileToCreate = new File(file_path, concept_map.getUploadedFileFileName());
        FileUtils.copyFile(concept_map.getUploadedFile(), fileToCreate);

        List<String> temp_text = FileUtils.readLines(concept_map.getUploadedFile());
        StringBuilder text = new StringBuilder();
        for (String s : temp_text) {
            text.append(s);
        }
        concept_map.setInput_text(text.toString());
        /*
         System.out.println(concept_map.getInput_text());
         System.out.println(concept_map.getChapter_name());
         System.out.println(concept_map.getChapter_number());
         System.out.println(concept_map.getSection_number());
         */
        String temp_filename = concept_map.getUploadedFileFileName().split("\\.(?=[^\\.]+$)")[0];
        temp_filename = temp_filename.trim();
        
        
         String temp = "java -jar /home/chanakya/NetBeansProjects/Concepto/src/java/generate/MajorCore.jar " + file_path + " " + temp_filename;
         System.out.println(temp);
         Process proc = Runtime.getRuntime().exec(temp);
         int exitVal = proc.waitFor();
         System.out.println("Process exitValue: " + exitVal);
         
         String temp2 = "java -jar /home/chanakya/NetBeansProjects/Concepto/src/java/generate/MajorCoreII.jar " + file_path + " " + temp_filename;
         System.out.println(temp2);
         Process procd = Runtime.getRuntime().exec(temp2);
         
         int exitVald = procd.waitFor();

         System.out.println("Process exitValue1: " + exitVald);
         
         String cmd = "python /home/chanakya/NetBeansProjects/Concepto/src/java/generate/add_to_graph.py \"/home/chanakya/NetBeansProjects/Concepto/UploadedFiles/" + temp_filename + "_OllieOutput.txt\"";
         String[] finalCommand;
         finalCommand = new String[3];
         finalCommand[0] = "/bin/sh";
         finalCommand[1] = "-c";
         finalCommand[2] = cmd;
        
         try{
         Process procv = Runtime.getRuntime().exec(finalCommand);
         int exitValv = procv.waitFor();
         System.out.println("Process exitValue2: " + exitValv);
         }catch(Throwable t){
         t.printStackTrace();
         }
         
         cmd = "python /home/chanakya/NetBeansProjects/Concepto/src/java/generate/json_correct.py";
         finalCommand = new String[3];
         finalCommand[0] = "/bin/sh";
         finalCommand[1] = "-c";
         finalCommand[2] = cmd;
        
         try{
         Process procv = Runtime.getRuntime().exec(finalCommand);
         int exitValv = procv.waitFor();
         System.out.println("Process exitValue3: " + exitValv);
         }catch(Throwable t){
         t.printStackTrace();
         }
         
        List<String> temp_text_1 = FileUtils.readLines(FileUtils.getFile("/home/chanakya/NetBeansProjects/Concepto/web", "new_graph.json"));
        StringBuilder text_1 = new StringBuilder();
        for (String s : temp_text_1) {
            text_1.append(s);
        }
        concept_map.setOutput_text(text_1.toString());
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Major", "root", "pass");

        PreparedStatement ps = con.prepareStatement("insert into conceptmapdata values(?,?,?,?,?,?)");
        ps.setString(1, concept_map.getInput_text());
        ps.setString(2, concept_map.getOutput_text());
        ps.setString(3, concept_map.getChapter_name());
        ps.setString(4, concept_map.getChapter_number());
        ps.setString(5, concept_map.getSection_number());
        ps.setString(6, concept_map.getSection_name());
        Integer status = ps.executeUpdate();
        System.out.println(status);
        return SUCCESS;
    }

    @Override
    public Object getModel() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return concept_map;
    }
    /*
     @Override
     public void setServletRequest(HttpServletRequest hsr) {
     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }
     */
}
