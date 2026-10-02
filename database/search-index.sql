ALTER TABLE documents
    ADD COLUMN IF NOT EXISTS search_vector tsvector;

UPDATE documents
SET search_vector =
        to_tsvector(
                'english',
                coalesce(title, '') || ' ' ||
                coalesce(description, '') || ' ' ||
                coalesce(content, '')
        );

CREATE INDEX IF NOT EXISTS documents_search_idx
    ON documents
    USING GIN(search_vector);

CREATE OR REPLACE FUNCTION update_document_search_vector()
RETURNS TRIGGER AS $$
BEGIN
    NEW.search_vector :=
        to_tsvector(
            'english',
            coalesce(NEW.title, '') || ' ' ||
            coalesce(NEW.description, '') || ' ' ||
            coalesce(NEW.content, '')
        );

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS documents_search_vector_trigger
ON documents;

CREATE TRIGGER documents_search_vector_trigger
    BEFORE INSERT OR UPDATE
                         ON documents
                         FOR EACH ROW
                         EXECUTE FUNCTION update_document_search_vector();