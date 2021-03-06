CREATE TABLE test.Users (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    POINTS INTEGER
);
CREATE TABLE test.Posts (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    USER_ID INTEGER,
    TEXT VARCHAR(255) NOT NULL,
    POINTS INTEGER,
    FOREIGN KEY(USER_ID) REFERENCES User(ID)
);
CREATE TABLE test.Comments (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    ARTICLE_ID INTEGER,
    USER_ID INTEGER,
    TEXT VARCHAR(255) NOT NULL,
    POINTS INTEGER,
    FOREIGN KEY(ARTICLE_ID) REFERENCES Article(ID),
    FOREIGN KEY(USER_ID) REFERENCES User(ID)
);