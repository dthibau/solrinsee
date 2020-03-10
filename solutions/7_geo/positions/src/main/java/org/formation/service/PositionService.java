package org.formation.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.formation.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PositionService {

	@Autowired
	SolrClient solrClient;
	
	
    Logger log = Logger.getLogger(PositionService.class.getName());
    
    SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss'Z'");

    public void index(final Position position) {
        log.info("Indexing " + position);
        
        final SolrInputDocument doc = new SolrInputDocument();
        doc.addField("coursier_id", position.getId());
        doc.addField("location", position.getLatitude()+","+position.getLongitude());
        doc.addField("location_rpt", "POINT("+ position.getLatitude()+" "+position.getLongitude() +")");
        doc.addField("timestamp", df.format(position.getTimestamp()));


        try {
			final UpdateResponse updateResponse = solrClient.add("geo", doc);
	        log.info("UpdateResponse " + updateResponse);

			 // Indexed documents must be committed
	        solrClient.commit("geo");
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       


    }
}
