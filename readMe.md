# Getting Started

index : 
1. CREATE PRIMARY INDEX idx_default_primary ON `product` USING GSI
2. CREATE INDEX product_cxname ON `product`(LOWER(productName));
3. CREATE INDEX product_cxsize ON `product`(LOWER(size));

url :
create product : 
	http://localhost:12340/api/v1/product/
	http://localhost:12340/api/v1/products/
update product:
   http://localhost:12340/api/v1/products/1
   
delete product:
   http://localhost:12340/api/v1/products/1
   
fetching products:
  http://localhost:12340/api/v1/products/1
  http://localhost:12340/api/v1/products/name/prod1?page=30&offset=1
  http://localhost:12340/api/v1/products/size/L?page=30&offset=1
  http://localhost:12340/api/v1/products/name/prod1/size/L?page=30&offset=1
  http://localhost:12340/api/v1/products?page=30&offset=1
  