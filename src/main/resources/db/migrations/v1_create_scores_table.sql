-- src/main/resources/db/migration/V1__create_scores_table.sql

CREATE TABLE IF NOT EXISTS scores (
                                      id SERIAL PRIMARY KEY,
                                      player_name VARCHAR(255) NOT NULL,
    score INT NOT NULL,
    game_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
