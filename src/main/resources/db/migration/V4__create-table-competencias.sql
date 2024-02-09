CREATE TABLE competencias (
    id SERIAL PRIMARY KEY,
    candidato_id BIGINT NOT NULL,
    competencia VARCHAR(255) NOT NULL,
    FOREIGN KEY (candidato_id) REFERENCES candidato(id) ON DELETE CASCADE
)