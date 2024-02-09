-- Adicionar restrição UNIQUE para o campo cpf
ALTER TABLE candidato
ADD CONSTRAINT unique_cpf UNIQUE (cpf);

-- Adicionar restrição UNIQUE para o campo email
ALTER TABLE candidato
ADD CONSTRAINT unique_email UNIQUE (email);

-- Adicionar restrição UNIQUE para o campo telefone
ALTER TABLE candidato
ADD CONSTRAINT unique_telefone UNIQUE (telefone);