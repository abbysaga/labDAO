import java.sql.SQLException;
import java.util.Scanner;

public class MainProgram {
    private DAO_Implementation obj;
    Connection con;

    MainProgram() throws SQLException, ClassNotFoundException {
        this.con = new Connection();
        this.obj = new DAO_Implementation(con.connect());
    }

    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        Boolean flag = true;

        Scanner key = new Scanner(System.in);

        MainProgram obj1 = new MainProgram();

        String ans;

        System.out.println("Choose from the following options");

        while (flag) {
            System.out.println("\nEnter A to Add, D to Delete, S to Search, Di to Display, E to Edit, Ex to Exit");

            ans = key.nextLine();

            if (ans.equals("A")) {
                obj1.addingdata();
                obj1.displaying();
            }

            if (ans.equals("E")) {
                obj1.editingdata();
            }

            if (ans.equals("D")) {
                obj1.deletingdata();
            }
        }
    }

    private void deletingdata() throws SQLException, ClassNotFoundException{
        String catcode, catdesc;
        Category cat, catr;
        String oldccode;

        Scanner key = new Scanner(System.in);

        System.out.println("Enter a category code ");
        catcode = key.nextLine();

        catr = obj.search(catcode);

        if(catr ==null){
            System.out.println("Record your trying to delete does not exist");

            return;
        }

        obj.delete(catcode);

        obj.display();
    }

    private void displaying() {
    }

    private void addingdata() throws SQLException, ClassNotFoundException{
        String catcode, catdesc;
        Category cat, catr;

        Scanner key = new Scanner(System.in);

        System.out.println("Enter a category code ");
        catcode = key.nextLine();

        catr=obj.search(catcode);

        if(!(catr ==null)){
            System.out.print("Please use another number");

            return;
        } else {
            System.out.print(" yet, this is a new record\n");
        }

        System.out.println("Enter a category description ");
        catdesc = key.nextLine();

        cat = new Category(catcode, catdesc);

        if(obj.search(catcode)==null){
            obj.add(cat);
            obj.display();
        }
    }

    public void editingdata() throws SQLException, ClassNotFoundException {
        String catcode, catdesc;
        Category cat, catr;
        String oldccode;

        Scanner key = new Scanner(System.in);

        System.out.println("Enter a category code ");
        catcode = key.nextLine();
        catr = obj.search(catcode);

        if(catr ==null){
            System.out.println("Record your trying to edit does not exist");
            return;
        }

        oldccode = catr.getCatcode();
        System.out.println("Enter the new code");
        catcode = key.nextLine();
        System.out.println("Enter the new category description ");
        catdesc = key.nextLine();

        cat = new Category(catcode, catdesc);
        obj.edit(cat,oldccode);
        catr = obj.search(catcode);
        obj.display();
    }
}
