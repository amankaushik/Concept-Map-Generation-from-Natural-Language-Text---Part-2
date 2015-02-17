/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generate;

/**
 *
 * @author chanakya
 */
import com.opensymphony.xwork2.ActionSupport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import mapcontent.ConceptMapData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowArticlesAction extends ActionSupport{
    ConceptMapData map_data = new ConceptMapData();
    List<ConceptMapData> list_map_data = new ArrayList();

    public ConceptMapData getMap_data() {
        return map_data;
    }

    public void setMap_data(ConceptMapData map_data) {
        this.map_data = map_data;
    }

    public List<ConceptMapData> getList_map_data() {
        return list_map_data;
    }

    public void setList_map_data(List<ConceptMapData> list_map_data) {
        this.list_map_data = list_map_data;
    }
    
    public String execute() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Major", "root", "pass");
        String query = "SELECT * from conceptmapdata";
        System.out.println(query);
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ConceptMapData data = new ConceptMapData();
            //data.setInput_text(rs.getString("input_text"));
            //data.setOutput_text(rs.getString("output_text"));
            data.setChapter_name(rs.getString("chapter_name").toLowerCase().trim());
            data.setSection_name(rs.getString("section_name").toLowerCase().trim());
            list_map_data.add(data);
        }
        return SUCCESS;
    }
}
