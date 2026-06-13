import { useNavigate } from 'react-router-dom'

export default function Topbar() {
  const navigate = useNavigate()

  const handleLogout = () => {
    localStorage.removeItem('token')
    navigate('/login')
  }

  return (
    <header className="topbar">
      <div className="user-profile">
        <div className="user-avatar">
          {/* Placeholder for user initials or image */}
          JD
        </div>
        <button onClick={handleLogout}>Logout</button>
      </div>
    </header>
  )
}