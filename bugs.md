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

## BUG-002 — Checkout step one does not validate field content

- **Severity:** Minor
- **Area:** Checkout (Your Information form)
- **Status:** Open (present as of <date>)

**Steps to reproduce**
1. Log in as `standard_user`, add a product, and proceed to checkout.
2. In the "Your Information" form, enter either:
    - a non-numeric postal code (e.g. `abcde`), or
    - whitespace-only values (e.g. `"   "`) in First Name, Last Name, and/or Zip/Postal Code.
3. Click Continue.

**Expected result**
The form should reject invalid content: postal code should require a numeric/valid format, and whitespace-only values should not count as valid names.

**Actual result**
The form performs only a non-empty check. Any non-empty value is accepted — non-numeric postal codes and whitespace-only fields all pass, and checkout proceeds to the overview.

**Notes**
Demonstrated by `CheckoutTest.checkoutShouldRejectInvalidFieldContent` (intentionally failing,
linked to BUG-002). The form accepts whitespace-only names and non-numeric postal codes and
advances to checkout-step-two; a validating form should remain on step one.