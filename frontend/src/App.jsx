// App.jsx
import { Route, Routes } from "react-router-dom";
import Home from "./pages/home";
import Login from "./pages/Login";
import CustomerList from "./pages/CustomerList";
import "./App.css";

function App() {
  return (
    <>
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/showlist" element={<CustomerList/>}/>
      </Routes>
    </>
  );
}

export default App;