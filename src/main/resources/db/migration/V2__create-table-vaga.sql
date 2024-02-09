CREATE TABLE vaga (
    id SERIAL PRIMARY KEY,
    titulo_vaga VARCHAR(255) NOT NULL,
    data_inicio DATE NOT NULL,
    data_expiracao DATE NOT NULL,
    descricao_vaga TEXT NOT NULL,
    salario FLOAT,
    total_de_candidatos_aplicado INTEGER DEFAULT 0
)
