package pl.myo.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class ReflectionUtils
{
   private ReflectionUtils()
   {
   }

   public static PropertyDescriptor getPropertyDescriptor(Object obj, String nestedProperty)
   {
      try
      {
         return PropertyUtils.getPropertyDescriptor(obj, nestedProperty);
      }
      catch (Exception e)
      {
         throw new RuntimeException("property not found", e);
      }
   }

   public static void setProperty(Object bean, String name, Object value)
   {
      try
      {
         PropertyUtils.setProperty(bean, name, value);
      }
      catch (Exception e)
      {
         throw new RuntimeException("unable to set property", e);
      }
   }

   public static Object getProperty(Object bean, String name)
   {
      try
      {
         return PropertyUtils.getProperty(bean, name);
      }
      catch (Exception e)
      {
         throw new RuntimeException("unable to get property", e);
      }
   }

   public static boolean hasProperty(Object obj, String nestedProperty)
   {
      return getPropertyDescriptor(obj, nestedProperty) != null;
   }

   public static Class getPropertyClass(Object obj, String nestedProperty)
   {
      PropertyDescriptor propertyDescriptor = getPropertyDescriptor(obj, nestedProperty);
      if (propertyDescriptor != null)
      {
         return propertyDescriptor.getPropertyType();
      }
      return null;
   }

   public static Object getNestedObject(Object obj, String nestedProperty)
   {
      try
      {
         return PropertyUtils.getNestedProperty(obj, nestedProperty);
      }
      catch (Exception e)
      {
         throw new RuntimeException("property not found", e);
      }
   }

   public static String getNestedFieldName(String nestedProperty)
   {
      int pos = nestedProperty.lastIndexOf(".");
      if (pos > 0)
      {
         return nestedProperty.substring(pos + 1);
      }
      return nestedProperty;
   }

   public static Field getNestedField(Class clazz, String nestedProperty)
   {
      String[] nestedPath = nestedProperty.split("\\.");
      Class current = clazz;
      Field nestedField = null;
      for (String path : nestedPath)
      {
         nestedField = FieldUtils.getField(current, path, true);
         if (nestedField == null)
         {
            return null;
         }
         current = nestedField.getType();
      }
      return nestedField;
   }

   public static Field[] getFields(Class clazz, String... excludedFields)
   {
      List<Field> result = new ArrayList<Field>();
      Field[] fields = clazz.getDeclaredFields();
      for (Field field : fields)
      {
         int modifiers = field.getModifiers();
         if (!Modifier.isStatic(modifiers) && !isExcluded(field, excludedFields))
         {
            result.add(field);
         }
      }
      return result.toArray(new Field[0]);
   }

   private static boolean isExcluded(Field field, String... excludedFields)
   {
      if (excludedFields != null)
      {
         for (String excludedField : excludedFields)
         {
            if (field.getName().equals(excludedField))
            {
               return true;
            }
         }
      }
      return false;
   }

   private static void updateField(Object previous, Object current, String field)
   {
      Object currentValue = getProperty(previous, field);
      Object nextValue = getProperty(current, field);
      if (hasChanged(currentValue, nextValue))
      {
         setProperty(previous, field, nextValue);
      }
   }

   public static void updateFields(Object previous, Object current, String... fields)
   {
      for (String field : fields)
      {
         updateField(previous, current, field);
      }
   }

   private static boolean hasChanged(Object previousValue, Object currentValue)
   {
      if (previousValue == null && currentValue == null)
      {
         return false;
      }
      if (previousValue == null || currentValue == null)
      {
         return true;
      }
      return !previousValue.equals(currentValue);
   }
}
