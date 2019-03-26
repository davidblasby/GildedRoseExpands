CREATE TABLE ITEM (
  id        IDENTITY NOT NULL PRIMARY KEY,
  name  VARCHAR(50) NOT NULL,
  description VARCHAR(50) NOT NULL,
  price INTEGER(10) NOT NULL
);

CREATE UNIQUE INDEX idx_name_unique
  ON ITEM (name);

 CREATE TABLE ITEM_VIEW(
    ID IDENTITY NOT NULL PRIMARY KEY,
    ITEM_NAME VARCHAR(50)   NOT NULL,
    TIME_VIEWED TIMESTAMP(26,6)  NOT NULL
 );

CREATE  INDEX idx_ITEM_VIEW_ITEM_NAME
   ON ITEM_VIEW (ITEM_NAME);

 CREATE TABLE INVENTORY_ITEM(
     ID IDENTITY NOT NULL PRIMARY KEY,
     ITEM_NAME  VARCHAR(50)    NOT NULL,
     NUMBER_REMAINING BIGINT(19)  NOT NULL,
     CHECK (NUMBER_REMAINING >=0)
  );

 CREATE  INDEX idx_INVENTORY_ITEM_ITEM_NAME
    ON INVENTORY_ITEM (ITEM_NAME);