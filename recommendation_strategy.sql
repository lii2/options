INSERT INTO recommendation_strategy(TREND, "name", description) values ('BULLISH', '10 Day EMA Crossing Bullish', 'Recommends user to buy calls to sell if the stock price average for a day crosses the 10 day ema in a bullish manner');
INSERT INTO recommendation_strategy(TREND, "name", description) values ('BEARISH', '10 Day EMA Crossing Bearish', 'Recommends user to buy puts to sell if the stock price average for a day crosses the 10 day ema in a bearish manner');

INSERT INTO recommendation_strategy(TREND, "name", description) values ('BEARISH', 'Bollinger Band Exit Bearish', 'Recommends user to buy puts to sell if the stock price average retreats from the upper Bollinger Band');
INSERT INTO recommendation_strategy(TREND, "name", description) values ('BULLISH', 'Bollinger Band Exit Bullish', 'Recommends user to buy calls to sell if the stock price average retreats from the lower Bollinger Band');

INSERT INTO recommendation_strategy_version(recommendation_strategy_key, "version") values (1, 1);
INSERT INTO recommendation_strategy_version(recommendation_strategy_key, "version") values (2, 1);
INSERT INTO recommendation_strategy_version(recommendation_strategy_key, "version") values (3, 1);
INSERT INTO recommendation_strategy_version(recommendation_strategy_key, "version") values (4, 1);