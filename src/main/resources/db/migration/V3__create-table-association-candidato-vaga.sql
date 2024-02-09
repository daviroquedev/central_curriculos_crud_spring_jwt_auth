CREATE TABLE candidato_vaga (
    candidato_id BIGINT NOT NULL,
    vaga_id BIGINT NOT NULL,
    PRIMARY KEY (candidato_id, vaga_id),
    FOREIGN KEY (candidato_id) REFERENCES candidato(id) ON DELETE CASCADE,
    FOREIGN KEY (vaga_id) REFERENCES vaga(id) ON DELETE CASCADE
)