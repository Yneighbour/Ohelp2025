export function pad2(n) {
  return String(n).padStart(2, '0');
}

export function formatDateYMD(date) {
  if (!date) return '';
  // date can be 'YYYY-MM-DD' or Date
  if (typeof date === 'string') return date;
  return `${date.getFullYear()}-${pad2(date.getMonth() + 1)}-${pad2(date.getDate())}`;
}

export function formatTimeHM(value) {
  if (!value) return '';
  try {
    const d = typeof value === 'string' ? new Date(value) : value;
    if (Number.isNaN(d.getTime())) return '';
    return `${pad2(d.getHours())}:${pad2(d.getMinutes())}`;
  } catch {
    return '';
  }
}

export function safeNumber(value) {
  const n = Number(value);
  return Number.isFinite(n) ? n : null;
}
