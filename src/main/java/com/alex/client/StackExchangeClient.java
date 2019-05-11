package com.alex.client;

import com.alex.api.StackQuestion;

import java.util.List;

public class StackExchangeClient {

    private static final String BASE_URL = "https://api.stackexchange.com";

    public List<StackQuestion> getNewQuestions() {
        String url = "/2.2/questions?fromdate=1557532800&todate=1557619200&order=desc&sort=activity&site=stackoverflow";
//        final String url = UriBuilder.fromUri(BASE_URL + "/2.2/questions")
//                .
//                .queryParam("adId", quikrAdId).build().toString();
        return null;
    }
}
