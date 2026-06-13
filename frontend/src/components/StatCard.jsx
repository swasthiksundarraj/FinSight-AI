import React from 'react';
import '../styles/main.css';

const StatCard = ({ icon, label, value, color }) => {
  return (
    <div className={`stat-card color-${color}`}>
      <div className="stat-icon">
        {icon}
      </div>
      <div className="stat-info">
        <span className="stat-label">{label}</span>
        <span className="stat-value">{value}</span>
      </div>
    </div>
  );
};

export default StatCard;