/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generate;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mapcontent.ConceptMapData;
import org.json.simple.JSONObject;

/**
 *
 * @author chanakya
 */
public class ShowConceptMapAction extends ActionSupport{
    private String key1;
    private String key2;
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }
    public String execute() throws ClassNotFoundException, SQLException, IOException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Major", "root", "pass");
        PreparedStatement ps = con.prepareStatement("SELECT * from conceptmapdata where chapter_name = ? and section_name = ?");
        ps.setString(1, getKey1());
        ps.setString(2, getKey2());
        System.out.println(getKey1());
        System.out.println(getKey2());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            
            setData(rs.getString("output_text"));
        }
        try {
 
		FileWriter file = new FileWriter("/home/chanakya/NetBeansProjects/Concepto/web/new_graph.json");
		file.write(getData());
		file.flush();
		file.close();
 
	} catch (IOException e) {
		e.printStackTrace();
	}
 
        return SUCCESS;
    }
}
