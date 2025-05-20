# Assignment - 4 (ByteMeApp)

## Overview

ByteMeApp is a console and GUI based application that allows customers to place orders and admins to manage the menu and restock items. The application maintains a list of customers and their order history.

## Assumptions

- Each customer is identified by their name, and the same name will refer to the same customer instance.
- GUI has two buttons, one for browsing menu and one for viewing pending Orders.
- Only admin can view pending orders through GUI. (Password: 0000)
- GUI can only be launched once and is opened after all CLI commands/operations are completed.
- The menu items have a name, price, category, and quantity.
- Orders can be placed, edited, and removed by customers.
- Admins have the ability to manage the menu and restock items.
- The application maintains a list of customers and their order history.
- Admin can change status of any order to completed, pending, or refunded.
- Customers can change their VIP status for free (toggleable option).

## How to Use

1. Run the code.
2. Select the user type (customer or admin).
3. Follow the prompts to place an order, manage the menu, or restock items.
4. For customers, the order history can be viewed.
5. For admins, the menu can be managed and orders can be updated.
6. Go to main menu (exit everything and go to main menu) to launch GUI (option 3) to view pending orders or browse menu.
7. The application can be exited at any time by entering 'exit'.

