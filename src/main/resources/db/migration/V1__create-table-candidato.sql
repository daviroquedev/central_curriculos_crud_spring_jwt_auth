CREATE TABLE candidato (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    data_nascimento DATE,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    escolaridade VARCHAR(50),
    funcao VARCHAR(255),
    lista_competencias JSONB, -- Pode ser um JSONB para armazenar uma lista de competências
    status_solicitacao VARCHAR(50) DEFAULT 'Pendente',
    role TEXT NOT NULL
)
