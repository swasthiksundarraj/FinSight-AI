import { useEffect, useState } from 'react'
import api from '../api'

export default function Insights() {
  const [insights, setInsights] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetch = async () => {
      setLoading(true)
      try {
        const res = await api.get('/api/insights')
        setInsights(res.data.insights || [])
      } catch (err) {
        setError('Failed to load insights')
        console.error(err)
      }
      setLoading(false)
    }
    fetch()
  }, [])

  return (
    <div className="insights-page">
      <h2>AI Insights</h2>
      {loading ? (
        <div className="loading">Loading insights...</div>
      ) : error ? (
        <div className="error">{error}</div>
      ) : insights.length > 0 ? (
        <ul>
        {insights.map((s, idx) => (
          <li key={idx}>{s}</li>
        ))}
      </ul>
      ) : (
        <div className="info">No insights available yet</div>
      )}
    </div>
  )
}