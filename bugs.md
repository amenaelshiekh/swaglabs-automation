# Known Bugs — SauceDemo (Swag Labs)

Defects observed in the application under test (saucedemo.com).
These are issues in the SauceDemo app itself, not in the test framework.

---

## BUG-001 — "Reset App State" does not restore inventory buttons

- **Severity:** Minor
- **Area:** Inventory / Menu
- **Status:** Open (present as of <date>)

**Steps to reproduce**
1. Log in as `standard_user`.
2. On the inventory page, click "Add to cart" for any product (button changes to "Remove").
3. Open the burger menu and click "Reset App State".

**Expected result**
The cart is emptied AND the product's button reverts from "Remove" to "Add to cart".

**Actual result**
The cart badge is cleared, but the product button still shows "Remove" until the page is manually reloaded.
