import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/main.css';

const RecentTransactions = ({ transactions = [] }) => {
  if (transactions.length === 0) {
    return (
      <div className="card">
        <div className="card-header">
          <h3>Recent Transactions</h3>
        </div>
        <p className="empty-state">No recent transactions found.</p>
      </div>
    );
  }

  return (
    <div className="card">
      <div className="card-header flex-between">
        <h3>Recent Transactions</h3>
        <Link to="/transactions" className="btn-link">View All</Link>
      </div>
      <div className="transaction-list">
        {transactions.map((tx) => (
          <div key={tx._id || tx.id} className="transaction-item">
            <div className="transaction-info">
              <span className="transaction-desc">{tx.description || tx.category}</span>
              <span className="transaction-date">{new Date(tx.date).toLocaleDateString()}</span>
            </div>
            <span className={`transaction-amount ${tx.type === 'expense' ? 'expense' : 'income'}`}>
              {tx.type === 'expense' ? '-' : '+'}₹{Math.abs(tx.amount).toLocaleString()}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default RecentTransactions;