package org.example.newspaperspring.dao.utils;

import org.springframework.stereotype.Component;

@Component
public class SQLQueries {

    // SELECT queries
    public static final String SELECT_ARTICLE_BY_ID_QUERY =
            "SELECT a.id, a.name, t.description,a.type_id, a.newspaper_id FROM article a INNER JOIN type t ON a.type_id = t.id WHERE a.id = ? GROUP BY a.id, a.name, a.type_id, a.newspaper_id";
    public static final String SELECT_ARTICLES_QUERY =
            "SELECT a.id, a.name, t.description,a.type_id, a.newspaper_id FROM article a INNER JOIN type t ON a.type_id = t.id";
    public static final String SELECT_CREDENTIAL_BY_USERNAME_QUERY = "select * from credential where username = ?";
    public static final String SELECT_NEWSPAPER_BY_ID_QUERY = "select * from newspaper where id = ?";
    public static final String SELECT_NEWSPAPERS_QUERY = "select * from newspaper";
    public static final String SELECT_READARTICLES_QUERY = "select * from readarticle";
    public static final String SELECT_READER_BY_ID_QUERY = "select * from reader where id = ?";
    public static final String SELECT_READERS_BY_ARTICLE_ID_QUERY = "SELECT r.* FROM reader r JOIN readarticle ra ON r.id = ra.reader_id WHERE ra.article_id = ?";
    public static final String SELECT_READERS_QUERY = "select * from reader";
    public static final String SELECT_TYPES_QUERY = "select * from type";

    // Cambiar los INSERT para tener en cuenta los auto increment
    // INSERT queries
    public static final String INSERT_ARTICLE_QUERY = "insert into article (name, type_id, newspaper_id) values(?,?,?)";
    public static final String INSERT_NEWSPAPER_QUERY = "insert into newspaper values(?,?,?)";
    public static final String INSERT_READARTICLE_QUERY = "insert into readarticle values(?,?,?)";
    public static final String INSERT_READER_QUERY = "insert into reader(name,dob) values(?,?)";
    public static final String INSERT_READER_CREDENTIALS_QUERY = "insert into credential values(?,?,?)";
    public static final String INSERT_READER_VALUES_QUERY = "insert into reader values(?,?,?,?)";

    // UPDATE queries
    public static final String UPDATE_ARTICLE_QUERY = "update article set name = ?, type_id = ?, newspaper_id = ? where id = ?";
    public static final String UPDATE_NEWSPAPER_QUERY = "update newspaper set name = ? where id = ?";
    public static final String UPDATE_READARTICLE_QUERY = "update readarticle set rating = ? where article_id = ?";
    public static final String UPDATE_READER_QUERY = "update reader set name = ? where id = ?";

    // DELETE queries
    public static final String DELETE_ARTICLE_QUERY = "delete from article where id = ?";
    public static final String DELETE_NEWSPAPER_QUERY = "delete from newspaper where id = ?";
    public static final String DELETE_READARTICLE_QUERY = "delete from readarticle where article_id = ?";
    public static final String DELETE_READARTICLE_BY_ARTICLE_ID_QUERY = "delete from readarticle where article_id = ?";
    public static final String DELETE_READER_QUERY = "delete from reader where id = ?";
    public static final String DELETE_READER_CREDENTIALS_BY_READER_ID_QUERY = "delete from credential where reader_id = ?";


}
