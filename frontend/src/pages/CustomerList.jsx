import React, { useEffect, useState } from "react";
import axios from "axios";
import "./CustomerList.css";

const CustomerList = () => {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [newUser, setNewUser] = useState({
    first_name: "",
    last_name: "",
    password: "",
    street: "",
    address: "",
    city: "",
    state: "",
    email: "",
    phone: "",
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) {
          throw new Error("Authentication token not found.");
        }

        const config = {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        };

        const response = await axios.get("http://localhost:2000", config);
        setCustomers(response.data);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);
  if (loading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p>Error loading customers: {error.message}</p>;
  }

  const handleInputChange = (event) => {
    setNewUser({ ...newUser, [event.target.name]: event.target.value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      await axios.post("http://localhost:2000/auth/users/register", newUser);
      // Refresh the customer list or other post submission actions
    } catch (err) {
      console.error("Error submitting new user:", err);
    }
  };

  const handleDelete = async (userId) => {
    try {
      await axios.delete(`http://localhost:2000/users/${userId}`);
    } catch (err) {
      console.error("Error deleting user:", err);
    }
  };

  const handleUpdate = async (userId) => {
    // You can navigate to an update page or open a modal for editing
    console.log("Update user with ID:", userId);
  };

  return (
    <div className="Display">
      <h1>Customer List</h1>
      <table>
        <thead>
          <tr>
            <th>User ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Street</th>
            <th>Address</th>
            <th>City</th>
            <th>State</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {customers.map((customer) => (
            <tr key={customer.userId}>
              <td>{customer.userId}</td>
              <td>{customer.first_name}</td>
              <td>{customer.last_name}</td>
              <td>{customer.street}</td>
              <td>{customer.address}</td>
              <td>{customer.city}</td>
              <td>{customer.state}</td>
              <td>{customer.email}</td>
              <td>{customer.phone}</td>
              <td>
                <button onClick={() => handleEdit(customer.userId)}>
                  Edit
                </button>
                <button onClick={() => handleDelete(customer.userId)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <form onSubmit={handleSubmit} className="NewUserForm">
        <h2>Add New User</h2>
        <div>
          <input
            name="first_name"
            value={newUser.first_name}
            onChange={handleInputChange}
            placeholder="First Name"
          />
          <input
            name="last_name"
            value={newUser.last_name}
            onChange={handleInputChange}
            placeholder="Last Name"
          />
          <input
            name="password"
            value={newUser.password}
            onChange={handleInputChange}
            placeholder="Password"
          />
          <input
            name="street"
            value={newUser.street}
            onChange={handleInputChange}
            placeholder="Street"
          />
          <input
            name="address"
            value={newUser.address}
            onChange={handleInputChange}
            placeholder="Address"
          />
          <input
            name="city"
            value={newUser.city}
            onChange={handleInputChange}
            placeholder="City"
          />
          <input
            name="state"
            value={newUser.state}
            onChange={handleInputChange}
            placeholder="State"
          />
          <input
            name="email"
            value={newUser.email}
            onChange={handleInputChange}
            placeholder="Email"
          />
          <input
            name="phone"
            value={newUser.phone}
            onChange={handleInputChange}
            placeholder="Phone Number"
          />
          <input type="submit" value="Submit" />
        </div>
      </form>
    </div>
  );
};

export default CustomerList;
