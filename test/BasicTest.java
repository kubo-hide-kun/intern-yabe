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

    @Test
    public void postComments() {
        User bob = new User("bob@gmail.com", "secret", "Bob").save();
        Post bobPost = new Post(bob, "My first post", "Hello world").save();

        new Comment(bobPost, "Jeff", "Nice post").save();
        new Comment(bobPost, "Tom", "I knew that !").save();

        List<Comment> bobPostComments = Comment.find("byPost", bobPost).fetch();

        assertEquals(2, bobPostComments.size());

        Comment firstComment = bobPostComments.get(0);
        assertNotNull(firstComment);
        assertEquals("Jeff", firstComment.author);
        assertEquals("Nice post", firstComment.content);
        assertNotNull(firstComment.postedAt);

        Comment secondComment = bobPostComments.get(1);
        assertNotNull(secondComment);
        assertEquals("Tom", secondComment.author);
        assertEquals("I knew that !", secondComment.content);
        assertNotNull(secondComment.postedAt);
    }

    @Test
    public void useTheCommentsRelation() {
        User bob = new User("bob@gmail.com", "secret", "Bob").save();
        Post bobPost = new Post(bob, "My first post", "Hello world").save();

        bobPost.addComment("Jeff", "Nice post");
        bobPost.addComment("Tom", "I knew that !");

        assertEquals(1, User.count());
        assertEquals(1, Post.count());
        assertEquals(2, Comment.count());

        bobPost = Post.find("byAuthor", bob).first();
        assertNotNull(bobPost);

        assertEquals(2, bobPost.comments.size());
        assertEquals("Jeff", bobPost.comments.get(0).author);

        bobPost.delete();

        assertEquals(1, User.count());
        assertEquals(0, Post.count());
        assertEquals(0, Comment.count());
    }
}
