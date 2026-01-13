import { safeNumber } from './datetime';

export function parseBloodPressure(value) {
  if (!value || typeof value !== 'string') return { sys: null, dia: null };
  const [sysRaw, diaRaw] = value.split('/');
  return {
    sys: safeNumber(sysRaw),
    dia: safeNumber(diaRaw),
  };
}

export function statusForBloodPressure(bp) {
  const { sys, dia } = parseBloodPressure(bp);
  if (sys == null || dia == null) return { status: 'warning', statusText: '未知' };

  const danger = sys >= 160 || dia >= 100 || sys <= 80 || dia <= 50;
  if (danger) return { status: 'danger', statusText: '异常' };

  const normal = sys >= 90 && sys <= 140 && dia >= 60 && dia <= 90;
  return normal
    ? { status: 'normal', statusText: '正常' }
    : { status: 'warning', statusText: '偏高/偏低' };
}

export function statusForHeartRate(hr) {
  const n = safeNumber(hr);
  if (n == null) return { status: 'warning', statusText: '未知' };
  if (n < 50 || n > 110) return { status: 'danger', statusText: '异常' };
  if (n < 60 || n > 100) return { status: 'warning', statusText: '偏高/偏低' };
  return { status: 'normal', statusText: '正常' };
}

export function statusForTemperature(t) {
  const n = safeNumber(t);
  if (n == null) return { status: 'warning', statusText: '未知' };
  if (n < 35.5 || n >= 38) return { status: 'danger', statusText: '异常' };
  if (n >= 37.3) return { status: 'warning', statusText: '偏高' };
  return { status: 'normal', statusText: '正常' };
}

export function statusForGlucose(g) {
  const n = safeNumber(g);
  if (n == null) return { status: 'warning', statusText: '未知' };
  if (n < 3.5 || n >= 7.8) return { status: 'danger', statusText: '异常' };
  if (n >= 6.2) return { status: 'warning', statusText: '偏高' };
  return { status: 'normal', statusText: '正常' };
}

export function buildDerivedAlertsFromLatestRecord(record) {
  if (!record) return [];

  const alerts = [];

  const bp = statusForBloodPressure(record.bloodPressure);
  if (bp.status !== 'normal') {
    alerts.push({
      key: 'bp',
      level: bp.status === 'danger' ? 'urgent' : 'important',
      levelText: bp.status === 'danger' ? '紧急' : '重要',
      title: '血压异常预警',
      time: '刚刚',
      summary: `当前血压 ${record.bloodPressure || '--'} mmHg，建议关注。`,
      content: '检测到血压指标异常波动，请结合近期饮食/情绪/用药情况综合判断。',
      advice: '建议静坐休息 5-10 分钟后复测；若持续异常请联系医生。',
    });
  }

  const hr = statusForHeartRate(record.heartRate);
  if (hr.status !== 'normal') {
    alerts.push({
      key: 'hr',
      level: hr.status === 'danger' ? 'urgent' : 'important',
      levelText: hr.status === 'danger' ? '紧急' : '重要',
      title: '心率异常预警',
      time: '刚刚',
      summary: `当前心率 ${record.heartRate ?? '--'} 次/分，建议关注。`,
      content: '检测到心率指标异常波动，请避免剧烈运动并观察是否伴随胸闷/头晕。',
      advice: '建议保持安静休息并复测；如有不适请及时就医。',
    });
  }

  const g = statusForGlucose(record.glucoseLevel);
  if (g.status !== 'normal') {
    alerts.push({
      key: 'glu',
      level: g.status === 'danger' ? 'urgent' : 'important',
      levelText: g.status === 'danger' ? '紧急' : '重要',
      title: '血糖异常预警',
      time: '刚刚',
      summary: `当前血糖 ${record.glucoseLevel ?? '--'} mmol/L，建议关注。`,
      content: '检测到血糖指标异常，建议结合饮食/用药/运动情况综合判断。',
      advice: '建议按医嘱调整饮食与用药，并按时复测。',
    });
  }

  const temp = statusForTemperature(record.temperature);
  if (temp.status !== 'normal') {
    alerts.push({
      key: 'temp',
      level: temp.status === 'danger' ? 'urgent' : 'important',
      levelText: temp.status === 'danger' ? '紧急' : '重要',
      title: '体温异常预警',
      time: '刚刚',
      summary: `当前体温 ${record.temperature ?? '--'} ℃，建议关注。`,
      content: '检测到体温异常，注意观察是否伴随咳嗽/乏力等症状。',
      advice: '建议补充水分并复测；如持续发热请就医。',
    });
  }

  return alerts;
}
