package pl.ftt.core.scanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;

public class PackageScannerImpl implements IPackageScanner {
	private static final String RESOURCE_PATTERN = "/**/*.class";

	private final ResourcePatternResolver resourcePatternResolver;

	public PackageScannerImpl() {
		this.resourcePatternResolver = ResourcePatternUtils
				.getResourcePatternResolver(new PathMatchingResourcePatternResolver());
	}

	@Override
	public List<Class> scanPackages(
			Class<? extends Annotation> annotationClass,
			String... packagesToScan) {
		return scanPackages(annotationClass, false, packagesToScan);
	}

	@Override
	public List<Class> scanPackages(
			Class<? extends Annotation> annotationClass,
			boolean includeAbstract, String... packagesToScan) {
		try {
			List<Class> result = new ArrayList<Class>();
			TypeFilter filter = new AnnotationTypeFilter(annotationClass, false);
			for (String pkg : packagesToScan) {
				String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
						+ ClassUtils.convertClassNameToResourcePath(pkg)
						+ RESOURCE_PATTERN;
				Resource[] resources = this.resourcePatternResolver
						.getResources(pattern);
				MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(
						this.resourcePatternResolver);
				for (Resource resource : resources) {
					if (resource.isReadable()) {
						MetadataReader reader = readerFactory
								.getMetadataReader(resource);
						if (filter.match(reader, readerFactory)
								&& (includeAbstract ? true : !reader
										.getClassMetadata().isAbstract())) {
							String className = reader.getClassMetadata()
									.getClassName();
							result.add(this.resourcePatternResolver
									.getClassLoader().loadClass(className));
						}
					}
				}
			}
			return result;
		} catch (IOException e) {
			 throw new PackageScannerException("failed to scan classpath", e);
		} catch (ClassNotFoundException e) {
			throw new PackageScannerException("unable to load class", e);
		}
	}
}
