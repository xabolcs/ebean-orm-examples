package play.modules.ebean;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SimpleCustomer {

  @Id
  long id;

  String name;

  // getters and setters
}
