# SmartSpend Database

MySQL database: smartspend_db. Tables are created by Hibernate (ddl-auto=update).

## stores
id, name, type, website

## products
id, name, brand, category, unit

## prices
id, product_id, store_id, regular_price, sale_price, is_on_sale, recorded_at

## Relationships
- A price belongs to one product and one store
- A product has many prices, one per store and time recorded

## Notes
- recorded_at lets us keep price history over time
- Kroger rows come from the Kroger API; other stores use seeded sample data
