CREATE TABLE user_calendar_links (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    calendar_year_id INT NOT NULL REFERENCES calendar_year(id) ON DELETE CASCADE,
    UNIQUE (username, calendar_year_id)
);
