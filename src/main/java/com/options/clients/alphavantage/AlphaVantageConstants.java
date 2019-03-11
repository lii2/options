package com.options.clients.alphavantage;

public interface AlphaVantageConstants {

    String TRADE_APP_API_KEY = "G3BOD7VKYD8DJWEK";

    enum Query {
        TIME_SERIES_DAILY("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s"),
        EMA_DAILY("https://www.alphavantage.co/query?function=EMA&symbol=%s&interval=daily&time_period=%s&series_type=close&apikey=%s"),
        MACD_DAILY("https://www.alphavantage.co/query?function=MACD&symbol=%s&interval=daily&series_type=close&apikey=%s"),
        BBANDS_DAILY("https://www.alphavantage.co/query?function=BBANDS&symbol=%s&interval=daily&time_period=14&series_type=close&apikey=%s");

        String url;

        Query(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}