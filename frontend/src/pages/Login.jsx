import React, { useState } from "react";
import axios from "axios";
import "./Login.css";
import { useNavigate } from "react-router-dom";

const Login = () => {
    //state variables
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emailError, setEmailError] = useState("");
  const navigate = useNavigate();

  const handleEmailChange = (e) => {
    const enteredEmail = e.target.value;
    setEmail(enteredEmail);
    // Validate email format
    const isValidEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(enteredEmail);//regular expression
    setEmailError(isValidEmail ? "" : "Please enter a valid email address");
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (emailError || !email || !password) {
      // Email is not valid or fields are empty, do not submit the form
      return;
    }
    try {
      const response = await axios.post(
        "http://localhost:2000/auth/users/login",
        {
          email: email,
          password: password,
        }
      );
      // Store the token in local storage
      const token = response.data.token;
      localStorage.setItem("token", token);
      console.log("Login successful. Token:", token);
      navigate("/showlist");
      //If the promise is rejected
    } catch (error) {
      console.error("Login error:", error);
    }
  };

  return (
    <div>
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
        <label for="userEmail">Email:</label>
        <input
          type="email"
          id="userEmail"
          name="userEmail"
          placeholder="Enter email"
          value={email}
          onChange={handleEmailChange}
          required
        />
        {emailError && <p style={{ color: "red" }}>{emailError}</p>}

        <label for="userPassword">Password:</label>
        <input
          type="password"
          id="userPassword"
          name="userPassword"
          placeholder="Enter password"
          value={password}
          onChange={handlePasswordChange}
          required
        />

        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;
