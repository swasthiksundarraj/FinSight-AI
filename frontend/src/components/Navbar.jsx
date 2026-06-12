function Navbar() {
  return (
    <header className="navbar">
      <div className="navbar__brand">
        <span className="navbar__logo" aria-hidden="true">
          <svg viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
            <rect width="32" height="32" rx="8" fill="currentColor" />
            <path
              d="M8 22V10h3.2v4.8h9.6V10H24v12h-3.2v-4.8H11.2V22H8z"
              fill="#0a2540"
            />
          </svg>
        </span>
        <div className="navbar__title-group">
          <h1 className="navbar__title">FinSight AI</h1>
          <span className="navbar__subtitle">Personal Finance Dashboard</span>
        </div>
      </div>

      <nav className="navbar__nav" aria-label="Main navigation">
        <a href="#dashboard" className="navbar__link navbar__link--active">
          Dashboard
        </a>
        <a href="#transactions" className="navbar__link">
          Transactions
        </a>
        <a href="#insights" className="navbar__link">
          Insights
        </a>
      </nav>

      <div className="navbar__actions">
        <button type="button" className="navbar__icon-btn" aria-label="Notifications">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" />
            <path d="M13.73 21a2 2 0 0 1-3.46 0" />
          </svg>
        </button>
        <div className="navbar__avatar" aria-label="User profile">
          <span>SS</span>
        </div>
      </div>
    </header>
  )
}

export default Navbar
