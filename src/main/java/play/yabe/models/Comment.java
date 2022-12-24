package play.yabe.models;

import play.modules.ebean.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Comment extends BaseModel {

	@NotNull
	private String author;

	@NotNull
	private Date postedAt;

	@Lob
	@NotNull
	private String content;

	@ManyToOne
	@NotNull
	private Post post;

	public Comment(Post post, String author, String content) {
		this.post = post;
		this.author = author;
		this.content = content;
		this.postedAt = new Date();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String toString() {
		return content.length() > 50 ? content.substring(0, 50) + "..." : content;
	}
}
