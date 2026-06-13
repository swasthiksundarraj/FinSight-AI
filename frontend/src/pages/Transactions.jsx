import { useEffect, useState } from 'react'
import api from '../api'

export default function Transactions() {
  const [transactions, setTransactions] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchTransactions = async () => {
      setLoading(true)
      try {
        const res = await api.get('/api/transactions')
        setTransactions(res.data || [])
      } catch (err) {
        setError('Failed to load transactions')
        console.error(err)
      } finally {
        setLoading(false)
      }
    }
    fetchTransactions()
  }, [])

  if (loading) return <div>Loading transactions...</div>
  if (error) return <div className="error-message">{error}</div>

  return (
    <div className="card">
      <div className="card-header">
        <h3>All Transactions</h3>
      </div>
      {transactions.length > 0 ? (
        <table>
          <thead>
            <tr>
              <th>Date</th>
              <th>Description</th>
              <th>Category</th>
              <th>Type</th>
              <th>Amount</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((t) => (
              <tr key={t.id}>
                <td>{new Date(t.transactionDate).toLocaleDateString()}</td>
                <td>{t.description}</td>
                <td>{t.category}</td>
                <td>{t.type}</td>
                <td>₹{t.amount.toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No transactions found.</p>
      )}
    </div>
  )
}