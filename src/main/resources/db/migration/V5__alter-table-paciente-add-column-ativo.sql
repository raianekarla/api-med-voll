ALTER TABLE pacientes ADD COLUMN ativo BOOLEAN;
UPDATE pacientes SET ativo = TRUE;
