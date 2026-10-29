# SmartSpend API

Base URL: http://localhost:8080

## Endpoints
- GET /api/products : list all products
- GET /api/products/search?query=milk : search products by name
- GET /api/products/{id}/prices : prices for one product across stores
- GET /api/products/compare?query=eggs : compare prices and return the cheapest store
- GET /api/products/stores : list all stores

## Example
GET /api/products/compare?query=eggs returns the product, its prices per store, the cheapest store and the cheapest price.
