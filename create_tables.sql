
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