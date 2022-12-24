package play.yabe.models;

import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import play.modules.ebean.BaseModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class Post extends BaseModel {

	@NotNull
	private String title;

	@NotNull
	private Date postedAt;

	@Lob
	@NotNull
	private String content;

	@NotNull
	@ManyToOne
	private User author;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@ManyToMany(cascade = CascadeType.PERSIST)
	private Set<Tag> tags;

	public Post(User author, String title, String content) {
		this.comments = new ArrayList<Comment>();
		this.tags = new TreeSet();
		this.author = author;
		this.title = title;
		this.content = content;
		this.postedAt = new Date();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Post addComment(String author, String content){
		Comment newComment = new Comment(this, author, content);
		this.comments.add(newComment);
		this.save();
		return this;
	}

	public Post tagItWith(String name) {
		tags.add(Tag.findOrCreateByName(name));
		return this;
	}

	public static List<Post> findTaggedWith(String tag) {
		return db().createQuery(Post.class)
			.where()
			.raw("tags.name=?", tag)
			.findList();
	}

	public static List<Post> findTaggedWith(String... tags) {
		RawSql rawSql = RawSqlBuilder
			.parse("select p.id id from post p inner join post_tag pt on p.id=pt.post_id inner join tag t on pt.tag_id=t.id group by p.id")
			// map result columns to bean properties
			//           .columnMapping("p.id", "post.id")
			//           .columnMapping("o.status", "order.status")
			//           .columnMapping("c.id", "order.customer.id")
			//           .columnMapping("c.name", "order.customer.name")
			.create();
		return db().createQuery(Post.class)
			.setRawSql(rawSql)
			.where()
			.in("t.name", tags)
			.having()
			.eq("count(t.id)", tags.length)
			.findList();
	}

	public String toString() {
		return title;
	}
}
