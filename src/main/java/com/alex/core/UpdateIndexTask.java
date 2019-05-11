package com.alex.core;

import com.alex.client.StackExchangeClient;
import com.alex.db.ElasticClient;
import com.alex.utils.JsonUtils;
import lombok.AllArgsConstructor;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.ws.rs.core.UriBuilder;

@AllArgsConstructor
public class UpdateIndexTask implements Runnable {

    private static CloseableHttpClient HTTP_CLIENT = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

    private static final String BASE_URL = "localhost:8080";

    public Boolean refreshDocuments(final String questionIds) {
        final String url = UriBuilder.fromUri(BASE_URL)
                .path("stack")
                .path("questions")
                .path(questionIds)
                .build().toString();
        return executePostRequest(url, Boolean.class);
    }

    @Override
    public void run() {
        // TODO - check if RUN allowed
        // If not sleep
        // GET from data store next set of docs to be read

        String questionString = "1;2;3";
        refreshDocuments(questionString);

    }

    protected <T> T executePostRequest(final String url,
                                       final Class<T> responseClass) {
        final HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", "application/json");
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(postRequest)) {
            String getResponse = JsonUtils.convertStreamToString(response.getEntity().getContent());
            return JsonUtils.deserializeV2(getResponse, responseClass);
        } catch (Exception e) {
            return null;
        }
    }
}
