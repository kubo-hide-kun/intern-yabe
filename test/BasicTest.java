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

    @Test
    public void craetePost() {
    	User bob = new User("bob@gmail.com", "secret", "Bob").save();
    	new Post(bob, "My first post", "Hello world").save();

    	assertEquals(1,Post.count());
    	List<Post> bobPosts = Post.find("byAuthor", bob).fetch();

    	assertEquals(1,bobPosts.size());
    	Post firstPost = bobPosts.get(0);
    	assertNotNull(firstPost);
    	assertEquals(bob, firstPost.author);
    	assertEquals("My first post", firstPost.title);
    	assertEquals("Hello world",firstPost.content);
    	assertNotNull(firstPost.postedAt);
    }

}
