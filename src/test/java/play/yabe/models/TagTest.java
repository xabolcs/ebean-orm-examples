package play.yabe.models;


import org.junit.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;

public class TagTest {

	@Test
	public void testTags() {
		// Create a new user and save it
		User bob = new User("bob@gmail.com", "secret", "Bob");
		bob.save();

		// Create a new post
		Post bobPost = new Post(bob, "My first post", "Hello world");
		bobPost.save();
		Post anotherBobPost = new Post(bob, "My second post post", "Hello world");
		anotherBobPost.save();

		// Well
		assertEquals(0, Post.findTaggedWith("Red").size());

		// Tag it now
		bobPost.tagItWith("Red").tagItWith("Blue").save();
		anotherBobPost.tagItWith("Red").tagItWith("Green").save();

		// Check
		assertEquals(2, Post.findTaggedWith("Red").size());
		assertEquals(1, Post.findTaggedWith("Blue").size());
		assertEquals(1, Post.findTaggedWith("Green").size());

		assertEquals(1, Post.findTaggedWith("Red", "Blue").size());
		assertEquals(1, Post.findTaggedWith("Red", "Green").size());
		assertEquals(0, Post.findTaggedWith("Red", "Green", "Blue").size());
		assertEquals(0, Post.findTaggedWith("Green", "Blue").size());

		List<Map> cloud = new ArrayList<Map>();
		cloud.addAll(Tag.getCloud());
		Collections.sort(cloud, new Comparator<Map>() {
			public int compare(Map m1, Map m2) {
				return m1.get("tag").toString().compareTo(m2.get("tag").toString());
			}
		});
		assertEquals("[{tag=Blue, pound=1}, {tag=Green, pound=1}, {tag=Red, pound=2}]", cloud.toString());

	}
}
