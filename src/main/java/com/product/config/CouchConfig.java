package com.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;


@Configuration
@EnableReactiveCouchbaseRepositories(basePackageClasses = {CouchConfig.class})
public class CouchConfig extends AbstractCouchbaseConfiguration {

	  @Value("${couchbase.hostname}")
	  private String hostName;
	   
	  @Value("${couchbase.bucket}")
	  private String bucketName;
	  
	  @Value("${couchbase.userName}")
	  private String userName;
	   
	  @Value("${couchbase.password}")
	  private String password;
	  
	@Override
	public String getConnectionString() {
		return hostName;
	}
	
	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getBucketName() {
		return bucketName;
	}

}
