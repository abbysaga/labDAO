import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class DAO_ImplementationTest {
    @InjectMocks
    private DAO_Implementation panelDao;

    @Mock
    private Connection connection;

    @Mock
    private ResultSet result;

    @Mock
    private PreparedStatement stmt;

    Category cat;

    @BeforeEach
    public void setup() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.openMocks(this);

        when(connection.prepareStatement(any(String.class))).thenReturn(stmt);

        cat = new Category("101", "Food");
    }

    @Test
    void add() throws SQLException, ClassNotFoundException {
        panelDao.add(cat);

        Mockito.verify(stmt).executeUpdate();
    }

    @Test
    void edit() throws SQLException, ClassNotFoundException {
        panelDao.add(cat);

        cat = new Category("101", "Food and Bev");
        Category pp = panelDao.edit(cat, "101");

        assertEquals("Food and Bev",pp.getCatdesc());
    }

    @Test
    void delete() throws SQLException, ClassNotFoundException {
        panelDao.add(cat);

        panelDao.delete(cat.getCatcode());
        Mockito.verify(stmt, times(2)).executeUpdate();
    }

    @Test
    void display() throws SQLException, ClassNotFoundException {
        when(result.next()).thenReturn(true, false);
        when(result.getString("catcode")).thenReturn("101");
        when(result.getString("catdesc")).thenReturn("Food");

        Mockito.doReturn(result).when(stmt).executeQuery();

        panelDao.display();
        Mockito.verify(stmt).executeQuery();
    }

    @Test
    void search() throws SQLException, ClassNotFoundException {
        Mockito.when(connection.prepareStatement("Select * from category where catcode = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)).thenReturn(stmt);

        Mockito.when(stmt.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);
        Mockito.when(result.getString("catcode")).thenReturn("101");
        Mockito.when(result.first()).thenReturn(true);

        Category r = panelDao.search("101");
        assertEquals("101", r.getCatcode());
    }

    @Test
    void Searchtestnorecord() throws SQLException, ClassNotFoundException {
        Mockito.when(connection.prepareStatement("Select * from category where catcode = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)).thenReturn(stmt);

        Mockito.when(stmt.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);
        Mockito.when(result.getString("catcode")).thenReturn("Norecord");
        Mockito.when(result.getString("catdesc")).thenReturn("Norecord");

        Category r = panelDao.search("Norecord");

        Assertions.assertNull(r);
    }
}
