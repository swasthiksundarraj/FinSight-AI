import Navbar from './components/Navbar'
import StatCard from './components/StatCard'
import RecentTransactions from './components/RecentTransactions'
import { dashboardStats, recentTransactions } from './data/mockData'
import './App.css'

const currency = (value) =>
  value.toLocaleString('en-US', { style: 'currency', currency: 'USD' })

function App() {
  const { totalIncome, totalExpenses, totalSavings, financialHealthScore } =
    dashboardStats

  return (
    <div className="app">
      <Navbar />

      <main className="dashboard" id="dashboard">
        <div className="dashboard__welcome">
          <div>
            <h2 className="dashboard__heading">Good morning, Swasthik</h2>
            <p className="dashboard__subheading">
              Here&apos;s an overview of your finances for June 2026
            </p>
          </div>
          <button type="button" className="dashboard__cta">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" aria-hidden="true">
              <path d="M12 5v14M5 12h14" />
            </svg>
            Add Transaction
          </button>
        </div>

        <div className="dashboard__stats">
          <StatCard
            title="Total Income"
            value={currency(totalIncome)}
            change={dashboardStats.incomeChange}
            variant="income"
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6" />
              </svg>
            }
          />
          <StatCard
            title="Total Expenses"
            value={currency(totalExpenses)}
            change={dashboardStats.expensesChange}
            variant="expenses"
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <path d="M17 9V7a2 2 0 0 0-2-2H5a2 2 0 0 0-2 2v6a2 2 0 0 0 2 2h2" />
                <rect x="9" y="9" width="13" height="11" rx="2" />
              </svg>
            }
          />
          <StatCard
            title="Total Savings"
            value={currency(totalSavings)}
            change={dashboardStats.savingsChange}
            variant="savings"
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <path d="M19 5c-1.5 0-2.8 1.4-3 2-3.5-1.5-11-.3-11 5 0 1.8 0 3 2 4.5V20h4v-2h3v2h4v-4c1-.5 1.7-1 2-2h2v-4h-2c0-1-.5-1.5-1-2" />
                <path d="M2 9v1c0 1.1.9 2 2 2h1" />
              </svg>
            }
          />
          <StatCard
            title="Financial Health Score"
            value={`${financialHealthScore}/100`}
            change={dashboardStats.healthChange}
            variant="health"
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <path d="M22 12h-4l-3 9L9 3l-3 9H2" />
              </svg>
            }
          />
        </div>

        <RecentTransactions transactions={recentTransactions} />
      </main>
    </div>
  )
}

export default App
