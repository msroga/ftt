package pl.ftt.core.scanner;

import java.lang.annotation.Annotation;
import java.util.List;

public interface IPackageScanner 
{
	List<Class> scanPackages(
			Class<? extends Annotation> annotationClass,
			String... packagesToScan);
	
	List<Class> scanPackages(
			Class<? extends Annotation> annotationClass,
			boolean includeAbstract, String... packagesToScan);
}
