import { useAuth } from '../hooks/useAuth'

export default function Profile() {
  const { user, loading } = useAuth()

  return (
    <div className="card">
      <div className="card-header">
        <h3>User Profile</h3>
      </div>
      {loading ? (
        <p>Loading...</p>
      ) : user ? (
        <div>
          <p style={{ marginBottom: '0.5rem' }}><strong>Name:</strong> {user.name}</p>
          <p style={{ marginBottom: '0.5rem' }}><strong>Email:</strong> {user.email}</p>
          <p><strong>User ID:</strong> {user.id}</p>
        </div>
      ) : (
        <p className="error-message">Failed to load profile</p>
      )}
    </div>
  )
}