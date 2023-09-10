# Smart Warehouse Management System (SWMS)

Welcome to the Smart Warehouse Management System (SWMS) project!

- **Multi-Tenancy Support:** SWMS supports multiple tenants, making it easy to cater to various customers within a
  single project. Each tenant can customize their specific requirements, ensuring flexibility and adaptability.

- **Plugin Frameworks:** Extend the functionality of SWMS using plugin frameworks. This allows for seamless integration
  of additional features and modules, enabling you to tailor the system to your unique needs.

- **Human and Robot Operations:** SWMS is equipped to handle both human and robot operations, facilitating smooth
  coordination between warehouse staff and automated systems. This flexibility ensures efficient warehouse management
  and can easily connect with your Warehouse Control Systems (WCS) and Robot Control Systems (RCS).

## Installation

To get started with the WMS, follow these installation steps:

1. Clone this repository: `git clone https://github.com/jingsewu/SWMS.git`
2. Install the required middlewares: JDK 17, Nacos (2.0+), Redis, MySQL (8.0+).
3. Execute the scripts in the /script folder into MySQL.
4. Update the IP and port settings in Nacos and the SWMS Tenant database.
5. Start the servers located in the /server directory.

## Usage

The SWMS provides a user-friendly interface for managing your warehouse. You can perform tasks such as:

- **Adding New Products:** Easily add new products to your inventory, simplifying the process of introducing new items
  to your warehouse.

- **Managing Inventory Levels:** Effortlessly monitor and manage inventory levels to ensure optimal stock availability
  and prevent overstocking or understocking.

- **Supporting Full Warehouse Flows:** SWMS supports complete warehouse workflows, encompassing inbound processes (
  receiving), outbound processes (shipping), rechecking, packing, stocktaking, and inventory relocation. This
  comprehensive support ensures efficient and organized operations.

- **Generating Performance Reports:** Access detailed reports on warehouse performance, providing valuable insights into
  key metrics, such as inventory turnover, order fulfillment rates, and operational efficiency.

- **Supporting Operator and Robot Operations:** SWMS seamlessly accommodates both human and robot operations, promoting
  a flexible and efficient work environment. Warehouse staff can collaborate effectively with automated systems,
  optimizing productivity.

- **Easy Integration with WCS and RCS:** SWMS is designed for easy integration with Warehouse Control Systems (WCS) and
  Robot Control Systems (RCS). This streamlined connectivity allows for synchronized and efficient management of robotic
  systems within the warehouse environment.

For detailed instructions and examples, refer to our example [website](http://test.smartswms.xyz/):.

## Contributing

We welcome contributions from the community to help improve this project. To contribute:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them.
4. Submit a pull request, detailing your changes and the problem they solve.

Please review our [Contribution Guidelines](CONTRIBUTING.md) for more information.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

If you have any questions or need assistance, feel free to reach out to us on
our [GitHub Issues](https://github.com/jingsewu/SWMS/issues) page.

Thank you for using and contributing to the Smart Warehouse Management System!

## Architecture

![Architecture](./doc/image/architecture.png)
