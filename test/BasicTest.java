import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Test
    public void createAndRetrivuUser() {
        new User("bob@gmail.com", "secret", "Bob").save();

        assertNotNull(User.connect("bob@gmail.com", "secret"));
        assertNull(User.connect("bob@gmail.com", "badpassword"));
        assertNull(User.connect("tom@gmail.com", "secret"));
    }

}
