package org.formation.service;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.formation.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElasticPositionService {

	@Autowired
	RestClient restClient;

	Logger log = Logger.getLogger(ElasticPositionService.class.getName());

	SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss'Z'");

	public void index(final Position position) {
		log.info("Indexing " + position);


		HttpEntity entity = new NStringEntity("{\n" + "    \"coursier_id\" : " + position.getId() + ",\n" 
								+ "\"location\" : \"" + position.getLatitude() + "," + position.getLongitude()  + "\",\n" 
								+ "\"@timestamp\" : \"" + df.format(position.getTimestamp()) + "\"\n}",
				ContentType.APPLICATION_JSON);
		
		try {
			Request postRequest = new Request("POST", "/positions/_doc");
			postRequest.setEntity(entity);
			Response indexResponse = restClient.performRequest(postRequest);

			log.info(EntityUtils.toString(indexResponse.getEntity()));
		} catch (PatternSyntaxException | DirectoryIteratorException | IOException e) {
			System.err.println(e);
		}
	}

}
