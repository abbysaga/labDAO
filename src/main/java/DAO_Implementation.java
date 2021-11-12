import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Implementation implements DAOInterface {

    public Connection con3;

    public DAO_Implementation(Connection con3) throws SQLException {
        this.con3 = con3;

    }

    @Override
    public void add(Category cat) throws ClassNotFoundException, SQLException {

        String quer1 = "INSERT INTO Category VALUES ( ?, ? )";
        PreparedStatement query = con3.prepareStatement(quer1);

        query.setString(1, cat.getCatcode());
        query.setString(2, cat.getCatdesc());

        query.executeUpdate();

        System.out.println("One record added");
    }

    @Override
    public Category edit(Category cat, String ccat) throws SQLException, ClassNotFoundException {
        PreparedStatement query;
        query = con3.prepareStatement("Update category set catcode=?, catdesc=? where catcode = ?");
        query.setString(1, cat.getCatcode());
        query.setString(2, cat.getCatdesc());
        query.setString(3, ccat);

        query.executeUpdate();

        System.out.println("One record edited");

        return cat;
    }

    @Override
    public void delete(String catcode) throws SQLException{
        String quer1 = "Delete from Category where catcode = ?";
        PreparedStatement query = con3.prepareStatement(quer1);
        query.setString(1, catcode);
        query.executeUpdate();

        System.out.println("One record Deleted");
    }

    @Override
    public void display() throws ClassNotFoundException, SQLException{
        String quer1 = "Select * from category";
        PreparedStatement query = con3.prepareStatement(quer1);

        ResultSet rs = query.executeQuery();

        Category obj1;

        //display records if there is data;
        while (rs.next()) {
            obj1 = new Category(rs.getString("catcode"), rs.getString("catdesc"));

            System.out.println();

            System.out.print("Category code: " + obj1.getCatcode() + " ");
            System.out.print("Category Description " + obj1.getCatdesc());
        }
    }

    @Override
    public Category search(String catcode) throws SQLException, ClassNotFoundException {
        String quer1 = "Select * from category where catcode = ?";
        PreparedStatement query = con3.prepareStatement(quer1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query.setString(1, catcode);

        ResultSet rs = query.executeQuery();

        if(!rs.first()){
            System.out.print("Record not existing");
            return null;
        }

        Category obj1=null;
        obj1 = new Category(rs.getString("catcode"), rs.getString("catdesc"));
        return obj1;
    }
}
