# Building and Running

## Building

In the main directory (where the pom.xml) is;

`mvn clean install`

## Running

In the main directory (where the pom.xml) is;

`mvn spring-boot:run`

You can access the API at http://localhost:8080/ (see below for samples)

## Example Queries

### Background

There are two pre-configured users;
* "guest" with password "password"
* "admin" with password "admin"

Basic HTTP authorization is used. 

There are a few admin sections where you need to be the "admin" user.   
If you find yourself logged in as the wrong person, open a new incognito window and it should prompt you to login.

There is some sample data auto-loaded when your run the system (as shown below).

### Get All Items

http://localhost:8080/items

`curl --user guest:password http://localhost:8080/items`

```JSON
[
  {
    "name": "widget1",
    "description": "test item 1",
    "price": 100
  },
  {
    "name": "widget2",
    "description": "test item 2",
    "price": 200
  },
  {
    "name": "widget3",
    "description": "test item 3",
    "price": 300
  },
  {
    "name": "widget4",
    "description": "test item 4",
    "price": 400
  }
]
```

The format definition is in [ItemViewModel.java](../src/main/java/com/miw/gildedroseexpands/entity/item/support/ItemViewModel.java)

NOTE: if the price is surged, this will show the surged price.  You can see this by executing the request, above, more than 10 times

```JSON
[
  {
    "name": "widget1",
    "description": "test item 1",
    "price": 110
  },
  {
    "name": "widget2",
    "description": "test item 2",
    "price": 220
  },
  {
    "name": "widget3",
    "description": "test item 3",
    "price": 330
  },
  {
    "name": "widget4",
    "description": "test item 4",
    "price": 440
  }
]
```

### Get Single Items

http://localhost:8080/items/widget1

Replace "widget1" with the name of item you want to see.

`curl --user guest:password http://localhost:8080/items/widget1`

```JSON
{
  "name": "widget1",
  "description": "test item 1",
  "price": 100
}
```

After more than 10 requests (including the "Get All Items"), you will see the surge price;

```JSON
{
  "name": "widget1",
  "description": "test item 1",
  "price": 110
}
```

The format definition is in [ItemViewModel.java](../src/main/java/com/miw/gildedroseexpands/entity/item/support/ItemViewModel.java)

NOTE: if you supply an unknown item name, you will get a 404.


### Buy Item

Buying an item requires a POST and to be logged on.

`curl -d '{"itemName":"widget1"}' --user guest:password -H "Content-Type: application/json" -X POST http://localhost:8080/buy`

Replace "widget1" with the item name you want to buy.

It will return `true` if successful.

It will return a security exception (401/403) if you are not logged on.

It will return a 400 if there is no inventory available.

NOTE: There are 11 "widget1" items - you can run the above 11 time without error.  The 12th request will produce a 400.

The format definition for the input JSON object is  [PurchaseRequest.java](../src/main/java/com/miw/gildedroseexpands/entity/item/controller/PurchaseRequest.java)

### Admin: Inventory

This is an admin section (must be logged on as admin).

http://localhost:8080/inventory

`curl --user admin:admin http://localhost:8080/inventory`

```JSON
[
  {
    "itemName": "widget1",
    "numberRemaining": 11
  },
  {
    "itemName": "widget2",
    "numberRemaining": 22
  },
  {
    "itemName": "widget3",
    "numberRemaining": 33
  }
]
```

You can use this to see the available inventory after doing a "buy."

NOTE: there is no inventory for "widget4" - it defaults to having 0 inventory.

The format definition for object is [InventoryItemViewModel.java.java](../src/main/java/com/miw/gildedroseexpands/entity/inventory/support/InventoryItemViewModel.java)

### Admin: Item Views By Item

This is an admin section (must be logged on as admin).


http://localhost:8080/admin/itemviews/widget1

`curl --user admin:admin http://localhost:8080/admin/itemviews/widget1`

```JSON
[
  {
    "id": 4,
    "itemName": "widget1",
    "timeViewed": "2019-03-26T05:11:21.877Z"
  },
  {
    "id": 8,
    "itemName": "widget1",
    "timeViewed": "2019-03-26T05:14:18.775Z"
  }
]
```

This shows the recent views of a single item.

NOTE: 

* There are some pre-loaded (old) views that you might see
* When you view an item, old views (>1 hour ago) are purged 

The format definition for object is [ItemViewDB.java.java](../src/main/java/com/miw/gildedroseexpands/entity/surgetracking/support/ItemViewDB.java)

### Admin: Item Views All Items


This is an admin section (must be logged on as admin).

http://localhost:8080/admin/itemviews

`curl --user admin:admin http://localhost:8080/admin/itemviews`

```JSON
[
  {
    "id": 4,
    "itemName": "widget1",
    "timeViewed": "2019-03-26T05:11:21.877Z"
  },
  {
    "id": 5,
    "itemName": "widget2",
    "timeViewed": "2019-03-26T05:11:21.909Z"
  },
  {
    "id": 6,
    "itemName": "widget3",
    "timeViewed": "2019-03-26T05:11:21.912Z"
  },
  {
    "id": 7,
    "itemName": "widget4",
    "timeViewed": "2019-03-26T05:11:21.914Z"
  }
]
```

NOTE: 

* There are some pre-loaded (old) views that you might see
* When you view an item, old views (>1 hour ago) are purged 

The format definition for object is [ItemViewDB.java.java](../src/main/java/com/miw/gildedroseexpands/entity/surgetracking/support/ItemViewDB.java)

### Admin: h2-console

You can access the h2-console at http://localhost:8080/h2-console

Connect to this database;

`jdbc:h2:mem:testdb`

NOTE: if you get security errors, you are not logged in the admin user.  Open a new incognito window and login as "admin" with password "admin".

## Configuration

To change the Surge Pricing Parameters, edit the [application.properties](../src/main/resources/application.properties) file.

```
surge.max-views-to-active-surge=10
surge.surge-price-factor=1.1
surge.surge-timeframe-seconds=3600
```
