package play.modules.ebean;

import com.avaje.ebean.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseModel extends Model {

	@Id
	@GeneratedValue
	public Long id;

	public Long getId()
	{
		return id;
	}

}
