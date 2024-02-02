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
  const [isEditing, setIsEditing] = useState(false);
  const [editingUser, setEditingUser] = useState(null);

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
      const token = localStorage.getItem("token");
      if (!token) {
        throw new Error("Authentication token not found.");
      }
  
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
  
      const response = await axios.delete(`http://localhost:2000/users/${userId}`, config);
      console.log("User successfully deleted:", response.data); // Or use any other notification mechanism
      
      // Optionally, refresh the customer list to reflect the deletion
      const updatedCustomers = customers.filter(customer => customer.userId !== userId);
      setCustomers(updatedCustomers);
  
    } catch (err) {
      console.error("Error deleting user:", err);
    }
  };

  const showEditForm = (user) => {
    setEditingUser(user);
    setIsEditing(true);
  };

  const handleEditInputChange = (event) => {
    setEditingUser({ ...editingUser, [event.target.name]: event.target.value });
  };
  
  const handleUpdate = async (event, userId) => {
    event.preventDefault();
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
  
      await axios.put(`http://localhost:2000/users/${userId}`, editingUser, config);
      console.log("User successfully updated");
  
      // Update the local customers state to reflect changes
      const updatedCustomers = customers.map((customer) =>
        customer.userId === userId ? { ...customer, ...editingUser } : customer
      );
      setCustomers(updatedCustomers);
  
      setIsEditing(false); // Close the edit form
    } catch (err) {
      console.error("Error updating user:", err);
    }
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
                {/* <button onClick={() => handleEdit(customer.userId)}>
                  Edit
                </button> */}
                <button onClick={() => handleDelete(customer.userId)}>
                  Delete
                </button>
                <button onClick={() => showEditForm(customer)}>Edit</button>
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


      {isEditing && (
  <div className="editForm">
    <h2>Edit User</h2>
    <form onSubmit={(e) => handleUpdate(e, editingUser.userId)}>
      <input name="first_name" value={editingUser.first_name} onChange={handleEditInputChange} />
      <input name="last_name" value={editingUser.last_name} onChange={handleEditInputChange} />
      <input name="street" value={editingUser.street} onChange={handleEditInputChange} />
      <input name="address" value={editingUser.address} onChange={handleEditInputChange} />
      <input name="city" value={editingUser.city} onChange={handleEditInputChange} />
      <input name="state" value={editingUser.state} onChange={handleEditInputChange} />
      <input name="email" value={editingUser.email} onChange={handleEditInputChange} />
      <input name="phone" value={editingUser.phone} onChange={handleEditInputChange} />
      <button type="submit">Save Changes</button>
      <button onClick={() => setIsEditing(false)}>Cancel</button>
    </form>
  </div>
)}
    </div>
  );
};

export default CustomerList;
