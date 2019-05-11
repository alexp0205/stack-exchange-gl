package com.alex.client;

import com.alex.api.stack.StackQuestionResponse;
import com.alex.utils.JsonUtils;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.ws.rs.core.UriBuilder;

public class StackExchangeClient {

    private CloseableHttpClient HTTP_CLIENT = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

    private static final String BASE_URL = "https://api.stackexchange.com";

    public StackQuestionResponse getNewQuestions(final long fromDate,
                                                 final long toDate,
                                                 final int page,
                                                 final int pageSize) {
        final String url = UriBuilder.fromUri(BASE_URL)
                .path("2.2/questions")
                .queryParam("fromdate", fromDate)
                .queryParam("todate", toDate)
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
                .queryParam("order", "desc")
                .queryParam("site", "stackoverflow")
                .build().toString();
        return executeHttpGetRequest(url, StackQuestionResponse.class);
    }

    public StackQuestionResponse getQuestions(final String ids) {
        final String url = UriBuilder.fromUri(BASE_URL)
                .path("/2.2/questions")
                .path(ids)
                .queryParam("order", "desc")
                .queryParam("site", "stackoverflow")
                .build().toString();
        return executeHttpGetRequest(url, StackQuestionResponse.class);
    }

    protected <T> T executeHttpGetRequest(final String url,
                                          final Class<T> responseClass) {
        final HttpGet getRequest = new HttpGet(url);
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(getRequest)) {
            String getResponse = JsonUtils.convertStreamToString(response.getEntity().getContent());
            return JsonUtils.deserializeV2(getResponse, responseClass);
        } catch (Exception e) {
            return null;
        }
    }
}
