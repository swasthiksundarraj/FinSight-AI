function StatCard({ title, value, change, variant, icon }) {
  const isPositive = change >= 0
  const changeLabel = `${isPositive ? '+' : ''}${change}% vs last month`

  return (
    <article className={`stat-card stat-card--${variant}`}>
      <div className="stat-card__header">
        <span className="stat-card__icon" aria-hidden="true">
          {icon}
        </span>
        <h3 className="stat-card__title">{title}</h3>
      </div>
      <p className="stat-card__value">{value}</p>
      <p className={`stat-card__change ${isPositive ? 'stat-card__change--up' : 'stat-card__change--down'}`}>
        <span className="stat-card__change-arrow" aria-hidden="true">
          {isPositive ? '↑' : '↓'}
        </span>
        {changeLabel}
      </p>
    </article>
  )
}

export default StatCard
