package com.news.elasticsearch.config;

import java.net.InetSocketAddress;

import javax.annotation.Resource;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.news.elasticsearch.bean")
public class ElasticSearchConfiguration {

	@Value("${elasticsearch.host}")
	private String host;

	@Value("${elasticsearch.port}")
	private int port;
	@Value("${elasticsearch.cluster-name}")
	private String cluster;

	@Value("${spring.data.elasticsearch.cluster-nodes}")
	private String clusterNodes;
	@Value("${spring.data.elasticsearch.cluster-name}")
	private String clusterName;
	@Resource
	private Environment environment;

	@Bean
	public Client client() {
		Settings settings = Settings.builder().put("cluster.name", cluster).build();

		TransportClient client = new PreBuiltTransportClient(settings);
		TransportAddress address = new InetSocketTransportAddress(
				new InetSocketAddress(environment.getProperty("elasticsearch.host"),
						Integer.parseInt(environment.getProperty("elasticsearch.port"))));
		client.addTransportAddress(address);
		return client;
	}

	@Bean
	public ElasticsearchOperations elasticsearchOperations() {
		return new ElasticsearchTemplate(client());
	}
}
