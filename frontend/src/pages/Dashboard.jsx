import { useEffect, useState } from 'react';
import api from '../api';
import StatCard from '../components/StatCard';
import RecentTransactions from '../components/RecentTransactions';

const useDashboardData = () => {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchAllData = async () => {
      setLoading(true);
      try {
        const [
          incomeRes,
          expenseRes,
          savingsRes,
          healthRes,
          transactionsRes,
        ] = await Promise.all([
          api.get('/api/dashboard/total-income'),
          api.get('/api/dashboard/total-expense'),
          api.get('/api/dashboard/savings'),
          api.get('/api/health-score'),
          api.get('/api/transactions?limit=5'),
        ]);

        setStats({
          totalIncome: incomeRes.data.totalIncome || 0,
          totalExpense: expenseRes.data.totalExpense || 0,
          savings: savingsRes.data.savings || 0,
          healthScore: healthRes.data.healthScore || 0,
          recentTransactions: transactionsRes.data || [],
        });
      } catch (err) {
        console.error("Dashboard fetch error:", err);
        setError('Failed to load dashboard data. The server might be offline or you may not be authenticated.');
      } finally {
        setLoading(false);
      }
    };

    fetchAllData();
  }, []);

  return { stats, loading, error };
};

export default function Dashboard() {
  const { stats, loading, error } = useDashboardData();

  if (loading) {
    return (
      <div className="page-content">
        <div className="loading-indicator">Loading your financial dashboard...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="page-content">
        <div className="error-message">{error}</div>
      </div>
    );
  }

  return (
    <>
      <div className="dashboard-header">
        <h2>Welcome Back!</h2>
        <p>Here's a summary of your financial health.</p>
      </div>
      
      {stats && (
        <>
          <div className="stat-cards-grid">
            <StatCard 
              icon="💰" 
              label="Total Income" 
              value={`₹${stats.totalIncome.toLocaleString()}`} 
              color="green" 
            />
            <StatCard 
              icon="📉" 
              label="Total Expenses" 
              value={`₹${stats.totalExpense.toLocaleString()}`} 
              color="red" 
            />
            <StatCard 
              icon="🏦" 
              label="Total Savings" 
              value={`₹${stats.savings.toLocaleString()}`} 
              color="blue" 
            />
            <StatCard 
              icon="❤️‍🩹" 
              label="Financial Health" 
              value={`${stats.healthScore} / 100`} 
              color="purple" 
            />
          </div>

          <div className="dashboard-columns">
            <div className="column">
              <RecentTransactions transactions={stats.recentTransactions} />
            </div>
            <div className="column">
              {/* Placeholder for future charts or insights component */}
              <div className="card">
                <div className="card-header">
                  <h3>AI Insights</h3>
                </div>
                <p>Your AI advisor will provide recommendations here based on your spending patterns.</p>
              </div>
            </div>
          </div>
        </>
      )}
    </>
  );
}