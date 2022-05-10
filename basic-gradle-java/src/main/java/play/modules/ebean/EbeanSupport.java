package play.modules.ebean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import javax.persistence.MappedSuperclass;

import play.PlayPlugin;
import play.data.validation.Validation;
import play.db.Model;
import play.exceptions.UnexpectedException;
import play.mvc.Scope.Params;

import io.ebean.Query;
import io.ebean.Update;

@SuppressWarnings("unchecked")
@MappedSuperclass
public abstract class EbeanSupport<T> implements play.db.Model
{

  public static <T extends EbeanSupport> T create(Class<?> type, String name, Map<String, String[]> params, Annotation[] annotations)
  {
    try {
      Constructor<?> c = type.getDeclaredConstructor();
      c.setAccessible(true);
      Object model = c.newInstance();
      return (T) edit(model, name, params, annotations);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T extends EbeanSupport> T edit(Object o, String name, Map<String, String[]> params, Annotation[] annotations)
  {
    throw new UnexpectedException("");
  }

  public <T extends EbeanSupport> T edit(String name, Map<String, String[]> params)
  {
    edit(this, name, params, null);
    return (T) this;
  }

  public boolean validateAndSave()
  {
    if (Validation.current().valid(this).ok) {
      save();
      return true;
    }
    return false;
  }

  public <T extends EbeanSupport> T save()
  {
    _save();
    return (T) this;
  }

  public <T extends EbeanSupport> T refresh()
  {
    return (T) this;
  }

  public <T extends EbeanSupport> T delete()
  {
    _delete();
    return (T) this;
  }

  public static <T extends EbeanSupport> T create(String name, Params params)
  {
    throw enhancementError();
  }

  public static long count()
  {
    throw enhancementError();
  }

  public static long count(String query, Object... params)
  {
    throw enhancementError();
  }

  public static <T extends EbeanSupport> List<T> findAll()
  {
    throw enhancementError();
  }

  public static <T extends EbeanSupport> T findById(Object id)
  {
    throw enhancementError();
  }

  public static <T extends EbeanSupport> T findOne(String query,Object... params)
  {
    throw enhancementError();
  }

  public static <T extends EbeanSupport> Query<T> find(String query, Object... params)
  {
    throw enhancementError();
  }

  public static <T extends EbeanSupport> Query<T> all()
  {
    throw enhancementError();
  }

  public static int delete(String query, Object... params)
  {
    throw enhancementError();
  }

  public static int deleteAll()
  {
    throw enhancementError();
  }

  protected static <T extends EbeanSupport> Query<T> createQuery(Class<T> beanType, String where, Object[] params)
  {
    throw new UnexpectedException("");
  }

  protected static <T extends EbeanSupport> Update<T> createDeleteQuery(Class<T> beanType, String where, Object[] params)
  {
    throw new UnexpectedException("");
  }

  protected void afterLoad()
  {
  }

  protected void beforeSave(boolean isInsert)
  {
  }

  protected void afterSave(boolean isInsert)
  {
  }

  private static UnsupportedOperationException enhancementError()
  {
    return new UnsupportedOperationException("Please annotate your JPA model with @javax.persistence.Entity annotation.");
  }

  public void _save()
  {
    PlayPlugin.postEvent("JPASupport.objectPersisted", this);
  }

  public void _delete()
  {
    PlayPlugin.postEvent("JPASupport.objectDeleted", this);
  }

  public Object _key()
  {
    return Model.Manager.factoryFor(this.getClass()).keyValue(this);
  }

}
