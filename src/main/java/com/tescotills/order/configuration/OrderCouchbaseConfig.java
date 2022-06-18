package com.tescotills.order.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableCouchbaseRepositories(basePackages = { "com.tescotills.order" })
public class OrderCouchbaseConfig extends AbstractCouchbaseConfiguration {

	@Value("${order.couchbase.uri}")
	String couchbaseUri;

	@Value("${order.couchbase.bucket}")
	String bucket;

	@Value("${order.couchbase.password}")
	String password;

	@Override
	protected List<String> getBootstrapHosts() {
		return Arrays.asList(couchbaseUri);
	}

	@Override
	protected String getBucketName() {
		return bucket;
	}

	@Override
	protected String getBucketPassword() {
		return password;
	}
}