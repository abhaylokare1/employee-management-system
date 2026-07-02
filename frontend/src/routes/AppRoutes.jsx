import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "../pages/Home";
import Employees from "../pages/Employees";
import AddEmployee from "../pages/AddEmployee";
import EditEmployee from "../pages/EditEmployee";
import Login from "../pages/Login";
import NotFound from "../pages/NotFound";

const AppRoutes = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<Home />} />
        <Route path="/employees" element={<Employees />} />
        <Route path="/add-employee" element={<AddEmployee />} />
        <Route path="/edit-employee/:id" element={<EditEmployee />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRoutes;