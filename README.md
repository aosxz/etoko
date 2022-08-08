
# Project EToko
Project EToko ini merupakan Supply untuk toko-toko grosir untuk mempermudah perhitungan dan pendataan
dengan 4 tabel yang digunakan ,diantara nya Tabel Product,Shipment,Purchase,Buyer
## Aplikasi ini dibangun untuk tujuan berikut:

 - Mempermudah Pendataan 
 - Mempermudah  perhitungan pada transaksi 
 - Menyimpan data pelanggan sampai produk
 - Mengedit atau Menghapus data antara Product dan Buyer.






## Teknologi yang digunakan
- Java
- SQL
- Spring Boot
## Database
- MS SQL
# Mini Project EToko



<!--- If we have only one group/collection, then no need for the "ungrouped" heading -->


## Variables

| Key | Value | Type |
| --- | ------|-------------|
| base_url | http://localhost:6666 |  |



## Endpoints

* [Buyer ](#buyer-done)
    1. [Get All Buyer](#1-get-all-buyer)
    1. [Find Buyer By Id](#2-find-buyer-by-id)
    1. [Insert Buyer](#3-insert-buyer)
    1. [Update Buyer by Id](#4-update-buyer-by-id)
    1. [Delete Buyer by Id](#5-delete-buyer-by-id)
* [Shipment ](#shipment-done)
    1. [Get All Shipment](#1-get-all-shipment)
    1. [Find Shipment By Name](#2-find-shipment-by-name)
    1. [Insert Shipment](#3-insert-shipment)
    1. [Update Shipment](#4-update-shipment)
    1. [Delete Shipment](#5-delete-shipment)
* [Purchase](#purchase)
    1. [Insert Purchase](#1-insert-purchase)
    1. [Purchase Get All](#2-purchase-get-all)
    1. [findByPurchaseDate](#3-findbypurchasedate)
    1. [findPurchaseByBuyerId](#4-findpurchasebybuyerid)
    1. [findPurchaseByCategoryProduct](#5-findpurchasebycategoryproduct)
    1. [findPurchaseByShipment](#6-findpurchasebyshipment)
    1. [Delete Purchase by Id](#7-delete-purchase-by-id)
    1. [Update Purchase](#8-update-purchase)
    1. [findPurchaseByPurchaseId](#9-findpurchasebypurchaseid)
* [Product](#product)
    1. [Get All Product](#1-get-all-product)
    1. [Insert Product](#2-insert-product)
    1. [Update Product](#3-update-product)
    1. [Delete Product](#4-delete-product)
    1. [Find Product By Id](#5-find-product-by-id)

--------



## Buyer 



### 1. Get All Buyer



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/buyer/find-all
```



### 2. Find Buyer By Id



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/buyer/find-by-id/2
```



### 3. Insert Buyer



***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{base_url}}/buyer/insert
```



***Body:***

```js        
{
        "firstName": "Andi",
        "lastName": "Tri",
        "birthDate": "10 Februari 1999",
        "birthPlace": "Jakarta",
        "address": "Jl Nawi"
        }
```



### 4. Update Buyer by Id



***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{base_url}}/buyer/1
```



***Body:***

```js        
{
    
    
        "firstName": "Aos",
        "lastName": "Saliraa",
        "birthDate": "11 Januari 1999",
        "birthPlace": "Kuningan",
        "address": "Jl Nawi"
    
        }
```



### 5. Delete Buyer by Id



***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{base_url}}/buyer/6
```



## Shipment Done



### 1. Get All Shipment



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/shipment/getAll
```



### 2. Find Shipment By Name



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/shipment/find-by-name/J&T
```



### 3. Insert Shipment



***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{base_url}}/shipment/insert
```



***Body:***

```js        
{
        "nameShipment": "J&T",
        "description": "JNE Cepat",
        "cost": "500000"

}
```



### 4. Update Shipment



***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{base_url}}/shipment/update/Gosend
```



***Body:***

```js        
{


         "description": "Gosend Cepat Sangat"
}
```



### 5. Delete Shipment



***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{base_url}}/shipment/delete/WWWsW
```



## Purchase



### 1. Insert Purchase



***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{base_url}}/purchase/insert
```



***Body:***

```js        
{
    "purchaseId": "P001",
        "buyerId": 1,
        "nameShipment": "JNE",
        "productId": "M001",
        "quantity": 100
        }
```



### 2. Purchase Get All



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/purchase/getAll
```



### 3. findByPurchaseDate



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/purchase/findByPurchaseDate/11-June-2022
```



### 4. findPurchaseByBuyerId



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/purchase/findPurchaseByBuyerId/8
```



### 5. findPurchaseByCategoryProduct



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/purchase/findPurchaseByCategoryProduct/Laptops
```



### 6. findPurchaseByShipment



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/purchase/findPurchaseByShipment/WWWSW
```



### 7. Delete Purchase by Id



***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{base_url}}/purchase/delete/B002
```



### 8. Update Purchase



***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{base_url}}/purchase/update/B002
```



***Body:***

```js        
{
            "buyerId": 1,
            "nameShipment": "Test",
            "productId": "LLL001",
            "quantity": 100,
            "totalPrice": "Rp13.000,00"
            }
```



### 9. findPurchaseByPurchaseId



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/purchase/findPurchaseByPurchaseId/P001
```



## Product



### 1. Get All Product



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/product/find-all
```



### 2. Insert Product



***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{base_url}}/product/insert
```



***Body:***

```js        
{
        "productId": "R00012",
        "nameProduct": "Malboro",
        "category": "Roko",
        "price": "550000",
        "description": "Malboro 1 Slop",
        "stock": 1010
    }
```



### 3. Update Product



***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{base_url}}/product/LLL001
```



***Body:***

```js        
{
    
    "stock": 1000
}
```



### 4. Delete Product



***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{base_url}}/product/LLL001sdd
```



### 5. Find Product By Id



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{base_url}}/product/find-by-id/M001
```



---
[Back to top](#mini-project-etoko)
