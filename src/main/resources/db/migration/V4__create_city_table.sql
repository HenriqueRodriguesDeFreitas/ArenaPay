CREATE TABLE IF NOT EXISTS city(
  id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    state_id UUID NOT NULL,
    CONSTRAINT fk_city_state FOREIGN KEY (state_id) REFERENCES state(id)
);
