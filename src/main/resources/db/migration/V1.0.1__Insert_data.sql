INSERT INTO item(name,description,price) values ('widget1', 'test item 1', 100);
INSERT INTO item(name,description,price) values ('widget2', 'test item 2', 200);
INSERT INTO item(name,description,price) values ('widget3', 'test item 3', 300);
INSERT INTO item(name,description,price) values ('widget4', 'test item 4', 400);

INSERT INTO item_view(item_name,time_viewed) values ('widget1','2018-03-25 12:00:00');
INSERT INTO item_view(item_name,time_viewed) values ('widget1','2018-03-25 12:01:00');
INSERT INTO item_view(item_name,time_viewed) values ('widget2','2018-03-25 12:02:00');


INSERT INTO INVENTORY_ITEM(ITEM_NAME,NUMBER_REMAINING) values ('widget1',11);
INSERT INTO INVENTORY_ITEM(ITEM_NAME,NUMBER_REMAINING) values ('widget2',22);
INSERT INTO INVENTORY_ITEM(ITEM_NAME,NUMBER_REMAINING) values ('widget3',33);
