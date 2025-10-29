CREATE TABLE calendar_year (
    id SERIAL PRIMARY KEY,
    year INT NOT NULL UNIQUE CHECK (calendar_year.year >= 1600)
);

CREATE TABLE calendar_month (
    id SERIAL PRIMARY KEY,
    month_number INT NOT NULL CHECK ( month_number > 0 and month_number <= 12),
    calendar_year_id INT NOT NULL REFERENCES calendar_year(id) ON DELETE CASCADE
);

CREATE TABLE calendar_week (
    id SERIAL PRIMARY KEY,
    week_number INT NOT NULL CHECK ( week_number > 0 and week_number <= 7),
    calendar_month_id INT NOT NULL REFERENCES calendar_month(id) ON DELETE CASCADE
);

CREATE TABLE calendar_day (
    id SERIAL PRIMARY KEY,
    day_of_month INT NOT NULL CHECK ( day_of_month > 0 and day_of_month <= 31 ),
    day_of_week VARCHAR(20) NOT NULL,
    is_weekend BOOLEAN NOT NULL,
    calendar_week_id INT NOT NULL REFERENCES calendar_week(id) ON DELETE CASCADE
);
