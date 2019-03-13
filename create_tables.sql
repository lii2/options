
-- Table: public.ticker
CREATE TABLE ticker
(
    ticker_key SERIAL PRIMARY KEY,
    ticker_symbol VARCHAR NOT NULL UNIQUE
)

-- Table: public.daily_data
CREATE TABLE daily_data
(
    daily_data_key SERIAL PRIMARY KEY,
    ticker_key INTEGER NOT NULL,
     "day" date NOT NULL,
    CONSTRAINT ticker_key_fk FOREIGN KEY (ticker_key)
         REFERENCES public.ticker (ticker_key) MATCH SIMPLE
         ON UPDATE CASCADE
         ON DELETE CASCADE
)

-- Table: public.time_series_daily
CREATE TABLE public.time_series_daily
(
    time_series_daily_key SERIAL PRIMARY KEY,
    daily_data_key integer NOT NULL,
    open numeric NOT NULL,
    close numeric NOT NULL,
    high numeric NOT NULL,
    low numeric NOT NULL,
     CONSTRAINT daily_data_fk FOREIGN KEY (daily_data_key)
        REFERENCES public.daily_data (daily_data_key) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

-- Table: public.bbands
CREATE TABLE bbands
(
    bbands_key SERIAL PRIMARY KEY,
    real_middle_band numeric NOT NULL,
    real_upper_band numeric NOT NULL,
    real_lower_band numeric NOT NULL,
    daily_data_key integer NOT NULL,
     CONSTRAINT daily_data_fk FOREIGN KEY (daily_data_key)
        REFERENCES public.daily_data (daily_data_key) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

-- Table: public.ema
CREATE TABLE ema
(
    ema_key SERIAL PRIMARY KEY,
    ema numeric NOT NULL,
      daily_data_key integer NOT NULL,
     CONSTRAINT daily_data_fk FOREIGN KEY (daily_data_key)
        REFERENCES public.daily_data (daily_data_key) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

-- Table: public.macd
CREATE TABLE macd
(
    macd_key SERIAL PRIMARY KEY,
    macd_hist numeric,
    macd_signal numeric,
    macd numeric NOT NULL,
     daily_data_key integer NOT NULL,
     CONSTRAINT daily_data_fk FOREIGN KEY (daily_data_key)
        REFERENCES public.daily_data (daily_data_key) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)


-- Table: public.recommendation_strategy
CREATE TABLE recommendation_strategy
(
    recommendation_strategy_key SERIAL PRIMARY KEY,
    trend varchar check (trend = 'BULLISH' OR trend = 'BEARISH'),
    name varchar not null,
    description varchar
 )

-- Table: public.recommendation_strategy_version
CREATE TABLE recommendation_strategy_version
(
    recommendation_strategy_version_key SERIAL PRIMARY KEY,
    recommendation_strategy_key integer NOT NULL,
    version integer not null,
     CONSTRAINT recommendation_strategy_fk FOREIGN KEY (recommendation_strategy_key)
        REFERENCES public.recommendation_strategy (recommendation_strategy_key) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
 )

-- Table: public.backtest_result
create table backtest_result
(
    backtest_result_key SERIAL PRIMARY KEY,
    recommendation_strategy_version_key integer not null,
    ticker_key integer not null,
    date_tested date not null,
    days_to_hold integer not null,
    biggest_loss numeric not null,
    successes integer not null,
    failures integer not null,
    fizzles integer not null,
    total integer not null,
    percent_success numeric not null,
    percent_not_failed numeric not null,

    CONSTRAINT ticker_key_fk FOREIGN KEY (ticker_key)
         REFERENCES public.ticker (ticker_key) MATCH SIMPLE
         ON UPDATE CASCADE
         ON DELETE CASCADE,

    CONSTRAINT recommendation_strategy_version_fk FOREIGN KEY (recommendation_strategy_version_key)
         REFERENCES public.recommendation_strategy_version (recommendation_strategy_version_key) MATCH SIMPLE
         ON UPDATE CASCADE
         ON DELETE CASCADE

)