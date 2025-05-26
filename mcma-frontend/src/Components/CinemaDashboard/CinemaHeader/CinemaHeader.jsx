import React from "react";
import { Users, Search, LogOut } from "lucide-react";
import { useNavigate } from "react-router-dom";

const CinemaHeader = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Clear auth data
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    // Redirect to login
    navigate("/login");
  };

  return (
    <div className="top-bar">
      <h1 className="dashboard-title">CINEMA ADMIN</h1>
      <div className="top-bar-right">
        <div className="search-container">
          <input type="text" placeholder="Search..." className="search-input" />
          <Search className="search-icon" />
        </div>
        <div className="profile-icon">
          <Users />
        </div>
        <button onClick={handleLogout} className="logout-button">
          <LogOut size={20} />
          <span>Logout</span>
        </button>
      </div>
    </div>
  );
};

export default CinemaHeader;
