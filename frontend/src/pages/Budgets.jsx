import { useEffect, useState } from 'react'
import api from '../api'

export default function Budgets() {
  const [budgets, setBudgets] = useState([])
  const [status, setStatus] = useState([])
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    const fetchBudgets = async () => {
      try {
        const res = await api.get('/api/budgets')
        setBudgets(res.data)
      } catch (err) {
        console.error(err)
      }
    }
    fetchBudgets()
  }, [])

  useEffect(() => {
    const loadStatus = async () => {
      setLoading(true)
      try {
        const res = await api.get('/api/budgets/status')
        setStatus(res.data)
      } catch (err) {
        console.error(err)
      }
      setLoading(false)
    }

    loadStatus()
  }, [])

  return (
    <div className="card">
      <div className="card-header">
        <h3>Budget Limits</h3>
      </div>
      {budgets.length > 0 ? (
        <ul>
          {budgets.map((b) => (
            <li key={b.id}>{b.category}: ₹{b.monthlyLimit.toLocaleString()}</li>
          ))}
        </ul>
      ) : (
        <p>No budgets configured.</p>
      )}

      <div className="card-header" style={{ marginTop: '2rem' }}>
        <h3>Budget Status</h3>
      </div>
      {loading ? (
        <p>Loading budget status...</p>
      ) : status.length > 0 ? (
        <ul>
          {status.map((s, idx) => (
            <li key={idx}>
              <strong>{s.category}</strong> - Spent: ₹{s.spent.toLocaleString()} / Limit: ₹{s.limit.toLocaleString()} (Remaining: ₹{s.remaining.toLocaleString()})
            </li>
          ))}
        </ul>
      ) : (
        <p>No budget status available.</p>
      )}
    </div>
  )
}