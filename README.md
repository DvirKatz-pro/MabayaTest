# MabayaTest
2 APIs for a Campaign and a Product

How to use:

Product API:
1. Create a Product:
a sample product would look something like this in JSON

{
    "title": "Cup",
    "category": "HOME",
    "price": 200.56,
    "serialNumber": 11
}

created using this post request: http://localhost:8080/product

Note that "category" is an enum and must respond to one of these Category enums 

public enum Categories {
        ELECTRONICS, OUTDOORS, HYGIENE, HOME, HEALTH, SCHOOL, UNKNOWN
}

2. Read (get) a Product:
A get with this request
http://localhost:8080/product/11

where 11 is the desired serial number

3. Read (get) all Products:
A get with this request
http://localhost:8080/product

4. Update a Product:
A put with a JSON product 
{
    "title": "Cup",
    "category": "HOME",
    "price": 300.56,
    "serialNumber": 11
}

using this request: http://localhost:8080/product

5. Delete a prodcut:
A delete with this request 
http://localhost:8080/product/11

where 11 is the desired serial number to delete


campaign API:
1. Create a Campaign:
A sample Campaign would look something like this in JSON

{
    "name": "campaign1",
    "startDate": 20230818,
    "bid": 1000,
    "productSerialNumbers": [
        2,
        3,
        4,
        11
    ]
}

Note: A start date must be of format yyyyMMdd

created using this post request: http://localhost:8080/campaign

2. Read (get) a Campaign:
A get with this request
http://localhost:8080/campaign/campaign1

where campaign1 is the Campaign name 

3. Read (get) all Campaigns:
A get with this request
http://localhost:8080/campaign

4. Update a Campaign:
A put with a JSON product 
{
    "name": "campaign1",
    "startDate": 20230818,
    "bid": 1000,
    "productSerialNumbers": [
        2,
        3,
        4,
        11
    ]
}
using this request: http://localhost:8080/campaign

5. Delete a Campaign:
A delete with this request 
http://localhost:8080/product/campaign1

where campaign1 is the desired campaign name to delete

6. Get a promoted Product:
A get request with this URL: http://localhost:8080/campaign/getPromotedProduct?category=ELECTRONICS

where category must be an enum Categories as written above.

The API will go through all active Campaigns and retrieve the Campaign with the highest bid that has a product with the given category, if no such prodcut with the given category could be found, we return the first product we find belonging the an active Campaign with the highest bid  




