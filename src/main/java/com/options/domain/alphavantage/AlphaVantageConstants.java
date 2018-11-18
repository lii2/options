package com.options.domain.alphavantage;

public interface AlphaVantageConstants {

    String TRADE_APP_API_KEY = "G3BOD7VKYD8DJWEK";

    String TIME_SERIES_DAILY = "TIME_SERIES_DAILY";

    String SMA = "SMA";

    enum Query{
        TIME_SERIES_DAILY("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s"),
        SMA_DAILY("https://www.alphavantage.co/query?function=SMA&symbol=%s&interval=daily&time_period=%s&series_type=open&apikey=%s"),
        EMA_DAILY("https://www.alphavantage.co/query?function=EMA&symbol=%s&interval=daily&time_period=%s&series_type=open&apikey=%s");

        Query(String url){
            this.url = url;
        }
        String url;
        public String getUrl() {
            return url;
        }
    }
}