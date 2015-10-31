package pl.myo.exception;

import org.hibernate.service.spi.ServiceException;

public class DataNotFoundException extends ServiceException {
   private static final long serialVersionUID = 1427585195008700394L;

   /**
    * Parametrized constructor
    *
    * @param message exception message
    */
   public DataNotFoundException(String message) {
      super(message);
   }

   /**
    * Parametrized constructor
    *
    * @param message   exception message
    * @param throwable throwable instance
    */
   public DataNotFoundException(String message, Throwable throwable) {
      super(message, throwable);
   }
}
