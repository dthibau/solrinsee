package org.formation;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

	@Bean
	public RestClient elasticClient() {
		return RestClient.builder(
			    new HttpHost("localhost", 9200)).build();

	}

}
