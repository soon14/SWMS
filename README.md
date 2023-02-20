# SWMS

super warehouse management system

### Extends Model

> This is the customization if you found some function is not fit for you. In this model, all files are interface, you need to implement them.

### Outbound Model

##### Order process - sub model

Entities:

- Order: Representing a customer order, including the customer information, the items ordered, the quantities, and the
  status of the order.
- Customer: Representing a customer of the warehouse, including their name, address, and contact information.

Value Objects:

- Order Item: Representing a single item that has been ordered by a customer.
- Shipping Address: Representing the address where an order is to be shipped.

Aggregates:

- Order Process: Representing the overall process of managing customer orders, including creating orders, processing
  payments, and shipping orders.

Services:

- Order Management: Representing the process of managing customer orders, including creating new orders, updating the
  status of orders, and processing payments.
- Order Query: Representing the process of querying the order process for information about customer orders, such as the
  status of an order, the items ordered, and the shipping information.

> These entities, value objects, aggregates, and services form the core components of the Order process in the WMS domain and would be used to drive the design of the system. The implementation of each component would be influenced by the specific requirements of the WMS domain, and the design would need to be refined and iterated as new information becomes available.

##### Picking Model - sub model

Entities:

- Outbound Order:
- Picking Order: Represents a request to pick a certain number of items from the warehouse for shipment. This entity
  contains information such as the customer name, shipping address, and the items to be picked.
- Shipment Order:

Value Objects:

- Picking Item: Represents a specific item to be picked and contains information such as the item name, item ID, and the
  quantity to be picked.

Aggregates:

- Picking Process: Represents the overall process of picking items from the warehouse. This aggregate contains the
  Picking Order and the Picking Item entities and defines the business rules and operations for the process.

Services:

- Picking Service: A service that coordinates the actions of the Picking Process aggregate and performs the operations
  needed to fulfill a Picking Order. This service communicates with the Inventory and Shipping services to manage the
  inventory and shipment of the picked items.

> This is just one example of a Picking model in a WMS and the specific design can vary depending on the requirements and constraints of the system.

##### Shipping process - sub model

Entities:

- Shipping Order: Representing an order to ship items to a customer.
- Shipped Item: Representing a single item that has been shipped.

Value Objects:

- Shipping Information: Representing information about a shipment, including the shipping method, shipping address, and
  shipping cost.

Aggregates:

- Shipping Process: Representing the overall process of shipping items to customers, including packing items, preparing
  shipping labels, and arranging for shipping.

Services:

- Shipping Management: Representing the process of managing the shipping of items to customers, including creating
  shipping orders, generating shipping labels, and tracking the progress of the shipping process.
- Shipping Query: Representing the process of querying the shipping process for information about shipments, such as the
  status of a shipment, the shipping address, and the shipping method.

> These entities, value objects, aggregates, and services form the core components of the Shipping process in the WMS domain and would be used to drive the design of the system. The implementation of each component would be influenced by the specific requirements of the WMS domain, and the design would need to be refined and iterated as new information becomes available.

### Warehouse Management:

Entities:

- Warehouse: Representing a physical warehouse where items are stored.
- Warehouse Location: Representing a specific location within the warehouse where items are stored.

Value Objects:

- Warehouse Information: Representing information about a warehouse, including its address, contact information, and
  capacity.

Aggregates:

- Warehouse Management: Representing the overall process of managing a warehouse, including tracking the use of
  warehouse space, updating warehouse information, and generating warehouse reports.

Services:

- Warehouse Space Management: Representing the process of managing the use of warehouse space, including allocating
  space for incoming items, relocating items within the warehouse, and tracking the progress of the space allocation
  process.
- Warehouse Information Management: Representing the process of updating and maintaining information about the
  warehouse, including updating contact information, capacity, and address.
- Warehouse Query: Representing the process of querying the warehouse management process for information about the
  warehouse, including information about specific warehouse locations, capacity, and the status of the warehouse.

> These entities, value objects, aggregates, and services form the core components of the Warehouse Management process in the WMS domain and would be used to drive the design of the system. The implementation of each component would be influenced by the specific requirements of the WMS domain, and the design would need to be refined and iterated as new information becomes available.

### Stock Movement

Entities:

- Stock Movement: Representing a movement of stock in the warehouse, including the type of movement (e.g., incoming,
  outgoing), the quantity, and the location.

Value Objects:

- Stock Location: Representing a specific location in the warehouse where stock is stored.
- Stock Quantity: Representing the quantity of stock in the warehouse.

Aggregates:

- Stock Management: Representing the overall process of managing stock in the warehouse, including tracking stock
  movements, updating stock quantities, and generating stock reports.

Services:

- Stock Movement Management: Representing the process of managing stock movements in the warehouse, including creating
  stock movements, updating stock quantities, and tracking the progress of the stock movement process.
- Stock Query: Representing the process of querying the stock management process for information about stock movements,
  such as the quantity of stock in a specific location, the type of movement, and the history of stock movements.

> These entities, value objects, aggregates, and services form the core components of the Stock Movement process in the WMS domain and would be used to drive the design of the system. The implementation of each component would be influenced by the specific requirements of the WMS domain, and the design would need to be refined and iterated as new information becomes available.

### Receiving process

Entities:

- Receiving Order: Representing an order to receive items into the warehouse.
- Received Item: Representing a single item that has been received into the warehouse.

Value Objects:

- Receiving Information: Representing information about a receiving order, including the delivery method, delivery
  address, and expected delivery date.

Aggregates:

- Receiving Process: Representing the overall process of receiving items into the warehouse, including inspecting items,
  verifying quantities, and storing items in the warehouse.

Services:

- Receiving Management: Representing the process of managing the receiving of items into the warehouse, including
  creating receiving orders, verifying delivery information, and tracking the progress of the receiving process.
- Receiving Query: Representing the process of querying the receiving process for information about received items, such
  as the quantity received, the delivery address, and the delivery method.

> These entities, value objects, aggregates, and services form the core components of the Receiving process in the WMS domain and would be used to drive the design of the system. The implementation of each component would be influenced by the specific requirements of the WMS domain, and the design would need to be refined and iterated as new information becomes available.


## Inventory

Entities:

- Item: Representing a single item stored in the warehouse, including its identifier, name, stock level, etc.

Value Objects:

- ItemLocation: Representing the location of an item in the warehouse, including the aisle, shelf, and bin.
- ItemInformation: Representing information about an item, such as its description, weight, and dimensions.

Aggregates:

- Inventory: Representing the collection of items stored in the warehouse, and the overall management of the inventory.

Services:

- Stock Management: Representing the process of managing the stock levels of items in the warehouse, including adding
  items to the inventory, adjusting stock levels, and removing items from the inventory.
- Inventory Query: Representing the process of querying the inventory for information about items, such as the stock
  levels, location, and information.

> These entities, value objects, aggregates, and services form the core components of the Inventory in the WMS domain and would be used to drive the design of the system. The implementation of each component would be influenced by the specific requirements of the WMS domain, and the design would need to be refined and iterated as new information becomes available.
