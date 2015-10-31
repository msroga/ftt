package pl.myo.core.pages;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marek on 2015-05-27.
 */
public class PageContext<T extends Serializable> implements Serializable
{
   protected T context;

   protected boolean readOnly;

   protected Map<String, Serializable> params = new HashMap<>();

   public PageContext(T context)
   {
      this.context = context;
      this.readOnly = false;
   }

   public PageContext(T context, boolean readOnly)
   {
      this.context = context;
      this.readOnly = readOnly;
   }

   public T getContext()
   {
      return context;
   }

   public void setContext(T context)
   {
      this.context = context;
   }

   public boolean isReadOnly()
   {
      return readOnly;
   }

   public void setReadOnly(boolean readOnly)
   {
      this.readOnly = readOnly;
   }

   public Map<String, Serializable> getParams()
   {
      return params;
   }

   public void setParams(Map<String, Serializable> params)
   {
      this.params = params;
   }

   public PageContext<T> putParam(String key, Serializable serializable)
   {
      if (params != null)
      {
         params.put(key, serializable);
      }
      return this;
   }
}
