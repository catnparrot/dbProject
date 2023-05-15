DROP USER parrot cascade;

CREATE USER parrot IDENTIFIED BY 12345;

GRANT CONNECT, resource, dba to parrot;

SELECT * FROM accounts;

CREATE TABLE users (
    userid              varchar2(50)        PRIMARY KEY,
    username            varchar2(50)        NOT NULL,
    userpassword        varchar2(50)        NOT NULL,
    userage             number(3)           NOT NULL,
    useremail           varchar2(50)        NOT NULL
);

create table boards (
    bno         NUMBER                  PRIMARY KEY,
    btitle      varchar2(100)           NOt NULL,
    bcontent    clob                     NOt NULL,
    bwriter     varchar2(50)                   NOt NULL,
    bdate       DATE              DEFAULT sysdate,
    bfilename   varchar2(50)                  NULL,
    bfiledata   blob                 NULL
);

CREATE SEQUENCE     SEQ_BNO NOCACHE;

CREATE TABLE accounts (
    ano     varchar(20)     PRIMARY KEY,
    owner   varchar(20)     NOT NULL,
    balance NUMBER          NOT NULL
);

INSERT INTO accounts (ano, owner, balance)
VALUES('111-111-111', '하여름', '1000000');

INSERT INTO accounts (ano, owner, balance)
VALUES('222-222-222', '한겨울', '0');

COMMIT;

CREATE OR REPLACE PROCEDURE user_create (
    a_userid      IN      users.userid%TYPE,
    a_username      IN      users.username%TYPE,
    a_userpassword      IN      users.userpassword%TYPE,
    a_userage      IN      users.userage%TYPE,
    a_useremail      IN      users.useremail%TYPE,
    a_rows          OUT     PLS_INTEGER
)
IS
BEGIN
    INSERT INTO users (userid, username, userpassword, userage, useremail)
    VALUES (a_userid, a_username, a_userpassword, a_userage, a_useremail);
    a_rows := SQL%ROWCOUNT;
    commit;
END;
/

CREATE OR REPLACE FUNCTION user_login (
    a_userid        users.userid%TYPE,
    a_userpassword  users.userpassword%TYPE
) RETURN PLS_INTEGER
IS
    v_userpassword users.userpassword%TYPE;
    v_result PLS_INTEGER;
BEGIN
    SELECT userpassword INTO v_userpassword
    FROM users
    where userid = a_userid;
    
    IF v_userpassword = a_userpassword THEN
        RETURN 0;
    ELSE
        RETURN 1;
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 2;
END;
/	