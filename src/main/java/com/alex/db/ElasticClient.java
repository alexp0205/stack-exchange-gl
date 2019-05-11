package com.alex.db;

import com.alex.api.Question;
import com.alex.utils.JsonUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ElasticClient {
    private RestHighLevelClient client;

    private static final String QUESTIONS_INDEX = "questions";

    public ElasticClient() {
        client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http")));
    }

    public List<Question> getQuestions(final List<String> tags,
                                       final int from,
                                       final int size) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(QUESTIONS_INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("tags", tags));
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return Arrays.stream(searchResponse.getHits().getHits())
                    .map(searchHit -> JsonUtils.deserializeV2(searchHit.getSourceAsString(), Question.class))
                    .collect(Collectors.toList());
        } catch (IOException io) {
            return null;
        }
    }


    public Question index(final Question question) {
        IndexRequest request = new IndexRequest(QUESTIONS_INDEX);
        request.id(question.getQuestionId().toString());
        request.source(JsonUtils.serializeV2(question), XContentType.JSON);
        return question;
    }
}
