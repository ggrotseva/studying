package bookshop.constants;

public enum StoredProcedure {
    ;
    final static String STORED_PROCEDURE_QUERY = "DELIMITER $$\n" +
            "CREATE PROCEDURE usp_authors_books_count(first VARCHAR(255), last VARCHAR(255), OUT book_count INT)\n" +
            "BEGIN\n" +
            "\tSELECT COUNT(b.id) into book_count FROM books b\n" +
            "    JOIN authors a ON a.id = b.author_id\n" +
            "    WHERE a.first_name = first AND a.last_name = last;\n" +
            "END$$";
}
