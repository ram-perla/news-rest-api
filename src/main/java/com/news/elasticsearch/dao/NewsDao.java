package com.news.elasticsearch.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class NewsDao {

	private final String INDEX = "news";
	private final String TYPE = "feed";

	private ObjectMapper objectMapper;

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	public NewsDao(ObjectMapper objectMapper, ElasticsearchOperations elasticsearchOperations) {
		this.objectMapper = objectMapper;
		this.elasticsearchOperations = elasticsearchOperations;
	}

	public List<Map<String, Object>> getNewsByQuery(String query) {
		SearchResponse response = elasticsearchOperations.getClient().prepareSearch(INDEX).setTypes(TYPE)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.queryStringQuery(query))
				.setFrom(0).get();
		List<Map<String, Object>> hits = new ArrayList<Map<String, Object>>();
		SearchHit[] data = response.getHits().getHits();
		for (int i = 0; i < data.length; i++) {
			hits.add(data[i].getSourceAsMap());
		}
		return hits;
	}

	public Map<String, Object> getNewsById(String id) {
		SearchResponse response = elasticsearchOperations.getClient().prepareSearch(INDEX).setTypes(TYPE)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.idsQuery(id)).setFrom(0).get();
		Map<String, Object> hit = new HashMap<String, Object>();
		SearchHit[] data = response.getHits().getHits();

		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				hit = data[i].getSourceAsMap();
			}
		}
		return hit;
	}
}
