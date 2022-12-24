package play.yabe.models;

import play.modules.ebean.BaseModel;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Entity
public class Tag extends BaseModel  implements Comparable<Tag> {

	@NotNull
	private String name;

	public Tag(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Tag findOrCreateByName(String name) {
		Tag tag = db().createQuery(Tag.class)
			.where()
			.raw("name=?", name)
			.findUnique();
		if (tag == null) {
			tag = new Tag(name);
			// tag.save();
		}
		return tag;
	}

	public static List<Map> getCloud() {
		List result = db().createSqlQuery("select t.name as tag, count(p.id) as pound from post p join post_tag pt on p.id=pt.post_id join tag t on pt.tag_id=t.id group by t.name").findList();
		return result;
	}

	public String toString() {
		return name;
	}

	public int compareTo(Tag otherTag) {
		return name.compareTo(otherTag.name);
	}
}
