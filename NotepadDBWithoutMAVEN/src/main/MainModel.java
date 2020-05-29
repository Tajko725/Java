package main;

import dbUtils.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainModel {
    private Connection connection;

    public MainModel(){
        try{
            connection = DbConnection.getConnection();

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        if(this.connection ==null)
            System.exit(1);
    }

    public boolean isDatabaseConnected(){
        return this.connection != null;
    }

    public NotepadItem getNotepadItem(String name) throws Exception{
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM files where name = ?";
        try{
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, name);

            rs = pr.executeQuery();
            if(rs.next()){
                return new NotepadItem(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
            return null;
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        finally {
            if(pr != null)
                pr.close();
            if(rs != null)
                rs.close();
        }
    }

    public List<NotepadItem> getNotepadItems() throws Exception{
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM files";
        try{
            ArrayList<NotepadItem> items = new ArrayList<>();
            pr = this.connection.prepareStatement(sql);
            rs = pr.executeQuery();
            while(rs.next()){
                items.add(new NotepadItem(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            return items;
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        finally {
            if(pr != null)
                pr.close();
            if(rs != null)
                rs.close();
        }
    }

    public Integer getMaxNr() throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        ps = this.connection.prepareStatement("SELECT Count(nr) FROM files");
        rs = ps.executeQuery();
        if(rs.next())
            return rs.getInt(1);
        else
            return  1;
    }

    public boolean UpdateNotepadItem(NotepadItem oryginalItem, NotepadItem selectedItem) {
        try (PreparedStatement pr = this.connection.prepareStatement("UPDATE files SET text = ?  WHERE nr = ? AND name = ?")) {
            pr.setString(1, selectedItem.getText());
            pr.setInt(2, oryginalItem.getNr());
            pr.setString(3, selectedItem.getName());
            return pr.executeUpdate() != 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean AddNotepadItem(NotepadItem item) throws SQLException {
        if (item == null)
            return false;

        PreparedStatement ps;
        ps = this.connection.prepareStatement("INSERT INTO files(nr, name, text) VALUES(?,?,?)");
        ps.setInt(1, item.getNr());
        ps.setString(2, item.getName());
        ps.setString(3, item.getText());
        return ps.executeUpdate() != 0;
    }

    public boolean AddOrUpdateNotepadItem(NotepadItem item) throws SQLException {
        if (item == null)
            return false;

        PreparedStatement ps;
        ResultSet rs;
        boolean isExist = false;
        ps = this.connection.prepareStatement("SELECT * FROM files WHERE nr = ?");
        ps.setInt(1, item.getNr());
        rs = ps.executeQuery();

        if (rs.next())
            isExist = true;

        if (!isExist) {
            ps = this.connection.prepareStatement("INSERT INTO files(nr, name, text) VALUES(?,?,?)");
            ps.setInt(1, item.getNr());
            ps.setString(2, item.getName());
            ps.setString(3, item.getText());
        } else {
            ps = this.connection.prepareStatement("UPDATE files SET name = ?, text = ? WHERE nr = ?");
            ps.setString(1, item.getName());
            ps.setString(2, item.getText());
            ps.setInt(3, item.getNr());
        }
        return ps.executeUpdate() != 0;
    }

    public boolean DeleteItems(ArrayList<NotepadItem> items) throws SQLException {
        if(items == null || items.size() == 0)
            return  false;

        PreparedStatement pr = null;
        try{
            while(items.size() >0)
            {
                NotepadItem item = items.get(items.size()-1);
                pr = this.connection.prepareStatement("DELETE FROM files WHERE nr = ? AND name = ?");
                pr.setInt(1, item.getNr());
                pr.setString(2, item.getName());
                pr.executeUpdate();
                items.remove(items.size()-1);
            }
            return true;
        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
        finally {
            if(pr != null)
                pr.close();
        }
    }
}
