package com.news.elasticsearch.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.elasticsearch.dao.NewsDao;

@RestController
@RequestMapping("/news")
public class NewsController {

	private NewsDao newsDao;

	public NewsController(NewsDao bookDao) {
		this.newsDao = bookDao;
	}

	@GetMapping("/{id}")
	public Map<String, Object> getBookById(@PathVariable String id) {
		return newsDao.getNewsById(id);
	}

	@GetMapping("query/{query}")
	public List<Map<String, Object>> getNewsByQuery(@PathVariable String query) {
		return newsDao.getNewsByQuery(query);
	}
}
