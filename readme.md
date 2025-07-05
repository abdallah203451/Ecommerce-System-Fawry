# E-Commerce System

e-commerce system using Java that handles different product types, shopping cart functionality, and checkout processing.

## Project Assumptions

### Product Types

This system supports 4 different product types:

1. **Product** - Basic product with name, price, and quantity
2. **ExpirableProduct** - Product with expiration date functionality
3. **ShippableProduct** - Product that can be shipped with weight information
4. **ExpirableShippableProduct** - Product that is both expirable and shippable

### Shipping Calculations

- **Minimum shipping fee**: $5
- **Shipping rate**: $10 per kilogram
- **Weight unit**: All product weights are entered in kilograms (KG)

**Total shipping cost formula:**

```
Shipping Fee = Base Fee ($5) + (Total Weight in KG × $10)
```
